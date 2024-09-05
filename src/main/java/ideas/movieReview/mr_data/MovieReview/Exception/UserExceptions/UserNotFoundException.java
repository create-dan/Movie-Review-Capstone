package ideas.movieReview.mr_data.MovieReview.Exception.UserExceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}