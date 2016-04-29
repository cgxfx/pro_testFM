package tool;

import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;

public class SendEmail {
	
	private static Boolean checkEmail(String email) {
		if (StringUtils.isBlank(email))
			return false;
		String regexEmail = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$";
		return Pattern.matches(regexEmail, email);
	}
	
	public static String sendEmail(String email) {
		String randomStr = "";
		try{
			if (!checkEmail(email)) 
				throw new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_EMAILs);
			
			randomStr = RandomStr.getRandomStr();
			
			String from="15757118232@163.com";
			String password="ljj19951030";
			String to=email;
			String subject="土豆FM  验证码";
			//生成SMTP的主机名称
			int n =from.indexOf('@');
			int m=from.length() ;
			String mailserver ="smtp."+from.substring(n+1,m);
		    //建立邮件会话
			Properties pro=new Properties();
		    pro.put("mail.smtp.host",mailserver);
		    pro.put("mail.smtp.auth","true");
		    Session sess=Session.getInstance(pro);
		    sess.setDebug(true);
		    //新建一个消息对象
		    MimeMessage message=new MimeMessage(sess);
		    //设置发件人
		    InternetAddress from_mail=new InternetAddress(from);
		    message.setFrom(from_mail);
		   //设置收件人
		    InternetAddress to_mail=new InternetAddress(to);
		    message.setRecipient(Message.RecipientType.TO ,to_mail);
		    //设置主题
		    message.setSubject(subject);
		   //设置内容
		   message.setText(randomStr);
		   //设置发送时间
		   message.setSentDate(new Date());
		   //发送邮件
		   message.saveChanges();  //保证报头域同会话内容保持一致
		   Transport transport =sess.getTransport("smtp");
		   transport.connect(mailserver,from,password);
		   transport.sendMessage(message,message.getAllRecipients());
		   transport.close();
		} catch(Exception e){
//			throw new BizException(Constants.UNKNOW, e.getMessage());
		}
		
		return randomStr;
	} 
}
