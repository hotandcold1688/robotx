package com.macyou.robot;

import com.macyou.context.SearchContext;
import com.macyou.exception.RobotCommonException;
import com.macyou.robot.common.StringUtils;
import com.macyou.robot.index.IndexDirFactory;
import com.macyou.search.AnswerSearcher;

public class SimpleRobot implements Robot{
	
	private IndexDirFactory indexDirFactory;
	private AnswerSearcher defaultAnswerSearcher;
	
    public String answer(String question,String sceneId) throws Exception {
    	SearchContext context=prepareContext(question,sceneId);
    	return defaultAnswerSearcher.searchAnswer(context);
    }
    
    private SearchContext prepareContext(String question,String sceneId){
    	if(StringUtils.isEmpty(question)){
    		throw new RobotCommonException("queryAnswer error,question is null");
    	}
    	if(StringUtils.isEmpty(sceneId)){
    		throw new RobotCommonException("queryAnswer error,sceneId is null");
    	}
    	//获取索引地址
    	String indexDir=indexDirFactory.getIndexDir(sceneId);
    	SearchContext context=new SearchContext();
    	context.setQuestion(question);
    	context.setIndexDir(indexDir);
    	context.setRobotScene(sceneId);
    	return context;	
    }

	public void setIndexDirFactory(IndexDirFactory indexDirFactory) {
		this.indexDirFactory = indexDirFactory;
	}

	public void setDefaultAnswerSearcher(AnswerSearcher defaultAnswerSearcher) {
		this.defaultAnswerSearcher = defaultAnswerSearcher;
	}
    
    
}

