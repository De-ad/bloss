package com.brigada.bloss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.brigada.bloss.dao.UserRepository;
import com.brigada.bloss.entity.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                                .findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with username '" + username + "'"));
        
        return UserDetailsImpl.fromUser(user);
        
    }
    
}
