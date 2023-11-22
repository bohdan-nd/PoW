package com.trspo.pool

import com.trspo.pool.rabbitmq.MessageProducer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class PoolApplication

fun main(args: Array<String>) {
    runApplication<PoolApplication>(*args)
}
