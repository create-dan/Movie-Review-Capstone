package ideas.movieReview.mr_data.MovieReview.Service;

import ideas.movieReview.mr_data.MovieReview.Entity.Movie;
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
        if (res == 1)
            return "Movie Deleted with id " + movieId;
        return "Movie Not Deleted";
    }

    public Movie getMovieById(int movieId) {
        return movieRepository.findById(movieId).orElse(null);
    }

    public List<MovieDTO> searchMovies(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }
}
