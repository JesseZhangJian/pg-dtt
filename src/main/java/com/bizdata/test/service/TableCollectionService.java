package com.bizdata.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bizdata.test.mapper.TableCollectionMapper;

@Component
public class TableCollectionService {
	
	@Autowired
	private TableCollectionMapper tableCollectionMapper;
	
	public List<String> queryTableCollection(){
		List<String> list = new ArrayList<String>();
		List<Map<String, Object>> listMap = tableCollectionMapper.queryTableCollection();
		for (Map<String, Object> map : listMap) {
			list.add(MapUtils.getString(map, "tableName", ""));
		}
		return list;
	}
}
