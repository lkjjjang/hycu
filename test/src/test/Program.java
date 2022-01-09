package test;

import java.security.MessageDigest;

public class Program {

	public static void main(String[] args) {
		String input = "food1004**";
		
		System.out.println(getSHA256(input));
	}
	
	public static String getSHA256(String input) {
		StringBuffer result = new StringBuffer();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			// slat값은 내마음대로 
			byte[] salt = "hycujjang.com! This is lkjjjang Salt.".getBytes();
			digest.reset();
			digest.update(salt);
			byte[] chars = digest.digest(input.getBytes("UTF-8"));
			for (int i = 0; i < chars.length; i++) {
				String hex = Integer.toHexString(0xff & chars[i]);
				if (hex.length() == 1) {
					result.append("0");
				}
				result.append(hex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public static String getContentUpdate(String contents) {
		int sbCapacity = contents.length();
        StringBuilder sb = new StringBuilder(sbCapacity * 2);
        String targetStr = "<img style=\"width:";
        char[] targetCh = targetStr.toCharArray();
        char[] contentsChars = contents.toCharArray();

        for (int i = 0; i < contentsChars.length; i++) {
            if (contentsChars[i] == '<') {
                boolean isImgTag = false;
                for (int j = 0; j < targetCh.length; j++) {
                    if (contentsChars[i] != targetCh[j]) {
                        isImgTag = false;
                        sb.append(contentsChars[i]);
                        break;
                    } else {
                        isImgTag = true;
                        sb.append(contentsChars[i++]);
                    }
                }

                if (isImgTag) {
                    sb.append(contentsChars[i]);
                }

                boolean inputSizePx = false;

                for (int k = 1; k <= 6; k++) {
                    if (i + 30 > sbCapacity) {
                        break;
                    }
                    if (contentsChars[i + k] == 'p' && contentsChars[i + k + 1] == 'x') {
                        inputSizePx = true;
                    }
                }

                char[] size = {'1', '0', '0', '%', ';', '"'};
                if (inputSizePx) {
                    sb.append("100%;");
                    i += size.length;
                }
            } else {
                sb.append(contentsChars[i]);
            }
        }
        return sb.toString();
	}

}
