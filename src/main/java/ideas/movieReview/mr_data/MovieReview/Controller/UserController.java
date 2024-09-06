package ideas.movieReview.mr_data.MovieReview.Controller;

import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;

import ideas.movieReview.mr_data.MovieReview.Service.UserService;


import ideas.movieReview.mr_data.MovieReview.dto.UserDTOS.UserLoginDTO;
import ideas.movieReview.mr_data.MovieReview.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userService.register(user);

    }

    @PostMapping("/login")
    public String loginUser(@RequestBody UserLoginDTO loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());

        return jwtUtil.generateToken(userDetails);
    }


    @DeleteMapping("admin/user/{userId}")
    public String deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return "User deleted with id " + userId;
    }


}
