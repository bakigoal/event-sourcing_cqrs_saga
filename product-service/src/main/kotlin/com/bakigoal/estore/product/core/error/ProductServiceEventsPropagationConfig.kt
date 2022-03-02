package com.bakigoal.estore.product.core.error

import org.axonframework.config.EventProcessingConfigurer
import org.axonframework.eventhandling.PropagatingErrorHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class ProductServiceEventsPropagationConfig {

    @Autowired
    lateinit var config: EventProcessingConfigurer

    @PostConstruct
    fun init() {
        config.registerListenerInvocationErrorHandler("product-group") {
            PropagatingErrorHandler.instance()
        }
    }
}