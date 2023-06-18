package com.mj.board.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mj.domain.Board;
import com.mj.exception.BoardException;

public class BoardDAO {
	//String url="jdbc:oracle:thin:@localhost:1521:XE";
	//String user="mj";
	//String pass="1234";
	//PoolManager pool=PoolManager.getInstance();
	private Connection con;
	
	
	public void setCon(Connection con) {
		this.con = con;
	}
	
	//글쓰기
	public void insert(Board board) throws BoardException{
		//Connection con=null; 
		PreparedStatement pstmt=null;
		
		try {
			//드라이버 로드
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//오라클 접속
			//con=DriverManager.getConnection(url, user, pass);
			//con=pool.getConnection();  //빌려옴
			
			StringBuilder sb=new StringBuilder();  //동기화 X
			
			sb.append("insert into board(board_idx, title, writer, content)");
			sb.append(" values(seq_board.nextval, ?, ?, ?)");
			
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			
			int result=pstmt.executeUpdate();
			//result=0;  //예외처리 테스트
			if(result<1) {
				throw new BoardException("글쓰기 실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//뉴스 + 댓글 목록 가져오기 
	public List<Board> selectAll() {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Board> boardList=new ArrayList();
		
		StringBuilder sb=new StringBuilder();
		sb.append("select count(cwriter) as cnt, b.board_idx as board_idx, title, writer, regdate, hit");
		sb.append(" from board b left outer join comments c");
		sb.append(" on b.board_idx=c.board_idx");
		sb.append(" group by b.board_idx, title, writer, regdate, hit");
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				Board board=new Board();
				board.setBoard_idx(rs.getInt("board_idx"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setRegdate(rs.getString("regdate"));
				board.setHit(rs.getInt("cnt"));
				board.setHit(rs.getInt("hit"));
				boardList.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return boardList;
	}
	
}
