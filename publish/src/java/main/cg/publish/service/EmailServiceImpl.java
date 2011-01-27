package cg.publish.service;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Provide basic send email function to one or multiple email addresses.
 */
public class EmailServiceImpl implements EmailService {
  
  private String hostname;
  private String username;
  private String password;
  private String emailFrom;
  
  
  public void sendMail(
      String emailTo, 
      String subject,
      String message) throws MessagingException {
    
    String emailToNew[] = { emailTo }; 
    sendMail(emailFrom, emailToNew, subject, message);

  }
  
  public void sendMail(
      String emailFrom, 
      String emailTo, 
      String subject,
      String message) throws MessagingException {
    
    String emailToNew[] = { emailTo }; 
    sendMail(emailFrom, emailToNew, subject, message);

  }
  
  public void sendMail( 
      String emailTo[], 
      String subject,
      String message) throws MessagingException {
    
    sendMail(emailFrom, emailTo, subject, message);

  }

  public void sendMail(
      String emailFrom,
      String emailTo[], 
      String subject, 
      String message) throws MessagingException {   
    
    Properties properties = new Properties();
    properties.put("mail.smtp.host", hostname);
    Session session = null;
    if (username != null && username.length() > 0) {
      properties.put("mail.smtp.auth", "true");
      Authenticator authenticator = new Authenticator() {
        public PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(username, password);
        }
      };
      session = Session.getInstance(properties, authenticator); // create new session with user/password.
    } else {
      session = Session.getDefaultInstance(properties); // try to reuse old session in JVM.
    }
    Message msg = new MimeMessage(session);
    InternetAddress addressFrom = new InternetAddress(emailFrom);
    msg.setFrom(addressFrom);
    InternetAddress[] addressTo = new InternetAddress[emailTo.length];
    for (int i = 0; i < emailTo.length; i++) {
      addressTo[i] = new InternetAddress(emailTo[i]);
    }
    msg.setRecipients(Message.RecipientType.TO, addressTo);
    msg.setSubject(subject);
    msg.setContent(message, "text/html");
    msg.setSentDate(new Date());
    Transport.send(msg);
  }
  
  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getEmailFrom() {
    return emailFrom;
  }

  public void setEmailFrom(String emailFrom) {
    this.emailFrom = emailFrom;
  }

}
