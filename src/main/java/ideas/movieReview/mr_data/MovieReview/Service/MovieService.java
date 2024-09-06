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
        if (title == null || title.isEmpty()) {
            return movieRepository.findBy();
        } else {
            return movieRepository.findByTitleContainingIgnoreCase(title);
        }
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

    public Movie getMovieById(int movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie with ID " + movieId + " not found."));
    }

    public List<MovieDTO> searchMovies(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }
}
