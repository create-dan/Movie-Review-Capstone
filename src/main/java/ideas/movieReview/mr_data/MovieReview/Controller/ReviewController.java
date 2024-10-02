package ideas.movieReview.mr_data.MovieReview.Controller;


import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;
import ideas.movieReview.mr_data.MovieReview.Entity.Movie;
import ideas.movieReview.mr_data.MovieReview.Entity.Review;
import ideas.movieReview.mr_data.MovieReview.Service.ReviewService;
import ideas.movieReview.mr_data.MovieReview.dto.ReviewDTOS.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @PostMapping("/add")
    public ResponseEntity<Review> saveReview(@RequestBody Review review) {
        Review savedReview = reviewService.saveReview(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }


    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable int reviewId) {
        String result = reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/movie")
    public ResponseEntity<List<ReviewDTO>> getReviewsByMovie(@RequestParam int movieId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByMovie(movieId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }


    @GetMapping("/user")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUser(@RequestBody ApplicationUser user) {
        List<ReviewDTO> reviews = reviewService.getReviewsByUser(user);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }


    @GetMapping("/movie/{movieId}/avg")
    public ResponseEntity<Double> getAverageRatingByMovie(@PathVariable int movieId) {
        Double averageRating = reviewService.getAverageRatingByMovie(movieId);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }


    @GetMapping("/movie/totalreviews/{movieId}")
    public ResponseEntity<Integer> getTotalReviewsByMovie(@PathVariable int movieId) {
        int totalReviews = reviewService.getTotalReviewsByMovie(movieId);
        return new ResponseEntity<>(totalReviews, HttpStatus.OK);
    }


    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalUsers() {
        int totalUsers = reviewService.getTotalUsers();
        return new ResponseEntity<>(totalUsers, HttpStatus.OK);
    }

}

