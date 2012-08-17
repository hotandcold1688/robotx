package com.macyou.robot.index;

import java.util.List;

import com.macyou.robot.common.Knowledge;
import com.macyou.robot.lifecycle.Lifecycle;

/**
 * 
 * generic is not used since we are focus on knowledge 
 * 
 * @author zili.dengzl
 *
 */
public interface Fetcher extends Lifecycle {


    /**
     * @return
     */
    List<Knowledge> nextPage();
    
 
    /**
     * @return
     */
    boolean hasNext();
    
 
    
}
