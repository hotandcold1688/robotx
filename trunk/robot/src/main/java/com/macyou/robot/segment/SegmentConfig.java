package com.macyou.robot.segment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SegmentConfig {

	
	//词组快
    private  Set<String>             chunks               = new HashSet<String>();
    //停词
    private  Set<String>             stopWords            = new HashSet<String>();
    //保留词
    private char[]                  keepChars            = { '\'', '-', '_' };
    //干预词库
    private Map<String, String>     interveneWords       = new HashMap<String, String>();
    //强制分隔符
    private char                    delimiter            = '|';
    //默认分词匹配策略
    private String                  segmentMatchStrategy = "forward";
    //业务词组分数
    private double                  extScore             = 0.9;
    //基础词组分数
    private double                  baseScore            = 0.7;
    //默认分数
    private double                  defaultScore         = 0.5;
    
    
    {
    	BufferedReader br = null;
    	try {
			br=new BufferedReader(new InputStreamReader(new FileInputStream("D:/robotx/robot/src/main/resources/chunks.dict")));
		    String l=null;
			while((l=br.readLine())!=null){
				chunks.add(l);
			}
			br=new BufferedReader(new InputStreamReader(new FileInputStream("D:/robotx/robot/src/main/resources/stopWords.dict")));
			while((l=br.readLine())!=null){
				stopWords.add(l);
			}
    	} catch (Exception e) {
			System.out.println("初始化失败");
		}finally{
			try {
				br.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    private Stemmer stemmer	=new PorterStemmer();
    
    public Set<String> getChunks() {
        return chunks;
    }

    public void setChunks(Set<String> chunks) {
        this.chunks = chunks;
    }

    public Set<String> getStopWords() {
        return stopWords;
    }

    public void setStopWords(Set<String> stopWords) {
        //停词去词根
        Set<String> stopWordsTmp = new HashSet<String>();
        for (String stopWord : stopWords) {
            stopWordsTmp.add(stemmer.stem(stopWord));
        }
        this.stopWords = stopWordsTmp;
    }

    public char[] getKeepChars() {
        return keepChars;
    }

    public void setKeepChars(char[] keepChars) {
        this.keepChars = keepChars;
    }

    public Map<String, String> getInterveneWords() {
        return interveneWords;
    }

    public void setInterveneWords(Map<String, String> interveneWords) {
        this.interveneWords = interveneWords;
    }

    public char getDelimiter() {
        return delimiter;
    }

    public String getSegmentMatchStrategy() {
        return segmentMatchStrategy;
    }

    public void setSegmentMatchStrategy(String segmentMatchStrategy) {
        this.segmentMatchStrategy = segmentMatchStrategy;
    }

    public double getExtScore() {
        return extScore;
    }

    public void setExtScore(double extScore) {
        this.extScore = extScore;
    }

    public double getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(double baseScore) {
        this.baseScore = baseScore;
    }

    public double getDefaultScore() {
        return defaultScore;
    }

    public void setDefaultScore(double defaultScore) {
        this.defaultScore = defaultScore;
    }


    
    
}
