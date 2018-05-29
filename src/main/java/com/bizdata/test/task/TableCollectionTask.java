/**    
 * @FileName: TableCollectionTask.java
 * @author: ZHAN567
 * @Description: TODO(用一句话描述该文件做什么)    
 * @date: 2018年4月19日 下午2:15:38  
 * @version: V1.0    
 */
package com.bizdata.test.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**  
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author ZHAN567
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Component
@EnableScheduling
public class TableCollectionTask {
	
    private final static Logger LOGGER = LoggerFactory.getLogger(TableCollectionTask.class); 


//	@Scheduled(cron="0/1 * * * * ?")
	public void tableCollectionTask(){
//		FileUtil.tableList.clear();
		LOGGER.error(TableCollectionTask.class.getName() + "===========================================================================");
		LOGGER.info(TableCollectionTask.class.getName() + "===========================================================================");
	}
}
