package ideas.movieReview.mr_data.MovieReview.Exception.UserExceptions;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException(String message){
        super(message);
    }
}
