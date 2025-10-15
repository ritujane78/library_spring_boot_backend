package com.jane.spring_boot_library.controller;

import com.jane.spring_boot_library.entity.Book;
import com.jane.spring_boot_library.service.BookService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.GeneratedValue;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@AuthenticationPrincipal Jwt jwt,
                             @RequestParam Long bookId) throws Exception{

        String userEmail="ritujane78@gmail.com";
        return bookService.checkoutBook(userEmail, bookId);

    }

    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutBookByUser(@AuthenticationPrincipal Jwt jwt,
                                      @RequestParam Long bookId) {
        String userEmail = "ritujane78@gmail.com";
//        System.out.println(userEmail);
//        System.out.println(jwt.getTokenValue());
        return bookService.checkOutBookByUser(userEmail, bookId);
    }


    @GetMapping("secure/currentloans/count")
    public int currentLoansCount(@AuthenticationPrincipal Jwt jwt){
        String userEmail="";
        if(jwt.getTokenValue()!=null){
            userEmail = "ritujane78@gmail.com";
        }
        return bookService.loansCount(userEmail);
    }
}
