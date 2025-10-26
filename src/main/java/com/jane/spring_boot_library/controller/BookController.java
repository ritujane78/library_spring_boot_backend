package com.jane.spring_boot_library.controller;

import com.jane.spring_boot_library.entity.Book;
import com.jane.spring_boot_library.responsemodels.ShelfCurrentLoansResponse;
import com.jane.spring_boot_library.service.BookService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.GeneratedValue;

import java.util.List;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/secure/currentloans")
    public List<ShelfCurrentLoansResponse> currentLoans(@RequestParam String userEmail)
            throws Exception
    {
//        String userEmail = jwt.getClaim("email");
//        String userEmail = "ritujane78@gmail.com";
        return bookService.currentLoans(userEmail);
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestParam String userEmail,
                             @RequestParam Long bookId) throws Exception{

//        String userEmail="ritujane78@gmail.com";
        return bookService.checkoutBook(userEmail, bookId);

    }

    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutBookByUser(@RequestParam String userEmail,
                                      @RequestParam Long bookId) {
//        String userEmail = "ritujane78@gmail.com";
//        System.out.println(userEmail);
//        System.out.println(jwt.getTokenValue());
        return bookService.checkOutBookByUser(userEmail, bookId);
    }


    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestParam String userEmail){
//        String userEmail="";
//        if(jwt.getTokenValue()!=null){
//            userEmail = "ritujane78@gmail.com";
//        }
        return bookService.loansCount(userEmail);
    }

    @PutMapping("/secure/return")
    public void returnBook(@RequestParam Long bookId, @RequestParam String userEmail) throws Exception{
//        String userEmail = "ritujane78@gmail.com";

        bookService.returnBook(userEmail,bookId);
    }

    @PutMapping("/secure/renew/loan")
    public void renewLoan(@RequestParam Long bookId, @RequestParam String userEmail) throws Exception {
//        String userEmail = jwt.getClaim("email");
//        String userEmail = "ritujane78@gmail.com";
        bookService.renewLoan(userEmail, bookId);
    }
}
