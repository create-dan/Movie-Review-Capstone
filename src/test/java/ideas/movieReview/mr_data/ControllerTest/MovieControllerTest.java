package ideas.movieReview.mr_data.ControllerTest;

import ideas.movieReview.mr_data.MovieReview.Controller.MovieController;
import ideas.movieReview.mr_data.MovieReview.Entity.Movie;
import ideas.movieReview.mr_data.MovieReview.Service.MovieService;
import ideas.movieReview.mr_data.MovieReview.dto.MovieDTOS.MovieDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Get all movies with optional title search
    @Test
    public void getMovies() {
        List<MovieDTO> mockMovie = new ArrayList<>();
                when(movieService.getMovies(null)).thenReturn(mockMovie);

        List<MovieDTO> result = movieController.getMovies(null);
        verify(movieService, times(1)).getMovies(null);

    }

    // Save a new movie
    @Test
    public void testSaveMovie(){
        Movie movie = new Movie();
        movie.setTitle("movie");

        when(movieService.saveMovie(movie)).thenReturn(movie);

        Movie result = movieService.saveMovie(movie);
        assertEquals("movie",result.getTitle());
        verify(movieService,times(1)).saveMovie(movie);
    }

    // Delete a movie by ID
    @Test
    public void testDeleteMovie(){
        int movieId =1;
        when(movieService.deleteMovie(movieId)).thenReturn("Movie Deleted");

        String result = movieService.deleteMovie(movieId);

        assertEquals("Movie Deleted",result);
        verify(movieService,times(1)).deleteMovie(movieId);
    }

    // Get a movie by ID
    @Test
    public void testGetMovieById(){
        int movieId = 1;
        MovieDTO movie = mock(MovieDTO.class);
        when(movie.getTitle()).thenReturn("title");
        when(movieService.getMovieById(movieId)).thenReturn(movie);

        MovieDTO result = movieService.getMovieById(movieId);

        assertEquals(movie,result);
        assertEquals("title",result.getTitle());
        verify(movieService,times(1)).getMovieById(movieId);
    }

    // Search movies by title
    @Test
    public void testSearchMovies() {
        String title = "Test Movie";
        List<MovieDTO> mockMovies = new ArrayList<>();

        MovieDTO movie = mock(MovieDTO.class);
        when(movie.getTitle()).thenReturn(title);
        mockMovies.add(movie);

        when(movieService.searchMovies(title)).thenReturn(mockMovies);

        List<MovieDTO> result = movieController.searchMovies(title);

        assertEquals(1, result.size());
        assertEquals("Test Movie", result.get(0).getTitle());
        verify(movieService, times(1)).searchMovies(title);
    }



}
