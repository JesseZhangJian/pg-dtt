/*
 * Copyright (C), 2002-2015, 苏宁易购电子商务有限公司
 * FileName: //文件名
 * Author:   13041609
 * Date:     2015-11-5 下午5:49:51
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.bizdata.dal;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈一句话功能简述〉<br>
 * 分库路由规则实现〈功能详细描述〉
 * 
 * @author 13041609
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ShardRouter {
	
    /**
     * 日志类
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShardRouter.class);

    /**
     * 功能描述: <br>
     * 单号分库实现〈功能详细描述〉
     * 
     * @param scpOrderNo
     * @return 结果返回为0表示分库1;结果返回为1表示分库2;结果返回-1表示主库
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static int getShardNo(String scpOrderNo) {
        int shardNo = -1;
        // 数据库
        String dbHashCode1Temp = "2";
        // 偏移量
        String dbOffset1Temp = "1";
        // 表
        String tableHashCode1Temp = "2";
        // 偏移量
        String tableOffset1Temp = "1";

        if (StringUtils.isEmpty(dbHashCode1Temp)
                || StringUtils.isEmpty(dbOffset1Temp)
                || (StringUtils.isEmpty(tableHashCode1Temp) || StringUtils.isEmpty(tableOffset1Temp)
                        || !NumberUtils.isNumber(dbHashCode1Temp) || !NumberUtils.isNumber(dbOffset1Temp))
                || !NumberUtils.isNumber(tableHashCode1Temp) || !NumberUtils.isNumber(tableOffset1Temp)) {
            LOGGER.error("ShardRouter 分库规则失败");
            return shardNo;
        }

        int dbHashCode1 = Integer.valueOf(dbHashCode1Temp);
        int dbOffset1 = Integer.valueOf(dbOffset1Temp);
        int tableHashCode1 = Integer.valueOf(tableHashCode1Temp);
        int tableOffset1 = Integer.valueOf(tableOffset1Temp);

        if (StringUtils.isEmpty(scpOrderNo)) {
            return shardNo;
        }

        if (!NumberUtils.isNumber(scpOrderNo)) {
            LOGGER.error("ShardRouter 无法根据订单号" + scpOrderNo + "实现分库路由规则");
            throw new IllegalArgumentException("ShardRouter 无法根据订单号" + scpOrderNo + "实现分库路由规则");
        }

        long scpOrderNoTemp = Long.valueOf(scpOrderNo);
        String type = scpOrderNo.substring(0, 1);
        if ("1".equals(type)) {
            // if (scpOrderNoTemp >= ps1 && scpOrderNoTemp <= pe1) {
            shardNo = (int) ((scpOrderNoTemp % (dbHashCode1 * tableHashCode1)) / dbHashCode1 + dbOffset1 * tableOffset1);
            // }
        } else if ("2".equals(type)) {
            // if (scpOrderNoTemp >= rs1 && scpOrderNoTemp <= re1) {
            shardNo = (int) ((scpOrderNoTemp % (dbHashCode1 * tableHashCode1)) / dbHashCode1 + dbOffset1 * tableOffset1);
            // }
        } else if ("3".equals(type)) {
            // if (scpOrderNoTemp >= cs1 && scpOrderNoTemp <= ce1) {
            shardNo = (int) ((scpOrderNoTemp % (dbHashCode1 * tableHashCode1)) / dbHashCode1 + dbOffset1 * tableOffset1);
            // }
        } else if ("4".equals(type)) {
            // if (scpOrderNoTemp >= cs1 && scpOrderNoTemp <= ce1) {
            shardNo = (int) ((scpOrderNoTemp % (dbHashCode1 * tableHashCode1)) / dbHashCode1 + dbOffset1 * tableOffset1);
            // }
        }
        return shardNo;
    }

}
