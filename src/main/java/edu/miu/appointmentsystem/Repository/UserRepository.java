package edu.miu.appointmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import edu.miu.appointmentsystem.domain.User;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository <User,Integer> {

    User findByUsername(String username) ;
}
