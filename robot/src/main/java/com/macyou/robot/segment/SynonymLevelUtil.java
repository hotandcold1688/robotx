package com.macyou.robot.segment;

/**
 * 
 * @author qinzhong
 */
public class SynonymLevelUtil {
	
	public static final char NEAR = '#';
	
	public static final char SAME =  '=';
	
	public static final char ISOLATE = '@';
	
	/**
	 * ���ͬ��ʴ��ֱ�׼��ͬ������ʶ�ַ���Ϊ6�Σ�
	 * ��һ�Σ���һλ
	 * �ڶ��Σ��ڶ�λ
	 * ����Σ�������λ
	 * ���ĶΣ�����λ
	 * ����Σ�������λ
	 * ����Σ��ڰ�λ
	 * @param group1Id
	 * @param group2Id
	 * @return
	 */
	public static short getSynonymLevelFromGroupId(String group1Id, String group2Id){
		
		if(group1Id.charAt(0) != group2Id.charAt(0)){
			return SynonymLevelConfig.LEVEL_NONSYNONYM;
		}else if(group1Id.charAt(1) != group2Id.charAt(1)){
			return SynonymLevelConfig.LEVEL_FIRST;
		}else if(group1Id.charAt(2) != group2Id.charAt(2) ||
				group1Id.charAt(3) != group2Id.charAt(3)){
			return SynonymLevelConfig.LEVEL_SECOND;
		}else if(group1Id.charAt(4) != group2Id.charAt(4)){
			return SynonymLevelConfig.LEVEL_THIRD;
		}else if(group1Id.charAt(5) != group2Id.charAt(5) ||
				group1Id.charAt(6) != group2Id.charAt(6)){
			return SynonymLevelConfig.LEVEL_FORTH;
		}else if(group1Id.charAt(7) != group2Id.charAt(7)){
			return SynonymLevelConfig.LEVEL_FIFTH;
		}else {
			char ch = group1Id.charAt(7);
			if(ch == NEAR)
				return SynonymLevelConfig.LEVEL_NEAR;
			if(ch == SAME)
				return SynonymLevelConfig.LEVEL_ABSOLUTE;
			return SynonymLevelConfig.LEVEL_NONSYNONYM;
		}
	}

}
