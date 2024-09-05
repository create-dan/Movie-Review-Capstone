package ideas.movieReview.mr_data.MovieReview.dto.ReviewDTOS;

public interface ReviewDTO {

    int getReviewId();
    int getRating();
    String getDescription();

    MovieDTO getMovie();
    UserDTO getUser();

    interface MovieDTO{
        int getMovieId();
        String getTitle();
    }

    interface UserDTO{
        int getUserId();
        String getUsername();
        String getEmail();
    }

}
