package com.example.messengingmodules.rabbitmq.services;

import com.example.messengingmodules.Queue.Rabbitmq;
import com.example.messengingmodules.events.OtpEvents;
import com.example.messengingmodules.rabbitmq.quee_request.OtpQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpListeners {
    private final ApplicationEventPublisher applicationEventPublisher;

    @RabbitListener(queues = "OTP_QUEE")
    public void listeners(OtpQueue message) {
        sendMail(message);
    }
    private void sendMail(OtpQueue message){
        log.info("{} ", message);
        applicationEventPublisher.publishEvent(new OtpEvents(message));
    }
}
