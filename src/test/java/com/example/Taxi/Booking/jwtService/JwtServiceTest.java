package com.example.Taxi.Booking.jwtService;

import com.example.Taxi.Booking.expection.InvalidUserException;
import com.example.Taxi.Booking.model.User;
import com.example.Taxi.Booking.security.JwtService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JwtServiceTest {
    @InjectMocks
    private JwtService jwtService;
    @Mock
    private User user;
    @Mock
    private UserDetails userDetails;
    private String token;
    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(jwtService, "secretKey", "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437");
        ReflectionTestUtils.setField(jwtService, "expirationTime", 3600000);
        when(user.getUserId()).thenReturn(1L);
        when(user.getName()).thenReturn("js");
        when(user.getEmail()).thenReturn("js@gmail.com");
        token = jwtService.generateToken(user);
    }
    @Test
    public void testGenerateToken() {
        assertNotNull(token);
        assertEquals(user.getEmail(), jwtService.extractEmail(token));
    }
    @Test
    public void testExtractEmail() {
        assertEquals(user.getEmail(), jwtService.extractEmail(token));
    }
    @Test
    public void testIsTokenValid() {
        when(userDetails.getUsername()).thenReturn("js@gmail.com");
        assertTrue(jwtService.isTokenValid(token, userDetails));
        when(userDetails.getUsername()).thenReturn("js@gmail.");
        assertThrows(InvalidUserException.class, () -> jwtService.isTokenValid(token, userDetails));
        ReflectionTestUtils.setField(jwtService, "expirationTime", 3600000);
        token = jwtService.generateToken(user);
        assertThrows(InvalidUserException.class, () -> jwtService.isTokenValid(token, userDetails));
    }
    @Test
    public void testIsTokenExpired() {
        assertFalse(jwtService.isTokenExpired(token));
    }
    @Test
    public void testExtractClaim() {
        Function<Claims, Date> claimsResolver = Claims::getExpiration;
        Date expirationDate = jwtService.extractClaim(token, claimsResolver);
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }
}