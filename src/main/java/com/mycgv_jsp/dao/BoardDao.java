package com.mycgv_jsp.dao;

import java.util.ArrayList;

import com.mycgv_jsp.vo.BoardVo;

public class BoardDao extends DBConn {
	
	/** 전체 카운트 가져오기 - 페이징 처리*/
	public int totalRowCount() {
			int count = 0;
			String sql = "select count(*) from mycgv_board";
			getPreparedStatement(sql);
			
			try {
				rs = pstmt.executeQuery();
				while(rs.next()) {				
					count = rs.getInt(1);
				}			
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return count;		
		}
	
	/**select - 게시글 전체 리스트 - 페이징 처리(startCount,endCount)*/
	public ArrayList<BoardVo> select(int startCount, int endCount){
		ArrayList<BoardVo> list = new ArrayList<BoardVo>();
		StringBuffer sb = new StringBuffer();
		sb.append("select rno, bid, btitle, bcontent, bhits, id, bdate from (")
		.append("select rownum rno,bid,btitle,bcontent,bhits,id,to_char(bdate,'YY-MM-DD') bdate from (")
		.append("select bid,btitle,bcontent,bhits,id,bdate from mycgv_board order by bdate desc)) ")
		.append("where rno between ? and ?");
		getPreparedStatement(sb.toString());
		
		try {
			pstmt.setInt(1, startCount);
			pstmt.setInt(2, endCount);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo boardVo = new BoardVo();
				boardVo.setRno(rs.getInt("rno"));
				boardVo.setBid(rs.getString("bid"));
				boardVo.setBtitle(rs.getString("btitle"));
				boardVo.setBcontent(rs.getString("bcontent"));
				boardVo.setBhits(rs.getInt("bhits"));
				boardVo.setId(rs.getString("id"));
				boardVo.setBdate(rs.getString("bdate"));

				list.add(boardVo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	/**insert - 게시글 등록*/
	public int insert(BoardVo boardVo) {
		
		int result=0;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into mycgv_board (bid, btitle, bcontent, bhits, id, bdate, bfile, bsfile) ")
		.append("values ('b_'||ltrim(to_char(sequ_mycgv_board.nextval,'0000')),?,?,0,?,sysdate,?,?)");
		getPreparedStatement(sb.toString());
		
		try {
			
			pstmt.setString(1, boardVo.getBtitle());
			pstmt.setString(2, boardVo.getBcontent());
			pstmt.setString(3, boardVo.getId());
			pstmt.setString(4, boardVo.getBfile());
			pstmt.setString(5, boardVo.getBsfile());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**select - 게시글 전체 리스트
	public ArrayList<BoardVo> select(){
		ArrayList<BoardVo> list = new ArrayList<BoardVo>();
		StringBuffer sb = new StringBuffer();
		sb.append("select rownum rno,bid,btitle,bcontent,bhits,id,to_char(bdate,'YY-MM-DD') bdate from (")
		.append("select bid,btitle,bcontent,bhits,id,bdate from mycgv_board order by bdate desc)");
		getPreparedStatement(sb.toString());
		
		try {
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				BoardVo boardVo = new BoardVo();
				boardVo.setRno(rs.getInt("rno"));
				boardVo.setBid(rs.getString("bid"));
				boardVo.setBtitle(rs.getString("btitle"));
				boardVo.setBcontent(rs.getString("bcontent"));
				boardVo.setBhits(rs.getInt("bhits"));
				boardVo.setId(rs.getString("id"));
				boardVo.setBdate(rs.getString("bdate"));

				list.add(boardVo);
			}
			
			close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	*/
	
	public BoardVo select(String bid) {
		BoardVo boardVo = null;
		String sql = "select bid, btitle, bcontent, bhits, id, to_char(bdate,'YY-MM-DD HH:MI') bdate, bfile, bsfile from mycgv_board where bid=?";
		getPreparedStatement(sql);
		
		try {
			
			pstmt.setString(1, bid);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardVo = new BoardVo();
				boardVo.setBid(rs.getString("bid"));
				boardVo.setBtitle(rs.getString("btitle"));
				boardVo.setBcontent(rs.getString("bcontent"));
				boardVo.setBhits(rs.getInt("bhits"));
				boardVo.setId(rs.getString("id"));
				boardVo.setBdate(rs.getString("bdate"));
				boardVo.setBfile(rs.getString("bfile"));
				boardVo.setBsfile(rs.getString("bsfile"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardVo;
	}
	
	/**조회 수 증가*/
	public void updateHits(String bid) {
		
		String sql = "update mycgv_board set bhits =  bhits + 1 where bid = ?";
		getPreparedStatement(sql);
		
		try {
			pstmt.setString(1, bid);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public int update(BoardVo boardVo) {
		int result=0;
		String sql = "update mycgv_board set btitle=?, bcontent=? where bid=?";
		getPreparedStatement(sql);
		
		try {
			
			pstmt.setString(1, boardVo.getBtitle());
			pstmt.setString(2, boardVo.getBcontent());
			pstmt.setString(3, boardVo.getBid());
			
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int delete(String bid) {
		int result=0;
		String sql = "delete from mycgv_board where bid=?";
		getPreparedStatement(sql);
		
		try {
			
			pstmt.setString(1, bid);
			
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
