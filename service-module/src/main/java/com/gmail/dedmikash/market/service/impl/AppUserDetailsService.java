package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.service.UserService;
import com.gmail.dedmikash.market.service.model.AppUserPrincipal;
import com.gmail.dedmikash.market.service.model.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public AppUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDTO user = userService.readByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User is not found");
        }
        return new AppUserPrincipal(user);
    }
}
