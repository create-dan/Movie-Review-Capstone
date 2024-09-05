package ideas.movieReview.mr_data.MovieReview.Exception.MovieExceptions;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String message) {
        super(message);
    }
}