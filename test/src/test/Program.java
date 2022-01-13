package test;

import java.security.MessageDigest;
import java.util.HashMap;

public class Program {

	public static void main(String[] args) {
		String input = "{\"evaluationID\":\"17\",\"lectureName\":\"웹서비스와애플리케이션기초\"}";
		
		System.out.println(getMap(input));
	}
	
	public static HashMap<String, String> getMap(String json) {
		StringBuilder key = new StringBuilder(json.length() / 2);
        StringBuilder value = new StringBuilder(json.length() /2 );
        HashMap<String, String> result = new HashMap<String, String>();
        char[] chars = json.toCharArray();
        int startInx = 2;
        // {"evaluationID":"17","lectureName":"웹서비스와애플리케이션기초"}
        for (int i = 1; i < chars.length; i++) {
            if (chars[i - 1] == '"' && chars[i] == ',' && chars[i + 1] == '"') {
                boolean isValue = false;
                for (int j = startInx; j < i - 2; j++) {
                    if (!isValue && chars[j] != '"') {
                        key.append(chars[j]);
                    }

                    if (chars[j] == '"') {
                        isValue = true;
                        j += 2;
                    }

                    if (isValue) {
                        value.append(chars[j + 1]);
                    }
                }
                
                String resultKey = key.toString();
                String resultValue = value.toString();
                System.out.println("key : " + resultKey + " value : " + resultValue);
                if (resultValue.equals("") || resultValue == null) {
                	return null;
                }
                
                result.put(resultKey, resultValue);
                startInx = i + 2;
                key.setLength(0);
                value.setLength(0);
            }
            
            if (i == chars.length - 1) {
                boolean isValue = false;
                for (int j = startInx; j < i - 2; j++) {
                    if (!isValue && chars[j] != '"') {
                        key.append(chars[j]);
                    }

                    if (chars[j] == '"') {
                        isValue = true;
                        j += 2;
                    }

                    if (isValue) {
                        value.append(chars[j + 1]);
                    }
                }
                
                String resultKey = key.toString();
                String resultValue = value.toString();
                System.out.println("key : " + resultKey + " value : " + resultValue);
                if (resultValue.equals("") || resultValue == null) {
                	return null;
                }
                
                result.put(resultKey, resultValue);
                startInx = i + 2;
                key.setLength(0);
                value.setLength(0);
            }
        }
        return result;
	}

}
