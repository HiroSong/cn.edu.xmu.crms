package cn.edu.xmu.crms.util.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author SongLingbing
 * @date 2018/12/24 12:58
 */
@Component
public class SimplePasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence password) {
        return password.toString();
    }

    @Override
    public boolean matches(CharSequence rowPassword, String password) {
        return password.equals(rowPassword.toString());
    }
}
