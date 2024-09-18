package ideas.movieReview.mr_data.MovieReview.Repositories;

import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;
import ideas.movieReview.mr_data.MovieReview.dto.UserDTOS.UserDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<ApplicationUser, Integer> {


    Optional<UserDTO> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    Optional<ApplicationUser> findByEmail(String email);
    Optional<UserDTO> findByUserId(int userId);


}
