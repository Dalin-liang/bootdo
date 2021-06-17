package com.bootdo.support.service.impl;

import com.bootdo.support.dao.GoodsInfoMapper;
import com.bootdo.support.dto.SupportGoodsInfoDTO;
import com.bootdo.support.entity.SupportGoodsInfo;
import com.bootdo.support.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class GoodsInfoServiceImpl implements GoodsInfoService {

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    @Transactional(readOnly = false)
    public int insert(SupportGoodsInfo supportGoodsInfo) {
        return goodsInfoMapper.insert(supportGoodsInfo);
    }

    @Override
    public List<Map<String, Object>> get(SupportGoodsInfoDTO supportGoodsInfoDTO) {
        return goodsInfoMapper.get(supportGoodsInfoDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public int update(SupportGoodsInfo supportGoodsInfo) {
        return goodsInfoMapper.update(supportGoodsInfo);
    }

    @Override
    @Transactional(readOnly = false)
    public int delete(String id) {
        return goodsInfoMapper.delete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int logicalDelete(String id) {
        return goodsInfoMapper.logicalDelete(id);
    }

    @Override
    public Map<String, Object> getUniqueById(String id) {
        return goodsInfoMapper.getUniqueById(id);
    }
}
