package com.bootdo.support.dto;

import java.io.Serializable;
import java.util.Date;



/**
 * 基本属性表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-10-17 15:23:46
 */
public class KnowledgeDetailDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//唯一主键
	private String id;
	//knowledge_info表id
	private String knowledgeInfoId;
	//编号
	private String code;
	//名称
	private String name;
	//俗称、别名
	private String otherName;
	//英文名
	private String englishName;
	//分子式
	private String molecularFormula;
	//分子量
	private Float molecularWeight;
	//化学类别
	private String chemicalType;
	//主要成分
	private String mainIngredient;
	//外观与性状
	private String appearanceTraits;
	//侵入途径
	private String intrusionWay;
	//特别警示
	private String specialWarning;
	//储运要求
	private String storageTransportationMethods;
	//废弃处理
	private String discardMethods;
	//图片
	private String image;
	//用途
	private String use;
	//毒理学资料
	private String toxicologicalInfo;
	//环境资料
	private String environmentalInfo;
	//包装方法
	private String pack;
	//避免接触条件
	private String avoidContact;
	//禁忌物
	private String incompatibility;
	//燃烧（分解）产物
	private String combustionProduct;
	//创建时间
	private Date createDate;

	/**
	 * 设置：唯一主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：唯一主键
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：knowledge_info表id
	 */
	public void setKnowledgeInfoId(String knowledgeInfoId) {
		this.knowledgeInfoId = knowledgeInfoId;
	}
	/**
	 * 获取：knowledge_info表id
	 */
	public String getKnowledgeInfoId() {
		return knowledgeInfoId;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：俗称、别名
	 */
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	/**
	 * 获取：俗称、别名
	 */
	public String getOtherName() {
		return otherName;
	}
	/**
	 * 设置：英文名
	 */
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	/**
	 * 获取：英文名
	 */
	public String getEnglishName() {
		return englishName;
	}
	/**
	 * 设置：分子式
	 */
	public void setMolecularFormula(String molecularFormula) {
		this.molecularFormula = molecularFormula;
	}
	/**
	 * 获取：分子式
	 */
	public String getMolecularFormula() {
		return molecularFormula;
	}
	/**
	 * 设置：分子量
	 */
	public void setMolecularWeight(Float molecularWeight) {
		this.molecularWeight = molecularWeight;
	}
	/**
	 * 获取：分子量
	 */
	public Float getMolecularWeight() {
		return molecularWeight;
	}
	/**
	 * 设置：化学类别
	 */
	public void setChemicalType(String chemicalType) {
		this.chemicalType = chemicalType;
	}
	/**
	 * 获取：化学类别
	 */
	public String getChemicalType() {
		return chemicalType;
	}
	/**
	 * 设置：主要成分
	 */
	public void setMainIngredient(String mainIngredient) {
		this.mainIngredient = mainIngredient;
	}
	/**
	 * 获取：主要成分
	 */
	public String getMainIngredient() {
		return mainIngredient;
	}
	/**
	 * 设置：外观与性状
	 */
	public void setAppearanceTraits(String appearanceTraits) {
		this.appearanceTraits = appearanceTraits;
	}
	/**
	 * 获取：外观与性状
	 */
	public String getAppearanceTraits() {
		return appearanceTraits;
	}
	/**
	 * 设置：侵入途径
	 */
	public void setIntrusionWay(String intrusionWay) {
		this.intrusionWay = intrusionWay;
	}
	/**
	 * 获取：侵入途径
	 */
	public String getIntrusionWay() {
		return intrusionWay;
	}
	/**
	 * 设置：特别警示
	 */
	public void setSpecialWarning(String specialWarning) {
		this.specialWarning = specialWarning;
	}
	/**
	 * 获取：特别警示
	 */
	public String getSpecialWarning() {
		return specialWarning;
	}
	/**
	 * 设置：储运要求
	 */
	public void setStorageTransportationMethods(String storageTransportationMethods) {
		this.storageTransportationMethods = storageTransportationMethods;
	}
	/**
	 * 获取：储运要求
	 */
	public String getStorageTransportationMethods() {
		return storageTransportationMethods;
	}
	/**
	 * 设置：废弃处理
	 */
	public void setDiscardMethods(String discardMethods) {
		this.discardMethods = discardMethods;
	}
	/**
	 * 获取：废弃处理
	 */
	public String getDiscardMethods() {
		return discardMethods;
	}
	/**
	 * 设置：图片
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取：图片
	 */
	public String getImage() {
		return image;
	}
	/**
	 * 设置：用途
	 */
	public void setUse(String use) {
		this.use = use;
	}
	/**
	 * 获取：用途
	 */
	public String getUse() {
		return use;
	}
	/**
	 * 设置：毒理学资料
	 */
	public void setToxicologicalInfo(String toxicologicalInfo) {
		this.toxicologicalInfo = toxicologicalInfo;
	}
	/**
	 * 获取：毒理学资料
	 */
	public String getToxicologicalInfo() {
		return toxicologicalInfo;
	}
	/**
	 * 设置：环境资料
	 */
	public void setEnvironmentalInfo(String environmentalInfo) {
		this.environmentalInfo = environmentalInfo;
	}
	/**
	 * 获取：环境资料
	 */
	public String getEnvironmentalInfo() {
		return environmentalInfo;
	}
	/**
	 * 设置：包装方法
	 */
	public void setPack(String pack) {
		this.pack = pack;
	}
	/**
	 * 获取：包装方法
	 */
	public String getPack() {
		return pack;
	}
	/**
	 * 设置：避免接触条件
	 */
	public void setAvoidContact(String avoidContact) {
		this.avoidContact = avoidContact;
	}
	/**
	 * 获取：避免接触条件
	 */
	public String getAvoidContact() {
		return avoidContact;
	}
	/**
	 * 设置：禁忌物
	 */
	public void setIncompatibility(String incompatibility) {
		this.incompatibility = incompatibility;
	}
	/**
	 * 获取：禁忌物
	 */
	public String getIncompatibility() {
		return incompatibility;
	}
	/**
	 * 设置：燃烧（分解）产物
	 */
	public void setCombustionProduct(String combustionProduct) {
		this.combustionProduct = combustionProduct;
	}
	/**
	 * 获取：燃烧（分解）产物
	 */
	public String getCombustionProduct() {
		return combustionProduct;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
