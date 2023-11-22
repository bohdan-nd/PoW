package com.trspo.blockchaing.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class MessageProducer {
    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate
    @Value("\${rabbitmq.message-exchange}")
    lateinit var messageExchange:String

    val logger: Logger = LoggerFactory.getLogger(MessageProducer::class.java)

    fun sendStartMessage(){
        rabbitTemplate.convertAndSend(messageExchange,"","send-message")
        logger.info("Send confirmation message to the network")
    }
}