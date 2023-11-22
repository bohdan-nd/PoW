package com.trspo.pool.service

class FillData {
    companion object {
        private var names = arrayListOf("Alex", "Matthew", "Andrew", "Lili", "Kate", "Stas", "Maria")
        private var operations = arrayListOf("sent", "lent")

        fun fillTransactionData(): String {
            val senderName = names.random()
            val receiverName = names.random()
            val operation = operations.random()
            val moneyAmount = (10..541).random()

            return "$senderName $operation $moneyAmount $ to $receiverName"
        }
    }
}