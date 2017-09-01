package com.dayee.writtenExam.model;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by tang on 2017/1/21.
 */
public class DefaultAuthenticator extends Authenticator {
    // 用户名
    private String username = null;

    // 密码
    private String password = null;

    public DefaultAuthenticator() {

        super();
    }

    public DefaultAuthenticator(String username, String password) {

        super();
        this.username = username;
        this.password = password;
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {

        // Properties props = System.getProperties();
        // props.setProperty("proxySet", "true");
        // props.setProperty("socksProxyHost", "192.168.155.1");
        // props.setProperty("socksProxyPort", "1081");
        // Session session = Session.getDefaultInstance(props,
        // new Authenticator() {
        //
        // @Override
        // protected javax.mail.PasswordAuthentication
        // getPasswordAuthentication() {
        //
        // return new javax.mail.PasswordAuthentication(
        // username,
        // password);
        // }
        // });
        return new PasswordAuthentication(username, password);
    }
}
