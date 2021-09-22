package edu.miu.appointmentsystem.service;

import edu.miu.appointmentsystem.domain.*;
import edu.miu.appointmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers(User admin) {
//        if (admin.isAdmin() ) {
            return userRepository.findAll();
//        }
//        return null;
    }

    @Override
    public User getUserById(Integer id, User admin) {
//        if (admin.isAdmin() ) {
            return userRepository.findById(id).orElse(null);
//        }
//        return null;
    }

    @Override
    public User saveUser(User user, User admin) {
//        if (admin.isAdmin() ) {
            return userRepository.save(user);
//        }
//        return null;
    }

    @Override
    public boolean deleteUser(Integer id, User admin) {
//        if (admin.isAdmin() ) {
            userRepository.deleteById(id);
            return true;
//        }
//        return false;
    }

    @Override
    public boolean addRole(String roleName, User user, User admin) {
//        if (admin.isAdmin() ) {
            if(!user.isRole(roleName))
            {
                user.getRoles().add(roleName);
                return true;
            }
//        }
        return false;
    }

    @Override
    public boolean removeRole(String roleName, User user, User admin) {
//        if (admin.isAdmin() ) {
            if(user.isRole(roleName))
            {
                user.getRoles().remove(roleName);
                return true;
            }
//        }
        return false;
    }
}
