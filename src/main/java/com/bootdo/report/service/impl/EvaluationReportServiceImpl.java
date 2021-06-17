package com.bootdo.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.report.dao.EvaluationReportDao;
import com.bootdo.report.domain.EvaluationReportDO;
import com.bootdo.report.service.EvaluationReportService;



@Service
public class EvaluationReportServiceImpl implements EvaluationReportService {
	@Autowired
	private EvaluationReportDao evaluationReportDao;
	
	@Override
	public EvaluationReportDO get(String id){
		return evaluationReportDao.get(id);
	}
	
	@Override
	public List<EvaluationReportDO> list(Map<String, Object> map){
		return evaluationReportDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return evaluationReportDao.count(map);
	}
	
	@Override
	public int save(EvaluationReportDO evaluationReport){
		return evaluationReportDao.save(evaluationReport);
	}
	
	@Override
	public int update(EvaluationReportDO evaluationReport){
		return evaluationReportDao.update(evaluationReport);
	}
	
	@Override
	public int remove(String id){
		return evaluationReportDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return evaluationReportDao.batchRemove(ids);
	}

	@Override
	public List<EvaluationReportDO> evaluationReportList(Map<String, Object> map) {
		return evaluationReportDao.evaluationReportList(map);
	}

	@Override
	public int evaluationReportCount(Map<String, Object> map) {
		return evaluationReportDao.evaluationReportCount(map);
	}


}
