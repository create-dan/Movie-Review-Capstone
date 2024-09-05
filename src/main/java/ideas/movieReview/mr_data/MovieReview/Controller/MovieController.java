package ideas.movieReview.mr_data.MovieReview.Controller;

import ideas.movieReview.mr_data.MovieReview.Entity.Movie;

import ideas.movieReview.mr_data.MovieReview.Service.MovieService;
import ideas.movieReview.mr_data.MovieReview.dto.MovieDTOS.MovieDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Transactional
public class MovieController {

    @Autowired
    private MovieService movieService;

    // Get all movies with optional title search
    @GetMapping("movies")
    public List<MovieDTO> getMovies(@RequestParam(required = false) String title) {
        return movieService.getMovies(title);
    }

    // Save a new movie
    @PostMapping("movies")
    public Movie saveMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }

    // Delete a movie by ID
    @DeleteMapping("movies/{movieId}")
    public String deleteMovie(@PathVariable int movieId) {
        return movieService.deleteMovie(movieId);
    }

    // Get a movie by ID
    @GetMapping("movies/{movieId}")
    public Movie getMovieById(@PathVariable int movieId) {
        return movieService.getMovieById(movieId);
    }

    // Search movies by title
    @GetMapping("movies/search")
    public List<MovieDTO> searchMovies(@RequestParam String title) {
        return movieService.searchMovies(title);
    }


}
