package com.bootdo.support.service.impl;

import com.bootdo.support.dao.GoodsPopMapper;
import com.bootdo.support.dao.StorehouseGoodsDao;
import com.bootdo.support.dto.StorehouseGoodsDO;
import com.bootdo.support.dto.SupportGoodsPopDTO;
import com.bootdo.support.entity.SupportGoodsPop;
import com.bootdo.support.service.GoodsPopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class GoodsPopServiceImpl implements GoodsPopService {

    @Autowired
    private GoodsPopMapper goodsPopMapper;
    @Autowired
    private  StorehouseGoodsDao storehouseGoodsDao;
    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public int insert(SupportGoodsPop supportGoodsPop) {
		StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
    	storehouseGoods.setGoodsinfoId(supportGoodsPop.getGoodsinfo_id());
    	storehouseGoods.setGoodsstorehouseId(supportGoodsPop.getGoodsstorehouse_id());
    	storehouseGoods.setInventorynum(supportGoodsPop.getPopnum());
		storehouseGoodsDao.reduceStock(storehouseGoods);
        return goodsPopMapper.insert(supportGoodsPop);
    }

    @Override
    public List<Map<String, Object>> get(SupportGoodsPopDTO supportGoodsPopDTO) {
        return goodsPopMapper.get(supportGoodsPopDTO);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public int update(SupportGoodsPop supportGoodsPop) {
    	Map<String,Object> resultMap=goodsPopMapper.getUniqueById(supportGoodsPop.getId());
    	if(resultMap!=null&&resultMap.size()>0) {
    		int o_num=Integer.parseInt(resultMap.get("popnum").toString());//原数量
    		int n_num=supportGoodsPop.getPopnum();//新数量
    		int num=0;
    		if(o_num<n_num) {
    			num=n_num-o_num;
    			StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
            	storehouseGoods.setGoodsinfoId(supportGoodsPop.getGoodsinfo_id());
            	storehouseGoods.setGoodsstorehouseId(supportGoodsPop.getGoodsstorehouse_id());
            	storehouseGoods.setInventorynum(num);
    			storehouseGoodsDao.reduceStock(storehouseGoods);
    		}else {
    			num=o_num-n_num;
      			StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
            	storehouseGoods.setGoodsinfoId(supportGoodsPop.getGoodsinfo_id());
            	storehouseGoods.setGoodsstorehouseId(supportGoodsPop.getGoodsstorehouse_id());
            	storehouseGoods.setInventorynum(num);
    			storehouseGoodsDao.addStock(storehouseGoods);
    		}
      
    }
    	  return goodsPopMapper.update(supportGoodsPop);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public int delete(String id) {
    	Map<String,Object> resultMap=goodsPopMapper.getUniqueById(id);

			StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
			storehouseGoods.setGoodsinfoId(resultMap.get("goodsinfo_id").toString());
	    	storehouseGoods.setGoodsstorehouseId(resultMap.get("goodsstorehouse_id").toString());
        	storehouseGoods.setInventorynum(Integer.parseInt(resultMap.get("popnum").toString()));
			storehouseGoodsDao.addStock(storehouseGoods);
        return goodsPopMapper.delete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int logicalDelete(String id) {
        return goodsPopMapper.logicalDelete(id);
    }

    @Override
    public Map<String, Object> getUniqueById(String id) {
        return goodsPopMapper.getUniqueById(id);
    }
}
