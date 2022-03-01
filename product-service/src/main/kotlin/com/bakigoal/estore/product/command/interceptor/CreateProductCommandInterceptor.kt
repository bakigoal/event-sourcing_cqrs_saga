package com.bakigoal.estore.product.command.interceptor

import com.bakigoal.estore.product.command.CreateProductCommand
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.messaging.MessageDispatchInterceptor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.util.function.BiFunction
import javax.annotation.PostConstruct

@Component
class CreateProductCommandInterceptor : MessageDispatchInterceptor<CommandMessage<*>> {

    companion object{
        val logger: Logger = LoggerFactory.getLogger(CreateProductCommandInterceptor::class.java)
    }

    @Autowired
    lateinit var commandBus: CommandBus

    @PostConstruct
    fun init() {
        commandBus.registerDispatchInterceptor(this)
    }

    override fun handle(messages: MutableList<out CommandMessage<*>>?) =
        BiFunction<Int, CommandMessage<*>, CommandMessage<*>> { index, command ->
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