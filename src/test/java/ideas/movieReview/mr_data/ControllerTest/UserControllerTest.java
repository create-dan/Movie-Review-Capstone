package ideas.movieReview.mr_data.ControllerTest;

import ideas.movieReview.mr_data.MovieReview.Controller.UserController;
import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;
import ideas.movieReview.mr_data.MovieReview.Repositories.UserRepository;
import ideas.movieReview.mr_data.MovieReview.Service.UserService;
import ideas.movieReview.mr_data.MovieReview.dto.UserDTOS.UserDTO;
import ideas.movieReview.mr_data.MovieReview.dto.UserDTOS.UserLoginDTO;
import ideas.movieReview.mr_data.MovieReview.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static ideas.movieReview.mr_data.MovieReview.Roles.Roles.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtUtil jwtUtil;

    @Mock
    PasswordEncoder bCryptPasswordEncoder;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    //Test Register method
    @Test
    public void testRegisterUser_WhenRoleIsNull() {

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setEmail("user@gmail.com");
        applicationUser.setPassword("password");
        applicationUser.setRole(null);


        when(bCryptPasswordEncoder.encode(applicationUser.getPassword())).thenReturn("encodedPassword");

        when(userService.register(applicationUser)).thenReturn(applicationUser);

        ApplicationUser result = userController.registerUser(applicationUser);

        assertEquals("user@gmail.com", result.getEmail());
        assertEquals(ROLE_USER, applicationUser.getRole());
        verify(bCryptPasswordEncoder, times(1)).encode("password");
        verify(userService, times(1)).register(applicationUser);
    }

    @Test
    public void testRegisterUser_WhenRoleIsAlreadySet() {

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setEmail("admin@gmail.com");
        applicationUser.setPassword("adminPassword");
        applicationUser.setRole("ROLE_ADMIN");


        when(bCryptPasswordEncoder.encode(applicationUser.getPassword())).thenReturn("encodedAdminPassword");

        when(userService.register(applicationUser)).thenReturn(applicationUser);

        ApplicationUser result = userController.registerUser(applicationUser);


        assertEquals("admin@gmail.com", result.getEmail());
        assertEquals("ROLE_ADMIN", applicationUser.getRole());
        verify(bCryptPasswordEncoder, times(1)).encode("adminPassword");
        verify(userService, times(1)).register(applicationUser);
    }


    //Login Test
    @Test
    public void testLoginUser() {
        UserLoginDTO loginRequest = new UserLoginDTO("test@example.com", "password");

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()))).thenReturn(null);

        UserDetails userDetails = mock(UserDetails.class);

        when(userService.loadUserByUsername("test@example.com")).thenReturn(userDetails);


        when(jwtUtil.generateToken(userDetails)).thenReturn("fakeJwt");
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(new ApplicationUser()));



        ResponseEntity<?> responseEntity = userController.loginUser(loginRequest);

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService, times(1)).loadUserByUsername("test@example.com");
        verify(jwtUtil, times(1)).generateToken(userDetails);

    }


    @Test
    public void testDeleteUser() {
        int userId = 1;

        doNothing().when(userService).deleteUser(userId);

        String response = userController.deleteUser(userId);

        assertEquals("User deleted with id " + userId, response);
        verify(userService, times(1)).deleteUser(userId);
    }


    @Test
    public void testGetUserById(){
        ApplicationUser user = new ApplicationUser();
        user.setUserId(1);
        user.setEmail("test@gmail.com");

        UserDTO userDTO = mock(UserDTO.class);
        when(userDTO.getUserId()).thenReturn(1);
        when(userDTO.getEmail()).thenReturn("test@gmail.com");
        when(userService.getUserById(user)).thenReturn(Optional.of(userDTO));

        UserDTO result = userController.getUserById(user);

        assertNotNull(result);
        assertEquals(1,result.getUserId());
        assertEquals("test@gmail.com",result.getEmail());
        verify(userService, times(1)).getUserById(user);


    }


    @Test
    public void testGetUserById_UserNotFound() {
        ApplicationUser user = new ApplicationUser();
        user.setUserId(99);

        when(userService.getUserById(user)).thenReturn(Optional.empty());

        UserDTO result = userController.getUserById(user);

        assertNull(result);
        verify(userService, times(1)).getUserById(user);
    }

    @Test
    public void testGetTotalUsers() {
        when(userService.getTotalUsers()).thenReturn(10);

        int result = userController.getTotalUsers();

        assertEquals(10, result);
        verify(userService, times(1)).getTotalUsers();
    }
}
