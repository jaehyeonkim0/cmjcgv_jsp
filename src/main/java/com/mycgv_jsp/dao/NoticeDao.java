package com.mycgv_jsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycgv_jsp.vo.NoticeVo;

@Repository
public class NoticeDao implements MycgvDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	/**select - 게시글 전체 리스트 - 페이징 처리(startCount,endCount)*/
	public List<Object> select(int startCount, int endCount){
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("start", startCount);
		param.put("end", endCount);
		
		//List<NoticeVo> list = sqlSession.selectList("mapper.notice.list", param);
		
		return sqlSession.selectList("mapper.notice.list", param);
		/*
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
		*/
	}
	
	@Override
	public int insert(Object noticeVo) {
		return sqlSession.insert("mapper.notice.insert", noticeVo);
		/*
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
		*/
	}
	
	public ArrayList<NoticeVo> select(){
		List<NoticeVo> list = sqlSession.selectList("mapper.notice.list2");
		return (ArrayList<NoticeVo>) list;
		/*
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
		*/
	}
	
	public NoticeVo select(String nid) {
		return sqlSession.selectOne("mapper.notice.content", nid);
		/*
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
		*/
	}
	
	public int update(NoticeVo noticeVo) {
		return sqlSession.update("mapper.notice.update", noticeVo);
		/*
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
		*/
	}
	
	public int delete(String nid) {
		return sqlSession.delete("mapper.notice.delete", nid);
		/*
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
		*/
	}
	
	public void updateNhits(String nid) {
		sqlSession.update("mapper.notice.updateHits", nid);
		/*
		String sql = "update mycgv_notice set nhits=nhits+1 where nid=?";
		getPreparedStatement(sql);
		
		try {
			pstmt.setString(1, nid);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}

}
