package com.macyou.robot.index;

public interface IndexBuilderFactory {

	public IndexBuilder getIndexBuilder(IndexType type) throws Exception;

	static enum IndexType {
		FULL, INCREMENT;
	};
}

