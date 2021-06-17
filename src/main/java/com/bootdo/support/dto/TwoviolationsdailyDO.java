package com.bootdo.support.dto;

import com.bootdo.common.utils.ExcelField;

import java.io.Serializable;
import java.util.Date;



/**
 * 两违日志表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 14:13:34
 */
public class TwoviolationsdailyDO implements Serializable {
	private static final long serialVersionUID = 1L;

	//主键id
	private String id;
	//所在的部门表ID
	private String deptId;
	//值班人员 ：单位人员表ID
	@ExcelField(title="值班人员", align=2, sort=2)
	private String deptpersonId;
	//值班日期
	@ExcelField(title="值班日期", align=2, sort=1)
	private Date schedulingDate;
	//发现黑名单车辆时间
	@ExcelField(title="发现时间", align=2, sort=1)
	private Date time;
	//车牌号码
	@ExcelField(title="车牌号码", align=2, sort=2)
	private String number;
	//地点（位置）
	private String address;
	//行车方向（过境位置）
	private String direction;
	//货物
	private String goods;
	//是否通知路面执法队
	private Integer isNotify;
	//执法队追踪情况（车辆有放行条放行、查扣、没有找到车辆）
	private String trackSituation;
	//备注
	private String remarks;
	//创建人
	private String createBy;
	//创建时间
	private Date createDate;
	//更新人
	private String updateBy;
	//更新时间
	private Date updateDate;

	private String fileList;

	/**
	 * 设置：主键id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：所在的部门表ID
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：所在的部门表ID
	 */
	public String getDeptId() {
		return deptId;
	}
	/**
	 * 设置：值班人员 ：单位人员表ID
	 */
	public void setDeptpersonId(String deptpersonId) {
		this.deptpersonId = deptpersonId;
	}
	/**
	 * 获取：值班人员 ：单位人员表ID
	 */
	public String getDeptpersonId() {
		return deptpersonId;
	}
	/**
	 * 设置：值班日期
	 */
	public void setSchedulingDate(Date schedulingDate) {
		this.schedulingDate = schedulingDate;
	}
	/**
	 * 获取：值班日期
	 */
	public Date getSchedulingDate() {
		return schedulingDate;
	}
	/**
	 * 设置：发现黑名单车辆时间
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	/**
	 * 获取：发现黑名单车辆时间
	 */
	public Date getTime() {
		return time;
	}
	/**
	 * 设置：车牌号码
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * 获取：车牌号码
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * 设置：地点（位置）
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：地点（位置）
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：行车方向（过境位置）
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}
	/**
	 * 获取：行车方向（过境位置）
	 */
	public String getDirection() {
		return direction;
	}
	/**
	 * 设置：货物
	 */
	public void setGoods(String goods) {
		this.goods = goods;
	}
	/**
	 * 获取：货物
	 */
	public String getGoods() {
		return goods;
	}
	/**
	 * 设置：是否通知路面执法队
	 */
	public void setIsNotify(Integer isNotify) {
		this.isNotify = isNotify;
	}
	/**
	 * 获取：是否通知路面执法队
	 */
	public Integer getIsNotify() {
		return isNotify;
	}
	/**
	 * 设置：执法队追踪情况（车辆有放行条放行、查扣、没有找到车辆）
	 */
	public void setTrackSituation(String trackSituation) {
		this.trackSituation = trackSituation;
	}
	/**
	 * 获取：执法队追踪情况（车辆有放行条放行、查扣、没有找到车辆）
	 */
	public String getTrackSituation() {
		return trackSituation;
	}
	/**
	 * 设置：备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取：备注
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：更新人
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：更新人
	 */
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	public String getFileList() {
		return fileList;
	}

	public void setFileList(String fileList) {
		this.fileList = fileList;
	}

	/**
	 * 导出excel
	 */
	public class TwoviolationsdailyExpDO  {

		@ExcelField(title="值班日期", align=2, sort=1)
		private String schedulingDate;
		@ExcelField(title="值班人员", align=2, sort=2)
		private String deptperson;
		@ExcelField(title="发现时间", align=2, sort=3)
		private String time;
		@ExcelField(title="车牌号码", align=2, sort=4)
		private String number;
		@ExcelField(title="发现地点", align=2, sort=5)
		private String address;
		@ExcelField(title="行车方向", align=2, sort=6)
		private String direction;
		@ExcelField(title="货物", align=2, sort=7)
		private String goods;
		@ExcelField(title="是否通知路面执法队", align=2, sort=7)
		private String isNotify;
		@ExcelField(title="执法队追踪情况", align=2, sort=7)
		private String trackSituation;
		@ExcelField(title="备注", align=2, sort=7)
		private String remarks;

		public String getSchedulingDate() {
			return schedulingDate;
		}

		public void setSchedulingDate(String schedulingDate) {
			this.schedulingDate = schedulingDate;
		}

		public String getDeptperson() {
			return deptperson;
		}

		public void setDeptperson(String deptperson) {
			this.deptperson = deptperson;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getDirection() {
			return direction;
		}

		public void setDirection(String direction) {
			this.direction = direction;
		}

		public String getGoods() {
			return goods;
		}

		public void setGoods(String goods) {
			this.goods = goods;
		}

		public String getIsNotify() {
			return isNotify;
		}

		public void setIsNotify(String isNotify) {
			this.isNotify = isNotify;
		}

		public String getTrackSituation() {
			return trackSituation;
		}

		public void setTrackSituation(String trackSituation) {
			this.trackSituation = trackSituation;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
	}
}
