package com.bootdo.dispatch.center.base;

import java.util.List;

/**
 * 资源组
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/19
 */
public interface DispatchResGroup<T extends DispatchRes> {


    /**
     * 获取分组的所有资源
     * @return
     */
    List<T> getAllRes();

}
