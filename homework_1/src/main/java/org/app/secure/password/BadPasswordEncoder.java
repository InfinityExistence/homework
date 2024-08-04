package org.app.secure.password;

import java.util.Base64;

public class BadPasswordEncoder implements PasswordEncoder{
    @Override
    public String encode(String rawPassword) {
        return new String(Base64.getEncoder().encode(rawPassword.getBytes()));
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
