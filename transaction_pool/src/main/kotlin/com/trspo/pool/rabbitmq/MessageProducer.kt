package com.trspo.pool.rabbitmq

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Component
class MessageProducer {
    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate
    @Value("\${rabbitmq.message-exchange}")
    lateinit var messageExchange:String

    fun sendStartMessage(){
        rabbitTemplate.convertAndSend(messageExchange,"","send-message")
        print("Send message")
    }
}