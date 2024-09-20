package ideas.movieReview.mr_data.RepositoryTest;


import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;
import ideas.movieReview.mr_data.MovieReview.Entity.Movie;
import ideas.movieReview.mr_data.MovieReview.Entity.Review;
import ideas.movieReview.mr_data.MovieReview.Repositories.MovieRepository;
import ideas.movieReview.mr_data.MovieReview.Repositories.ReviewRepository;
import ideas.movieReview.mr_data.MovieReview.Repositories.UserRepository;
import ideas.movieReview.mr_data.MovieReview.dto.ReviewDTOS.ReviewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    private Movie movie;
    private ApplicationUser user;

    @BeforeEach
    void setUp() {

        movie = new Movie();
        movie.setTitle("test");
        movie.setDescription("test");
        movie.setGenre("test");
        movie.setDirector("test");
        movie.setMovieCast("test");
        movie.setWriter("test");
        movie.setPosterUrl("test");
        movie = this.movieRepository.save(movie);


        user = new ApplicationUser();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");
        userRepository.save(user);
    }


    @Test
    @Rollback
    public void testFindByMovieId() {
        Review review = new Review();
        review.setMovie(movie);
        review.setUser(user);
        review.setRating(5);
        review.setDescription("Great movie!");
        reviewRepository.save(review);


        List<ReviewDTO> reviews = this.reviewRepository.findByMovieId(movie.getMovieId());
        assertEquals(1, reviews.size());
        assertEquals("Great movie!", reviews.get(0).getDescription());
    }

    @Test
    @Rollback
    public void testFindByUser() {
        Review review = new Review();
        review.setMovie(movie);
        review.setUser(user);
        review.setRating(4);
        review.setDescription("Enjoyed it!");
        reviewRepository.save(review);

        List<ReviewDTO> userReviews = this.reviewRepository.findByUser(user);
        assertEquals(1, userReviews.size());
        assertEquals("Enjoyed it!", userReviews.get(0).getDescription());
    }


    @Test
    @Rollback
    public void testFindAverageRatingByMovieId() {
        Review review1 = new Review();
        review1.setMovie(movie);
        review1.setUser(user);
        review1.setRating(4);
        reviewRepository.save(review1);

        Review review2 = new Review();
        review2.setMovie(movie);
        review2.setUser(user);
        review2.setRating(5);
        reviewRepository.save(review2);

        Double averageRating = this.reviewRepository.findAverageRatingByMovieId(movie.getMovieId());
        assertEquals(4.5, averageRating);
    }


    @Test
    @Rollback
    public void testCountReviewsByMovieId() {
        Review review = new Review();
        review.setMovie(movie);
        review.setUser(user);
        review.setRating(3);
        reviewRepository.save(review);

        int reviewCount = this.reviewRepository.countReviewsByMovieId(movie.getMovieId());
        assertEquals(1, reviewCount);
    }
}
