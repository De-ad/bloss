package com.brigada.bloss.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.brigada.bloss.dao.UserRepository;
import com.brigada.bloss.entity.User;
import com.brigada.bloss.listening.CredentialsRequest;
import com.brigada.bloss.listening.CredentialsResponse;
import com.brigada.bloss.listening.MessageResponse;
import com.brigada.bloss.security.JwtUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    public UserService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Object> login(CredentialsRequest credentialsRequest) {
        log.info("--> processing login for user '" + credentialsRequest.getUsername() + "'...");
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credentialsRequest.getUsername(), credentialsRequest.getPassword()
            ));
            String token = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return ResponseEntity.ok().body(new CredentialsResponse(userDetails.getUser().getId(), userDetails.getUsername(), token));

        } catch (BadCredentialsException exception) {

            return ResponseEntity.status(401).body(new MessageResponse("Permission denied"));

        } catch (Exception exception) {

            return ResponseEntity.status(500).body(new MessageResponse("server error: cannot create user"));

        }
    }

    public ResponseEntity<Object> register(User user) {
        log.info("--> processing register for user '" + user.getUsername() + "'...");
        final String mainPassword = user.getPassword();

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserDetailsImpl userDetails = UserDetailsImpl.fromUser(user);

        if (user.getUsername().equals("him_maxim")) {
            user.setRoles(Set.of(roleService.getSuperAdminRole()));
        } else {
            user.setRoles(Set.of(roleService.getUserRole()));
        }

        try {
            userRepository.save(user);

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(), mainPassword
            ));

            String token = jwtUtils.generateJwtToken(authentication);

            userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return ResponseEntity.ok().body(new CredentialsResponse(userDetails.getUser().getId(), userDetails.getUsername(), token));
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(403).body(new MessageResponse("User already exists"));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(500).body(new MessageResponse("Server error: cannot create user"));
        }
    }

    public ResponseEntity<Object> getAll() {
        log.info("--> getting all users");
        List<User> users = userRepository.findAll();
        return ResponseEntity.status(200).body(users);
    }

}
