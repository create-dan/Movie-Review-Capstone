package ideas.movieReview.mr_data;

import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;
import ideas.movieReview.mr_data.MovieReview.Entity.Movie;
import ideas.movieReview.mr_data.MovieReview.Repositories.MovieRepository;
import ideas.movieReview.mr_data.MovieReview.Repositories.ReviewRepository;
import ideas.movieReview.mr_data.MovieReview.Repositories.UserRepository;
import ideas.movieReview.mr_data.MovieReview.dto.MovieDTOS.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class MrDataApplication  {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ReviewRepository reviewRepository;

    public static void main(String[] args) {
        System.out.println("Application running");
        SpringApplication.run(MrDataApplication.class, args);
    }


//    @Override
//    public void run(String... args) throws Exception {
//
////        ApplicationUser user1 = new ApplicationUser(0,"user1", "user1@example.com", "password1");
////        ApplicationUser user2 = new ApplicationUser(0,"user2", "user2@example.com", "password2");
////        ApplicationUser user3 = new ApplicationUser(0,"user3", "user3@example.com", "password3");
//
////        userRepository.save(user1);
////        userRepository.save(user2);
////        userRepository.save(user3);
//
//
////        Movie movie1 = new Movie(1, "Movie1", "Description1", "Genre1", "Director1", "Cast1", "Writer1", "url");
////        Movie movie2 = new Movie(2, "Movie2", "Description2", "Genre2", "Director2", "Cast2", "Writer2", "url");
////        Movie movie3 = new Movie(3, "Movie3", "Description3", "Genre3", "Director3", "Cast3", "Writer3", "url");
////        Movie movie4 = new Movie(4, "Movie4", "Description4", "Genre4", "Director4", "Cast4", "Writer4", "url");
////        Movie movie5 = new Movie(5, "Movie5", "Description5", "Genre5", "Director5", "Cast5", "Writer5", "url");
////        Movie movie6 = new Movie(6, "Movie6", "Description6", "Genre6", "Director6", "Cast6", "Writer6", "url");
////        Movie movie7 = new Movie(7, "Movie7", "Description7", "Genre7", "Director7", "Cast7", "Writer7", "url");
////        Movie movie8 = new Movie(8, "Movie8", "Description8", "Genre8", "Director8", "Cast8", "Writer8", "url");
////        Movie movie9 = new Movie(9, "Movie9", "Description9", "Genre9", "Director9", "Cast9", "Writer9", "url");
//
////        movieRepository.saveAll(Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8, movie9));
//
//        ApplicationUser user1 = new ApplicationUser(2, "", "", "");
//        ApplicationUser user2 = new ApplicationUser(3, "", "", "");
//        ApplicationUser user3 = new ApplicationUser(4, "", "", "");
//
//        Movie movie1 = new Movie(102, "", "", "", "", "", "", "",List.of());
////        Movie movie2 = new Movie(103, "", "", "", "", "", "", "");
////        Movie movie3 = new Movie(104, "", "", "", "", "", "", "");
////        Movie movie4 = new Movie(105, "", "", "", "", "", "", "");
////        Movie movie5 = new Movie(106, "", "", "", "", "", "", "");
////        Movie movie6 = new Movie(107, "", "", "", "", "", "", "");
////        Movie movie7 = new Movie(108, "", "", "", "", "", "", "");
////        Movie movie8 = new Movie(109, "", "", "", "", "", "", "");
////        Movie movie9 = new Movie(110, "", "", "", "", "", "", "");
////
////        Review review1 = new Review(movie1, user1, 4, "Good movie");
////        Review review2 = new Review(movie1, user2, 3, "Average movie");
////        Review review3 = new Review(movie1, user3, 5, "Excellent movie");
////        reviewRepository.saveAll(Arrays.asList(review1,review2,review3));
////
////        Review review4 = new Review(movie2, user1, 5, "Fantastic movie");
////        Review review5 = new Review(movie2, user2, 2, "Not my taste");
////        Review review6 = new Review(movie2, user3, 4, "Worth watching");
////        reviewRepository.saveAll(Arrays.asList(review4,review5,review6));
////
////        Review review7 = new Review(movie3, user1, 3, "Could be better");
////        Review review8 = new Review(movie3, user2, 4, "Nice watch");
////        Review review9 = new Review(movie3, user3, 5, "Loved it");
////        reviewRepository.saveAll(Arrays.asList(review7,review8,review9));
//
//
////        ***Movie repo****
//
////        movieRepository.findAll().stream().forEach(movie -> System.out.println(movie));
////        System.out.println(movieRepository.findById(102));
//
////        Optional<Movie> byMovieIdWithReviews = movieRepository.findByMovieIdWithReviews(52);
////        byMovieIdWithReviews.get().getReviews().stream().forEach(review -> System.out.println(review));
//
////        movieRepository.findByTitleIgnoreCase("movie1").stream().forEach(movie -> System.out.println(movie));
//
////        ****Review Repo****
//
////        reviewRepository.findByMovie(movie1).stream().forEach(review -> System.out.println(review));
////        System.out.println();
////        System.out.println();
////        reviewRepository.findByMovieId(102).stream().forEach(review -> System.out.println(review));
//
//
////        reviewRepository.findByUser(user1).stream().forEach(review-> System.out.println(review));
////        Double averageRatingByMovieId = reviewRepository.findAverageRatingByMovieId(52);
////        System.out.println(averageRatingByMovieId);
////        System.out.println(reviewRepository.countReviewsByMovieId(102));
////        System.out.println(reviewRepository.countReviewsByMovieId(52));
//
////        ****ApplicationUser repo
////        System.out.println(userRepository.findByEmailAndPassword("user1@example.com", "password1").get().getUsername());
//
////        movieRepository.findAll().forEach(movie -> System.out.println(movie));
////        userRepository.findAll().forEach(user-> System.out.println(user));
//
////        List<ReviewDTO> reviews = reviewRepository.findByMovie(movie1);
////        reviews.stream().forEach(reviewDTO -> System.out.println(reviewDTO.getReviewId()+"
////        "+reviewDTO.getRating()+" "+reviewDTO.getDescription()+" "+reviewDTO.getUser().getUsername()+" "+reviewDTO.getMovie().getTitle()));
//
//
////        System.out.println( reviewRepository.findAverageRatingByMovieId(102));
//
////        movieRepository.findByTitleContainingIgnoreCase("m").stream().forEach(movie -> System.out.println(movie));
//
//
//
//
//    }
}
