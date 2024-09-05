package ideas.movieReview.mr_data.MovieReview.Exception.UserExceptions;

public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}