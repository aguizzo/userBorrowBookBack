package com.example.userBorrowBook.repository;

import com.example.userBorrowBook.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp, String> {
    UserApp findByEmail(String email);

    List<UserApp> findByAge(int age);

    List<UserApp> findByUserAppNameContaining(String name);
    // You can add custom query methods here if needed
    // For example:
    // Optional<UserApp> findByUsername(String username);
    // List<UserApp> findByEmail(String email);
}
