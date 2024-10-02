package ideas.movieReview.mr_data.MovieReview.Controller;

import ideas.movieReview.mr_data.MovieReview.Entity.Movie;

import ideas.movieReview.mr_data.MovieReview.Service.MovieService;
import ideas.movieReview.mr_data.MovieReview.dto.MovieDTOS.MovieDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@Transactional
@CrossOrigin("*")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @GetMapping("movies")
    public ResponseEntity<List<MovieDTO>> getMovies(@RequestParam(required = false) String title) {
        List<MovieDTO> movies = movieService.getMovies(title);
        return ResponseEntity.ok(movies);  // Return 200 OK with the list of movies
    }

    @PostMapping("admin/movie")
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieService.saveMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);  // Return 201 Created with the saved movie
    }


    @DeleteMapping("admin/movie/{movieId}")
    public ResponseEntity<String> deleteMovie(@PathVariable int movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.ok("Movie Deleted with ID " + movieId);  // Return 200 OK if successfully deleted
    }

    @GetMapping("movies/{movieId}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable int movieId) {
        MovieDTO movie = movieService.getMovieById(movieId);
        return ResponseEntity.ok(movie);  // Return 200 OK with the movie
    }

}
