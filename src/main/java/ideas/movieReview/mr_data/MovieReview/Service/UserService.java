package ideas.movieReview.mr_data.MovieReview.Service;

import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;
import ideas.movieReview.mr_data.MovieReview.Exception.UserExceptions.EmailAlreadyRegisteredException;
import ideas.movieReview.mr_data.MovieReview.Exception.UserExceptions.UserNotFoundException;
import ideas.movieReview.mr_data.MovieReview.Repositories.UserRepository;
import ideas.movieReview.mr_data.MovieReview.dto.UserDTOS.UserDTO;
import ideas.movieReview.mr_data.MovieReview.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public ApplicationUser register(ApplicationUser user) {
        Optional<ApplicationUser> userDb = userRepository.findByEmail(user.getEmail());
        if (!userDb.isEmpty()) {
            throw new EmailAlreadyRegisteredException("User with email already exists ");
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


    public Optional<UserDTO> getUserById(@RequestBody ApplicationUser user) {

        Optional<ApplicationUser> userSaved = userRepository.findById(user.getUserId());
        if (userSaved.isEmpty()) {
            throw new UserNotFoundException("User with ID " + user.getUserId() + " not found.");
        }
        return userRepository.findByUserId(user.getUserId());
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
