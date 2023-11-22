package com.trspo.blockchaing.entity

import com.trspo.grpc.block.AddBlockRequest
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Block")
@NoArgsConstructor
data class Block(
        val previousHash: String,
        @Id
        val hash: String,
        val accuracy: Int,
        val nonce: Long,
        val timestamp: String,
        val transactionList: List<Transaction>,
        val minerId: String) {

    companion object {
        fun fromRequestToBlock(request: AddBlockRequest): Block {
            val blockMessage = request.minedBlock
            val blockTransactions = blockMessage.transactionList.transactionsList
            var transactionList: ArrayList<Transaction> = ArrayList()

            for (blockTrans in blockTransactions) {
                val transaction = Transaction.toTransaction(blockTrans)
                transactionList.add(transaction)
            }

            return Block(blockMessage.previousHash,
                    blockMessage.hash,
                    blockMessage.accuracy,
                    blockMessage.nonce,
                    blockMessage.timeStamp, transactionList, blockMessage.minerId)

        }
    }
}