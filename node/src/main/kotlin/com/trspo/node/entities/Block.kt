package com.trspo.node.entities

import com.trspo.grpc.block.BlockMessage
import com.trspo.grpc.block.BlockTransaction
import com.trspo.grpc.block.BlockTransactionList
import lombok.NoArgsConstructor
import java.sql.Timestamp

@NoArgsConstructor
data class Block(
        val previousHash: String,
        val transactionList: List<Transaction>,
        val accuracy: Int) {
    constructor() : this("", ArrayList<Transaction>(), 6)

    var nonce: Long = 0
    lateinit var timeStamp: Timestamp
    lateinit var hash: String
    lateinit var minerId: String

    fun getBlockHeader(): String {
        var blockHeader: String = previousHash

        for (transaction in transactionList)
            blockHeader += transaction.transactionHash

        return blockHeader
    }

    fun finishMining() {
        timeStamp = Timestamp(System.currentTimeMillis())
    }

    companion object{
        private fun transactionToBlockTrans(transaction:Transaction):BlockTransaction{
            return BlockTransaction.newBuilder()
                    .setId(transaction.id.toString())
                    .setData(transaction.data)
                    .build()
        }

        private fun transactionToBlockTransactionList(transactionList: List<Transaction>):BlockTransactionList{
            val blockTransactions = ArrayList<BlockTransaction>()
            for(transaction in transactionList){
                val blockTransaction = Block.transactionToBlockTrans(transaction)
                blockTransactions.add(blockTransaction)
            }

            return BlockTransactionList.newBuilder()
                    .addAllTransactions(blockTransactions)
                    .build()
        }

        fun toBlockMessage(block:Block):BlockMessage{
            val blockTransactionList = Block.transactionToBlockTransactionList(block.transactionList)

            return BlockMessage.newBuilder()
                    .setPreviousHash(block.previousHash)
                    .setAccuracy(block.accuracy)
                    .setTransactionList(blockTransactionList)
                    .setTimeStamp(block.timeStamp.toString())
                    .setMinerId(block.minerId)
                    .setHash(block.hash)
                    .setNonce(block.nonce)
                    .build()
        }
    }
}