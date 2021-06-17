package com.bootdo.support.service.impl;

import com.bootdo.support.dao.SchedulingMapper;
import com.bootdo.support.dto.SupportSchedulingDTO;
import com.bootdo.support.entity.SupportScheduling;
import com.bootdo.support.service.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class SchedulingServiceImpl implements SchedulingService {

    @Autowired
    private SchedulingMapper schedulingMapper;

    @Override
    @Transactional(readOnly = false)
    public int insert(SupportScheduling supportScheduling) {
        return schedulingMapper.insert(supportScheduling);
    }

    @Override
    public List<Map<String, Object>> get(SupportSchedulingDTO supportGoodsTransDTO) {
        return schedulingMapper.get(supportGoodsTransDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public int update(SupportScheduling supportScheduling) {
        return schedulingMapper.update(supportScheduling);
    }

    @Override
    @Transactional(readOnly = false)
    public int delete(String id) {
        return schedulingMapper.delete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int logicalDelete(String id) {
        return schedulingMapper.logicalDelete(id);
    }

    @Override
    public Map<String, Object> getUniqueById(String id) {
        return schedulingMapper.getUniqueById(id);
    }

	@Override
	public List<Map<String, Object>> getUserByDeptId(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return schedulingMapper.getUserByDeptId(paramMap);
	}

	@Override
	public int count(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return schedulingMapper.count(map);
	}

	@Override
	public int batchRemove(String[] ids) {
		// TODO Auto-generated method stub
		return schedulingMapper.batchRemove(ids);
	}

	@Override
	public List<Map<String, Object>> countByUser(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return schedulingMapper.countByUser(paramMap);
	}

	@Override
	public List<Map<String, Object>> countByDept(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return schedulingMapper.countByDept(paramMap);
	}

    @Override
    public int countByUserCount(Map<String, Object> paramMap) {
        return schedulingMapper.countByUserCount(paramMap);
    }

    @Override
    public int countByDeptCount(Map<String, Object> paramMap) {
        return schedulingMapper.countByDeptCount(paramMap);
    }

    @Override
    public List<String> getSchedulingsDateByUserId(String userId) {
        return schedulingMapper.getSchedulingsDateByUserId(userId);
    }

    @Override
    public List<String> getUserViewSchedulingDate(Map<String, Object> params) {
        return schedulingMapper.getUserViewSchedulingDate(params);
    }

    @Override
    @Transactional(readOnly = false)
    public int workUpdate(SupportScheduling supportScheduling) {
        return schedulingMapper.workUpdate(supportScheduling);
    }
}
