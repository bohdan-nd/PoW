package com.trspo.node.controllers

import com.trspo.node.entities.Block
import com.trspo.node.services.MinerService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Consumer {
    @Autowired
    lateinit var minerService: MinerService

    @RabbitListener(queues = ["\${rabbitmq.mined-block-queue}"])
    fun validateBlock(block: Block) {
        minerService.validateBlock(block)
    }

    @RabbitListener(queues = ["\${rabbitmq.message-queue}"])
    fun startMiningStage(a: String) {
        minerService.startMiningStage(a)
    }
}