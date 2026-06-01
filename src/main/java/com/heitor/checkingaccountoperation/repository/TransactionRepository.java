package com.heitor.checkingaccountoperation.repository;

import com.heitor.checkingaccountoperation.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccount(Integer account);

}