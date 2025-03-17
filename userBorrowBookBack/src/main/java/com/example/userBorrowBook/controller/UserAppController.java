package com.example.userBorrowBook.controller;

import com.example.userBorrowBook.model.UserApp;
import com.example.userBorrowBook.repository.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserAppController {

    @Autowired
    private UserAppRepository userAppRepository;

    @GetMapping
    public ResponseEntity<List<UserApp>> getAllUsers() {
        List<UserApp> users = userAppRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserApp> getUserById(@PathVariable String id) {
        Optional<UserApp> user = userAppRepository.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<UserApp> createUser(@RequestBody UserApp user) {
        UserApp savedUser = userAppRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserApp> updateUser(@PathVariable String id, @RequestBody UserApp userDetails) {
        Optional<UserApp> optionalUser = userAppRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserApp user = optionalUser.get();
            user.setUserAppName(userDetails.getUserAppName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setAge(userDetails.getAge());
            user.setAddress(userDetails.getAddress());
            user.setArchived(userDetails.isArchived());
            user.setDob(userDetails.getDob());
            UserApp updatedUser = userAppRepository.save(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String id) {
        try {
            userAppRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserApp>> getUsersByName(@PathVariable String name) {
        List<UserApp> users = userAppRepository.findByUserAppNameContaining(name);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserApp> getUserByEmail(@PathVariable String email) {
        UserApp user = userAppRepository.findByEmail(email);
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<List<UserApp>> getUsersByAge(@PathVariable int age) {
        List<UserApp> users = userAppRepository.findByAge(age);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
