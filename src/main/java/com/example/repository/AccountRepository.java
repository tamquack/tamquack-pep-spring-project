package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Account;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("SELECT acc.username FROM Account acc WHERE acc.username = ?1")
    Account findByUsername(String username);
    
}


