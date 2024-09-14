package ideas.movieReview.mr_data.ServiceTest;

import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;
import ideas.movieReview.mr_data.MovieReview.Exception.UserExceptions.EmailAlreadyRegisteredException;
import ideas.movieReview.mr_data.MovieReview.Exception.UserExceptions.UserNotFoundException;
import ideas.movieReview.mr_data.MovieReview.Repositories.UserRepository;
import ideas.movieReview.mr_data.MovieReview.Service.UserService;
import ideas.movieReview.mr_data.MovieReview.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private ApplicationUser user;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        user = new ApplicationUser();
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        user.setRole("USER");

    }

    @Test
    void testRegister_Success() {

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        ApplicationUser result = userService.register(user);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegister_EmailAlreadyExists() {

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThrows(EmailAlreadyRegisteredException.class, () -> userService.register(user));
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, never()).save(any(ApplicationUser.class));
    }

    @Test
    void testDeleteUser_Success() {

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.deleteUser(1);

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteUser_NotFound() {

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1));
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, never()).deleteById(1);
    }

    @Test
    void testLoadUserByUsername_Success() {

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(user.getEmail());

        assertNotNull(userDetails);
        assertEquals(user.getEmail(), userDetails.getUsername());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {

        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("unknown@example.com"));
        verify(userRepository, times(1)).findByEmail("unknown@example.com");
    }


}
