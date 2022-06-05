package mate.academy.spring.security;

import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findByEmail(email);
        org.springframework.security.core.userdetails.User.UserBuilder builder;
        if (userOptional.isPresent()) {
            builder = org.springframework.security.core.userdetails.User
                .withUsername(userOptional.get().getEmail())
                .password(userOptional.get().getPassword());

            return builder.build();
        }
        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
