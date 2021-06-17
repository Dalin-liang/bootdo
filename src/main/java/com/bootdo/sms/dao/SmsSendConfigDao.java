package com.bootdo.sms.dao;

import com.bootdo.sms.domain.SmsSendConfigDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 短信发送配置表
 * @author wux
 * @email 1992lcg@163.com
 * @date 2019-12-19 15:51:01
 */
@Mapper
public interface SmsSendConfigDao {

	SmsSendConfigDO get(Long id);
	
	List<SmsSendConfigDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SmsSendConfigDO smsSendConfig);
	
	int update(SmsSendConfigDO smsSendConfig);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

    int changeStatus(@Param("id") String id,@Param("issend") String issend);

	int changeStatusByType(@Param("type") String type,@Param("issend")  String issend);

	int changeStatusByParam(@Param("type")String type,@Param("targetid") String targetid, @Param("Sourceid")String Sourceid,@Param("issend") String issend);

	int changeStatusBySourceTypeId(@Param("sourceId")String sourceId,@Param("issend")String issend);

	int changeStatusBySourceMenuTypeId(@Param("sourceMenuId")String sourceId,@Param("issend")String issend);

	String getIsSendBySourceMenuType(@Param("SourceMenuType")String SourceMenuType);

	String getIsSendByPlanmainId(@Param("planmainId")String planmainId);

	String getIsSendByActionprogramMainId(@Param("actionprogramMainId")String actionprogramMainId);

	String getIsSendBySourceType(@Param("source_type")String source_type);
}
