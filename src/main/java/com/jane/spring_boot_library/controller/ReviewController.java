package com.jane.spring_boot_library.controller;


import com.jane.spring_boot_library.requestmodels.ReviewRequest;
import com.jane.spring_boot_library.service.ReviewService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/secure/user/book")
    public Boolean reviewBookByUser(@RequestParam String userEmail, @RequestParam Long bookId) throws Exception {
//        String userEmail = jwt.getClaim("email");
//        String userEmail = "ritujane78@gmail.com";

        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        return reviewService.userReviewListed(userEmail, bookId);
    }

    @PostMapping("/secure")
    public void postReview(@AuthenticationPrincipal Jwt jwt,
                           @RequestBody ReviewRequest reviewRequest) throws Exception{
//        String userEmail = jwt.getClaim("email");
        String userEmail= "ritujane78@gmail.com";

        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        reviewService.postReview(userEmail, reviewRequest);

    }
}
