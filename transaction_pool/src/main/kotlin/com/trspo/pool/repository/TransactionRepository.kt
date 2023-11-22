package com.trspo.pool.repository

import com.trspo.pool.entity.Transaction
import org.hibernate.LockMode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.util.*
import javax.transaction.Transactional

interface TransactionRepository: JpaRepository<Transaction,UUID> {

    @Transactional
    @Query("SELECT * FROM transaction WHERE mined = false GROUP BY id,RANDOM() LIMIT :amount",nativeQuery = true)
    fun getTransactionsBatch(amount:Int):List<Transaction>
}