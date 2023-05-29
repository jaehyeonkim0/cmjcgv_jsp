package com.mycgv_jsp.dao;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycgv_jsp.vo.MemberVo;
//3. repository = 실행한 결과만 가지고 있는 거; 실제 실행 안함
@Repository
public class MemberDao extends DBConn{
	
	
	//1. SqlSession을 쓴다
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	/** 전체 카운트 가져오기 - 페이징 처리*/
	public int totalRowCount() {
			int count = 0;
			String sql = "select count(*) from mycgv_member";
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
	
	/**
	 * idCheck - 아이디 중복체크
	 */
	public int idCheck(String id) {
		int result = 0;
		String sql = "SELECT COUNT(*) FROM MYCGV_MEMBER WHERE ID=?";
		getPreparedStatement(sql);
		
		try {
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * insert - 회원가입
	 */
	public int insert(MemberVo memberVo) {
		return sqlSession.insert("mapper.member.join", memberVo);
	}
	
	public int loginCheck(MemberVo memberVo) {
		//2. Mapper 작성 후 sqlsession 호출
		return sqlSession.selectOne("mapper.member.login", memberVo);
		/*
		int result=0;
		String sql = "select count(*) from mycgv_member where id=? and pass=?";
		getPreparedStatement(sql);
		
		try {
			
			pstmt.setString(1, memberVo.getId());
			pstmt.setString(2, memberVo.getPass());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
		*/
	}
	
	public ArrayList<MemberVo> select(int startCount, int endCount){
		
		ArrayList<MemberVo> list = new ArrayList<MemberVo>();
		String sql = "select rno, id, name, mdate, grade from ( "
				+ "select rownum rno, id, name, to_char(mdate,'YYYY-MM-DD') mdate, grade from ("
				+ "select id, name, mdate, grade from mycgv_member order by mdate desc)) "
				+ "where rno between ? and ?";
		getPreparedStatement(sql);
		
		try {
			pstmt.setInt(1, startCount);
			pstmt.setInt(2, endCount);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				MemberVo memberVo = new MemberVo();
				
				memberVo.setRno(rs.getInt(1));
				memberVo.setId(rs.getString(2));
				memberVo.setName(rs.getString(3));
				memberVo.setMdate(rs.getString(4));
				memberVo.setGrade(rs.getString(5));
				
				list.add(memberVo);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
}

