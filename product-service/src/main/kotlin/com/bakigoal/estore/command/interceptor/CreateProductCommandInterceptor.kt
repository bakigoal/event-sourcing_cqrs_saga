package com.bakigoal.estore.command.interceptor

import com.bakigoal.estore.command.CreateProductCommand
import com.bakigoal.estore.core.repo.ProductLookupRepo
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.messaging.MessageDispatchInterceptor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.function.BiFunction
import javax.annotation.PostConstruct

@Component
class CreateProductCommandInterceptor : MessageDispatchInterceptor<CommandMessage<*>> {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(CreateProductCommandInterceptor::class.java)
    }

    @Autowired
    lateinit var commandBus: CommandBus

    @Autowired
    lateinit var lookupRepo: ProductLookupRepo

    @PostConstruct
    fun init() {
        commandBus.registerDispatchInterceptor(this)
    }

    override fun handle(messages: MutableList<out CommandMessage<*>>?) =
        BiFunction<Int, CommandMessage<*>, CommandMessage<*>> { index, command ->
            val payload = command.payload
            logger.info("Intercepted $payload")
            if (payload is CreateProductCommand) {
                handleCreateProductCommand(payload)
            }
            command
        }

    private fun handleCreateProductCommand(createProductCommand: CreateProductCommand) {
        val fromDb = lookupRepo.findFirstByProductIdOrTitle(createProductCommand.productId, createProductCommand.title)
        if (fromDb.isPresent) {
            throw IllegalStateException("Product already exists ${fromDb.get()}")
        }
    }
}