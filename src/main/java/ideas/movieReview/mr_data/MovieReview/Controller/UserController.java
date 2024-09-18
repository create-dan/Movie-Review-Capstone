package ideas.movieReview.mr_data.MovieReview.Controller;

import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;

import ideas.movieReview.mr_data.MovieReview.Repositories.UserRepository;
import ideas.movieReview.mr_data.MovieReview.Service.UserService;


import ideas.movieReview.mr_data.MovieReview.dto.UserDTOS.UserDTO;
import ideas.movieReview.mr_data.MovieReview.dto.UserDTOS.UserLoginDTO;
import ideas.movieReview.mr_data.MovieReview.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody ApplicationUser user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if(user.getRole()==null){
            user.setRole(ROLE_USER);
        }

        return userService.register(user);

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());

        String jwtToken = jwtUtil.generateToken(userDetails);

        Optional<ApplicationUser> userSaved = userRepository.findByEmail(loginRequest.getEmail());
        record UserResponse (int userId,String username,String email,String password,String role,String token){};

        UserResponse u = new UserResponse(userSaved.get().getUserId(),userSaved.get().getUsername(),userSaved.get().getEmail(),userSaved.get().getPassword(),userSaved.get().getRole(),jwtToken);
        return ResponseEntity.ok().body(u);

    }


    @DeleteMapping("admin/user/{userId}")
    public String deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return "User deleted with id " + userId;
    }


    @PostMapping("/user")
    public UserDTO getUserById(@RequestBody ApplicationUser user) {
        Optional<UserDTO> userById = userService.getUserById(user);
        return userById.orElse(null);
    }





}
