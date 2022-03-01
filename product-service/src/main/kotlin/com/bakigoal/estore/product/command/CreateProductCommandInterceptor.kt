package com.bakigoal.estore.product.command

import org.axonframework.commandhandling.CommandMessage
import org.axonframework.messaging.MessageDispatchInterceptor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.util.function.BiFunction

@Component
class CreateProductCommandInterceptor : MessageDispatchInterceptor<CommandMessage<Any>> {

    companion object{
        val logger: Logger = LoggerFactory.getLogger(CreateProductCommandInterceptor::class.java)
    }

    override fun handle(messages: MutableList<out CommandMessage<Any>>?) =
        BiFunction<Int, CommandMessage<Any>, CommandMessage<Any>> { index, command ->
            val payload = command.payload
            logger.info("Intercepted $payload")
            if (payload is CreateProductCommand){
                if (payload.price<=0) {
                    throw IllegalArgumentException("Price cannot be less than or equal to 0")
                }
                if (payload.title.isBlank()) {
                    throw IllegalArgumentException("Title cannot be blank")
                }
            }
            command
        }
}