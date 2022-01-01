package com.hycujjang.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileUtils {
	private String directory;
	private String fileName;
	private final String root = "src=\"/tempImg/";
	
	public FileUtils() {		
	}
	public FileUtils(String directory, String fileName) {
		this.directory = directory;
		this.fileName = fileName;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getModifyedContent(String[] content, ArrayList<String> oriFile, ArrayList<String> reName, String userID) {
		String rootPath = this.root + userID;
		int rootLength = rootPath.length();
		int fileIndex = 0;
		
		for (int i = 0; i < content.length; i++) {
			if (content[i].length() < rootLength) {
				continue;
			}
			
			String src = content[i].substring(0, rootLength);
			if (!src.equals(rootPath)) {
				continue;
			}
			// �̰� �ɰ� src="/tempImg/1111\KakaoTalk_20210423_000414767.jpg"
			int start = content[i].lastIndexOf(File.separator) + 1;		
			int end = content[i].length() - 1;
			String ori = oriFile.get(fileIndex);
			if (ori.equals(content[i].substring(start, end))) {
				content[i] = "src=\"/upload/" + getTodayDate() + File.separator + reName.get(fileIndex++) + "\"";
			}
		}
		
		StringBuilder sb = new StringBuilder(2048);
		for (String con: content) {
			sb.append(con);
			sb.append(" ");
		}
		
		return sb.toString();
	}
	
	public ArrayList<String> moveFile(String targetDir, String oriDir, ArrayList<String> files) {
		ArrayList<String> validNamse = new ArrayList<String>();
		try {
			ArrayList<FileInputStream> inputStreams = new ArrayList<FileInputStream>();
			for (String file: files) {
				String path = oriDir + File.separator + file;
				FileInputStream inputFile = new FileInputStream(path);
				inputStreams.add(inputFile);
			}
			
			// ��ǥ ������ �����̸� �ߺ� üũ�� �۾�
			for (String file: files) {
				if (containsFileName(targetDir, file)) {
					validNamse.add(getChangeFileName(targetDir, file));					
				} else {
					validNamse.add(file);
				}
			}
			
			ArrayList<FileOutputStream> outputStream = new ArrayList<FileOutputStream>();
			for (String file: validNamse) {
				String path = targetDir + File.separator + file;
				FileOutputStream outputFile = new FileOutputStream(path);
				outputStream.add(outputFile);
			}
			
			int index = 0;
			for (FileInputStream fis: inputStreams) {
				byte[] buf = new byte[1024];
				int size = 0;
				while((size = fis.read(buf)) != -1 ) {
					outputStream.get(index).write(buf, 0, size);
				}
				outputStream.get(index).flush();
				outputStream.get(index).close();
				fis.close();
				index++;
			}
			
			deleteFolder(oriDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return validNamse;
	}
	
	public long getFolderSize() {
		long length = 0;
	    File[] files = new File(this.directory.toString()).listFiles();
	    int count = files.length;
	    for (int i = 0; i < count; i++) {
	        if (files[i].isFile()) {
	            length += files[i].length();
	        } else {
	            length += getFolderSize();
	        }
	    }
	    return length;
	}
	

	public ArrayList<String> getUsedFileList(String[] content, String userID) {
		ArrayList<String> result = new ArrayList<String>();		
		String fileName = "";
		
		for (int i = 0; i < content.length; i++) {
			int tagPathLength = this.root.length() + userID.length();
			if (content[i].length() < tagPathLength) {
				continue;
			}
			
			String rootName = content[i].substring(0, tagPathLength);
			if (!(this.root + userID).equals(rootName)) {
				continue;
			}
			
			// src="/tempImg/1111\KakaoTalk_20210423_000414767(1).jpg"></p><p>&nbsp;
			// �� ���¸� �ɰ��� ���ϸ� ����
			int separator = content[i].lastIndexOf(File.separator);			
			String[] tempName = content[i].substring(separator + 1, content[i].length() - 1).split("\"");
			fileName = tempName[0];
			result.add(fileName);
		}
		return result;
	}
	
	public String getChangeFileName(String directory, String name) {
		String resultName = name;
		int nameInx = 0;
		int extInx = 1;
		int front = name.lastIndexOf("(");
		int back = name.lastIndexOf(")");
		int dot = name.lastIndexOf(".");
		String[] sp = name.split("\\.");
		// �ߺ��� ������쿡�� ������
		// �� ���α׷����� ������ �ߺ���å�� �ƴҰ�� (1) �� �ٿ� ����	
		// () �� ������ �������� Ȯ���� �ߺ���å ����
		if (dot - back == 1 && front != -1 && back != -1) { // ������
			String numStr = resultName.substring(front + 1, back);
			if (!checkInt(numStr)) {
				resultName = sp[nameInx] + "(1)." + sp[extInx];
			}
		} else { // ������
			resultName = sp[nameInx] + "(1)." + sp[extInx];
		}
		
		while (containsFileName(directory, resultName)) {
			int f = resultName.lastIndexOf("(");
			int b = resultName.lastIndexOf(")");
			String[] resultNameSp = resultName.split("\\.");
			
			int num = Integer.parseInt(resultNameSp[nameInx].substring(f + 1, b));
			int bracketFront = resultNameSp[nameInx].lastIndexOf("(");
			
			String reName = resultNameSp[nameInx].substring(0, bracketFront);
			String reNum = "(" + (++num) + ").";
			resultName = reName + reNum + resultNameSp[extInx];
		}
		
		return resultName;
	}
	
	public boolean containsFileName(String directory, String name) {
		File dir = new File(directory);
		File files[] = dir.listFiles();
		
		for (File list: files) {
			String listStr = list.toString();
			int subStrStart = listStr.lastIndexOf(File.separator) + 1;
			String fileName = listStr.substring(subStrStart, listStr.length()); 
			if (fileName.equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void changeFileName() {
		int front = this.fileName.lastIndexOf("(");
		int back = this.fileName.lastIndexOf(")");
		int dot = this.fileName.lastIndexOf(".");
		// �ߺ��� ������쿡�� ������
		// �� ���α׷����� ������ �ߺ���å�� �ƴҰ�� (1) �� �ٿ� ����
		if (dot - back != 1 || front == -1 || back == -1) {
			String[] sp = this.fileName.split("\\.");
			setFileName(sp[0] + "(1)." + sp[1]);
			changeFileName();
		}
		
		while (containsFileName()) {
			int num = Integer.parseInt(this.fileName.substring(front + 1, back));
			int name = 0;
			int ext = 1;
			
			String[] sp = this.fileName.split("\\.");
			int bracketFront = sp[name].lastIndexOf("(");
			String reName = sp[name].substring(0, bracketFront);
			String reNum = "(" + (num + 1) + ").";
			
			setFileName(reName + reNum + sp[ext]);
		}
	}
	
	public boolean containsFileName() {
		File dir = new File(this.directory);
		File files[] = dir.listFiles();
		
		for (File list: files) {
			String listStr = list.toString();
			int subStrStart = listStr.lastIndexOf(File.separator) + 1;
			String fileName = listStr.substring(subStrStart, listStr.length()); 
			if (fileName.equals(this.fileName)) {
				return true;
			}
		}
		return false;
	}
	
	public void deleteFolder(String path) {
		File folder = new File(path);
	    try {
			if (!folder.exists()) {
				return;
			}
			
			File[] folder_list = folder.listFiles(); 			
			for (int i = 0; i < folder_list.length; i++) {
			    if (folder_list[i].isFile()) {
			    	folder_list[i].delete();
			    	folder_list[i].getName();
			    } else {
			    	deleteFolder(folder_list[i].getPath());
			    }
			}
			folder.delete();
	    } catch (Exception e) {
	    	e.getStackTrace();
	    }
	}
	
	private String getTodayDate() {
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		return sf.format(nowTime).toString();
	}
	
	private boolean checkInt(String numStr) {
		char[] ch = numStr.toCharArray();
		if (ch.length == 0) {
			return true;
		}
		//48 ~ 57 �ƽ�Ű
		boolean result = false;
		for (char c: ch) {
			result = c > 47 && c < 58? true: false;			
			if (!result) {
				return false;
			}
		}
		return true;
	}
}
