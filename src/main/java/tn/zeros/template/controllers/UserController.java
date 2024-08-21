package tn.zeros.template.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.zeros.template.entities.User;
import tn.zeros.template.services.IServices.IUserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@Slf4j
public class UserController {
    public final IUserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.retrieveAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Récupérer un utilisateur par son ID
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.retrieveUser(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   /* @GetMapping("/userByemail")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.loadUserByEmail(email);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
   @GetMapping("/search")
   public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
       log.info("Searching for user with email: {}", email);
       User user = userService.loadUserByEmail(email);
       if (user != null) {
           return ResponseEntity.ok(user);
       } else {
           return ResponseEntity.notFound().build();
       }
   }

    // Ajouter un nouvel utilisateur
    @PostMapping("/add-user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        if (newUser != null) {
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Supprimer un utilisateur
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.removeUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Modifier un utilisateur
    @PutMapping("/update-user/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.modifyUser(user);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        return userService.loadUserByEmail(currentEmail);
    }
    */

}
