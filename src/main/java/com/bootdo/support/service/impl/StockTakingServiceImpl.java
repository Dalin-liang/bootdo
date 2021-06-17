package com.bootdo.support.service.impl;

import com.bootdo.support.dao.StockTakingMapper;
import com.bootdo.support.dao.StorehouseGoodsDao;
import com.bootdo.support.dto.StorehouseGoodsDO;
import com.bootdo.support.dto.SupportStockTakingDTO;
import com.bootdo.support.entity.SupportStockTaking;
import com.bootdo.support.service.StockTakingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class StockTakingServiceImpl implements StockTakingService {

    @Autowired
    private StockTakingMapper stockTakingMapper;
    @Autowired
    private  StorehouseGoodsDao storehouseGoodsDao;
    @Override
    @Transactional(readOnly = false)
    public int insert(SupportStockTaking supportStockTaking) {
    	if(supportStockTaking.getCount()==0) {
    		StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
        	storehouseGoods.setGoodsinfoId(supportStockTaking.getGoodsinfo_id());
        	storehouseGoods.setGoodsstorehouseId(supportStockTaking.getGoodsstorehouse_id());
        	storehouseGoods.setInventorynum(supportStockTaking.getStocktakingnum());
    		storehouseGoodsDao.reduceStock(storehouseGoods);
    	}else {
     		StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
        	storehouseGoods.setGoodsinfoId(supportStockTaking.getGoodsinfo_id());
        	storehouseGoods.setGoodsstorehouseId(supportStockTaking.getGoodsstorehouse_id());
        	storehouseGoods.setInventorynum(supportStockTaking.getStocktakingnum());
    		storehouseGoodsDao.addStock(storehouseGoods);
    	}
        return stockTakingMapper.insert(supportStockTaking);
    }

    @Override
    public List<Map<String, Object>> get(SupportStockTakingDTO supportStockTakingDTO) {
        return stockTakingMapper.get(supportStockTakingDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public int update(SupportStockTaking supportStockTaking) {
    	Map<String,Object> resultMap=stockTakingMapper.getUniqueById(supportStockTaking.getId());
    	if(resultMap!=null&&resultMap.size()>0) {
    		int o_num=Integer.parseInt(resultMap.get("stocktakingnum").toString());//原数量
    		int n_num=supportStockTaking.getStocktakingnum();//新数量
    		int num=0;
    		if(supportStockTaking.getCount()==Integer.parseInt(resultMap.get("count").toString())&&supportStockTaking.getCount()==0) {//盘亏
    			if(o_num>n_num) {
    				num=o_num-n_num;
    	     		StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
    	        	storehouseGoods.setGoodsinfoId(supportStockTaking.getGoodsinfo_id());
    	        	storehouseGoods.setGoodsstorehouseId(supportStockTaking.getGoodsstorehouse_id());
    	        	storehouseGoods.setInventorynum(num);
    	    		storehouseGoodsDao.addStock(storehouseGoods);
    			}else {
    				num=n_num-o_num;
    		   		StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
    	        	storehouseGoods.setGoodsinfoId(supportStockTaking.getGoodsinfo_id());
    	        	storehouseGoods.setGoodsstorehouseId(supportStockTaking.getGoodsstorehouse_id());
    	        	storehouseGoods.setInventorynum(num);
    	    		storehouseGoodsDao.reduceStock(storehouseGoods);
    			}
    		}else if(supportStockTaking.getCount()==Integer.parseInt(resultMap.get("count").toString())&&supportStockTaking.getCount()==1){//盘盈
    			if(o_num>n_num) {
    				num=o_num-n_num;
    	     		StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
    	        	storehouseGoods.setGoodsinfoId(supportStockTaking.getGoodsinfo_id());
    	        	storehouseGoods.setGoodsstorehouseId(supportStockTaking.getGoodsstorehouse_id());
    	        	storehouseGoods.setInventorynum(num);
    	    		storehouseGoodsDao.reduceStock(storehouseGoods);
    			}else {
    				num=n_num-o_num;
    		   		StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
    	        	storehouseGoods.setGoodsinfoId(supportStockTaking.getGoodsinfo_id());
    	        	storehouseGoods.setGoodsstorehouseId(supportStockTaking.getGoodsstorehouse_id());
    	        	storehouseGoods.setInventorynum(num);
    	    		storehouseGoodsDao.addStock(storehouseGoods);
    			}
    		}
    	
    	
    }
        return stockTakingMapper.update(supportStockTaking);

    }

    @Override
    @Transactional(readOnly = false)
    public int delete(String id) {
    	Map<String,Object> resultMap=stockTakingMapper.getUniqueById(id);

      	if(Integer.parseInt(resultMap.get("count").toString())==0) {
    		StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
        	storehouseGoods.setGoodsinfoId(resultMap.get("goodsinfo_id").toString());
        	storehouseGoods.setGoodsstorehouseId(resultMap.get("goodsstorehouse_id").toString());
        	storehouseGoods.setInventorynum(Integer.parseInt(resultMap.get("stocktakingnum").toString()));
    		storehouseGoodsDao.addStock(storehouseGoods);
    	}else if(Integer.parseInt(resultMap.get("count").toString())==1) {
    		StorehouseGoodsDO storehouseGoods=new StorehouseGoodsDO();
        	storehouseGoods.setGoodsinfoId(resultMap.get("goodsinfo_id").toString());
        	storehouseGoods.setGoodsstorehouseId(resultMap.get("goodsstorehouse_id").toString());
        	storehouseGoods.setInventorynum(Integer.parseInt(resultMap.get("stocktakingnum").toString()));
    		storehouseGoodsDao.reduceStock(storehouseGoods);
    	}
        return stockTakingMapper.delete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int logicalDelete(String id) {
        return stockTakingMapper.logicalDelete(id);
    }

    @Override
    public Map<String, Object> getUniqueById(String id) {
        return stockTakingMapper.getUniqueById(id);
    }
}
