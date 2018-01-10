package com.beacon.api;

import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 测试邮件发送
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/10
 */
public class EmailSendTest extends BaseTest {

    @Inject
    private JavaMailSender javaMailSender;

    @Test
    public void sendMail() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("670042085@qq.com");
        helper.setTo("zgh11051121@163.com");
        helper.setSubject("主题：有附件");
        helper.setText("有附件的邮件");
        javaMailSender.send(mimeMessage);
    }
}
