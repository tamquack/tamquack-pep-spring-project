package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Account;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("SELECT acc.username FROM Account acc WHERE acc.username = ?1")
    String findByUsername(String username);

    @Query("SELECT acc FROM Account acc WHERE acc.username = ?1 AND acc.password = ?2")
    Account accountLogin(String username, String password);
    
}


