package ideas.movieReview.mr_data.MovieReview.Exception.UserExceptions;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException(String message) {
        super(message);
    }
}