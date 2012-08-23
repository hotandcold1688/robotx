package com.macyou.robot.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TextFileUtil {
	
	public static void sortAndWrite(File fileOrDir) throws IOException{
		if(fileOrDir.isDirectory()){
			File[] files = fileOrDir.listFiles();
			for(File subfile:files){
				sortAndWrite(subfile);
			}
		}else if(fileOrDir.isFile()){
			List<String> lines = readLines(new FileInputStream(fileOrDir));
			if(lines != null){
				Collections.sort(lines);
				writeLines(lines, new FileOutputStream(fileOrDir));
			}
		}
	}

	public static void distinctlySortAndWrite(File fileOrDir) throws IOException{
		if(fileOrDir.isDirectory()){
			File[] files = fileOrDir.listFiles();
			for(File subfile:files){
				distinctlySortAndWrite(subfile);
			}
		}else if(fileOrDir.isFile()){
			List<String> lines = distinctlyReadLines(new FileInputStream(fileOrDir));
			if(lines != null){
				Collections.sort(lines);
				writeLines(lines, new FileOutputStream(fileOrDir));
			}
		}
	}
	
	public static List<String> readLines(File fileOrDir) throws IOException{
		if(fileOrDir.isDirectory()){
			File[] files = fileOrDir.listFiles();
			List<String> ret = new ArrayList<String>();
			for(File subfile:files){
				List<String> ss = readLines(subfile);
				if(ss != null)
					ret.addAll(ss);
			}
			return ret;
		}else{
			return readLines(new FileInputStream(fileOrDir));
		}
	}

	public static List<String> distinctlyReadLines(InputStream in) throws IOException{
		Set<String> ret = new HashSet<String>();
		
		BufferedReader reader = 
			new BufferedReader(
					new InputStreamReader(in));
		String line;
		
		while(null!=(line=reader.readLine())){
			if(line.trim().length() > 0){
				ret.add(line.trim());
			}
		}
		reader.close();
		return ret.isEmpty()?null:new ArrayList<String>(ret);
	}
	
	public static List<String> readLines(InputStream in) throws IOException{
		List<String> ret = new ArrayList<String>();
		
		BufferedReader reader = 
			new BufferedReader(
					new InputStreamReader(in));
		String line;
		
		while(null!=(line=reader.readLine())){
			if(line.trim().length() > 0){
				ret.add(line.trim());
			}
		}
		reader.close();
		return ret.isEmpty()?null:ret;
	}
	
	public static void writeLines(List<?> lines, OutputStream out) throws IOException{
		PrintWriter writer =
			new PrintWriter(new OutputStreamWriter(out));
		for(Object line:lines)
			writer.println(line.toString());
		writer.close();
	}

	public static void writeLines(Collection<?> lines, OutputStream out) throws IOException{
		PrintWriter writer =
			new PrintWriter(new OutputStreamWriter(out));
		for(Object line:lines)
			writer.println(line.toString());
		writer.close();
	}
	
	public static String readContent(InputStream in) throws IOException{
		Reader reader = new InputStreamReader(in);
		String ret = readContent(reader);
		reader.close();
		return ret;
	}

	public static String readContent(Reader reader) throws IOException{
		StringBuffer sb = new StringBuffer();
		char[] buf = new char[1024];
		int numRead = 0;

		while (true) {
			numRead = reader.read(buf);
			if (numRead == -1)
				break;
			sb.append(buf, 0, numRead);
		}

		return sb.toString();
	}
	
	public static void writeContent(String content, OutputStream output) throws IOException{
		BufferedWriter writer =
			new BufferedWriter(new OutputStreamWriter(output));
		writer.write(content);
		writer.close();
	}
	
	
    public static void writeLines(List<?> lines, String fullPath) throws IOException{
        File file=new File(fullPath);
        FileOutputStream fos=new FileOutputStream(file);
        PrintWriter writer =
            new PrintWriter(new OutputStreamWriter(fos));
        for(Object line:lines)
            writer.print(line.toString());
        writer.close();
    }
	
}
