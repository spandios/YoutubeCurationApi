package com.youtube_timestamp.api;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@TestConfiguration
public class AuthConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        Set<GrantedAuthority> userAuth = new HashSet<>();
        userAuth.add(new SimpleGrantedAuthority("ROLE_USER"));

        Set<GrantedAuthority> managerAuth= new HashSet<>();
        userAuth.add(new SimpleGrantedAuthority("ROLE_USER"));
        userAuth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        userAuth.add(new SimpleGrantedAuthority("ROLE_SUPER"));


        UserDetails user = new User("wndudpower@gmail.com", "asdf4112",userAuth);
        UserDetails manager = new User("a@a.com", "asdf4112",managerAuth);
        return new InMemoryUserDetailsManager(Arrays.asList(
                user, manager
        ));
    }
}
