package com.mycgv_jsp.dao;

import java.util.List;

public interface MycgvDao {
	//���������� ���Ǵ� Dao���� �޼ҵ���� interface
	int insert(Object obj);
	List<Object> select(int startCount, int endCount);
}
