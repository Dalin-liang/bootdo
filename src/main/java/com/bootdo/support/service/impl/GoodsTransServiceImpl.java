package com.bootdo.support.service.impl;

import com.bootdo.support.dao.GoodsTransMapper;
import com.bootdo.support.dao.StorehouseGoodsDao;
import com.bootdo.support.dto.StorehouseGoodsDO;
import com.bootdo.support.dto.SupportGoodsTransDTO;
import com.bootdo.support.entity.SupportGoodsTrans;
import com.bootdo.support.service.GoodsTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class GoodsTransServiceImpl implements GoodsTransService {

    @Autowired
    private GoodsTransMapper goodsTransMapper;
    @Autowired
    private  StorehouseGoodsDao storehouseGoodsDao;

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public int insert(SupportGoodsTrans supportGoodsTrans) {
    	Map<String,Object>paramsMap=new HashMap<String, Object>();
    	paramsMap.put("goodsstorehouseId", supportGoodsTrans.getTo_goodsstorehouse_id());
    	paramsMap.put("goodsinfoId", supportGoodsTrans.getGoodsinfo_id());
    	List<StorehouseGoodsDO>list=storehouseGoodsDao.list(paramsMap);
    	if(list!=null&&list.size()>0) {//存在则添加库存
    		//调出
    		StorehouseGoodsDO from_storehouseGoods=new StorehouseGoodsDO();
    		from_storehouseGoods.setGoodsinfoId(supportGoodsTrans.getGoodsinfo_id());
    		from_storehouseGoods.setGoodsstorehouseId(supportGoodsTrans.getFrom_goodsstorehouse_id());
    		from_storehouseGoods.setInventorynum(supportGoodsTrans.getTransnum());
    		storehouseGoodsDao.reduceStock(from_storehouseGoods);
    		//调入
    		StorehouseGoodsDO to_storehouseGoods=new StorehouseGoodsDO();
    		to_storehouseGoods.setGoodsinfoId(supportGoodsTrans.getGoodsinfo_id());
    		to_storehouseGoods.setGoodsstorehouseId(supportGoodsTrans.getTo_goodsstorehouse_id());
    		to_storehouseGoods.setInventorynum(supportGoodsTrans.getTransnum());
    		storehouseGoodsDao.addStock(to_storehouseGoods);
        	
    	}else {//不存在生成库存信息
    		//出库
    		StorehouseGoodsDO from_storehouseGoods=new StorehouseGoodsDO();
    		from_storehouseGoods.setGoodsinfoId(supportGoodsTrans.getGoodsinfo_id());
    		from_storehouseGoods.setGoodsstorehouseId(supportGoodsTrans.getFrom_goodsstorehouse_id());
    		from_storehouseGoods.setInventorynum(supportGoodsTrans.getTransnum());
    		storehouseGoodsDao.reduceStock(from_storehouseGoods);
    		//入库
     		StorehouseGoodsDO to_storehouseGoods=new StorehouseGoodsDO();
     		to_storehouseGoods.setGoodsinfoId(supportGoodsTrans.getGoodsinfo_id());
     		to_storehouseGoods.setGoodsstorehouseId(supportGoodsTrans.getTo_goodsstorehouse_id());
     		to_storehouseGoods.setInventorynum(supportGoodsTrans.getTransnum());
        	storehouseGoodsDao.save(to_storehouseGoods); 		
    	}    	    	
        return goodsTransMapper.insert(supportGoodsTrans);
    }

    @Override
    public List<Map<String, Object>> get(SupportGoodsTransDTO supportGoodsTransDTO) {
        return goodsTransMapper.get(supportGoodsTransDTO);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public int update(SupportGoodsTrans supportGoodsTrans) {
     	Map<String,Object> resultMap=goodsTransMapper.getUniqueById(supportGoodsTrans.getId());
    	if(resultMap!=null&&resultMap.size()>0) {
    		int o_num=Integer.parseInt(resultMap.get("transnum").toString());//原数量
    		int n_num=supportGoodsTrans.getTransnum();//新数量
    		int num=0;
    		if(o_num<n_num) {
    			num=n_num-o_num;
    			StorehouseGoodsDO from_storehouseGoods=new StorehouseGoodsDO();
    			from_storehouseGoods.setGoodsinfoId(supportGoodsTrans.getGoodsinfo_id());
    			from_storehouseGoods.setGoodsstorehouseId(supportGoodsTrans.getFrom_goodsstorehouse_id());
    			from_storehouseGoods.setInventorynum(num);
    			storehouseGoodsDao.reduceStock(from_storehouseGoods);
    			
    			StorehouseGoodsDO to_storehouseGoods=new StorehouseGoodsDO();
    			to_storehouseGoods.setGoodsinfoId(supportGoodsTrans.getGoodsinfo_id());
    			to_storehouseGoods.setGoodsstorehouseId(supportGoodsTrans.getTo_goodsstorehouse_id());
    			to_storehouseGoods.setInventorynum(num);
    			storehouseGoodsDao.addStock(to_storehouseGoods);
    			
    			
    		}else {
    			num=o_num-n_num;
      			StorehouseGoodsDO to_storehouseGoods=new StorehouseGoodsDO();
      			to_storehouseGoods.setGoodsinfoId(supportGoodsTrans.getGoodsinfo_id());
      			to_storehouseGoods.setGoodsstorehouseId(supportGoodsTrans.getTo_goodsstorehouse_id());
      			to_storehouseGoods.setInventorynum(num);
    			storehouseGoodsDao.reduceStock(to_storehouseGoods);
    			
     			StorehouseGoodsDO from_storehouseGoods=new StorehouseGoodsDO();
     			from_storehouseGoods.setGoodsinfoId(supportGoodsTrans.getGoodsinfo_id());
     			from_storehouseGoods.setGoodsstorehouseId(supportGoodsTrans.getFrom_goodsstorehouse_id());
     			from_storehouseGoods.setInventorynum(num);
    			storehouseGoodsDao.addStock(from_storehouseGoods);
    		}	
    	}
    	
        return goodsTransMapper.update(supportGoodsTrans);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public int delete(String id) {
     	Map<String,Object> resultMap=goodsTransMapper.getUniqueById(id);

		StorehouseGoodsDO from_storehouseGoods=new StorehouseGoodsDO();
		from_storehouseGoods.setGoodsinfoId(resultMap.get("goodsinfo_id").toString());
		from_storehouseGoods.setGoodsstorehouseId(resultMap.get("from_goodsstorehouse_id").toString());
		from_storehouseGoods.setInventorynum(Integer.parseInt(resultMap.get("transnum").toString()));
		storehouseGoodsDao.addStock(from_storehouseGoods);
		
		StorehouseGoodsDO to_storehouseGoods=new StorehouseGoodsDO();
		to_storehouseGoods.setGoodsinfoId(resultMap.get("goodsinfo_id").toString());
		to_storehouseGoods.setGoodsstorehouseId(resultMap.get("to_goodsstorehouse_id").toString());
		to_storehouseGoods.setInventorynum(Integer.parseInt(resultMap.get("transnum").toString()));
		storehouseGoodsDao.reduceStock(to_storehouseGoods);
        return goodsTransMapper.delete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int logicalDelete(String id) {
        return goodsTransMapper.logicalDelete(id);
    }

    @Override
    public Map<String, Object> getUniqueById(String id) {
        return goodsTransMapper.getUniqueById(id);
    }
}
