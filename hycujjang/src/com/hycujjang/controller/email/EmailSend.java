package com.hycujjang.controller.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.hycujjang.util.Gmail;
import com.hycujjang.util.SHA256;

public class EmailSend {
	private String email;
	
	public EmailSend(String email) {
		this.email = email;
	}
		
	public int send() {
		String host = "http://hycujjang.com/";
		//String from = "lkjjjang1985@gmail.com";
		String from = "lkjjjang@hycujjang.com";
		String to = this.email;
		String subject = "�����򰡸� ���� �̸��� ���� �����Դϴ�.";
		String content = "���� ��ũ�� �����Ͽ� �̸��� ������ �����ϼ���." +
			"<a href='" + host + "emailCheckAction?code=" + SHA256.getSHA256(to) + ""
					+ "&id=" + to + "'>�̸��� �����ϱ�</a>";
		Properties p = new Properties();
		p.put("mail.smtp.starttls.enable", "true");
		//p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.host", "smtp.cafe24.com");
		p.put("mail.smtp.auth", "true");	
		p.put("mail.smtp.port", "587");
		
		try {
			Authenticator auth = new Gmail();
			Session ses = Session.getInstance(p, auth);
			ses.setDebug(true);
			
			MimeMessage msg = new MimeMessage(ses);
			msg.setSubject(subject);
			
			Address fromAddr = new InternetAddress(from);
			msg.setFrom(fromAddr);
			
			Address toAddr = new InternetAddress(to);
			msg.addRecipient(Message.RecipientType.TO, toAddr);
			msg.setContent(content, "text/html;charset=UTF8");		
					
			Transport.send(msg);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}