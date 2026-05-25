package com.abhinav.College.Discovery.Platform.Repository;

import com.abhinav.College.Discovery.Platform.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users,Long> {
    Optional<Users> findByusername(String username);
}
