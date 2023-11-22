package com.trspo.pool.service

import com.trspo.pool.entity.Transaction
import com.trspo.pool.repository.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionService {
    @Autowired
    lateinit var repository:TransactionRepository

    fun getAllTransactions():List<Transaction>{
        return repository.findAll()
    }
}