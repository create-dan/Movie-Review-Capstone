package ideas.movieReview.mr_data.MovieReview.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int movieId;

    private String title;
    private String description;
    private String genre;
    private String director;
    private String movieCast;
    private String writer;
    private String posterUrl;

    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
    private List<Review> reviews;


}
