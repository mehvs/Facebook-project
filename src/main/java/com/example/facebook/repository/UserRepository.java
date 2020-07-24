package com.example.facebook.repository;

import com.example.facebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String email);
    Optional<User> findFirstByPassword(String password);

    Optional<User> findByEmail(String email);
    Optional<User> findByResetToken(String resetToken);

    @Query("SELECT u FROM User AS u WHERE u.firstName=:firstName")
    List<User> getAllByName(@Param("firstName") String firstName);
}
