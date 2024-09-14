package ideas.movieReview.mr_data.ServiceTest;

import ideas.movieReview.mr_data.MovieReview.Entity.Movie;
import ideas.movieReview.mr_data.MovieReview.Exception.MovieExceptions.MovieNotFoundException;
import ideas.movieReview.mr_data.MovieReview.Repositories.MovieRepository;
import ideas.movieReview.mr_data.MovieReview.Service.MovieService;
import ideas.movieReview.mr_data.MovieReview.dto.MovieDTOS.MovieDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMovies_WithTitle() {

        String title = "Inception";
        MovieDTO movieDTO = mock(MovieDTO.class);
        when(movieRepository.findByTitleContainingIgnoreCase(title)).thenReturn(Arrays.asList(movieDTO));


        List<MovieDTO> result = movieService.getMovies(title);


        assertNotNull(result);
        assertEquals(1, result.size());
        verify(movieRepository, times(1)).findByTitleContainingIgnoreCase(title);
    }

    @Test
    public void testGetMovies_WithoutTitle() {

        MovieDTO movieDTO = mock(MovieDTO.class);
        when(movieRepository.findBy()).thenReturn(Arrays.asList(movieDTO));


        List<MovieDTO> result = movieService.getMovies(null);


        assertNotNull(result);
        assertEquals(1, result.size());
        verify(movieRepository, times(1)).findBy();
    }


    @Test
    public void testSaveMovie() {

        Movie movie = mock(Movie.class);
        when(movieRepository.save(movie)).thenReturn(movie);


        Movie result = movieService.saveMovie(movie);


        assertNotNull(result);
        verify(movieRepository, times(1)).save(movie);
    }


    @Test
    public void testDeleteMovie_Success() {

        int movieId = 1;
        when(movieRepository.deleteByMovieId(movieId)).thenReturn(1);

        String result = movieService.deleteMovie(movieId);


        assertEquals("Movie Deleted with ID 1", result);
        verify(movieRepository, times(1)).deleteByMovieId(movieId);
    }


    @Test
    public void testDeleteMovie_NotFound() {

        int movieId = 1;
        when(movieRepository.deleteByMovieId(movieId)).thenReturn(0);


        MovieNotFoundException thrown = assertThrows(MovieNotFoundException.class, () -> {
            movieService.deleteMovie(movieId);
        });
        assertEquals("Movie with ID 1 not found.", thrown.getMessage());
        verify(movieRepository, times(1)).deleteByMovieId(movieId);
    }

    @Test
    public void testGetMovieById_Success() {

        int movieId = 1;
        MovieDTO movieDTO = mock(MovieDTO.class);
        when(movieRepository.findByMovieId(movieId)).thenReturn(Optional.of(movieDTO));

        MovieDTO result = movieService.getMovieById(movieId);

        assertNotNull(result);
        verify(movieRepository, times(1)).findByMovieId(movieId);
    }


    @Test
    public void testGetMovieById_NotFound() {

        int movieId = 1;
        when(movieRepository.findByMovieId(movieId)).thenReturn(Optional.empty());


        MovieNotFoundException thrown = assertThrows(MovieNotFoundException.class, () -> {
            movieService.getMovieById(movieId);
        });
        assertEquals("Movie with ID 1 not found.", thrown.getMessage());
        verify(movieRepository, times(1)).findByMovieId(movieId);
    }


    @Test
    public void testSearchMovies() {

        String title = "Inception";
        MovieDTO movieDTO = mock(MovieDTO.class); // Mock the MovieDTO
        when(movieRepository.findByTitleContainingIgnoreCase(title)).thenReturn(Arrays.asList(movieDTO));


        List<MovieDTO> result = movieService.searchMovies(title);


        assertNotNull(result);
        assertEquals(1, result.size());
        verify(movieRepository, times(1)).findByTitleContainingIgnoreCase(title);
    }


}
