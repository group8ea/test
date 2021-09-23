package edu.miu.appointmentsystem.service;

import edu.miu.appointmentsystem.domain.*;
import edu.miu.appointmentsystem.dto.UserAdapter;
import edu.miu.appointmentsystem.dto.UserDTO;
import edu.miu.appointmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder ;

    @Override
    public UserDTO findByUsername(String username) {
        UserAdapter adapter = new UserAdapter();
        User user =  userRepository.findByUsername(username);
        return adapter.getUserDTO(user);
    }

    @Override
    public List<UserDTO> getUsers() {
        UserAdapter adapter = new UserAdapter();
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<UserDTO>();
        for (User user : users){
            userDTOS.add(adapter.getUserDTO(user));
        }
        return userDTOS;
    }

    @Override
    public UserDTO getUserById(Integer id) {
        UserAdapter adapter = new UserAdapter();
        User user = userRepository.findById(id).orElse(null);
        return adapter.getUserDTO(user);
    }
    @Override
    public void addUser(UserDTO userDTO, String roleName) {
        UserAdapter adapter = new UserAdapter();
        User user = adapter.getUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(new String []{roleName}));
        userRepository.save(user);
    }
    @Override
    public void updateUser(UserDTO userDTO) {
        UserAdapter adapter = new UserAdapter();
        User user = adapter.getUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean addRole(String roleName, UserDTO userDTO) {
        UserAdapter adapter = new UserAdapter();
        User user = adapter.getUser(userDTO);
        if (!user.isRole(roleName)) {
            user.getRoles().add(roleName);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeRole(String roleName, UserDTO userDTO) {
        UserAdapter adapter = new UserAdapter();
        User user = adapter.getUser(userDTO);
        if (user.isRole(roleName)) {
            user.getRoles().remove(roleName);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
