package com.jane.spring_boot_library.service;

import com.jane.spring_boot_library.dao.BookRepository;
import com.jane.spring_boot_library.dao.CheckoutRepository;
import com.jane.spring_boot_library.entity.Book;
import com.jane.spring_boot_library.entity.Checkout;
import com.jane.spring_boot_library.responsemodels.ShelfCurrentLoansResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class BookService {
    private BookRepository bookRepository;

    private CheckoutRepository checkoutRepository;

    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public Book checkoutBook(String userEmail, Long bookId) throws Exception{
        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if(!book.isPresent() || validateCheckout !=null || book.get().getCopiesAvailable() <=0){
            throw new Exception("Book doesn't exist or already checked out by the user.");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);

        bookRepository.save(book.get());

        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                bookId
        );
        checkoutRepository.save(checkout);

        return book.get();


    }
    public boolean checkOutBookByUser(String userEmail, Long bookId){
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        if(validateCheckout !=null){
            return true;
        }
        return false;
    }

    public int loansCount(String userEmail){
        return checkoutRepository.findByUserEmail(userEmail).size();
    }

    public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception {

        List<ShelfCurrentLoansResponse> shelfCurrentLoansResponses = new ArrayList<>();

        // Get all checkouts for the user
        List<Checkout> checkoutList = checkoutRepository.findByUserEmail(userEmail);

        // Collect all book IDs
        List<Long> bookIdList = new ArrayList<>();
        for (Checkout i : checkoutList) {
            bookIdList.add(i.getBookId());
        }

        // Get all books by IDs
        List<Book> books = bookRepository.findBooksByBookIds(bookIdList);

        for (Book book : books) {
            Optional<Checkout> checkoutOpt = checkoutList.stream()
                    .filter(x -> x.getBookId().equals(book.getId()))
                    .findFirst();

            if (checkoutOpt.isPresent()) {
                Checkout checkout = checkoutOpt.get();

                LocalDate returnDate = LocalDate.parse(checkout.getReturnDate());
                LocalDate today = LocalDate.now();

                // Calculate difference in days
//                long differenceInDays = ChronoUnit.DAYS.between(today, returnDate);
                long differenceInDays = java.time.Period.between(today, returnDate).getDays();

                shelfCurrentLoansResponses.add(
                        new ShelfCurrentLoansResponse(book, (int) differenceInDays)
                );
            }
        }

        return shelfCurrentLoansResponses;
    }

    public void returnBook(String userEmail, Long bookId) throws Exception{
        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if(!book.isPresent() || validateCheckout == null){
            throw new Exception("Book doesn't exist or not checked out by user");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);

        bookRepository.save(book.get());
        checkoutRepository.deleteById(validateCheckout.getId());
    }
    public void renewLoan(String userEmail, Long bookId) throws Exception {

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out by user");
        }

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = sdFormat.parse(validateCheckout.getReturnDate());
        Date d2 = sdFormat.parse(LocalDate.now().toString());

        if (d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0) {
            validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
            checkoutRepository.save(validateCheckout);
        }
    }
}
