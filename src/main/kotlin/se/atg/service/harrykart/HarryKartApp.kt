package se.atg.service.harrykart

import org.springframework.boot.autoconfigure.SpringBootApplication
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication

@SpringBootApplication
class HarryKartApp {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(HarryKartApp::class.java, *args)
        }

    }
}