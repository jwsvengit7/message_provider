package com.example.authmodule.messaging_quee.rabbitmq.sender;

import com.example.authmodule.messaging_quee.rabbitmq.RabbitMQConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitmqService {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;

    public void sendRabbitmq_message(String exchange,String routingKey,Object payload){
        log.info("{}",payload);
        rabbitTemplate.convertAndSend(exchange,
                routingKey,
                payload);
    }

}
