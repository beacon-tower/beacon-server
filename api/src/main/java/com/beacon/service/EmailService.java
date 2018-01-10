package com.beacon.service;

import com.beacon.commons.base.BaseDao;
import com.beacon.commons.base.BaseService;
import com.beacon.dao.EmailLogDao;
import com.beacon.entity.EmailLog;
import com.beacon.enums.dict.EmailLogDict;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件发送
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/10
 */
@Service
public class EmailService extends BaseService<EmailLog, Integer> {

    @Inject
    private EmailLogDao emailLogDao;

    @Inject
    private JavaMailSender javaMailSender;

    @Override
    public BaseDao<EmailLog, Integer> getBaseDao() {
        return this.emailLogDao;
    }

    /**
     * 发送邮件
     *
     * @param sendEmail 发送者
     * @param receiveEmail 接收者
     * @param title 邮件标题
     * @param content 邮件内容
     * @return 是否发送成功
     */
    private Boolean send(String sendEmail, String receiveEmail, String title, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sendEmail);
            helper.setTo(receiveEmail);
            helper.setSubject(title);
            helper.setText(content);
            javaMailSender.send(mimeMessage);
            return Boolean.TRUE;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 所有发送邮件，统一调用该方法，记录发送日志
     *
     * @param type 业务类型
     * @param sendEmail 发送者
     * @param receiveEmail 接收者
     * @param title 邮件标题
     * @param content 邮件内容
     * @return 是否发送成功
     */
    private Boolean send(String type, String sendEmail, String receiveEmail, String title, String content) {
        Boolean sendState = this.send(sendEmail, receiveEmail, title, content);
        EmailLog emailLog = new EmailLog();
        emailLog.setSendEmail(sendEmail);
        emailLog.setReceiveEmail(receiveEmail);
        emailLog.setType(type);
        emailLog.setTitle(title);
        emailLog.setContent(content);
        emailLog.setState(String.valueOf(sendState ? EmailLogDict.STATE_SUCCESS : EmailLogDict.STATE_FAIL));
        super.save(emailLog);
        return sendState;
    }
}
