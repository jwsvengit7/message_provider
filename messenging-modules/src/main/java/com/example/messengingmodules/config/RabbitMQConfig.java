package com.example.messengingmodules.config;

import com.example.messengingmodules.constant.Exchange;
import com.example.messengingmodules.constant.QueueAmpq;
import com.example.messengingmodules.constant.RoutingKey;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.example.messengingmodules.utils.Utils.DURABILITY;

@Configuration
public class RabbitMQConfig {
        @Bean
        public Queue queue() {
                return new Queue(QueueAmpq.OTP_QUEE.name(),DURABILITY);
        }
        @Bean
        public DirectExchange exchange() {
                return new DirectExchange(Exchange.OTP_EXCHANGE.name());
        }
        @Bean
        public MessageConverter messageConverter() {
                return new Jackson2JsonMessageConverter();
        }

        @Bean
        public Binding binding(Queue queue, DirectExchange exchange) {
                return BindingBuilder.bind(queue).to(exchange).with(RoutingKey.OTP_ROUTING_KEY.getRoutingKeyName());
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
                args.put("x-dead-letter-exchange", Exchange.OTP_EXCHANGE);
                args.put("x-dead-letter-routing-key", RoutingKey.OTP_ROUTING_KEY.getRoutingKeyName());
                args.put("x-message-ttl", 5000);
                return new Queue(QueueAmpq.OTP_QUEE.name(),DURABILITY);
        }
}
