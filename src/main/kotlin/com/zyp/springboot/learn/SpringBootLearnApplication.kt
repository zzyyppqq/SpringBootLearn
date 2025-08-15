package com.zyp.springboot.learn

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootLearnApplication

fun main(args: Array<String>) {
    runApplication<SpringBootLearnApplication>(*args)
}
