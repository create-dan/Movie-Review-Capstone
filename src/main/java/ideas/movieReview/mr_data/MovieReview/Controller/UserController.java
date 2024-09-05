package ideas.movieReview.mr_data.MovieReview.Controller;

import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;

import ideas.movieReview.mr_data.MovieReview.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    public ApplicationUser createUser(@RequestBody ApplicationUser user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return "User deleted with id " + userId;
    }




}
