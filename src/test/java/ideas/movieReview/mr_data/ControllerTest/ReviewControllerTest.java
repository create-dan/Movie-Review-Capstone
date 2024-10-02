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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public class ReviewControllerTest {

    @Mock
    ReviewService reviewService;

    @InjectMocks
    ReviewController reviewController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Save Review
    @Test
    public void testSaveReview() {
        Review review = new Review();
        review.setReviewId(1);
        review.setRating(4);
        review.setDescription("Nice");

        when(reviewService.saveReview(review)).thenReturn(review);

        ResponseEntity<Review> response = reviewController.saveReview(review);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, response.getBody().getReviewId());
        assertEquals("Nice", response.getBody().getDescription());
        verify(reviewService, times(1)).saveReview(review);
    }

    // Test deleteReview
    @Test
    public void testDeleteReview() {
        int reviewId = 1;

        when(reviewService.deleteReview(reviewId)).thenReturn("Review deleted successfully");

        ResponseEntity<String> response = reviewController.deleteReview(reviewId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Review deleted successfully", response.getBody());
        verify(reviewService, times(1)).deleteReview(reviewId);
    }

    // Test getReviewsByMovie
    @Test
    public void testGetReviewsByMovie() {
        int movieId = 1;

        List<ReviewDTO> mockReviews = new ArrayList<>();
        ReviewDTO reviewDTO = mock(ReviewDTO.class);
        when(reviewDTO.getDescription()).thenReturn("Amazing movie!");
        mockReviews.add(reviewDTO);

        when(reviewService.getReviewsByMovie(movieId)).thenReturn(mockReviews);

        ResponseEntity<List<ReviewDTO>> response = reviewController.getReviewsByMovie(movieId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Amazing movie!", response.getBody().get(0).getDescription());
        verify(reviewService, times(1)).getReviewsByMovie(movieId);
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

        ResponseEntity<List<ReviewDTO>> response = reviewController.getReviewsByUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Excellent review", response.getBody().get(0).getDescription());
        verify(reviewService, times(1)).getReviewsByUser(user);
    }

    // Test getAverageRatingByMovie
    @Test
    public void testGetAverageRatingByMovie() {
        int movieId = 1;

        when(reviewService.getAverageRatingByMovie(movieId)).thenReturn(4.5);

        ResponseEntity<Double> response = reviewController.getAverageRatingByMovie(movieId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(4.5, response.getBody());
        verify(reviewService, times(1)).getAverageRatingByMovie(movieId);
    }

    // Test getTotalReviewsByMovie
    @Test
    public void testGetTotalReviewsByMovie() {
        int movieId = 1;

        when(reviewService.getTotalReviewsByMovie(movieId)).thenReturn(10);

        ResponseEntity<Integer> response = reviewController.getTotalReviewsByMovie(movieId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10, response.getBody());
        verify(reviewService, times(1)).getTotalReviewsByMovie(movieId);
    }

    // Test getTotalUsers
    @Test
    public void testGetTotalUsers() {
        int totalUsers = 10;

        when(reviewService.getTotalUsers()).thenReturn(totalUsers);

        ResponseEntity<Integer> response = reviewController.getTotalUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10, response.getBody());
        verify(reviewService, times(1)).getTotalUsers();
    }
}

