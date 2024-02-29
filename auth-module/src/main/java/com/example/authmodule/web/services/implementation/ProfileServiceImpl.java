package com.example.authmodule.web.services.implementation;

import com.sms.smscommonsmodule.constant.*;
import com.example.authmodule.domain.entity.Customer;
import com.example.authmodule.messaging_quee.rabbitmq.queue_pjo.ProfileRequestQueue;
import com.example.authmodule.messaging_quee.rabbitmq.sender.RabbitmqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileServiceImpl {

    private final RabbitmqService<ProfileRequestQueue> rabbitmqService;

    public void sendRabbitmqProfile(Customer customer){
        ProfileRequestQueue profileRequestQueue = ProfileRequestQueue.builder()
                .role(customer.getRoles().name())
                .fullname(customer.getFullname())
                .id(customer.getId())
                .email(customer.getEmail())
                .build();
        rabbitmqService.sendRabbitmq_message(Exchange.PROFILE_ACCESS.name(), RoutingKey.PROFILE_ROUTING_KEY.getRoutingKeyName(), profileRequestQueue);

    }
}
