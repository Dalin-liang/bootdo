package com.bootdo.dispatch.center.res.group;

import com.bootdo.dispatch.center.base.DispatchResGroup;
import com.bootdo.dispatch.center.base.Location.SimpleLocation;
import com.bootdo.dispatch.center.res.CameraRes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/27
 */
@Service
public class CameraResGroup implements DispatchResGroup<CameraRes> {


    @Override
    public List<CameraRes> getAllRes() {
        List<CameraRes> result = new ArrayList<>(10);
        result.add(new CameraRes("car_00001","摄像头1","http://www.baidu.com",new SimpleLocation(113.892353,23.416344)));
        result.add(new CameraRes("car_00002","摄像头2","http://www.baidu.com",new SimpleLocation(113.89437,23.417211)));
        result.add(new CameraRes("car_00003","摄像头3","http://www.baidu.com",new SimpleLocation(113.894456,23.416187)));
        result.add(new CameraRes("car_00004","摄像头4","http://www.baidu.com",new SimpleLocation(113.894391,23.415714)));
        result.add(new CameraRes("car_00005","摄像头5","http://www.baidu.com",new SimpleLocation(113.892235,23.415901)));
        result.add(new CameraRes("car_00006","摄像头6","http://www.baidu.com",new SimpleLocation(113.893007,23.41725)));
        result.add(new CameraRes("car_00007","摄像头7","http://www.baidu.com",new SimpleLocation(113.893737,23.416719)));
        result.add(new CameraRes("car_00008","摄像头8","http://www.baidu.com",new SimpleLocation(113.894295,23.416197)));
        result.add(new CameraRes("car_00009","摄像头9","http://www.baidu.com",new SimpleLocation(113.895003,23.417171)));
        result.add(new CameraRes("car_00010","摄像头10","http://www.baidu.com",new SimpleLocation(113.89504,23.417132)));
        result.add(new CameraRes("car_00011","摄像头11","http://www.baidu.com",new SimpleLocation(113.895164,23.415788)));
        result.add(new CameraRes("car_00012","摄像头12","http://www.baidu.com",new SimpleLocation(113.894793,23.415478)));
        result.add(new CameraRes("car_00013","摄像头13","http://www.baidu.com",new SimpleLocation(113.892878,23.413913)));
        result.add(new CameraRes("car_00014","摄像头14","http://www.baidu.com",new SimpleLocation(113.892412,23.414041)));
        result.add(new CameraRes("car_00015","摄像头15","http://www.baidu.com",new SimpleLocation(113.891167,23.414868)));
        return result;
    }
}
