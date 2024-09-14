package ideas.movieReview.mr_data.MovieReview.Repositories;

import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;
import ideas.movieReview.mr_data.MovieReview.Entity.Movie;
import ideas.movieReview.mr_data.MovieReview.Entity.Review;
import ideas.movieReview.mr_data.MovieReview.dto.ReviewDTOS.ReviewDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {


    @Query("SELECT r FROM Review r WHERE r.movie.movieId = :movieId")
    List<ReviewDTO> findByMovieId(int movieId);

    List<ReviewDTO> findByUser(ApplicationUser user);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.movie.movieId = :movieId")
    Double findAverageRatingByMovieId(int movieId);

    @Query("SELECT COUNT(r) from Review r where r.movie.movieId= :movieId")
    int countReviewsByMovieId(int movieId);


}
