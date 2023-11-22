package com.trspo.pool.service

import com.trspo.pool.entity.Transaction
import com.trspo.pool.repository.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class TransactionGeneratorService {
    @Autowired
    lateinit var transactionRepository: TransactionRepository

    @Scheduled(fixedRate = 4000)
    fun generateTransactions() {
        val transactionData = FillData.fillTransactionData()
        val newTransaction = Transaction(data = transactionData)
        transactionRepository.save(newTransaction)
    }
}