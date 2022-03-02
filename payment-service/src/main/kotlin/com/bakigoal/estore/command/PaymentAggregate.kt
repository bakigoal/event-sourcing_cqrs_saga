package com.bakigoal.estore.command

import com.bakigoal.estore.core.commands.ProcessPaymentCommand
import com.bakigoal.estore.core.events.PaymentProcessedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Aggregate
class PaymentAggregate() {

    @AggregateIdentifier
    private var paymentId: String = ""

    companion object{
        val logger: Logger = LoggerFactory.getLogger(PaymentAggregate::class.java)
    }

    @CommandHandler
    constructor(paymentCommand: ProcessPaymentCommand) : this() {
        logger.info("payment command handler: $paymentCommand")
        val event: PaymentProcessedEvent = paymentCommand.toProcessedEvent()
        AggregateLifecycle.apply(event)
    }


    @EventSourcingHandler
    fun on(paymentProcessedEvent: PaymentProcessedEvent) {
        paymentId = paymentProcessedEvent.paymentId
    }
}

private fun ProcessPaymentCommand.toProcessedEvent() = PaymentProcessedEvent(orderId, userId, paymentId)
