package com.bizdata.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bizdata.test.service.ProductionService;
import com.dangdang.ddframe.rdb.sharding.keygen.DefaultKeyGenerator;

@Controller
@RequestMapping("/production")
public class ProductionController {
	
	@Autowired
	private ProductionService productionService;
	
	@Autowired
	private DefaultKeyGenerator generator;
	
	//定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(ProductionController.class); 
	
	@RequestMapping(path="/getProductionListByUserSn", method={RequestMethod.GET})
	@ResponseBody
	public String getProductionListByUserSn(String category, String sn, String productionDatetime) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("category", category);
		paramsMap.put("sn", sn);
		paramsMap.put("productionDatetime", productionDatetime);
		List<Map<String, Object>> list = productionService.getProductionListByUserSn(paramsMap);
		return list.toString();
	}
	
	@RequestMapping(path="/queryProduction", method={RequestMethod.GET})
	@ResponseBody
	public String queryProduction(String category, String beginDatetime, String endDatetime) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("category", category);
		beginDatetime = beginDatetime + " 00:00:00";
		endDatetime = endDatetime + " 23:59:59";
//		paramsMap.put("productionDatetime", beginDatetime +  "#" + endDatetime);
		paramsMap.put("beginTime", beginDatetime);
		paramsMap.put("endTime", endDatetime);
		logger.info("ProductionController类queryProduction()方法请求参数paramsMap=" + paramsMap);
		List<Map<String, Object>> list = productionService.queryProduction(paramsMap);
		logger.info("ProductionController类queryProduction()方法返回值数list=" + list);
		return list.toString();
	}
	
	@RequestMapping(value="/createProduction", method={RequestMethod.GET})
	@ResponseBody
	public String createProduction(String category, String batchNo, String sn, String productionDatetime) {
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("category", category);
			paramsMap.put("batchNo", batchNo);
			paramsMap.put("sn", sn);
			paramsMap.put("productionDatetime", productionDatetime);
			paramsMap.put("id", Math.abs(generator.generateKey().intValue()));
//			paramsMap.put("id", generator.generateKey().intValue());
			productionService.createProduction(paramsMap);
			logger.info("ProductionController类queryProduction()方法请求参数paramsMap=" + paramsMap);
		} catch (Exception e) {
			logger.error("ProductionController类queryProduction()报错" + e.getMessage());
		}
		return "success";
	}

	@RequestMapping(path="/selectProductionAll", method={RequestMethod.GET})
	@ResponseBody
	public String selectProductionAll() {
		List<Map<String, Object>> list = productionService.selectProductionAll();
		return list.toString();
	}

}
