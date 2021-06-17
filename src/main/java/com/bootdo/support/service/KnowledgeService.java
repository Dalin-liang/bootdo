package com.bootdo.support.service;

import com.bootdo.support.dto.KnowledgeDO;

import java.util.List;
import java.util.Map;

/**
 * 知识库
 * 
 * @author tanzhitao
 * @email 1992lcg@163.com
 * @date 2019-08-13 10:21:50
 */
public interface KnowledgeService {
	
	KnowledgeDO get(String id);
	
	List<Map<String,Object>> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(KnowledgeDO knowledge);
	
	int update(KnowledgeDO knowledge);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	/**
	 * 根据参数查询知识库信息
	 * @param id id
	 * @param warnId 预警类别ID
	 * @param type 分类
	 * @param title 标题
	 * @param keywork 关键字
	 * @param source 来源
	 * @return list
	 */
	List<KnowledgeDO> getListByParams(Map<String,Object> map);

}
