package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.entity.EmailDetail;
import com.avocado.ecomus.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImp implements EmailService {

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String email;

    @Override
    public void sendVerificationEmail(EmailDetail emailDetail, String templateName, Context context) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        String process = templateEngine.process(templateName, context);

        mimeMessageHelper.setFrom(email);
        mimeMessageHelper.setTo(emailDetail.getReceipient());
        mimeMessageHelper.setText(process, true);
        mimeMessageHelper.setSubject(emailDetail.getMsgSubject());

        mailSender.send(mimeMessage);
    }
}
