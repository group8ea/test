package edu.miu.appointmentsystem.service;

import edu.miu.appointmentsystem.domain.*;
import edu.miu.appointmentsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("customUserService")
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        edu.miu.appointmentsystem.domain.User user = userRepository.findByUsername(username);
        if(user ==null){
            throw new UsernameNotFoundException(username);
        }
        UserDetails userDetails = User.withUsername(user.getUsername()).password(user.getPassword()).authorities("USER").build();
        return userDetails;
    }
}