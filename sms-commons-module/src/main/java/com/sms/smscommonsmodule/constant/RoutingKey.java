package com.sms.smscommonsmodule.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoutingKey {
    OTP_ROUTING_KEY("otp_#"),
    PROFILE_ROUTING_KEY("profile_#");

    private String routingKeyName;

}
