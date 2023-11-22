package com.trspo.pool.api

import com.google.protobuf.Empty
import com.trspo.grpc.transaction.TransactionAmountRequest
import com.trspo.grpc.transaction.TransactionBatchRequest
import com.trspo.grpc.transaction.TransactionBatchResponse
import com.trspo.grpc.transaction.TransactionServiceGrpc
import com.trspo.pool.entity.Transaction
import com.trspo.pool.repository.TransactionRepository
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

@GrpcService
class TransactionHandlerService : TransactionServiceGrpc.TransactionServiceImplBase() {
    @Autowired
    lateinit var transactionRepository: TransactionRepository

    override fun getTransactions(transactionAmountRequest: TransactionAmountRequest, responseObserver: StreamObserver<TransactionBatchResponse>) {
        val transactionAmount: Int = transactionAmountRequest.transactionAmount
        val transactions = transactionRepository.getTransactionsBatch(transactionAmount)
        val batchResponse = Transaction.toTransactionBatchResponse(transactions)

        responseObserver.onNext(batchResponse)
        responseObserver.onCompleted()
    }

    override fun markTransactionMined(request: TransactionBatchRequest, responseObserver: StreamObserver<Empty>) {
        val transactionsToAdd: List<Transaction> = Transaction.fromTransactionBatch(request)

        for (transaction in transactionsToAdd) {
            val findTransaction: Transaction = transactionRepository.findByIdOrNull(transaction.id) ?: continue
            findTransaction.mined = true
            transactionRepository.save(findTransaction)
        }

        responseObserver.onNext(Empty.newBuilder().build())
        responseObserver.onCompleted()
    }
}