package com.bizdata.test.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizdata.test.mapper.ProductionMapper;

@Service
public class ProductionService {
	
	@Autowired
	private ProductionMapper productionMapper;
	
	public List<Map<String, Object>> getProductionListByUserSn(Map<String, Object> paramsMap) {
		return productionMapper.getProductionListByUserSn(paramsMap);
	}
	
	public void createProduction(Map<String, Object> paramsMap) {
		productionMapper.createProduction(paramsMap);
	}

	public List<Map<String, Object>> selectProductionAll() {
		return productionMapper.selectProductionAll();
	}

	public List<Map<String, Object>> queryProduction(Map<String, Object> paramsMap) {
//		String productionDatetime = MapUtils.getString(paramsMap, "productionDatetime", "");
//		if(productionDatetime.contains("#")){
//			String[] timeStr = productionDatetime.split("#");
//			String beginTime = timeStr[0];
//			String endTime = timeStr[1];
//			paramsMap.put("beginTime", beginTime);
//			paramsMap.put("endTime", endTime);
//		}
		return productionMapper.queryProduction(paramsMap);
	}
}
