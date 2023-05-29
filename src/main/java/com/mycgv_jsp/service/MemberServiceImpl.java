package com.mycgv_jsp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycgv_jsp.dao.MemberDao;
import com.mycgv_jsp.vo.MemberVo;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDao memberDao;

	@Override
	public int getLoginResult(MemberVo memberVo) {
		return memberDao.loginCheck(memberVo);
	}

	@Override
	public String getIdCheckResult(String id) {
		int result = memberDao.idCheck(id);
		
		return String.valueOf(result);
	}

	@Override
	public int getJoinResult(MemberVo memberVo) {
		return memberDao.insert(memberVo);
	}

	@Override
	public ArrayList<MemberVo> getList(int startCount, int endCount) {
		return memberDao.select(startCount, endCount);
	}

	@Override
	public int getTotalRowCount() {
		return memberDao.totalRowCount();
	}
	
	
}
