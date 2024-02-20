package com.example.authmodule.messaging_quee.rabbitmq.sender;

import com.example.authmodule.web.config.RabbitMQConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitmqService<T> {
    private final RabbitTemplate rabbitTemplate;

    public void sendRabbitmq_message(String exchange,String routingKey,T payload){
        log.info("{}",payload);
        rabbitTemplate.convertAndSend(exchange,
                routingKey,
                payload);
    }

}
