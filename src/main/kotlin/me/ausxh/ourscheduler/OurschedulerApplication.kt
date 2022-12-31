package me.ausxh.ourscheduler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class OurschedulerApplication

fun main(args: Array<String>) {
	runApplication<OurschedulerApplication>(*args)
}
