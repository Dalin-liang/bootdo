package com.bootdo.report.service;

import com.bootdo.common.utils.Query;
import com.bootdo.report.domain.EvaluationReportDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author wux
 * @email 1992lcg@163.com
 * @date 2020-04-02 16:27:24
 */
public interface EvaluationReportService {
	
	EvaluationReportDO get(String id);
	
	List<EvaluationReportDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(EvaluationReportDO evaluationReport);
	
	int update(EvaluationReportDO evaluationReport);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

    List<EvaluationReportDO> evaluationReportList(Map<String, Object> map);

	int evaluationReportCount(Map<String, Object> map);

}
