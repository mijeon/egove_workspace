package com.mj.domain;

public class Comments {
	private int comments_idx;
	private String msg;
	private String cwriter;
	private String cregdate;
	
	//mybatis에서는 association라 함
	private Board board;
}
