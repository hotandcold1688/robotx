package com.macyou.robot.exception;

public class RobotCommonException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RobotCommonException() {
		super();
	}

	public RobotCommonException(Throwable cause) {
		super(cause);
	}

	public RobotCommonException(String message) {
		super(message);
	}

	public RobotCommonException(String message, Throwable cause) {
		super(message, cause);
	}
}
