package com.hycujjang.devBoard.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.hycujjang.devBoard.objectPack.DevFileDAO;
import com.hycujjang.devBoard.objectPack.DevFileDTO;
import com.hycujjang.freeboard.objectPack.file.FileDAO;
import com.hycujjang.freeboard.objectPack.file.FileDTO;
import com.hycujjang.util.FileUtils;

@MultipartConfig(
		//location = "/tmp", ��� ���� ������ ���� ���ϴ°� �Ϲ���
		fileSizeThreshold = 1024*1024,
		maxFileSize = 1024*1024*50, // ���� 1���� �ִ� ������ 5mb
		maxRequestSize = 1024*1024*50*5 // ��ü ������ 25mb�� ���� 		
)
@WebServlet("/devImgUpload")
public class DevImgUpload extends HttpServlet{
	private final long maxSize = 1024 * 1024 * 10;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// multiPartRequest �ȵ� ������!!!!
		Part part = request.getPart("file");
		long inputFileSize = part.getSize();
		if (inputFileSize > this.maxSize) {
			writeResponse(response);
			return;
		}
		
		String userID = request.getParameter("userID");		
		String orifileName = part.getSubmittedFileName();	
		// �մܿ��� �Ѿ�� ������ �޾ƿ�
		// summernote ��ü���� �̹����� ������ �ɷ���
		InputStream fis = part.getInputStream();
		
		// ��� ���� �������� ��� ��� ��� ��)"c:/desktop/upload/.... ���"
		// ���� tempImg ���� ������ userID ������ ����� �̹��� ���� 
		// ������� ������ �� ������ �����ǰ� ���ϵ��� upload/���� ������ �̵�
		String directory = this.getServletContext().getRealPath("/tempImg/") + userID;
		FileUtils fileUtils = new FileUtils(directory, orifileName);	
		
		File folder = new File(directory);		
		if (!folder.exists()) {
			folder.mkdir();
		} else {
			long uploadedFileSize = fileUtils.getFolderSize();
			if (inputFileSize + uploadedFileSize > this.maxSize) {
				writeResponse(response);
				return;
			}
		}

		if (fileUtils.containsFileName()) {
			fileUtils.changeFileName();
		}

		directory = fileUtils.getDirectory();
		String newfileName = fileUtils.getFileName();
		// ��μ����� ��α����� ���, ���� �����
		String filePath = directory + File.separator + newfileName;
		// ��½�Ʈ���� �̿� ������ ���ϴ� ��ο� �ٿ�����
		FileOutputStream fos = new FileOutputStream(filePath);
		try {
			byte[] buf = new byte[1024];
			int size = 0;
			while((size = fis.read(buf)) != -1 ) {
				fos.write(buf, 0, size);
			}
			fos.flush();
			fos.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		// �̹������� db����
		DevFileDAO devFileDAO = new DevFileDAO();
		DevFileDTO devFileDTO = new DevFileDTO(userID, orifileName, newfileName);
		devFileDAO.uploadTempFileName(devFileDTO);
	
		// url�� json���� ������ ajax���� �ƿ� ����� ������ �νĸ��� �ƹ��� ������ ����
		// �̹�����ũ�� ajax�� ������ ������ �����ܿ��� ContentType �������� �ʰ� �⺻ type���� url�� ������ (�̷��� �ؾ� img��ũ�� �ڵ����� �ٿ���)
		// �����ڰ� ���ϴ� ������ �ƴ� �ٸ� ��ü ������ ������ ������ ContentType �� json���� ������ ��Ȳ�� ���� ������
		// ���ؽ�Ʈ���� / �� �����߱⶧���� hycu(���ؽ�Ʈ��)�� url���� ������
		String url = "/tempImg/" + userID + File.separator + newfileName;
		response.getWriter().write(url);
	}
	
	private void writeResponse(HttpServletResponse response) throws IOException {
		String result = "[{\"resultCode\":\"capacityFull\"}, {\"msg\":\"�����ʹ� 10mb ���� ��� �����մϴ�.\"}]";
		response.setContentType("application/json charset=utf-8");
		response.getWriter().write(result);
	}
}
