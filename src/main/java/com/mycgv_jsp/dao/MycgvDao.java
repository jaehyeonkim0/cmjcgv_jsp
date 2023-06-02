package com.mycgv_jsp.dao;

import java.util.List;

public interface MycgvDao {
	//공통적으로 사용되는 Dao들의 메소드들의 interface
	int insert(Object obj);
	List<Object> select(int startCount, int endCount);
}
