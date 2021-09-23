package edu.miu.appointmentsystem.dto;

import  edu.miu.appointmentsystem.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


public class UserAdapter {


    public User getUser(UserDTO userDTO){
        User user = new User();
        if (userDTO != null) {
            user = new User(
                    userDTO.getId(),
                    userDTO.getFirstName(),
                    userDTO.getLastName(),
                    userDTO.getEmail(),
                    userDTO.getGender(),
                    userDTO.getUsername(),
                    userDTO.getPassword(),
                    userDTO.getRoles(),
                    userDTO.getReservations(),
                    userDTO.getAppointments());
        }
        return user;
    }

    public UserDTO getUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        if (user != null) {
            userDTO = new UserDTO(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getGender(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getRoles(),
                    user.getReservations(),
                    user.getAppointments());
        }
        return userDTO;
    }
}
