package com.bizdata.test.mapper;

import java.util.List;
import java.util.Map;

public interface ProductionMapper {
	
	List<Map<String, Object>> getProductionListByUserSn(Map<String, Object> paramsMap);
	
	void createProduction(Map<String, Object> paramsMap);

	List<Map<String, Object>> selectProductionAll();
	

	List<Map<String, Object>> queryProduction(Map<String, Object> paramsMap);

}
