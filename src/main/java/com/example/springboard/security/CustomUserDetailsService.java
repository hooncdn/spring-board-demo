package com.example.springboard.security;

import com.example.springboard.domain.User;
import com.example.springboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username does not exist.");
        }

        List<GrantedAuthority> authorities = Collections
                .singletonList(new SimpleGrantedAuthority(user.getRole().getValue()));

        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), authorities, user.getStatus());
    }
}
