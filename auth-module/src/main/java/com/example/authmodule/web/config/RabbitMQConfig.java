package com.example.authmodule.web.config;

import com.example.authmodule.domain.constant.Exchange;
import com.example.authmodule.domain.constant.QueueAmpq;
import com.example.authmodule.domain.constant.RoutingKey;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.example.authmodule.utils.Utils.DURABILITY;

@Configuration
public class RabbitMQConfig {
        @Bean
        @Qualifier("queue")
        public Queue queue() {
                return new Queue(QueueAmpq.OTP_QUEE.name(),false);
        }
        @Bean
        @Qualifier("queueForProfile")
        public Queue queueForProfile() {
                return new Queue(QueueAmpq.PROFILE_ACCESS.name(), DURABILITY);
        }
        @Bean
        @Qualifier("exchange_notification")
        public DirectExchange exchange() {
                return new DirectExchange(Exchange.OTP_EXCHANGE.name());
        }
        @Bean
        @Qualifier("exchange_profile")
        public DirectExchange exchangeProfile() {
                return new DirectExchange(Exchange.PROFILE_ACCESS.name());
        }
        @Bean
        public MessageConverter messageConverter() {
                return new Jackson2JsonMessageConverter();
        }

        @Bean
        @Qualifier("binding_notification")
        public Binding binding(Queue queue, DirectExchange exchange) {
                return BindingBuilder.bind(queue).to(exchange).with(RoutingKey.OTP_ROUTING_KEY.getRoutingKeyName());
        }
        @Bean
        @Qualifier("binding_profile")
        public Binding bindingProfile(Queue queue, DirectExchange exchange) {
                return BindingBuilder.bind(queue).to(exchange).with(RoutingKey.PROFILE_ROUTING_KEY.getRoutingKeyName());
        }

        @Bean
        public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
                RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
                rabbitTemplate.setMessageConverter(messageConverter());
                return rabbitTemplate;
        }


        @Bean
        @Qualifier("retry_notification")
        public Queue retryQueue() {
                Map<String, Object> args = new HashMap<>(3);
                args.put("x-dead-letter-exchange", Exchange.OTP_EXCHANGE.name());
                args.put("x-dead-letter-routing-key", RoutingKey.OTP_ROUTING_KEY.getRoutingKeyName());
                args.put("x-message-ttl", 5000);
                return new Queue(QueueAmpq.OTP_QUEE.name(),false);
        }

        @Bean
        @Qualifier("retry_profile")
        public Queue retryQueueProfile() {
                Map<String, Object> args = new HashMap<>();
                args.put("x-dead-letter-exchange", Exchange.PROFILE_ACCESS.name());
                args.put("x-dead-letter-routing-key", RoutingKey.PROFILE_ROUTING_KEY.getRoutingKeyName());
                args.put("x-message-ttl", 5000);
                return new Queue(QueueAmpq.PROFILE_ACCESS.name(),DURABILITY);
        }

}
