package edu.miu.appointmentsystem.service;


import edu.miu.appointmentsystem.domain.*;
import java.util.List;


public interface UserService {

    User findByUsername(String username) ;

    List<User> getUsers(User admin) ;

    User getUserById(Integer id, User admin) ;

    User saveUser(User user, User admin) ;

    boolean deleteUser(Integer id, User admin) ;

    boolean addRole(String roleName, User user, User admin) ;

    boolean removeRole(String roleName, User user, User admin) ;
}
