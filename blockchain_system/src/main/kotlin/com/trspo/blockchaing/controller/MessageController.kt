package com.trspo.blockchaing.controller

import com.trspo.blockchaing.entity.Block
import com.trspo.blockchaing.repo.BlockChainRepository
import com.trspo.blockchaing.services.MessageProducer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("system")
class MessageController {
    @Autowired
    lateinit var blockChainRepository: BlockChainRepository

    @Autowired
    lateinit var messageProducer: MessageProducer

    @GetMapping("blockchain")
    fun getBlockchain(): List<Block> {
        return blockChainRepository.findAll()
    }

    @GetMapping("start")
    fun startSystem():String{
        messageProducer.sendStartMessage()
        return "System started. Have a good mining, sir."
    }
}