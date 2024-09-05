package ideas.movieReview.mr_data.MovieReview.Exception.ReviewExceptions;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(String message) {
        super(message);
    }
}