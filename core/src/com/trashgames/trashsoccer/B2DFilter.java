package com.trashgames.trashsoccer;

public class B2DFilter {
	public static final short TERRAIN = 1;	// 0000 0000 0000 0001
	public static final short PLAYER = 2;	// 0000 0000 0000 0010
	public static final short BALL = 4;		// 0000 0000 0000 0100
	public static final short GOAL = 8;		// 0000 0000 0000 1000
	public static final short SENSORL = 16;	// 0000 0000 0001 0000
	public static final short SENSORR = 32;	// 0000 0000 0010 0000
	public static final short ALL = -1;		// 1111 1111 1111 1111
}
