package com.trspo.pool.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar

@Configuration
class SchedulerConfig : SchedulingConfigurer {

    override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
        val threadScheduler: ThreadPoolTaskScheduler = ThreadPoolTaskScheduler()
        threadScheduler.setThreadNamePrefix("transaction-generation")
        threadScheduler.initialize()
        taskRegistrar.setTaskScheduler(threadScheduler)
    }

}