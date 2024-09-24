package ideas.movieReview.mr_data.RepositoryTest;

import ideas.movieReview.mr_data.MovieReview.Entity.Movie;
import ideas.movieReview.mr_data.MovieReview.Repositories.MovieRepository;
import ideas.movieReview.mr_data.MovieReview.dto.MovieDTOS.MovieDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    Movie movie = new Movie();
    @BeforeEach
    void setUp() {


        movie.setTitle("test");
        movie.setDescription("test");
        movie.setGenre("test");
        movie.setDirector("test");
        movie.setMovieCast("test");
        movie.setWriter("test");
        movie.setPosterUrl("test");

        movie = movieRepository.save(movie);
    }

    @Test
    public void testFindAllMovies() {

        List<MovieDTO> movies = this.movieRepository.findAllMovies();
        assertEquals(1, movies.size());
        assertEquals("test", movies.get(0).getTitle());
    }

    @Test
    public void testFindByMovieId() {
        Optional<MovieDTO> foundMovie = this.movieRepository.findByMovieId(movie.getMovieId());
        assertTrue(foundMovie.isPresent());
        assertEquals("test", foundMovie.get().getTitle());
    }

    @Test
    @Rollback
    public void testDeleteByMovieId() {


        Optional<MovieDTO> movieAdded = this.movieRepository.findByMovieId(movie.getMovieId());
        assertTrue(movieAdded.isPresent());

        int deletedCount = this.movieRepository.deleteByMovieId(movieAdded.get().getMovieId());
        assertEquals(1, deletedCount);

        Optional<MovieDTO> movieAfterDelete = this.movieRepository.findByMovieId(movieAdded.get().getMovieId());
        assertFalse(movieAfterDelete.isPresent());
    }
}