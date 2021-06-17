package com.bootdo.baiyi.service;

import com.bootdo.baiyi.domain.PushdataLocationDO;

import java.util.List;
import java.util.Map;

/**
 * 位置数据
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-09-16 10:11:24
 */
public interface PushdataLocationService {
	
	PushdataLocationDO get(String id);
	
	List<PushdataLocationDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);

}
