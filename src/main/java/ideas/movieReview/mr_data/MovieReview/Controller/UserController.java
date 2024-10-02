package ideas.movieReview.mr_data.MovieReview.Controller;

import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;

import ideas.movieReview.mr_data.MovieReview.Repositories.UserRepository;
import ideas.movieReview.mr_data.MovieReview.Service.UserService;


import ideas.movieReview.mr_data.MovieReview.dto.UserDTOS.UserDTO;
import ideas.movieReview.mr_data.MovieReview.dto.UserDTOS.UserLoginDTO;
import ideas.movieReview.mr_data.MovieReview.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static ideas.movieReview.mr_data.MovieReview.Roles.Roles.ROLE_USER;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    // Register User
    @PostMapping("/register")
    public ResponseEntity<ApplicationUser> registerUser(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(ROLE_USER);
        }

        ApplicationUser savedUser = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());
        String jwtToken = jwtUtil.generateToken(userDetails);

        Optional<ApplicationUser> userSaved = userRepository.findByEmail(loginRequest.getEmail());
        if (userSaved.isPresent()) {
            record UserResponse(int userId, String username, String email, String password, String role, String token) {}
            UserResponse u = new UserResponse(
                    userSaved.get().getUserId(),
                    userSaved.get().getUsername(),
                    userSaved.get().getEmail(),
                    userSaved.get().getPassword(),
                    userSaved.get().getRole(),
                    jwtToken
            );
            return ResponseEntity.ok().body(u);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    // Delete User by Admin
    @DeleteMapping("admin/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted with id " + userId);
    }

    // Get User by ID
    @PostMapping("/user")
    public ResponseEntity<UserDTO> getUserById(@RequestBody ApplicationUser user) {
        Optional<UserDTO> userById = userService.getUserById(user);
        return userById.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Get Total User Count
    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalUsers() {
        int totalUsers = userService.getTotalUsers();
        return ResponseEntity.ok(totalUsers);
    }
}
