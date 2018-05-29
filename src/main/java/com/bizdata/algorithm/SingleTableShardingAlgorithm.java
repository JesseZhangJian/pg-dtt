package com.bizdata.algorithm;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.stereotype.Service;

import com.bizdata.commons.utils.DateUtil;
import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.MultipleKeysTableShardingAlgorithm;
import com.google.common.collect.Range;

@Service
public class SingleTableShardingAlgorithm implements MultipleKeysTableShardingAlgorithm {

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
		for (ShardingValue<?> shardingValue : shardingValues) {
			if (shardingValue.getColumnName().equals("category")) {
				category = ((ShardingValue<String>) shardingValue).getValue();
			}
			tableName = ((ShardingValue<String>) shardingValue).getLogicTableName();
		}
		Collection<String> result = new LinkedHashSet<>();
		StringBuilder builder = new StringBuilder();
		builder.append(category).append("_").append(tableName);
		String suffix = builder.toString();
		for (String targetName : availableTargetNames) {
			if (suffix.endsWith(targetName)) {
				result.add(targetName);
			}
		}
		return result;
	}

}
