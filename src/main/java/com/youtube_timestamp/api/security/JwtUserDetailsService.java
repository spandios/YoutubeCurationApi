package com.youtube_timestamp.api.security;

import com.youtube_timestamp.api.user.entity.UserRole;
import com.youtube_timestamp.api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.youtube_timestamp.api.user.entity.UserEntity user = userRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        if (email.equals("wndudpower@gmail.com")) {
            grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }

        return new CurrentUser(user.getId(),user.getEmail(), user.getProviderId(), user.getName(), grantedAuthorities);
    }

    public com.youtube_timestamp.api.user.entity.UserEntity authenticateByEmailAndPassword(String email, String password) {
        com.youtube_timestamp.api.user.entity.UserEntity user = userRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        if(!passwordEncoder.matches(password, user.getProviderId())) {
            throw new BadCredentialsException("Password not matched");
        }

        return user;
    }

}
