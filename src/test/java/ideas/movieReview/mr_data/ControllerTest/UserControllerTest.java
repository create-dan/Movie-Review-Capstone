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
import org.springframework.http.HttpStatus;
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

    // Test Register method
    @Test
    public void testRegisterUser_WhenRoleIsNull() {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setEmail("user@gmail.com");
        applicationUser.setPassword("password");
        applicationUser.setRole(null);

        when(bCryptPasswordEncoder.encode(applicationUser.getPassword())).thenReturn("encodedPassword");
        when(userService.register(applicationUser)).thenReturn(applicationUser);

        ResponseEntity<ApplicationUser> response = userController.registerUser(applicationUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("user@gmail.com", response.getBody().getEmail());
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

        ResponseEntity<ApplicationUser> response = userController.registerUser(applicationUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("admin@gmail.com", response.getBody().getEmail());
        assertEquals("ROLE_ADMIN", applicationUser.getRole());
        verify(bCryptPasswordEncoder, times(1)).encode("adminPassword");
        verify(userService, times(1)).register(applicationUser);
    }

    // Login Test
    @Test
    public void testLoginUser() {
        UserLoginDTO loginRequest = new UserLoginDTO("test@example.com", "password");

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()))).thenReturn(null);
        UserDetails userDetails = mock(UserDetails.class);
        when(userService.loadUserByUsername("test@example.com")).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("fakeJwt");

        ApplicationUser mockUser = new ApplicationUser();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("password");
        mockUser.setUserId(1);
        mockUser.setUsername("testUser");
        mockUser.setRole("ROLE_USER");
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(mockUser));

        ResponseEntity<?> response = userController.loginUser(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService, times(1)).loadUserByUsername("test@example.com");
        verify(jwtUtil, times(1)).generateToken(userDetails);
    }

    @Test
    public void testDeleteUser() {
        int userId = 1;

        doNothing().when(userService).deleteUser(userId);

        ResponseEntity<String> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted with id " + userId, response.getBody());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    public void testGetUserById() {
        ApplicationUser user = new ApplicationUser();
        user.setUserId(1);
        user.setEmail("test@gmail.com");

        UserDTO userDTO = mock(UserDTO.class);
        when(userDTO.getUserId()).thenReturn(1);
        when(userDTO.getEmail()).thenReturn("test@gmail.com");
        when(userService.getUserById(user)).thenReturn(Optional.of(userDTO));

        ResponseEntity<UserDTO> response = userController.getUserById(user);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getUserId());
        assertEquals("test@gmail.com", response.getBody().getEmail());
        verify(userService, times(1)).getUserById(user);
    }

    @Test
    public void testGetUserById_UserNotFound() {
        ApplicationUser user = new ApplicationUser();
        user.setUserId(99);

        when(userService.getUserById(user)).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> response = userController.getUserById(user);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userService, times(1)).getUserById(user);
    }

    @Test
    public void testGetTotalUsers() {
        when(userService.getTotalUsers()).thenReturn(10);

        ResponseEntity<Integer> response = userController.getTotalUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10, response.getBody().intValue());
        verify(userService, times(1)).getTotalUsers();
    }
}

