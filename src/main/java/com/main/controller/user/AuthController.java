package com.main.controller.user;

import com.main.dto.create_edit.user.UserCreateEditDto;

import com.main.dto.read.user.UserActivity;
import com.main.dto.security.JwtResponse;
import com.main.dto.security.MessageResponse;

import com.main.entity.message.Message;
import com.main.entity.user.ERole;
import com.main.entity.user.Role;
import com.main.entity.user.User;

import com.main.repository.user.RoleRepository;
import com.main.repository.user.UserRepository;

import com.main.security.jwt.JwtUtils;
import com.main.security.services.UserDetailsImpl;

import com.main.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketHttpHeaders;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserCreateEditDto loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // Установим заголовок авторизации для веб-сокета
        WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
        System.out.println("Jwt user: " + jwt);
        handshakeHeaders.add("Authorization", "Bearer " + jwt);
        System.out.println("WebsocketHttpHeaders: " + handshakeHeaders);

        // Обновим активность пользователя, что он в сети мессенджера
        UserActivity userActivity = new UserActivity();
        userActivity.setOnline(loginRequest.isOnline());
        userActivity.setLastActive(loginRequest.getLastActive());

        userService.updateOnlineUser(userDetails.getId(), userActivity);

        System.out.println("ONLINE: " + loginRequest.isOnline() + ", Last Active: " + loginRequest.getLastActive());

        return ResponseEntity.ok(
                new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles)
            );
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserCreateEditDto signUpRequest) {

        System.out.println("SignUpRequest: " + signUpRequest);

        List<String> messageResponses = new ArrayList<>();

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            messageResponses.add("Username is already taken!");
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {

            messageResponses.add("Email is already in use!");
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        if(messageResponses.size() > 1) {
            return ResponseEntity.badRequest().body(messageResponses);
        }

        // Create new user's account
        User user = new User(
                        signUpRequest.getUsername(),
                        signUpRequest.getEmail(),
                        encoder.encode(signUpRequest.getPassword())
                    );

        List<String> strRoles = signUpRequest.getRoles();
        List<Role> roles = new ArrayList<>();

        System.out.println("strRoles: " + strRoles);

        System.out.println("Role repository: " + roleRepository.findByName(ERole.ROLE_USER));

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        System.out.println("Admin Role: " + adminRole);
                        System.out.println("Roles: " + roles);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                        System.out.println("User Role: " + userRole);
                        System.out.println("Roles: " + roles);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}

