//package ideas.movieReview.mr_data.MovieReview.Service;
//
//
//import ideas.movieReview.mr_data.MovieReview.Entity.Movie;
//import ideas.movieReview.mr_data.MovieReview.Repositories.MovieRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MovieBulkInsertService {
//
//    @Autowired
//    MovieRepository movieRepository;
//
//    @PostConstruct
//    public void addMoviesInBulk(){
//        int totalMovies = 500000;
//
//        long startTime = 0;
//        long endTime = 0;
//
//        startTime = System.currentTimeMillis();
//        for(int i=1;i<=totalMovies;i++)
//        {
//            System.out.println("Inserting " + totalMovies + " movies...");
//
//
//
//
//            Movie movie = new Movie();
//            movie.setDescription("Description "+ i);
//            movie.setWriter("Writer "+i);
//            movie.setTitle("Test Movie " + i);
//            movie.setPosterUrl("");
//            movie.setGenre("Action,Drama");
//            movie.setDirector("Director "+i);
//            movie.setReleaseDate("2024-05-03");
//            movie.setMovieLength("2h15min");
//            movie.setMovieCast("Matthew McConaughey, Anne Hathaway, Jessica Chastain");
//            movie.setVideoUrl("");
//
//            movieRepository.save(movie);
//            System.out.println("Added movie " + i);
//
//        }
//
//        endTime = System.currentTimeMillis();
//
//        System.out.println(totalMovies + " movies added successfully!");
//        System.out.println("Start Time "+startTime+" "+"End Time "+endTime);
//        long timeTaken = endTime - startTime;
//
//        System.out.println(totalMovies + " movies added successfully in " + (timeTaken / 1000.0) + " seconds.");
//    }
//}
