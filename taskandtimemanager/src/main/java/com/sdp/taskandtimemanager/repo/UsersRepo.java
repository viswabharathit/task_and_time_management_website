package com.sdp.taskandtimemanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import com.sdp.taskandtimemanager.model.Users;

public interface UsersRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    Users findManagerByEmail(String email);

    Users findUserByEmail(String email);

}