package com.mj.board.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.mj.domain.Board;
import com.mj.exception.BoardException;

public class BoardService {
	private PoolManager pool=PoolManager.getInstance();
	private BoardDAO boardDAO=new BoardDAO();
	
	//글쓰기
	public void insert(Board board) throws BoardException{
		//dao가 아닌 service에서 connection을 얻어오는 이유는?
		//서비스는 dao들을 이용해 트랜잭션 처리를 해야 하기 때문에 dao들 간의 connection을 공유시키기 위함
		Connection con=pool.getConnection();  
		
		boardDAO.setCon(con);  //일시키기 전에 connection 빌려오기
		
		try {
			con.setAutoCommit(false);  //connection 객체의 디폴트 커밋 속성을 false로 넣자 -> 무조건 커밋을 막기 위해
			boardDAO.insert(board);
			con.commit();  //트랜잭션 확정
		} catch (SQLException e1) {
			e1.printStackTrace();
		}catch (BoardException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BoardException("글쓰기 실패", e);
		}finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pool.freeConnection(con);  //일 시킨 후 connection 반납
		}
	}
	
	//목록 가져오기
	public List<Board> selectAll() {
		Connection con=pool.getConnection();  //connection 대여
		
		boardDAO.setCon(con);
		
		List boardList=boardDAO.selectAll();
		pool.freeConnection(con);  //connection 반납
		
		return boardList;
	}
}
