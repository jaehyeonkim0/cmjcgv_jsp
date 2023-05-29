package com.mycgv_jsp.dao;

import java.util.ArrayList;

import com.mycgv_jsp.vo.BoardVo;
import com.mycgv_jsp.vo.NoticeVo;

public class NoticeDao extends DBConn{
	
	/** 전체 카운트 가져오기 - 페이징 처리*/
	public int totalRowCount() {
			int count = 0;
			String sql = "select count(*) from mycgv_notice";
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
	public ArrayList<NoticeVo> select(int startCount, int endCount){
		ArrayList<NoticeVo> list = new ArrayList<NoticeVo>();
		StringBuffer sb = new StringBuffer();
		sb.append("select rno, nid, ntitle, ncontent, nhits, ndate from (")
		.append("select rownum rno,nid,ntitle,ncontent,nhits,to_char(ndate,'YY-MM-DD') ndate from (")
		.append("select nid,ntitle,ncontent,nhits,ndate from mycgv_notice order by ndate desc)) ")
		.append("where rno between ? and ?");
		getPreparedStatement(sb.toString());
		
		try {
			pstmt.setInt(1, startCount);
			pstmt.setInt(2, endCount);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeVo noticeVo = new NoticeVo();
				noticeVo.setRno(rs.getInt("rno"));
				noticeVo.setNid(rs.getString("nid"));
				noticeVo.setNtitle(rs.getString("ntitle"));
				noticeVo.setNcontent(rs.getString("ncontent"));
				noticeVo.setNhits(rs.getInt("nhits"));
				noticeVo.setNdate(rs.getString("ndate"));

				list.add(noticeVo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public int insert(NoticeVo noticeVo) {
		
		int result=0;
		String sql = "insert into mycgv_notice (nid, ntitle, ncontent, nhits, ndate) values "
				+ "('n_'||ltrim(to_char(sequ_mycgv_notice.nextval,'0000')),?,?,0,sysdate)";
		getPreparedStatement(sql);
		
		try {
			pstmt.setString(1, noticeVo.getNtitle());
			pstmt.setString(2, noticeVo.getNcontent());
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public ArrayList<NoticeVo> select(){
		ArrayList<NoticeVo> list = new ArrayList<NoticeVo>();
		String sql = "select rownum rno, nid, ntitle, ncontent, nhits, to_char(ndate, 'YY-MM-DD') ndate from ("
				+ "select nid, ntitle, ncontent, nhits, ndate from mycgv_notice order by ndate desc)";
		getPreparedStatement(sql);
		
		try {
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeVo noticeVo = new NoticeVo();
				
				noticeVo.setRno(rs.getInt(1));
				noticeVo.setNid(rs.getString(2));
				noticeVo.setNtitle(rs.getString(3));
				noticeVo.setNcontent(rs.getString(4));
				noticeVo.setNhits(rs.getInt(5));
				noticeVo.setNdate(rs.getString(6));
				
				list.add(noticeVo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public NoticeVo select(String nid) {
		NoticeVo noticeVo = null;
		String sql = "select nid, ntitle, ncontent, nhits, to_char(ndate, 'YY-MM-DD') ndate from mycgv_notice where nid=?";
		getPreparedStatement(sql);
		
		try {
			pstmt.setString(1, nid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				noticeVo = new NoticeVo();
				
				noticeVo.setNid(rs.getString(1));
				noticeVo.setNtitle(rs.getString(2));
				noticeVo.setNcontent(rs.getString(3));
				noticeVo.setNhits(rs.getInt(4));
				noticeVo.setNdate(rs.getString(5));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noticeVo;
	}
	
	public int update(NoticeVo noticeVo) {
		int result=0;
		String sql = "update mycgv_notice set ntitle=?, ncontent=? where nid=?";
		getPreparedStatement(sql);
		
		try {
			pstmt.setString(1, noticeVo.getNtitle());
			pstmt.setString(2, noticeVo.getNcontent());
			pstmt.setString(3, noticeVo.getNid());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int delete(String nid) {
		int result=0;
		String sql = "delete from mycgv_notice where nid=?";
		getPreparedStatement(sql);
		
		try {
			pstmt.setString(1, nid);
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
		
	}
	
	public void updateNhits(String nid) {
		String sql = "update mycgv_notice set nhits=nhits+1 where nid=?";
		getPreparedStatement(sql);
		
		try {
			pstmt.setString(1, nid);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
