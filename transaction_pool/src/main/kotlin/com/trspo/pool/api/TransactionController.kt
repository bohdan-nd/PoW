package com.trspo.pool.api

import com.trspo.pool.entity.Transaction
import com.trspo.pool.service.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("transactions")
class TransactionController {
    @Autowired
    lateinit var transactionService:TransactionService

    @GetMapping
    fun getPoolTransactions():List<Transaction>{
        return transactionService.getAllTransactions()
    }
}