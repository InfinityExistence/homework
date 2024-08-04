package org.app.secure.password;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class BadPasswordEncoderTest {

    private BadPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        passwordEncoder = new BadPasswordEncoder();
    }

    @Test
    public void testEncode() {
        String rawPassword = "password123";
        String expectedEncodedPassword = Base64.getEncoder().encodeToString(rawPassword.getBytes());

        String actualEncodedPassword = passwordEncoder.encode(rawPassword);

        assertEquals(expectedEncodedPassword, actualEncodedPassword);
    }

    @Test
    public void testMatches() {
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);

        assertTrue(matches);
    }

    @Test
    public void testMatchesWithDifferentPassword() {
        String rawPassword = "password123";
        String differentRawPassword = "differentPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        boolean matches = passwordEncoder.matches(differentRawPassword, encodedPassword);

        assertFalse(matches);
    }

    @Test
    public void testMatchesWithEmptyPassword() {
        String rawPassword = "";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);

        assertTrue(matches);
    }

    @Test
    public void testMatchesWithNullPassword() {
        String rawPassword = null;
        String encodedPassword = passwordEncoder.encode("password123");

        assertThrows(NullPointerException.class, () -> {
            passwordEncoder.matches(rawPassword, encodedPassword);
        });
    }
}