package com.example.cryptoservice.repository;

import com.example.cryptoservice.domain.TransactionFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionFeeRepository extends JpaRepository<TransactionFee, Long> {

}
