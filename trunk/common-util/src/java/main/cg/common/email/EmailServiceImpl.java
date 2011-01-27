package cg.common.email;

import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import cg.common.logging.Logger;


/**
 * Service for sending email notifications.
 * 
 */
public class EmailServiceImpl implements EmailService {

  static Logger _log = Logger.getLogger(EmailServiceImpl.class);
  
  //--- attributes
  private String _fromAddress = "nobody@iseemedia.com";
  private String _bccAddress = null; //"iseedocs_trace@iseemedia.com";
  private String _emailTemplatePackage = "cg.iseedocs.email";
  private String _mailServer = "localhost";
  private String _password = "";
  private String _username = "";
  private String _iseepublishServer = "";
  
   private String _testEmailAddress = null;
  
  
  public String getTestEmailAddress(){
	  return _testEmailAddress;
  }
  
  public void setTestEmailAddress(String emailAddress){
	  if (isEmailAddressValid(emailAddress))
		  _testEmailAddress = emailAddress;
  }

  public void sendEmail(String to, String subject, String emailTemplate, Map tokens, String contentType) throws Exception{

	  String emailTemplateFile = _emailTemplatePackage  + "/" + emailTemplate;
	  _log.debug("Email template name: " + emailTemplate);

	  InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(emailTemplateFile);
	  if (in != null && in.available() > 0){
		  StringBuffer template = new StringBuffer();
		  int c;
		  while ((c = in.read()) != -1) {
			  template.append((char)c);
		  }
		  tokens.put("iseepublish_server_link", _iseepublishServer);
		  // if there was a test email address, send this message to it
		  if (_testEmailAddress != null && isEmailAddressValid(_testEmailAddress))
			  to = _testEmailAddress;
		  sendEmail(_fromAddress, to, _bccAddress, subject, template.toString(), tokens, contentType);
		  
	  }else{
		  throw new Exception ("Can't read email template " + emailTemplate);
	  }
	  
	  
  }
  
  public void sendEmail(String from, String to, String bcc, 
		  String subject, String content, Map tokens, String contentType) throws Exception{
	  try {
		    Properties props = new Properties();		    
			props.put("mail.smtp.host", _mailServer);
			Session session = Session.getInstance(props, null);

	        Message msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress(from));
	        
	        msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	            
            // BCC to company folders for trace purpose
            if (bcc != null && bcc.lastIndexOf("@") > 1){
            	try{
            		msg.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
            		_log.debug("BCC to: " + bcc);
            	}catch(Exception e){
            		_log.error("Cannot add BCC to Recipients.", e);
            	}
            }
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            
            // replace message tokens, if any
            if(tokens != null){
            	content = replaceTokens(content, tokens);
            }
            // Fill the message
            //messageBodyPart.setDataHandler(new DataHandler(content, contentType));
            messageBodyPart.setContent(content, contentType);

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart);
            
            // Put parts in message
            msg.setContent(multipart);

            // do we need to authenticate to send email?
            _log.debug( "mailServer:" + _mailServer + ", username: " + _username + ", password: " + _password + ", content:\n" + content);
            if (_username != null && _username.length() > 0){
            	props.put("mail.smtp.auth", "true");
            	Transport transport  = session.getTransport("smtp");
            	transport.connect(_mailServer, _username, _password);
                msg.saveChanges();
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();

            }else{
            	Transport.send(msg);
            }
            
            _log.debug("Email has been sent To: " + to);
	            
	    }catch(Exception e){
	    	_log.error("Can't send Email.", e);
	    	throw e;
	    }
  }
  
  public boolean isEmailAddressValid(String address) {

		if (address == null || address.trim().length() < 6)
			return false;
		address = address.trim();
		int index = address.lastIndexOf("@");

		if (index < 1 || index > address.length() - 4)
			return false;

		// do some other validation

		if (address.lastIndexOf(".") < (index + 2))
			return false;

		return true;
	}
  
  private String replaceTokens(String src, Map tokens){
	  Iterator it = tokens.keySet().iterator();
	  while (it.hasNext()){
		  Object key = it.next();
		  Object value = tokens.get(key);
		  src = src.replaceAll("\\{" + key.toString() + "\\}", value.toString());
	  }
	  
	  return src;
  }

	public void setBccAddress(String bccAddress) {
		_bccAddress = bccAddress;
	}

	public void setEmailTemplatePackage(String emailTemplatePackage) {
		_emailTemplatePackage = emailTemplatePackage;
	}

	public void setFromAddress(String fromAddress) {
		_fromAddress = fromAddress;
	}

	public void setMailServer(String mailServer) {
		_mailServer = mailServer;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void setUsername(String user) {
		_username = user;
	}

	public String getIseepublishServer() {
		return _iseepublishServer;
	}

	public void setIseepublishServer(String iseepublishServer) {
		this._iseepublishServer = iseepublishServer;
	}

}