package ideas.movieReview.mr_data.MovieReview.Service;

import ideas.movieReview.mr_data.MovieReview.Entity.Movie;
import ideas.movieReview.mr_data.MovieReview.Exception.MovieExceptions.MovieNotFoundException;
import ideas.movieReview.mr_data.MovieReview.Repositories.MovieRepository;
import ideas.movieReview.mr_data.MovieReview.dto.MovieDTOS.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<MovieDTO> getMovies(String title) {
            return movieRepository.findAllMovies();
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public String deleteMovie(int movieId) {
        int res = movieRepository.deleteByMovieId(movieId);
        if (res == 0) {
            throw new MovieNotFoundException("Movie with ID " + movieId + " not found.");
        }
        return "Movie Deleted with ID " + movieId;
    }

    public MovieDTO getMovieById(int movieId) {
        return movieRepository.findByMovieId(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie with ID " + movieId + " not found."));
    }


}
