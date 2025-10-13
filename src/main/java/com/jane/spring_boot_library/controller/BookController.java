package com.jane.spring_boot_library.controller;

import com.jane.spring_boot_library.entity.Book;
import com.jane.spring_boot_library.service.BookService;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestParam Long bookId) throws Exception{

        String userEmail="test@123.com";
        return bookService.checkoutBook(userEmail, bookId);

    }

    @GetMapping("/secure/ischeckedout/byuser")
    public boolean isCheckedOut(@RequestParam Long bookId){
        String userEmail = "test@123.com";
        return bookService.checkOutBookByUser(userEmail, bookId);
    }

    @GetMapping("secure/currentloans/count")
    public int currentLoansCount(){
        String userEmail = "test@123.com";
        return bookService.loansCount(userEmail);
    }
}
