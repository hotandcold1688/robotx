package com.macyou.robot.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class AnalyzerTest {

    private static String text = "IkAnalyzer是一个开源的，基于java语言开发的轻量级的中文分词工具包。从2006年12月推出1.0版开始， IKAnalyzer已经推出了4个大版本。";

    @Test
    public void test() throws IOException {

        Analyzer analyzer = new IKAnalyzer(true);
        showToken(analyzer, text);

    }

    /**
     * 分词及打印分词结果的方法
     *
     * @param analyzer 分词器名称
     * @param text     要分词的字符串
     * @throws IOException 抛出的异常
     */
    public static void showToken(Analyzer analyzer, String text) throws IOException {

        Reader reader = new StringReader(text);
        TokenStream stream = (TokenStream) analyzer.tokenStream("", reader);
        //添加工具类  注意：以下这些与之前lucene2.x版本不同的地方
        CharTermAttribute termAtt = (CharTermAttribute) stream.addAttribute(CharTermAttribute.class);
        OffsetAttribute offAtt = (OffsetAttribute) stream.addAttribute(OffsetAttribute.class);
        // 循环打印出分词的结果，及分词出现的位置
        while (stream.incrementToken()) {
//            System.out.print(termAtt.toString() + "|(" + offAtt.startOffset() + " " + offAtt.endOffset() + ")");
            System.out.print(termAtt.toString()+"|");
        }
        System.out.println();
    }

}
