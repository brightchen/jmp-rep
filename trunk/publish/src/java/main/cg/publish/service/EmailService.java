package cg.publish.service;

import javax.mail.MessagingException;

/**
 * Interface for basic send email function.
 * 
 */
public interface EmailService { 
  
  public void sendMail(String emailTo, String subject, String message) throws MessagingException;
  
  public void sendMail(String emailFrom, String emailTo, String subject, String message) throws MessagingException;
  
  public void sendMail(String emailTo[], String subject, String message) throws MessagingException;
  
  public void sendMail(String emailFrom, String emailTo[], String subject, String message) throws MessagingException;

}
