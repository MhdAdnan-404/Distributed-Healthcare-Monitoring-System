package com.mhd.accountManagement.repository;

import com.mhd.accountManagement.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);

    Boolean existsByUsername(String username);
}
