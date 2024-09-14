package ideas.movieReview.mr_data.ControllerTest;

import ideas.movieReview.mr_data.MovieReview.Controller.ReviewController;
import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;
import ideas.movieReview.mr_data.MovieReview.Entity.Movie;
import ideas.movieReview.mr_data.MovieReview.Entity.Review;
import ideas.movieReview.mr_data.MovieReview.Service.ReviewService;
import ideas.movieReview.mr_data.MovieReview.dto.ReviewDTOS.ReviewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReviewControllerTest {
    @Mock
    ReviewService reviewService;

    @InjectMocks
    ReviewController reviewController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //Save Review
    @Test
    public void testSaveReview() {
        Review review = new Review();
        review.setReviewId(1);
        review.setRating(4);
        review.setDescription("Nice");

        when(reviewService.saveReview(review)).thenReturn(review);

        Review result = reviewController.saveReview(review);

        assertEquals(1, result.getReviewId());
        assertEquals("Nice", result.getDescription());
        verify(reviewService, times(1)).saveReview(review);

    }

    // Test deleteReview
    @Test
    public void testDeleteReview() {
        int reviewId = 1;

        when(reviewService.deleteReview(reviewId)).thenReturn("Review deleted successfully");

        String result = reviewController.deleteReview(reviewId);

        assertEquals("Review deleted successfully", result);
        verify(reviewService, times(1)).deleteReview(reviewId);
    }

    // Test getReviewsByMovie
    @Test
    public void testGetReviewsByMovie() {
        Movie movie = new Movie();
        movie.setMovieId(1);

        List<ReviewDTO> mockReviews = new ArrayList<>();
        ReviewDTO reviewDTO = mock(ReviewDTO.class);
        when(reviewDTO.getDescription()).thenReturn("Amazing movie!");
        mockReviews.add(reviewDTO);

        when(reviewService.getReviewsByMovie(movie.getMovieId())).thenReturn(mockReviews);

        List<ReviewDTO> result = reviewController.getReviewsByMovie(movie.getMovieId());

        assertEquals(1, result.size());
        assertEquals("Amazing movie!", result.get(0).getDescription());
        verify(reviewService, times(1)).getReviewsByMovie(movie.getMovieId());
    }

    // Test getReviewsByUser
    @Test
    public void testGetReviewsByUser() {
        ApplicationUser user = new ApplicationUser();
        user.setUserId(1);

        List<ReviewDTO> mockReviews = new ArrayList<>();
        ReviewDTO reviewDTO = mock(ReviewDTO.class);
        when(reviewDTO.getDescription()).thenReturn("Excellent review");
        mockReviews.add(reviewDTO);

        when(reviewService.getReviewsByUser(user)).thenReturn(mockReviews);

        List<ReviewDTO> result = reviewController.getReviewsByUser(user);

        assertEquals(1, result.size());
        assertEquals("Excellent review", result.get(0).getDescription());
        verify(reviewService, times(1)).getReviewsByUser(user);
    }

    // Test getAverageRatingByMovie
    @Test
    public void testGetAverageRatingByMovie() {
        int movieId = 1;

        when(reviewService.getAverageRatingByMovie(movieId)).thenReturn(4.5);

        Double result = reviewController.getAverageRatingByMovie(movieId);

        assertEquals(4.5, result);
        verify(reviewService, times(1)).getAverageRatingByMovie(movieId);
    }


    // Test getTotalReviewsByMovie
    @Test
    public void testGetTotalReviewsByMovie() {
        int movieId = 1;

        when(reviewService.getTotalReviewsByMovie(movieId)).thenReturn(10);

        int result = reviewController.getTotalReviewsByMovie(movieId);

        assertEquals(10, result);
        verify(reviewService, times(1)).getTotalReviewsByMovie(movieId);
    }


}
