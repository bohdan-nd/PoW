package com.trspo.blockchaing.entity

import com.trspo.grpc.block.BlockTransaction

data class Transaction(
        val id: String,
        val data: String) {
    override fun toString(): String {
        return String.format("\nTransaction:\nid -> %s\ndata -> %s", id, data)
    }

    companion object {
        fun toTransaction(blockTransaction: BlockTransaction): Transaction {
            return Transaction(blockTransaction.id, blockTransaction.data)
        }
    }
}