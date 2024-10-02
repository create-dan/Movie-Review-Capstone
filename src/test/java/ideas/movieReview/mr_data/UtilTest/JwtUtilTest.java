package ideas.movieReview.mr_data.UtilTest;

import ideas.movieReview.mr_data.MovieReview.Exception.UserExceptions.TokenExpiredException;
import ideas.movieReview.mr_data.MovieReview.util.JwtUtil;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtUtilTest {


    private JwtUtil jwtUtil;

    @Mock
    private UserDetails userDetails;

    private String token;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        jwtUtil = new JwtUtil();
        when(userDetails.getUsername()).thenReturn("testUser");
        token = jwtUtil.generateToken(userDetails);
    }

    @Test
    public void testExtractUsername() {
        String username = jwtUtil.extractUsername(token);
        assertEquals("testUser", username);
    }

    @Test
    void shouldTestExtractExpiration() {
        Date expiration = jwtUtil.extractExpiration(token);
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }


    @Test
    void shouldTestExtractClaim() {
        Claims claims = jwtUtil.extractAllClaims(token);
        String subject = claims.getSubject();
        assertEquals("testUser", subject);
    }

    @Test
    void shouldTestIsTokenExpiredFalse() {
        assertFalse(jwtUtil.isTokenExpired(token));
    }


    @Test
    void shouldTestIsTokenExpiredTrue() {
        // Mock an expired token
        String expiredToken = Jwts.builder()
                .setSubject("testUser")
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 11))
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, "moviereview")
                .compact();

        assertThrows(TokenExpiredException.class, () -> jwtUtil.isTokenExpired(expiredToken));
    }

    @Test
    void shouldTestGenerateToken() {
        String generatedToken = jwtUtil.generateToken(userDetails);
        assertNotNull(generatedToken);
        assertEquals(jwtUtil.extractUsername(generatedToken), "testUser");
    }

    @Test
    void shouldTestValidateToken_whenValid() {
        Boolean isValid = jwtUtil.validateToken(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void shouldTestValidateToken_InvalidUsername() {

        when(userDetails.getUsername()).thenReturn("differentUser");

        Boolean isValid = jwtUtil.validateToken(token, userDetails);
        assertFalse(isValid, "Token should be invalid because the username does not match");
    }

    @Test
    void shouldTestValidateToken_InvalidToken() {

        String invalidToken = "invalid.token.value";

        assertThrows(MalformedJwtException.class, () -> jwtUtil.validateToken(invalidToken, userDetails));
    }


}
