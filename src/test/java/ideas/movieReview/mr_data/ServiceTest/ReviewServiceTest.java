package ideas.movieReview.mr_data.ServiceTest;

import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;
import ideas.movieReview.mr_data.MovieReview.Entity.Review;
import ideas.movieReview.mr_data.MovieReview.Exception.ReviewExceptions.ReviewNotFoundException;
import ideas.movieReview.mr_data.MovieReview.Repositories.ReviewRepository;
import ideas.movieReview.mr_data.MovieReview.Service.ReviewService;
import ideas.movieReview.mr_data.MovieReview.dto.ReviewDTOS.ReviewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testSaveReview() {

        Review review = new Review();
        when(reviewRepository.save(review)).thenReturn(review);

        Review result = reviewService.saveReview(review);

        assertNotNull(result);
        assertEquals(review, result);
        verify(reviewRepository, times(1)).save(review);
    }


    @Test
    public void testDeleteReview_ReviewNotFound() {

        int reviewId = 1;
        when(reviewRepository.existsById(reviewId)).thenReturn(false);


        ReviewNotFoundException thrown = assertThrows(ReviewNotFoundException.class, () -> {
            reviewService.deleteReview(reviewId);
        });
        assertEquals("Review with ID " + reviewId + " not found.", thrown.getMessage());
        verify(reviewRepository, times(1)).existsById(reviewId);
    }

    @Test
    public void testDeleteReview_Success() {

        int reviewId = 1;
        when(reviewRepository.existsById(reviewId)).thenReturn(true);


        String result = reviewService.deleteReview(reviewId);


        assertEquals("Review deleted with ID " + reviewId, result);
        verify(reviewRepository, times(1)).existsById(reviewId);
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }


    @Test
    public void testGetReviewsByMovie() {

        int movieId = 1;
        ReviewDTO review1 = mock(ReviewDTO.class);
        ReviewDTO review2 = mock(ReviewDTO.class);
        List<ReviewDTO> reviews = Arrays.asList(review1, review2);
        when(reviewRepository.findByMovieId(movieId)).thenReturn(reviews);


        List<ReviewDTO> result = reviewService.getReviewsByMovie(movieId);


        assertNotNull(result);
        assertEquals(reviews.size(), result.size());
        verify(reviewRepository, times(1)).findByMovieId(movieId);
    }


    @Test
    public void testGetReviewsByUser() {

        ApplicationUser user = mock(ApplicationUser.class);
        ReviewDTO review1 = mock(ReviewDTO.class);
        ReviewDTO review2 = mock(ReviewDTO.class);
        List<ReviewDTO> reviews = Arrays.asList(review1, review2);
        when(reviewRepository.findByUser(user)).thenReturn(reviews);


        List<ReviewDTO> result = reviewService.getReviewsByUser(user);


        assertNotNull(result);
        assertEquals(reviews.size(), result.size());
        verify(reviewRepository, times(1)).findByUser(user);
    }


    @Test
    public void testGetAverageRatingByMovie() {

        int movieId = 1;
        Double averageRating = 4.5;
        when(reviewRepository.findAverageRatingByMovieId(movieId)).thenReturn(averageRating);


        Double result = reviewService.getAverageRatingByMovie(movieId);


        assertNotNull(result);
        assertEquals(averageRating, result);
        verify(reviewRepository, times(1)).findAverageRatingByMovieId(movieId);
    }

    @Test
    public void testGetTotalReviewsByMovie() {

        int movieId = 1;
        int totalReviews = 10;
        when(reviewRepository.countReviewsByMovieId(movieId)).thenReturn(totalReviews);

        int result = reviewService.getTotalReviewsByMovie(movieId);

        assertEquals(totalReviews, result);
        verify(reviewRepository, times(1)).countReviewsByMovieId(movieId);
    }
}
