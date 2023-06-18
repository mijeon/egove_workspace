package com.mj.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mj.board.model.BoardService;
import com.mj.domain.Board;

public class ListController extends HttpServlet{
	BoardService boardService=new BoardService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//일시키기
		List<Board> boardList=boardService.selectAll();
		
		//결과 저장
		request.setAttribute("boardList", boardList);
		
		//포워딩
		RequestDispatcher dis=request.getRequestDispatcher("/board/list.jsp");
		dis.forward(request, response);  //새로운 요청이 아니라 listController의 요청을 전달함
	}
}
