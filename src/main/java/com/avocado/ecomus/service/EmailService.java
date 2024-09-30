package com.avocado.ecomus.service;

import com.avocado.ecomus.entity.EmailDetail;
import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface EmailService {
    void sendVerificationEmail(EmailDetail emailDetail, String templateName, Context context) throws MessagingException;
}
