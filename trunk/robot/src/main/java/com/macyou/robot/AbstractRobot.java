/**
 * Copyright 2012 zili.dengzl. All Rights Reserved
 */
package com.macyou.robot;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.macyou.robot.common.Constants;
import com.macyou.robot.config.RobotConfig;
import com.macyou.robot.context.SearchContext;
import com.macyou.robot.exception.RobotCommonException;
import com.macyou.robot.index.DefaultIndexBuilder;
import com.macyou.robot.index.IndexBuilder;
import com.macyou.robot.session.SessionManager;

/**
 * @author zili.dengzl
 * @time 2012-8-15 下午4:41:55
 * 
 */
public abstract class AbstractRobot implements Robot {

	/**
	 * robotId
	 */
	protected String id;

	protected RobotConfig config;

	protected IndexSearcher searcher;

	protected Analyzer analyzer;

	protected IndexBuilder indexBuilder;

	protected SessionManager sessionManager;

	public AbstractRobot(RobotConfig config) {
		super();
		this.config = config;
	}

	@Override
	public String answer(String question) throws Exception {
		// 准备查询上下文，包括session的处理
		SearchContext context = prepareContext(question);

		// 尝试从cache中获取结果
		String answer = getAnswerFromCache(context);
		if (answer != null) {
			return answer;
		}
		// 尝试通过indexId直接获取结果
		answer = getAnswerDirectlyIfPossible(context);
		if (answer != null) {
			return answer;
		}
		// 分词,获取查询项
		Query query = getQuery(context);
		// 获取filter
		Filter filter = getFilter(context);
		// 通过lucene搜索
		TopFieldDocs docs = searcher.search(query, filter, config.getTopHitsNum(), config.getSort());
		// 计算相似度,拼装结果
		answer = getAnswer(docs);

		return answer;
	}

	@Override
	public void start() {
		try {
			id = config.getRobotId();
			analyzer = new IKAnalyzer(); // TODO: init use reflect, from config info
			File indexDir = new File(config.getIndexPath());
			initIndexBuilders(indexDir);
			initSearcher(indexDir);
			// initSessionManager
		} catch (Exception e) {
			throw new RobotCommonException("error while start", e);
		}
	}

	private void initSearcher(File indexDir) throws CorruptIndexException, IOException {
		// 如果索引文件不存在，需要先build索引，否则打开searcher会出错
		if (!indexDir.exists() || !indexDir.isDirectory()) {
			indexBuilder.fullBuildIndex();
		}else{
			indexBuilder.incrementBuildIndex();
		}
		searcher = new IndexSearcher(IndexReader.open(FSDirectory.open(indexDir)));
	}

	private void initIndexBuilders(File indexDir) throws InstantiationException, IllegalAccessException, Exception {
		IndexWriterConfig indexConfig = new IndexWriterConfig(Constants.LUCENE_VERSION, analyzer);
		indexConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);// recreate every time;
		IndexWriter writer = new IndexWriter(FSDirectory.open(indexDir), indexConfig);
		indexBuilder = new DefaultIndexBuilder(writer, config.getFetcher());
	}

	@Override
	public void stop() {
	}
	
	
	

	@Override
	public void refreshIndex() {
		
	}
	

	/**
	 * 尝试从缓存获取结果
	 * 
	 * @param context
	 * @return
	 */
	protected abstract String getAnswerFromCache(SearchContext context);

	/**
	 * 利用indexId这样的参数直接查询结果
	 * 
	 * @param context
	 * @return
	 */
	protected abstract String getAnswerDirectlyIfPossible(SearchContext context);

	/**
	 * 1.计算相似度 2.选择最佳答案,拼装返回结果
	 * 
	 * @param docs
	 * @return
	 */
	protected abstract String getAnswer(TopFieldDocs docs) throws Exception;

	/**
	 * @param context
	 * @return
	 */
	protected abstract Filter getFilter(SearchContext context);

	/**
	 * @param context
	 * @return
	 */
	protected abstract Query getQuery(SearchContext context) throws Exception;

	/**
	 * 准备查询上下文，包括session的处理
	 * 
	 * @param question
	 * @param sceneId
	 * @return
	 */
	protected abstract SearchContext prepareContext(String question) throws Exception;


	public String getRobotId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setConfig(RobotConfig config) {
		this.config = config;
	}

	public IndexSearcher getSearcher() {
		return searcher;
	}

	public void setSearcher(IndexSearcher searcher) {
		this.searcher = searcher;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

}
