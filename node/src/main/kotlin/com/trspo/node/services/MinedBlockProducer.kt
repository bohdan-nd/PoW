package com.trspo.node.services

import com.trspo.node.entities.Block
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


@Service
class MinedBlockProducer {
    @Autowired
    lateinit var transactionService: TransactionService
    @Autowired
    lateinit var blockService:BlockService
    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate
    @Value("\${rabbitmq.block-exchange}")
    lateinit var blockExchange: String

    val logger: Logger = LoggerFactory.getLogger(MinedBlockProducer::class.java)

    fun sendMinedBlockToNetwork(minedBlock: Block) {
        rabbitTemplate.convertAndSend(blockExchange, "", minedBlock)
        logger.info("Sent block to the network")
        markPoolTransactionsMined(minedBlock)
        blockService.sendMinedBlockToStorage(minedBlock)
    }

    fun markPoolTransactionsMined(minedBlock: Block){
        val transactions = minedBlock.transactionList
        transactionService.markTransactionMined(transactions)
    }
}