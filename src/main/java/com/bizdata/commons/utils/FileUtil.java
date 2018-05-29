/**    
 * @FileName: FileUtil.java
 * @author: ZHAN567
 * @Description: TODO(用一句话描述该文件做什么)    
 * @date: 2018年4月19日 下午1:51:03  
 * @version: V1.0    
 */
package com.bizdata.commons.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

/**  
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author ZHAN567
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class FileUtil {

	public static List<String> tableList = new ArrayList<String>();
	
	static{
//		tableList.addAll(getTableCollection());
	}
	
	public static List<String> getTableCollection(){
		String JsonContext = FileUtil.ReadFile("tableCollection.json"); 
		JSONObject jsonObject = JSONObject.fromObject(JsonContext);
		JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("list"));
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> tableList = JSONArray.fromObject(jsonArray);
		List<String> list = new ArrayList<String>();
		for (Map<String, Object> map : tableList) {
			list.add(MapUtils.getString(map, "table", ""));
		}
		return list;
	}

	/**
	 * 
	 * 
	 * @param @param Path
	 * @param @return
	 * @return String
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	public static String ReadFile(String Path){  
        BufferedReader reader = null;  
        String laststr = "";  
        try{  
            FileInputStream fileInputStream = new FileInputStream(Path);  
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");  
            reader = new BufferedReader(inputStreamReader);  
            String tempString = null;  
            while((tempString = reader.readLine()) != null){  
                laststr += tempString;  
            }  
            reader.close();  
        }catch(IOException e){  
            e.printStackTrace();  
        }finally{  
            if(reader != null){  
                try {  
                    reader.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return laststr;  
    }  
}
