package com.cloudnativewebapp.webapp.Repository;

import com.cloudnativewebapp.webapp.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public User findByUsername(String userName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET first_name = ?1, last_name = ?2, password = ?3, account_updated = ?4 WHERE username = ?5",
                nativeQuery = true)
    int updateUserbyUsername(String first_name, String last_name,
                             String password, String account_updated,
                             String username);

}
