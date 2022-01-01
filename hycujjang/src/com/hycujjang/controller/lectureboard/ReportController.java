package com.hycujjang.controller.lectureboard;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.objectPack.user.UserDAO;
import com.hycujjang.util.Gmail;

@WebServlet("/reportController")
public class ReportController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO userDAO = new UserDAO();
		String userID = null;
		if (request.getSession().getAttribute("userID") != null) {
			userID = (String) request.getSession().getAttribute("userID");
		}
		
		String reportTitle = null;
		String reportContent = null;
		if (request.getParameter("reportTitle") != null) {
			reportTitle = request.getParameter("reportTitle");
		}
		
		if (request.getParameter("reportContent") != null) {
			reportContent = request.getParameter("reportContent");
		}
		
		if (reportTitle == null || reportContent == null
				|| reportTitle.equals("") || reportContent.equals("")) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('�Է��� �ȵ� ������ �ֽ��ϴ�.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
		}
		
		String host = "http://localhost:18080/Lecture_Evalution/";
		String from = "lkjjjang1985@gmail.com";
		// ������ �̸��� �ּ�
		String to = "lkjjjang@naver.com";
		String subject = "������ ����Ʈ���� ������ �Ű� ���� �Դϴ�.";
		String content = "�Ű���: " + userID +
						 "<br>����: " + reportTitle +
						 "<br>����: " + reportContent;
			
		Properties p = new Properties();
		
		p.put("mail.smtp.user", from);
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.port", "465");
		p.put("mail.smtp.startssl.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.port", "465");
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");
		
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
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('������ �߻��߽��ϴ�.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
		}
		// ���� ���� �Ű�� ����
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('���������� �Ű� �Ǿ����ϴ�.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		
		
	}
}
