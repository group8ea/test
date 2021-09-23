package edu.miu.appointmentsystem.service;


import edu.miu.appointmentsystem.domain.*;
import edu.miu.appointmentsystem.dto.UserDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface UserService {

    UserDTO findByUsername(String username) ;

    List<UserDTO> getUsers() ;

    UserDTO getUserById(Integer id) ;

    void addUser(UserDTO userDTO, String roleName) ;

    void updateUser(UserDTO userDTO) ;

    void deleteUser(Integer id) ;

    boolean addRole(String roleName, UserDTO userDTO) ;

    boolean removeRole(String roleName, UserDTO userDTO) ;
}
