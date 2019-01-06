package cn.edu.xmu.crms.util.email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author SongLingbing
 * @date 2018/12/24 21:49
 */
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
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", "smtp.qq.com");
        properties.put("mail.smtp.port", 465);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.debug", "false");
        session = Session.getInstance(properties);
    }

    public void sendSimpleMail(String sendTo,String contentText) {

        if(sendTo == null) {
            return;
        }
        try{

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));

            message.setSubject("来自课堂管理系统的消息");

            message.setText(contentText);

            Transport transport = session.getTransport();

            transport.connect(from, passWord);

            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }catch(MessagingException e) {
            e.printStackTrace();
        }
    }
    public void sendPassWordMail(String sendTo,String passWord)
    {
        try{
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));

            message.setSubject("课堂管理系统-您的密码");

            String contentText="您的密码是："+passWord+"\n请妥善保管您的密码。";
            message.setText(contentText);

            Transport transport = session.getTransport();

            transport.connect(from, this.passWord);

            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }catch(MessagingException e) {
            e.printStackTrace();
        }
    }
}
