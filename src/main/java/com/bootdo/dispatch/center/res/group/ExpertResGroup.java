package com.bootdo.dispatch.center.res.group;

import com.bootdo.dispatch.center.base.DispatchResGroup;
import com.bootdo.dispatch.center.res.ExpertRes;
import com.bootdo.support.dao.ExpertInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/27
 */
@Service
public class ExpertResGroup implements DispatchResGroup<ExpertRes> {

    @Autowired
    private ExpertInfoMapper expertInfoMapper;

    @Override
    public List<ExpertRes> getAllRes() {
        return expertInfoMapper.getAllExpert();
    }
}
