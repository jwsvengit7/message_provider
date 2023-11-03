package com.example.messengingmodules.events;

import com.example.messengingmodules.rabbitmq.quee_request.OtpQueue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class OtpEvents extends ApplicationEvent {
    private OtpQueue otpQueue;
    public OtpEvents(OtpQueue otpQueue) {
        super(otpQueue);
        this.otpQueue=otpQueue;

    }
}
