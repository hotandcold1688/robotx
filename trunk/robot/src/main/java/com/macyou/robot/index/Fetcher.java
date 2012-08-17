package com.macyou.robot.index;

import java.util.List;

import com.macyou.robot.common.Knowledge;

/**
 * 
 * generic is not used since we are focus on knowledge 
 * 
 * @author zili.dengzl
 *
 */
public interface Fetcher {

    /**
     * 
     */
    void start();

    /**
     * @return
     */
    List<Knowledge> nextPage();
    
 
    /**
     * @return
     */
    boolean hasNext();
    
  
    /**
     * 
     */
    void stop();
    
}
