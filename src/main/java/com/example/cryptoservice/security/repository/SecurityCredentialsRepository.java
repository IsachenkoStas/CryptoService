package com.example.cryptoservice.security.repository;

import com.example.cryptoservice.security.domain.SecurityCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityCredentialsRepository extends JpaRepository<SecurityCredentials, Long> {
    Optional<SecurityCredentials> getByLogin(String login);

    @Query(nativeQuery = true,
            value = "select user_id from security_credentials where login= ?1")
    Long findUserIdByLogin(String login);
}