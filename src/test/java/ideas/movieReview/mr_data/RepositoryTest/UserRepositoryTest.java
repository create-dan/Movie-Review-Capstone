package ideas.movieReview.mr_data.RepositoryTest;


import ideas.movieReview.mr_data.MovieReview.Entity.ApplicationUser;
import ideas.movieReview.mr_data.MovieReview.Repositories.UserRepository;
import ideas.movieReview.mr_data.MovieReview.dto.UserDTOS.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    ApplicationUser applicationUser = new ApplicationUser();

    @BeforeEach
    void setUp(){
        applicationUser.setUsername("test");
        applicationUser.setEmail("test@gmail.com");
        applicationUser.setPassword("test");
        applicationUser.setRole("USER");

        applicationUser = userRepository.save(applicationUser);
    }

    @Test
    public void testFindByEmailAndPassword(){
        Optional<UserDTO> foundUser = this.userRepository.findByEmailAndPassword("test@gmail.com","test");

        assertTrue(foundUser.isPresent());
        assertEquals("test@gmail.com",foundUser.get().getEmail());

    }

    @Test
    public void testExistsByEmail(){
        boolean exists = this.userRepository.existsByEmail("test@gmail.com");
        assertTrue(exists);

        boolean notExists = this.userRepository.existsByEmail("no@gmail.com");
        assertFalse(notExists);
    }


    @Test
    public void testFindByEmail(){
        Optional<ApplicationUser> foundUser = this.userRepository.findByEmail("test@gmail.com");
        assertTrue(foundUser.isPresent());
        assertEquals("test@gmail.com",foundUser.get().getEmail());
    }


    @Test
    public void testFindByUserId(){
        Optional<UserDTO> foundUser = this.userRepository.findByUserId(applicationUser.getUserId());
        assertTrue(foundUser.isPresent());
        assertEquals("test@gmail.com",foundUser.get().getEmail());
    }

    





}
