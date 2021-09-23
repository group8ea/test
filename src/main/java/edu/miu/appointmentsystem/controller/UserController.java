package edu.miu.appointmentsystem.controller;

import edu.miu.appointmentsystem.domain.Appointment;
import edu.miu.appointmentsystem.domain.User;
import edu.miu.appointmentsystem.domain.enums.Role;
import edu.miu.appointmentsystem.dto.UserDTO;
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
    public ResponseEntity<?> getAllUsers() {
        Users users = new Users(userServiceImpl.getUsers());
        if (users != null)
            return new ResponseEntity<Users>(users, HttpStatus.OK);
        else
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Not authorized"), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        UserDTO userDTO = userServiceImpl.getUserById(id);
        if (userDTO == null) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("User with reference = "
                    + id.toString() + " is not available"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/users/{roleName}")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO, @PathVariable String roleName) {
        userServiceImpl.addUser(userDTO, roleName);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        UserDTO userDB = userServiceImpl.getUserById(id);
            userServiceImpl.updateUser(userDB);
        return new ResponseEntity<UserDTO>(userDB, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        UserDTO userDTO = userServiceImpl.getUserById(id);
        if (userDTO == null)
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("User with reference = "
                    + id.toString() + " is not available"), HttpStatus.NOT_FOUND);
        userServiceImpl.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/users/{id}/{roleName}/add")
    public ResponseEntity<?> addRole(@PathVariable Integer id, @PathVariable String roleName) {
        UserDTO userDTO = userServiceImpl.getUserById(id);
        if (userDTO == null)
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("User with reference = "
                    + id.toString() + " is not available"), HttpStatus.NOT_FOUND);
        boolean added = userServiceImpl.addRole(roleName, userDTO);
        if (added)
            return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
        else
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Role already exists"), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PutMapping("/users/{id}/{roleName}/remove")
    public ResponseEntity<?> removeRole(@PathVariable Integer id, @PathVariable String roleName) {
        UserDTO userDTO = userServiceImpl.getUserById(id);
        if (userDTO == null)
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("User with reference = "
                    + id.toString() + " is not available"), HttpStatus.NOT_FOUND);
        boolean added = userServiceImpl.removeRole(roleName, userDTO);
        if (added)
            return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
        else
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Role doesn't exist"), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
