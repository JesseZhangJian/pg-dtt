package com.bizdata.algorithm;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.stereotype.Service;

import com.bizdata.commons.utils.DateUtil;
import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.MultipleKeysTableShardingAlgorithm;
import com.google.common.collect.Range;

@Service
public class MultipleTableShardingAlgorithm implements MultipleKeysTableShardingAlgorithm {

	/**
	 * 根据分片值计算分片结果名称集合.
	 *
	 * @param availableTargetNames
	 *            所有的可用目标名称集合, 一般是数据源或表名称
	 * @param shardingValues
	 *            分片值集合
	 *
	 * @return 分片后指向的目标名称集合, 一般是数据源或表名称
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<String> doSharding(
			Collection<String> availableTargetNames,
			Collection<ShardingValue<?>> shardingValues) {
		String tableName = null;
		String category = null;
		String time = null;
		String beginTime = null;
		String endTime = null;
		Range<String> rangeTime = null;
		for (ShardingValue<?> shardingValue : shardingValues) {
			if (shardingValue.getColumnName().equals("category")) {
				category = ((ShardingValue<String>) shardingValue).getValue();
			}
			if (shardingValue.getColumnName().equals("production_datetime")) {
				rangeTime = ((ShardingValue<String>) shardingValue).getValueRange();
				if(rangeTime != null){
					beginTime = rangeTime.lowerEndpoint();
					endTime = rangeTime.upperEndpoint();
				} else{
					time = ((ShardingValue<String>) shardingValue).getValue();
				}
			}
			tableName = ((ShardingValue<String>) shardingValue).getLogicTableName();
		}
		if(!"hair".equals(category) && !"pcc".equals(category)){
			category = "other";
		}
		Collection<String> result = new LinkedHashSet<>();
		StringBuilder builder = new StringBuilder();
		if(beginTime != null && endTime != null){
			String timeStr1 = DateUtil.format1(DateUtil.format4(beginTime));
			String timeStr2 = DateUtil.format1(DateUtil.format4(endTime));
			if(timeStr1.equals(timeStr2)){
				builder.append(category)
				       .append("_").append(tableName)
				       .append("_").append(timeStr1);
				String suffix = builder.toString();
				for (String targetName : availableTargetNames) {
					if (targetName.endsWith(suffix)) {
						result.add(targetName);
					}
				}
			} else {
				String suffix1 = builder.append(category)
			       .append("_").append(tableName)
			       .append("_").append(timeStr1).toString();
				builder = new StringBuilder();
				String suffix2 = builder.append(category)
			       .append("_").append(tableName)
			       .append("_").append(timeStr2).toString();
				for (String targetName : availableTargetNames) {
					if (targetName.endsWith(suffix1) || targetName.endsWith(suffix2)) {
						result.add(targetName);
					}
				}
			}
		} else {
			String timeString = DateUtil.format1(DateUtil.format4(time));
			builder.append(category)
			       .append("_").append(tableName)
			       .append("_").append(timeString);
			String suffix = builder.toString();
			for (String targetName : availableTargetNames) {
				if (targetName.endsWith(suffix)) {
					result.add(targetName);
				}
			}
		}
		return result;
	}

}
