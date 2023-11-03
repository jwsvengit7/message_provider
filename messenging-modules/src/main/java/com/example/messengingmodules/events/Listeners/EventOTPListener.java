package com.example.messengingmodules.events.Listeners;

import com.example.messengingmodules.config.MailConfig;
import com.example.messengingmodules.events.OtpEvents;
import com.example.messengingmodules.rabbitmq.quee_request.OtpQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;


@Component
@RequiredArgsConstructor
@Slf4j
public class EventOTPListener implements ApplicationListener<OtpEvents> {
    private final MailConfig mailConfig;
    private final TemplateEngine templateEngine;

    @Override
    public void onApplicationEvent(OtpEvents event) {
        OtpQueue otpQueue = event.getOtpQueue();
        try {
            sendEmail(otpQueue);
            log.info("{} ", otpQueue);
        } catch (Exception e) {
            log.error("An error occurred while sending email", e);
        }
    }

    private void sendEmail(OtpQueue otpQueue) throws UnsupportedEncodingException, MessagingException, jakarta.mail.MessagingException {
        String subject = "SIGNUP";
        String companyName = "APP_NAME";

        MimeMessage mimeMessage = mailConfig.customJavaMailSender().createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        messageHelper.setFrom("chiorlujack@gmail.com", companyName);
        messageHelper.setSubject(subject);
        messageHelper.setTo(otpQueue.getEmail());

        Context context = new Context();

        context.setVariable("user_name", otpQueue.getEmail());
        context.setVariable("company_name", "SMS-SERVICE-PROVIDER");
        context.setVariable("otp", otpQueue.getOtp());

        String mailTemplate = "otp";
        String mailContent = templateEngine.process(mailTemplate, context);

        messageHelper.setText(mailContent, true);

        mailConfig.customJavaMailSender().send(mimeMessage);

        log.info("Email sent successfully to: " + otpQueue.getEmail());
    }
}
