package ideas.movieReview.mr_data.MovieReview.Service;

import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;
import ideas.movieReview.mr_data.MovieReview.Exception.UserExceptions.EmailAlreadyRegisteredException;
import ideas.movieReview.mr_data.MovieReview.Exception.UserExceptions.UserNotFoundException;
import ideas.movieReview.mr_data.MovieReview.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public ApplicationUser createUser(ApplicationUser user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyRegisteredException("User with email " + user.getEmail() + " already exists.");
        }
        return userRepository.save(user);
    }

    public void deleteUser(int userId) {

        Optional<ApplicationUser> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
        userRepository.deleteById(userId);
    }
}
