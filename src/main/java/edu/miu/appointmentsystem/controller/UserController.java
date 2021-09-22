package edu.miu.appointmentsystem.controller;

import edu.miu.appointmentsystem.domain.Appointment;
import edu.miu.appointmentsystem.domain.User;
import edu.miu.appointmentsystem.domain.enums.Role;
import edu.miu.appointmentsystem.exception.CustomErrorType;
import edu.miu.appointmentsystem.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(Authentication authentication) {
        User user = userServiceImpl.findByUsername(authentication.getName());
        Users users = new Users(userServiceImpl.getUsers(user));
        if (users != null)
            return new ResponseEntity<Users>(users, HttpStatus.OK);
        else
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Not authorized"), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(Authentication authentication, @PathVariable Integer id) {
        User admin = userServiceImpl.findByUsername(authentication.getName());
//        if (!admin.isAdmin())
//            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Not authorized"), HttpStatus.METHOD_NOT_ALLOWED);
        User user = userServiceImpl.getUserById(id, admin);
        if (user == null) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("User with reference = "
                    + id.toString() + " is not available"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(Authentication authentication, @RequestBody User user) {
        System.out.println("test");
        User admin = userServiceImpl.findByUsername(authentication.getName());
        User created = userServiceImpl.saveUser(user, admin);
        if (created != null)
            return new ResponseEntity<User>(user, HttpStatus.OK);
        else
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Not authorized"), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(Authentication authentication, @PathVariable Integer id, @RequestBody User user) {
        User admin = userServiceImpl.findByUsername(authentication.getName());
        User updated = userServiceImpl.saveUser(user, admin);
        System.out.println(admin.isAdmin());
        if (updated != null)
            return new ResponseEntity<User>(user, HttpStatus.OK);
        else
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Not authorized"), HttpStatus.METHOD_NOT_ALLOWED);

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(Authentication authentication, @PathVariable Integer id) {
        User admin = userServiceImpl.findByUsername(authentication.getName());
        User user = userServiceImpl.getUserById(id, admin);
        if (user == null)
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("User with reference = "
                    + id.toString() + " is not available"), HttpStatus.NOT_FOUND);
        boolean deleted = userServiceImpl.deleteUser(id, user);
        if (deleted)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Not authorized"), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping("/users/{id}/{roleName}/add")
    public ResponseEntity<?> addRole(Authentication authentication, @PathVariable Integer id, @PathVariable String roleName) {
        User admin = userServiceImpl.findByUsername(authentication.getName());
//        if (!admin.isAdmin())
//            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Not authorized"), HttpStatus.METHOD_NOT_ALLOWED);
        User user = userServiceImpl.getUserById(id, admin);
        if (user == null)
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("User with reference = "
                    + id.toString() + " is not available"), HttpStatus.NOT_FOUND);
//        if (roleName != "ADMIN" || roleName != "PROVIDER" || roleName != "CLIENT")
//            return new ResponseEntity<CustomErrorType>(new CustomErrorType("The role added is not allowed"), HttpStatus.NOT_FOUND);
        boolean added = userServiceImpl.addRole(roleName, user, admin);
        if (added)
            return new ResponseEntity<User>(user, HttpStatus.OK);
        else
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Not authorized"), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping("/users/{id}/{roleName}/remove")
    public ResponseEntity<?> removeRole(Authentication authentication, @PathVariable Integer id, @PathVariable String roleName) {
        User admin = userServiceImpl.findByUsername(authentication.getName());
//        if (!admin.isAdmin())
//            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Not authorized"), HttpStatus.METHOD_NOT_ALLOWED);
        User user = userServiceImpl.getUserById(id, admin);
        if (user == null)
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("User with reference = "
                    + id.toString() + " is not available"), HttpStatus.NOT_FOUND);
        if (roleName != "ADMIN" || roleName != "PROVIDER" || roleName != "CLIENT")
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("The role added is not allowed"), HttpStatus.NOT_FOUND);
        boolean added = userServiceImpl.removeRole(roleName, user, admin);
        if (added)
            return new ResponseEntity<User>(user, HttpStatus.OK);
        else
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Not authorized"), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
