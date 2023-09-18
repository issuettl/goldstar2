package kr.co.lge.goldstar.orm.dynamic.persistence;

import kr.co.rebel9.web.data.DataMap;

public interface IndexMapper {
	
	int getTodayMemberCount(DataMap params);

	int getTotalMemberCount(DataMap params);
	
}
