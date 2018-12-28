package cn.edu.xmu.crms.util.email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

    private Properties properties;
    private Session session;
    private String from;
    private String passWord;
    public Email()
    {
        from="759849985@qq.com";
        passWord="hquflekasrblbbhh";
        properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");// 连接协议
        properties.put("mail.smtp.host", "smtp.qq.com");// 主机名
        properties.put("mail.smtp.port", 465);// 端口号
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.debug", "false");// 设置是否显示debug信息 true 会在控制台显示相关信息
        // 得到回话对象
        session = Session.getInstance(properties);
    }

    public void sendSimpleMail(String sendTo,String contentText) {

        try{
            // 获取邮件对象
            Message message = new MimeMessage(session);
            // 设置发件人邮箱地址
            message.setFrom(new InternetAddress("759849985@qq.com"));
            // 设置收件人邮箱地址
            //message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com")});
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));//一个收件人
            // 设置邮件标题
            message.setSubject("来自课堂管理系统的消息");
            // 设置邮件内容
            message.setText(contentText);
            // 得到邮差对象
            Transport transport = session.getTransport();
            // 连接自己的邮箱账户
            transport.connect(from, passWord);// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }catch(MessagingException e) {
            e.printStackTrace();
        }
    }
    public void sendPassWordMail(String sendTo,String passWord)
    {
        try{
            // 获取邮件对象
            Message message = new MimeMessage(session);
            // 设置发件人邮箱地址
            message.setFrom(new InternetAddress("759849985@qq.com"));
            // 设置收件人邮箱地址
            //message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com")});
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));//一个收件人
            // 设置邮件标题
            message.setSubject("课堂管理系统-您的密码");
            // 设置邮件内容
            String contentText="您的密码是："+passWord+"\n请妥善保管您的密码。";
            message.setText(contentText);
            // 得到邮差对象
            Transport transport = session.getTransport();
            // 连接自己的邮箱账户
            transport.connect(from, passWord);// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }catch(MessagingException e) {
            e.printStackTrace();
        }
    }
}
