package ideas.movieReview.mr_data.MovieReview.Controller;


import ideas.movieReview.mr_data.MovieReview.Entity.Review;
import ideas.movieReview.mr_data.MovieReview.Service.ReviewService;
import ideas.movieReview.mr_data.MovieReview.dto.ReviewDTOS.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @PostMapping
    public Review saveReview(@RequestBody Review review) {
        return reviewService.saveReview(review);
    }

    // Delete a review by its ID
    @DeleteMapping("/{reviewId}")
    public String deleteReview(@PathVariable int reviewId) {
        return reviewService.deleteReview(reviewId);
    }


    @GetMapping("/movie/{movieId}")
    public List<ReviewDTO> getReviewsByMovie(@PathVariable int movieId) {
        return reviewService.getReviewsByMovie(movieId);
    }

    // Get all reviews by a specific user
    @GetMapping("/user/{userId}")
    public List<ReviewDTO> getReviewsByUser(@PathVariable int userId) {
        return reviewService.getReviewsByUser(userId);
    }

    @GetMapping("/movie/{movieId}/avg")
    public Double getAverageRatingByMovie(@PathVariable int movieId) {
        return reviewService.getAverageRatingByMovie(movieId);
    }

    @GetMapping("/movie/{movieId}/totalreviews")
    public int getTotalReviewsByMovie(@PathVariable int movieId) {
        return reviewService.getTotalReviewsByMovie(movieId);
    }
}
