package com.dayee.writtenExam.console.logic.email;

import com.dayee.writtenExam.console.dao.email.EmailConfigDao;
import com.dayee.writtenExam.console.model.constants.Constants;
import com.dayee.writtenExam.console.model.exception.ExamBaseException;
import com.dayee.writtenExam.framework.util.CollectionUtils;
import com.dayee.writtenExam.framework.util.StringUtils;
import com.dayee.writtenExam.model.config.MailConfig;
import com.dayee.writtenExam.model.email.Attachment;
import com.dayee.writtenExam.model.email.EmailConfig;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.mail.ByteArrayDataSource;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
@Component
public class EmailLogic {

    private static final Logger logger        = LoggerFactory
            .getLogger(EmailLogic.class);

    public static final int     TRY_SEND_TIME = 2;

    public static MailConfig consoleConfig = new MailConfig();

    @Autowired
    private EmailConfigDao emailConfigDao;

    static {
        // FIXME 从配置文件中获取
        /*
         * String fromName = "员工宝"; String address = "test@dayee.com"; String
         * userName = "zpmail"; String password = "B4vQaBRvww2K6NwW"; String
         * smtpServer = "smtpcloud.sohu.com";
         */
        /*String fromName = ConfigUtils
                .getProperty(Constants.EMAIL_CONFIG_SENDNAME);
        String address = ConfigUtils
                .getProperty(Constants.EMAIL_CONFIG_MAILADDRESS);
        String userName = ConfigUtils
                .getProperty(Constants.EMAIL_CONFIG_USERNAME);
        String password = ConfigUtils
                .getProperty(Constants.EMAIL_CONFIG_PASSWORD);
        String smtpServer = ConfigUtils
                .getProperty(Constants.EMAIL_CONFIG_SMTPSERVER);
        String smtpPort = ConfigUtils
                .getProperty(Constants.EMAIL_CONFIG_SMTPPORT);
        String trySendTime = ConfigUtils
                .getProperty(Constants.EMAIL_CONFIG_TRYTIME);

        consoleConfig.setShowSendName(fromName);
        consoleConfig.setMailUserName(userName);
        consoleConfig.setMailAddress(address);
        consoleConfig.setMailPassword(password);
        consoleConfig.setSmtpServer(smtpServer);
        consoleConfig.setSmtpPort(String.valueOf(smtpPort));
        consoleConfig.setTryTime(String.valueOf(trySendTime));*/
    }

    public String saveEmailConfig(EmailConfig email) {
        EmailConfig emailConfig = emailConfigDao.saveEmailConfig(email);
        if(emailConfig != null){
            return "SUCCESS";
        }
        return null;
    }

    public int sendEmail(String to, String mailSubject, String mailContent) {
        return sendEmail(to, null, mailSubject, mailContent, null, null);
    }

    public int sendEmail(String to, String fromName, String mailSubject, String mailContent) {
        return sendEmail(to, fromName, mailSubject, mailContent, null, null);
    }

    public int sendEmail(String to, String fromName, String mailSubject, String mailContent, List<Attachment> attList, String replyTo, String... ccTo) {
        {

            logger.info("准备发送邮件....");
            logger.info("to : {}", to);
            logger.info("fromName : {}", fromName);
            logger.info("mailSubject : {}", mailSubject);

            try {

                MailConfig config = consoleConfig;
                String smtpServer = config.getSmtpServer();
                String userName = config.getMailUserName();
                String password = config.getMailPassword();
                String address = config.getMailAddress();
                //int port = Integer.valueOf(config.getSmtpPort());
                int port = Integer.parseInt(config.getSmtpPort());
                // int port = smtpPort;
                int tryTime = TRY_SEND_TIME;
                if (StringUtils.hasLength(config.getSmtpPort())) {
                    if (StringUtils.isNumeric(config.getSmtpPort())) {
                        port = Integer.parseInt(config.getSmtpPort());
                    }
                }
                if (StringUtils.hasLength(config.getTryTime())) {
                    if (StringUtils.isNumeric(config.getTryTime())) {
                        tryTime = Integer.parseInt(config.getTryTime());
                    }
                }
                if (StringUtils.isEmpty(fromName)) {
                    fromName = config.getShowSendName();
                }
                HtmlEmail email = new HtmlEmail();
                email.setHostName(smtpServer);
                email.setAuthentication(userName, password);
                if (StringUtils.hasLength(fromName)) {
                    email.setFrom(address, fromName);// 发信者
                } else {
                    email.setFrom(address, fromName);
                }
                to = handletMailTo(to);
                email.addTo(to);// 收信者
                if (ccTo != null && ccTo.length > 0) {
                    for (String cc : ccTo) {
                        cc = handletMailTo(cc);
                        email.addCc(cc); // 抄送者
                    }
                }
                email.setSmtpPort(port);
                email.setSubject(mailSubject);// 标题
                email.setCharset(Constants.DEFAULT_ENCODING);// 编码格式
                email.addPart(mailContent, "text/html;charset=utf-8");
                if (CollectionUtils.notEmpty(attList)) {
                    for (Attachment attach : attList) {
                        ByteArrayDataSource ds = new ByteArrayDataSource(
                                attach.getContent(), attach.getType());
                        String fileName = MimeUtility.encodeText(attach.getName());
                        try {
                            email.attach(ds, fileName, fileName);
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
                logger.info("发送邮件地址：{}", address);
                logger.info("收件人：{}", to);
                if (logger.isDebugEnabled()) {
                    logger.debug("发送邮件密码：{}", password);
                    logger.debug("发送邮件服务器：{}", smtpServer);
                    logger.debug("发送邮件内容：");
                    logger.debug(mailContent);
                }
                sendMail(email, tryTime);
            } catch (Exception e) {
                logger.debug(e.getMessage(), e);
                throw new ExamBaseException(e.getMessage(), e);
            }

            return Constants.YES;
        }
    }

    public void sendMail(Email email, int tryTime) {

        boolean success = false;
        int time = 0;
        while (!success && time < tryTime) {
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("开始发送邮件...");
                }
                // 发送邮件
                email.setTLS(false);
                email.send();
                if (logger.isDebugEnabled()) {
                    logger.debug("邮件发送成功。");
                }
                success = true;
            } catch (Exception e) {
                time++;
                if (logger.isErrorEnabled()) {
                    logger.error("第" + time + "次尝试发送邮件失败：" + e.getMessage(), e);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    logger.warn(e1.getMessage(), e1);
                }
                if (time >= tryTime) {
                    throw new ExamBaseException(e.getMessage(), e);
                }
            }
        }
    }

    private String handletMailTo(String to) {

        if (null != to) {
            try {
                to = StringUtils.deleteLineSeparator(to);
            } catch (Exception e) {
                e.printStackTrace();
            }
            to = StringUtils.deleteUnwantedSpaces(to);
        }
        return to;
    }

    public String mailEncodeBASE64(String mailContent) {

        try {
            return MimeUtility.encodeText(mailContent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return mailContent;
    }

    public void sendHtmlMail(String from,
                             String to,
                             String mailSubject,
                             String mailContent) {

        MailConfig mailConfig = consoleConfig;
        sendHtmlMail(mailConfig, from, to, mailSubject, mailContent, null);

    }

    public boolean sendHtmlMail(MailConfig mailConfig,
                                String from,
                                String to,
                                String mailSubject,
                                String mailContent,
                                List<Attachment> attList,
                                String... ccTo) {

        boolean success = false;
        if (StringUtils.hasLength(to, true) && mailConfig.canSendMail()) {
            try {
                to = handletMailTo(to);
                mailContent = StringEscapeUtils.unescapeHtml4(mailContent);
                HtmlEmail email = new HtmlEmail();
                email.setHostName(mailConfig.getSmtpServer());

                email.setAuthentication(mailConfig.getMailUserName(),
                        mailConfig.getMailPassword());

                email.setFrom(mailConfig.getMailAddress(),
                        mailEncodeBASE64(from));// 发信者

                String smtpPort = mailConfig.getSmtpPort();
                if (smtpPort != null && Integer.parseInt(smtpPort) == Constants.DEFAULT_EMAIL_SMTP_PORT) {
                    email.setSmtpPort(Integer.parseInt(mailConfig.getSmtpPort()));
                }
                email.addTo(to);// 收信者
                if (ccTo != null && ccTo.length > 0) {
                    for (String cc : ccTo) {
                        cc = handletMailTo(cc);
                        email.addCc(cc);
                    }
                }
                email.setSubject(mailSubject);// 标题
                email.setCharset(Constants.DEFAULT_ENCODING);// 编码格式
                // email.setHtmlMsg(mailContent);// 内容
                email.addPart(mailContent, "text/html;charset=utf-8");
                if (logger.isDebugEnabled()) {
                    logger.debug("默认配置发送邮件地址：" + mailConfig.getMailAddress());
                    logger.debug("发送邮件服务器：" + mailConfig.getSmtpServer());
                    logger.debug("收件人：" + to);
                    logger.debug("发送邮件内容：");
                    logger.debug(mailContent);
                }
                if (CollectionUtils.notEmpty(attList)) {
                    for (Attachment attach : attList) {
                        ByteArrayDataSource ds = new ByteArrayDataSource(
                                attach.getContent(), attach.getType());
                        String fileName = MimeUtility.encodeText(attach
                                .getName());
                        try {
                            email.attach(ds, fileName, fileName);
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
                if (mailConfig.getSslStatus() != null && mailConfig
                        .getSslStatus()
                        .equals(String.valueOf(Constants.YES))) {
                    email.setSSL(true);
                    email.setSslSmtpPort(email.getSmtpPort());
                    if (logger.isDebugEnabled()) {
                        logger.debug("SSL端口：" + email.getSslSmtpPort());
                    }
                }
                // 发送邮件
                email.setTLS(false);
                email.send();
                success = true;
            } catch (Exception e) {
                success = false;
                logger.debug(e.getMessage(), e);
                throw new ExamBaseException(
                        e.getMessage(), e);
            }
        }
        return success;
    }
}
