package com.bootdo.support.service.impl;

import com.bootdo.support.dao.GoodsPushMapper;
import com.bootdo.support.dao.StorehouseGoodsDao;
import com.bootdo.support.dto.StorehouseGoodsDO;
import com.bootdo.support.dto.SupportGoodsPushDTO;
import com.bootdo.support.entity.SupportGoodsPush;
import com.bootdo.support.service.GoodsPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class GoodsPushServiceImpl implements GoodsPushService {

    @Autowired
    private GoodsPushMapper goodsPushMapper;
    @Autowired
    private  StorehouseGoodsDao storehouseGoodsDao;

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public int insert(SupportGoodsPush supportGoodsPush) {
    	int i=0;
    	i+= goodsPushMapper.insert(supportGoodsPush);
    	Map<String,Object>paramsMap=new HashMap<String, Object>();
    	paramsMap.put("goodsstorehouseId", supportGoodsPush.getGoodsstorehouse_id());
    	paramsMap.put("goodsinfoId", supportGoodsPush.getGoodsinfo_id());
    	List<StorehouseGoodsDO>list=storehouseGoodsDao.list(paramsMap);
    	//关联表存在则添加库存
    	if(list!=null&&list.size()>0) {
    		StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
        	storehouseGoods.setGoodsinfoId(supportGoodsPush.getGoodsinfo_id());
        	storehouseGoods.setGoodsstorehouseId(supportGoodsPush.getGoodsstorehouse_id());
        	storehouseGoods.setInventorynum(supportGoodsPush.getPushnum());
        	storehouseGoodsDao.addStock(storehouseGoods);
    	}else {
    		StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
        	storehouseGoods.setGoodsinfoId(supportGoodsPush.getGoodsinfo_id());
        	storehouseGoods.setGoodsstorehouseId(supportGoodsPush.getGoodsstorehouse_id());
        	storehouseGoods.setInventorynum(supportGoodsPush.getPushnum());
        	storehouseGoodsDao.save(storehouseGoods);
    	}
    	
    
        return i;
    }

    @Override
    public List<Map<String, Object>> get(SupportGoodsPushDTO supportGoodsPushDTO) {
        return goodsPushMapper.get(supportGoodsPushDTO);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public int update(SupportGoodsPush supportGoodsPush) {
    	Map<String,Object> resultMap=goodsPushMapper.getUniqueById(supportGoodsPush.getId());
    	if(resultMap!=null&&resultMap.size()>0) {
    		int o_num=Integer.parseInt(resultMap.get("pushnum").toString());//原数量
    		int n_num=supportGoodsPush.getPushnum();//新数量
    		int num=0;
    		if(o_num<n_num) {
    			num=n_num-o_num;
    			StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
            	storehouseGoods.setGoodsinfoId(supportGoodsPush.getGoodsinfo_id());
            	storehouseGoods.setGoodsstorehouseId(supportGoodsPush.getGoodsstorehouse_id());
            	storehouseGoods.setInventorynum(num);
    			storehouseGoodsDao.addStock(storehouseGoods);
    		}else {
    			num=o_num-n_num;
      			StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
            	storehouseGoods.setGoodsinfoId(supportGoodsPush.getGoodsinfo_id());
            	storehouseGoods.setGoodsstorehouseId(supportGoodsPush.getGoodsstorehouse_id());
            	storehouseGoods.setInventorynum(num);
    			storehouseGoodsDao.reduceStock(storehouseGoods);
    		}
    		
    	}
        return goodsPushMapper.update(supportGoodsPush);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public int delete(String id) {
    	Map<String,Object> resultMap=goodsPushMapper.getUniqueById(id);
    	StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
    	storehouseGoods.setGoodsinfoId(resultMap.get("goodsinfo_id").toString());
    	storehouseGoods.setGoodsstorehouseId(resultMap.get("goodsstorehouse_id").toString());
    	storehouseGoods.setInventorynum(Integer.parseInt(resultMap.get("pushnum").toString()));
		storehouseGoodsDao.reduceStock(storehouseGoods);
        return goodsPushMapper.delete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int logicalDelete(String id) {
        return goodsPushMapper.logicalDelete(id);
    }

    @Override
    public Map<String, Object> getUniqueById(String id) {
        return goodsPushMapper.getUniqueById(id);
    }

    @Override
    public List<Map<String, Object>> getStoreHouse() {
        return goodsPushMapper.getStoreHouse();
    }

    @Override
    public List<Map<String, Object>> getGoods() {
        return goodsPushMapper.getGoods();
    }

	@Override
	public List<Map<String, Object>> getAllStoreHouse() {
		return goodsPushMapper.getAllStoreHouse();
	}

	@Override
	public List<Map<String, Object>> getAllGoods() {
		return goodsPushMapper.getAllGoods();
	}
}
