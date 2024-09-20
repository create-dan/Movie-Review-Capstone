package ideas.movieReview.mr_data.MovieReview.Repositories;

import ideas.movieReview.mr_data.MovieReview.Entity.Movie;
import ideas.movieReview.mr_data.MovieReview.dto.MovieDTOS.MovieDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

    @Query(value = "SELECT m.movieId AS movieId, m.title AS title, m.description AS description, " + "m.genre AS genre, m.director AS director, m.movieCast AS movieCast, " + "m.writer AS writer, m.posterUrl AS posterUrl " + "FROM Movie m")
    List<MovieDTO> findAllMovies();

    Optional<MovieDTO> findByMovieId(int movieId);

    int deleteByMovieId(int movieId);


}
