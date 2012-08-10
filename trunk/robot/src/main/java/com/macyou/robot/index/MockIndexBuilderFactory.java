package com.macyou.robot.index;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.macyou.robot.common.Constants;
import com.macyou.robot.common.Knowledge;

public class MockIndexBuilderFactory implements IndexBuilderFactory {
	private static final Logger logger = LoggerFactory.getLogger(IndexFullBuilder.class);

	String indexDir;

	public MockIndexBuilderFactory(String indexDir) {
		super();
		this.indexDir = indexDir;
	}

	public IndexBuilder getIndexBuilder(IndexType type) throws Exception {
		logger.debug("start build one indexBuilder");
		
		Directory directory = FSDirectory.open(new File(indexDir));
		Fetcher fetcher = new MockFetcher();
		Analyzer analyzer = new StandardAnalyzer(Constants.LUCENE_VERSION);
		IndexWriterConfig config = new IndexWriterConfig(Constants.LUCENE_VERSION, analyzer);

		if (IndexType.FULL.equals(type)) {
			config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);// recreate every time
			IndexWriter writer = new IndexWriter(directory, config);
			return new IndexFullBuilder(writer, fetcher);
		} else {
			config.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
			IndexWriter writer = new IndexWriter(directory, config);
			return new IndexIncrementBuilder(writer, fetcher);
		}

	}
}

class MockFetcher implements Fetcher {

	/**
	 * format: [indexId,question,answer]
	 */
	private String[] source = { "1,what is lucene,a serach tool", "2,who is yikela,a rich man named shaoyi",
			"3,how r u,I'm fine, thank you" };

	private int page;

	private int pageSize = 2;

	@Override
	public void start() {

	}

	@Override
	public List<Knowledge> nextPage() {
		List<Knowledge> list = new ArrayList<Knowledge>();
		for (int i = page * pageSize; i < Math.min((page + 1) * pageSize, source.length); i++) {
			list.add(getKnowledge(source[i]));
		}
		page++;
		return list;
	}

	private Knowledge getKnowledge(String s) {
		Knowledge knowledge = new Knowledge();
		String[] ss = s.split(",");
		knowledge.setIndexId(ss[0]);
		knowledge.setQuestion(ss[1]);
		knowledge.setAnswer(ss[2]);
		return knowledge;
	}

	@Override
	public boolean hasNext() {
		return page * pageSize < source.length;
	}

	@Override
	public void end() {
	}

}