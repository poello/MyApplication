package com.example.webApp;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserList users = restTemplate.getForObject("http://localhost:8080/users?userLogin=" + username, UserList.class);
        if (users == null) throw new UsernameNotFoundException(username);

        if (!users.getUsers().isEmpty() && users.getUsers().size() == 1) {

            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(users.getUsers().get(0).getRole().toString()));

            return new org.springframework.security.core.userdetails.User(users.getUsers().get(0).getLogin(),
                    new BCryptPasswordEncoder().encode(users.getUsers().get(0).getPassword()), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
