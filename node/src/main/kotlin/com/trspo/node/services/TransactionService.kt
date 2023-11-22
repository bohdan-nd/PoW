package com.trspo.node.services

import com.trspo.grpc.transaction.TransactionAmountRequest
import com.trspo.grpc.transaction.TransactionBatchRequest
import com.trspo.grpc.transaction.TransactionBatchResponse
import com.trspo.grpc.transaction.TransactionServiceGrpc
import com.trspo.grpc.transaction.TransactionServiceGrpc.TransactionServiceBlockingStub
import com.trspo.node.entities.Transaction
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.stereotype.Service

@Service
class TransactionService {
    private val url: String = "pool-pow"
    private val channel: ManagedChannel = ManagedChannelBuilder.forAddress(url, 9090)
            .usePlaintext()
            .build()
    private val stub: TransactionServiceBlockingStub = TransactionServiceGrpc.newBlockingStub(channel)

    fun getTransactions(transactionAmount: Int): List<Transaction> {
        val transactionAmountRequest: TransactionAmountRequest = TransactionAmountRequest.newBuilder()
                .setTransactionAmount(transactionAmount)
                .build()

        val transactionBatch: TransactionBatchResponse = stub.getTransactions(transactionAmountRequest)

        return Transaction.fromTransactionBatch(transactionBatch)
    }

    fun markTransactionMined(transactions: List<Transaction>) {
        val returnRequest: TransactionBatchRequest = Transaction.toReturnRequest(transactions)

        stub.markTransactionMined(returnRequest)
    }

}