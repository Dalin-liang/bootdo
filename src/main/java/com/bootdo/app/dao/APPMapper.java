package com.bootdo.app.dao;

import com.bootdo.app.domain.AppSignReportDO;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public interface APPMapper {
    @Select("${sql}")
    public List<Map<String, Object>> execSelectSqlMap(@Param(value="sql")String sql);

    @Update("${sql}")
    public void execUpdateSql(@Param(value="sql")String sql);

    @Insert("${sql}")
    public void execInsertSql(@Param(value="sql")String sql);

    @Delete("${sql}")
    public void execDeleteSql(@Param(value="sql")String sql);

    /**
     * 查询app消息
     * @param param
     * @return
     */
    List<Map<String,Object>> queryappmsg(Map<String,Object> param);

    /**
     * 查询通讯录
     * @param param
     * @return
     */
    List<Map<String,Object>> queryphonebook(Map<String,Object> param);
    Integer queryphonebookcount(Map<String,Object> param);
    /**
     * 获取app消息总数
     * @param param
     * @return
     */
     Integer  queryappmsgcount(Map<String,Object> param);
    /**
     * 发送app消息
     * @param map
     */
    public void msgadd(HashMap<String,Object> map);
    /**
     * 标记app消息为已读
     * @param map
     */
    public void msgrd(HashMap<String,Object> map);

    /**
     * 执法人员轨迹
     * @param map
     */
    public void personTrajectory(HashMap<String,Object> map);

    /**
     * 保存签到
     * @param map
     */
    public void savesign(HashMap<String,Object> map);

    /**
     * 獲取指定日期簽到記錄
     * @param param
     * @return
     */
    List<Map<String,Object>> getsignByDay(Map<String,Object> param);
    Integer getsignByDaycount(Map<String,Object> param);

    /**
     * 巡查上報
     * @param map
     */
    public void dailyreport(HashMap<String,Object> map);

    /**
     * 获取上报记录
     * @param param
     * @return
     */
    List<Map<String,Object>> getdailyreportByDay(Map<String,Object> param);
    Integer getdailyreportByDaycount(Map<String,Object> param);


    /**
     * 附件记录
     * @param param
     * @return
     */
    List<Map<String,Object>> queryFilelist(Map<String,Object> param);

    /**
     * 获取资料普查记录
     * @param param
     * @return
     */
    List<Map<String,Object>> querylocaleData(Map<String,Object> param);
    Integer querylocaleDatacount(Map<String,Object> param);

    /**
     * 资料新增
     * @param map
     */
    public void placeinfoadd(HashMap<String,Object> map);

    /**
     * 资料修改
     * @param map
     */
    public void placeinfoedit(HashMap<String,Object> map);

    /**
     * 查询字典表
     * @param param
     * @return
     */
    List<Map<String,Object>> querydisct(Map<String,Object> param);

    int update(AppSignReportDO appSignReportDO);


}
