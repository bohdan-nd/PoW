package com.trspo.node.configuration

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {
    @Value("\${rabbitmq.mined-block-queue}")
    lateinit var blockQueue: String

    @Value("\${rabbitmq.message-queue}")
    lateinit var messageQueue: String

    @Value("\${rabbitmq.block-exchange}")
    lateinit var blockExchange: String

    @Value("\${rabbitmq.message-exchange}")
    lateinit var messageExchange: String

    @Bean
    fun blockQueue(): Queue {
        return Queue(blockQueue, true, true, true)
    }

    @Bean
    fun messageQueue(): Queue {
        return Queue(messageQueue, false, true, true)
    }

    @Bean
    fun blockExchange(): FanoutExchange {
        return FanoutExchange(blockExchange)
    }

    @Bean
    fun messageExchange(): FanoutExchange {
        return FanoutExchange(messageExchange)
    }

    @Bean
    fun blockBinding(blockExchange: FanoutExchange, blockQueue: Queue): Binding {
        return BindingBuilder.bind(blockQueue).to(blockExchange)
    }

    @Bean
    fun messageBinding(messageExchange: FanoutExchange, messageQueue: Queue): Binding {
        return BindingBuilder.bind(messageQueue).to(messageExchange)
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = producerJackson2MessageConverter()
        return rabbitTemplate
    }

    @Bean
    fun producerJackson2MessageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }
}