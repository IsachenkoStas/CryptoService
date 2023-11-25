package com.example.cryptoservice.repository;

import com.example.cryptoservice.domain.CryptoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoUserRepository extends JpaRepository<CryptoUser, Long> {

}
