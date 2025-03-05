package com.sdp.taskandtimemanager.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.OK;
import com.sdp.taskandtimemanager.auth.LoginRequest;
import com.sdp.taskandtimemanager.auth.RegisterRequest;
import com.sdp.taskandtimemanager.model.Users;
import com.sdp.taskandtimemanager.service.UsersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication")
@CrossOrigin(origins = "http://localhost:5173")
public class UsersController {

    @Autowired
    private UsersService service;

    @GetMapping("/findAll")
    public List<Users> findAll() {
        return service.findAllUsers();
    }

    @GetMapping("/current-id")
    public Long getCurrentUserId() {
        return service.getCurrentUserId();
    }

    @GetMapping("/mail")
    public Long getUserByEmail(@RequestParam String email) {
        return service.findByEmail(email);
    }

    @GetMapping("/findById/{userId}")
    public Users findById(@PathVariable Long userId) {
        return service.findUserById(userId);
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Allows users to register by providing necessary registration details.")
    public ResponseEntity<?> register(
            @Parameter(description = "Registration details of the user") @RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(service.register(registerRequest), OK);
    }

    @PostMapping("/register/pm")
    @Operation(summary = "Register a manager", description = "Allows Project Managers to register by providing necessary registration details.")
    public ResponseEntity<?> registerpm(
            @Parameter(description = "Registration details of the Project Manager") @RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(service.registerManager(registerRequest), OK);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Allows users to authenticate by providing valid login credentials.")
    public ResponseEntity<?> login(
            @Parameter(description = "Login credentials of the user") @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(service.login(loginRequest), OK);
    }

    @PutMapping("/update/{userId}")
    public Users update(@PathVariable Long userId, @RequestBody Users user) {
        return service.updateUser(userId, user);
    }

    @PatchMapping("/updateSpecific/{userId}")
    public Users patch(@PathVariable Long userId, @RequestBody Users user) {
        return service.patchUser(userId, user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Users> updateUser(@PathVariable Long userId, @RequestBody Users user) {
        Users updatedUser = service.updateUser(userId, user);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete/{userId}")
    public void delete(@PathVariable Long userId) {
        service.deleteUser(userId);
    }

}