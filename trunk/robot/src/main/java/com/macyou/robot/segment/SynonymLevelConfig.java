package com.macyou.robot.segment;

/**
 * ͬ�弶�������ࡣ
 * @author qinzhong
 *
 */
public interface SynonymLevelConfig {
	
	/**
	 * ��������ʾͬ��ʼ����е���ȫ��ͬ��ʡ�
	 */
	public static final short LEVEL_NONSYNONYM = 0;

	/**
	 * ������ר�ű�ʾ�������׼ͬ��ʼ����е�һ��ͬ��ʣ���ͬ�����ID�ĵ�һλ��ͬ��
	 */
	public static final short LEVEL_FIRST = 1;

	/**
	 * ������ר�ű�ʾ�������׼ͬ��ʼ����еĶ���ͬ��ʣ���ͬ�����ID�ĵ�һ����λ��ͬ��
	 */
	public static final short LEVEL_SECOND = 2;

	/**
	 * ������ר�ű�ʾ�������׼ͬ��ʼ����е���ͬ��ʣ���ͬ�����ID�ĵ�һ����λ��ͬ��
	 */
	public static final short LEVEL_THIRD = 3;

	/**
	 * ������ר�ű�ʾ�������׼ͬ��ʼ����е��ļ�ͬ��ʣ���ͬ�����ID�ĵ�һ����λ��ͬ��
	 */
	public static final short LEVEL_FORTH = 4;

	/**
	 * ������ר�ű�ʾ�������׼ͬ��ʼ����е��弶ͬ��ʣ���ͬ�����ID�ĵ�һ����λ��ͬ��
	 */
	public static final short LEVEL_FIFTH = 5;
	
	/**
	 * ������ר�ű�ʾ�������׼ͬ��ʼ����е���ͬ��ʣ���ͬ�����ID��ȫ��ͬ������#��β��
	 */
	public static final short LEVEL_NEAR = 6;

	/**
	 * ��������ʾͬ��ʼ����еľ��ͬ��ʣ���ͬ�����ID��ȫ��ͬ������=��β��
	 */
	public static final short LEVEL_ABSOLUTE = 7;
	

	/**
	 * ������ר��ͬ��ʼ����е���ȫ��ͬ�ʣ�����������ȫ��ͬ��
	 */
	public static final short LEVEL_EQUALS = 8;
	
	public static final short[] ALLLEVEL =
		new short[]{
		LEVEL_NONSYNONYM,
		LEVEL_FIRST,
		LEVEL_SECOND,
		LEVEL_THIRD,
		LEVEL_FORTH,
		LEVEL_FIFTH,
		LEVEL_NEAR,
		LEVEL_ABSOLUTE,
		LEVEL_EQUALS
	};
	
	/**
	 * ����ָ��ͬ�弶���ϵ��
	 * @param level
	 * @param coefficient
	 */
	public void setLevelCoef(short level, float coefficient);
	
	/**
	 * ��ȡָ��ͬ�弶���ϵ��
	 * @param level
	 * @return
	 */
	public float getLevelCoef(short level);
	
}
