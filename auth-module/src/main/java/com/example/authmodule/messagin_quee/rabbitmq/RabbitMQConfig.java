package com.example.authmodule.messagin_quee.rabbitmq;

import com.example.authmodule.domain.constant.QueueAmpq;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {


        @Value("${rabbitmq.exchange.name}")
        public String EXCHANGE_NAME;

        @Value("${rabbitmq.routing.key}")
        public String ROUTING_KEY;

        @Bean
        public Queue queue() {
                return new Queue(QueueAmpq.OTP_EXCHNAGE.name(), false);
        }

        @Bean
        public DirectExchange exchange() {
                return new DirectExchange(EXCHANGE_NAME);
        }

        @Bean
        public MessageConverter messageConverter() {
                return new Jackson2JsonMessageConverter();
        }

        @Bean
        public Binding binding(Queue queue, DirectExchange exchange) {
                return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
        }

        @Bean
        public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
                RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
                rabbitTemplate.setMessageConverter(messageConverter());
                return rabbitTemplate;
        }

        @Bean
        public Queue retryQueue() {
                Map<String, Object> args = new HashMap<>();
                args.put("x-dead-letter-exchange", EXCHANGE_NAME);
                args.put("x-dead-letter-routing-key", QueueAmpq.OTP_EXCHNAGE.name());
                args.put("x-message-ttl", 5000);
                return new Queue(QueueAmpq.OTP_EXCHNAGE.name(), false);
        }
}
