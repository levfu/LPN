package Utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private static final String FROM_EMAIL = "phule1022006@gmail.com";  // <-- Email gửi đi - không được sửa
    private static final String FROM_PASSWORD = "hrzx ybnh jrju zxvx"; // <-- Mật khẩu ứng dụng - không được sửa




    public static void sendVerificationEmail(String toEmail, String verificationCode) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );
            message.setSubject("Mã xác thực đăng ký tài khoản");
            message.setText("Mã xác thực của bạn là: " + verificationCode);

            Transport.send(message);

            System.out.println("Gửi email xác thực thành công!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    public static void sendNewPasswordEmail(String toEmail, String newPassword) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mật khẩu mới của bạn");
            message.setText("Mật khẩu mới của bạn là: " + newPassword);
            Transport.send(message);
            System.out.println("Gửi email chứa mật khẩu mới thành công!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
