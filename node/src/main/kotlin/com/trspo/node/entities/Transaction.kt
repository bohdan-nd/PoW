package com.trspo.node.entities

import com.google.common.hash.Hashing.sha256
import com.trspo.grpc.transaction.TransactionBatchRequest
import com.trspo.grpc.transaction.TransactionBatchResponse
import com.trspo.grpc.transaction.TransactionMessage
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.ArrayList

data class Transaction(
        val id: UUID,
        val data: String) {
    constructor() : this(UUID.randomUUID(), "")

    val transactionHash: String = hashData()
    private fun hashData(): String {
        val totalData: String = data + id

        return sha256()
                .hashString(totalData, StandardCharsets.UTF_8)
                .toString()
    }

    override fun toString(): String {
        return String.format("\nTransaction:\nid -> %s\ndata -> %s", id, data)
    }

    companion object {
        fun fromTransactionMessage(response: TransactionMessage): Transaction {
            val transactionId: UUID = UUID.fromString(response.id)
            return Transaction(transactionId, response.data)
        }

        fun fromTransactionBatch(response: TransactionBatchResponse): List<Transaction> {
            val transactions: MutableList<Transaction> = ArrayList()

            for (transactionMessage in response.transactionsList) {
                val newTransaction = fromTransactionMessage(transactionMessage)
                transactions.add(newTransaction)
            }
            return transactions
        }

        fun toTransactionMessages(transactions: List<Transaction>): MutableList<TransactionMessage> {
            val transactionMessageList: MutableList<TransactionMessage> = ArrayList()

            for (transaction in transactions) {
                val transactionMessage = transaction.toTransactionMessage()
                transactionMessageList.add(transactionMessage)
            }

            return transactionMessageList
        }

        fun toReturnRequest(transactions: List<Transaction>): TransactionBatchRequest {
            val transactionMessageList: MutableList<TransactionMessage> = Transaction.toTransactionMessages(transactions)

            return TransactionBatchRequest.newBuilder()
                    .addAllTransactions(transactionMessageList)
                    .build()
        }
    }

    fun toTransactionMessage(): TransactionMessage {
        return TransactionMessage.newBuilder()
                .setId(id.toString())
                .setData(data)
                .build()
    }
}