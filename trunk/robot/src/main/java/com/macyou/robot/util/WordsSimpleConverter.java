package com.macyou.robot.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class WordsSimpleConverter {
	private static final Log LOG = LogFactory.getLog(WordsSimpleConverter.class);
	
	public WordsSimpleConverter(String url){
		load(url);
	}
	/**
	 * 繁简，key繁，value简
	 */
	private Map<String, String> fjMap = null;
	
	private void load(String url){
		Map<String, String> map = null;
		map = create(url);
		if (map != null){
			fjMap = map;
			LOG.warn("繁简库导入成功！！");
		}else{
			LOG.warn("繁简库导入失败！！");
		}
	}
	private Map<String, String> create(String url){
		BufferedReader br = null;
		String line = null;
		Map<String, String> map = null;
		try {
			br = new BufferedReader(new FileReader(url));
			map = new HashMap<String, String>();
			while((line = br.readLine()) != null){
				line = line.trim().toLowerCase();
				operate(map, line);
			}
			return map;
		} catch (IOException e) {
			LOG.error(e.toString());
			return null;
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					LOG.error(e.toString());
				}
				br = null;
			}
		}
	}
	private void operate(Map<String, String> map, String line) {
		if (line.equals("")) return;
		String[] arr = line.split(" ");
		String temp;
		for (int i = 0; i < arr.length; i++){
			temp = arr[i].trim();
			String[] arr1 = temp.split("=");
			if (arr1.length != 2) continue;
			map.put(arr1[1], arr1[0]);
		}
	}

	public String toSimple(String arg){
		if (arg == null) return null;
		StringBuffer sb = new StringBuffer();
		String value;
		for (int i = 0; i < arg.length(); i++){
			value = fjMap.get(""+arg.charAt(i));
			if (value == null) value = ""+arg.charAt(i);
			sb.append(value);
		}
		return sb.toString();

	}

	public String toDBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);
			}
		}
		String returnString = new String(c);
		return returnString;
	}
	
	public String convert(String arg){
		if (arg == null) return "";
		arg = toSimple(arg);
		arg = toDBC(arg);
		arg = arg.toLowerCase();
		return arg;
	}
	
	public static void main(String[] args){
		System.out.println(new WordsSimpleConverter("src/main/resources/simple_conplex.txt").convert("誰 ｊｌｋｊｌｋｊｌＡＢＤＷＥＲ．／：ａ"));		
	}

}
