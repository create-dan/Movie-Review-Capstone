package ideas.movieReview.mr_data.MovieReview.Service;

import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;
import ideas.movieReview.mr_data.MovieReview.Entity.Movie;
import ideas.movieReview.mr_data.MovieReview.Entity.Review;
import ideas.movieReview.mr_data.MovieReview.Exception.ReviewExceptions.ReviewNotFoundException;
import ideas.movieReview.mr_data.MovieReview.Repositories.ReviewRepository;
import ideas.movieReview.mr_data.MovieReview.dto.ReviewDTOS.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public String deleteReview(int reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new ReviewNotFoundException("Review with ID " + reviewId + " not found.");
        }
        reviewRepository.deleteById(reviewId);
        return "Review deleted with ID " + reviewId;
    }

    // Get all reviews for a specific movie
    public List<ReviewDTO> getReviewsByMovie(int movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    // Get all reviews by a specific user
    public List<ReviewDTO> getReviewsByUser(ApplicationUser user) {
        return reviewRepository.findByUser(user);
    }

    // Get the average rating of a specific movie
    public Double getAverageRatingByMovie(int movieId) {
        return reviewRepository.findAverageRatingByMovieId(movieId);
    }

    // Get the total number of reviews for a specific movie
    public int getTotalReviewsByMovie(int movieId) {
        return reviewRepository.countReviewsByMovieId(movieId);
    }

    public int getTotalReviews() {
        return (int) reviewRepository.count();
    }
}
