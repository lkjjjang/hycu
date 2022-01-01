package com.hycujjang.controller.service;

import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.hycujjang.objectPack.user.UserDAO;
import com.hycujjang.objectPack.user.UserDTO;
import com.hycujjang.util.Gmail;
import com.hycujjang.util.SHA256;

public class EmailSend {
	private UserDTO userDTO;
	
	public EmailSend(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
		
	
	public int send() {
		String userID = this.userDTO.getUserID();
		UserDAO userDAO = new UserDAO();
		
		String host = "http://localhost:8080/hycu/";
		String from = "lkjjjang1985@gmail.com";
		String to = userDAO.getUserEmail(userID);
		String subject = "강의평가를 위한 이메일 인증 메일입니다.";
		String content = "다음 링크에 접속하여 이메일 인증을 진행하세요." +
			"<a href='" + host + "emailCheckAction?code=" + SHA256.getSHA256(to) + "'>이메일 인증하기</a>";
	
		Properties p = new Properties();
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.host", "smtp.gmail.com");
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
			
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
}
