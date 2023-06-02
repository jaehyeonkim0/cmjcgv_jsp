package com.mycgv_jsp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycgv_jsp.dao.MemberDao;
import com.mycgv_jsp.vo.MemberVo;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDao memberDao;

	@Override
	public int getLoginResult(MemberVo memberVo) {
		//MemberDao memberDao = new MemberDao();
		return memberDao.loginCheck(memberVo);
	}

	@Override
	public String getIdCheckResult(String id) {
		//MemberDao memberDao = new MemberDao();
		int result = memberDao.idCheck(id);
		
		return String.valueOf(result);
	}

	@Override
	public int getJoinResult(MemberVo memberVo) {
		//MemberDao memberDao = new MemberDao();
		return memberDao.insert(memberVo);
	}

	@Override
	public ArrayList<MemberVo> getList(int startCount, int endCount) {
		//MemberDao memberDao = new MemberDao();
		ArrayList<MemberVo> rlist = new ArrayList<MemberVo>();
		List<Object> list = memberDao.select(startCount, endCount);
		for(Object obj : list) {
			MemberVo memberVo = (MemberVo)obj;
			rlist.add(memberVo);
		}
		return rlist;
	}
	
	
}
