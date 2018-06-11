package com.ht.commonactivity.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * <p>
 * 文件类型配置表
 * </p>
 *
 * @author dyb
 * @since 2018-06-11
 */
@ApiModel
@TableName("activiti_file_type")
public class ActivitiFileType extends Model<ActivitiFileType> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty(required= true,value = "")
	private Long id;
    /**
     * 文件类型编码
     */
	@TableField("file_type_code")
	@ApiModelProperty(required= true,value = "文件类型编码")
	private String fileTypeCode;
    /**
     * 文件類型名稱
     */
	@TableField("file_type_name")
	@ApiModelProperty(required= true,value = "文件類型名稱")
	private String fileTypeName;
    /**
     * 层级
     */
	@TableField("lfile_type_level")
	@ApiModelProperty(required= true,value = "层级")
	private Integer lfileTypeLevel;
    /**
     * 父类型编码
     */
	@TableField("parent_code")
	@ApiModelProperty(required= true,value = "父类型编码")
	private String parentCode;
    /**
     * 文件类型路径
     */
	@TableField("filt_type_path")
	@ApiModelProperty(required= true,value = "文件类型路径")
	private String filtTypePath;
    /**
     * 排序字段
     */
	@TableField("order_no")
	@ApiModelProperty(required= true,value = "排序字段")
	private Integer orderNo;
    /**
     * 备注
     */
	@ApiModelProperty(required= true,value = "备注")
	private String remark;
    /**
     * 乐观锁
     */
	@TableField("lock_no")
	@ApiModelProperty(required= true,value = "乐观锁")
	private String lockNo;
    /**
     * 创建用户
     */
	@TableField("create_user")
	@ApiModelProperty(required= true,value = "创建用户")
	private String createUser;
    /**
     * 创建用户名称
     */
	@TableField("create_user_name")
	@ApiModelProperty(required= true,value = "创建用户名称")
	private String createUserName;
    /**
     * 创建时间
     */
	@TableField("create_time")
	@ApiModelProperty(required= true,value = "创建时间")
	private Date createTime;
    /**
     * 更新用户
     */
	@TableField("update_user")
	@ApiModelProperty(required= true,value = "更新用户")
	private String updateUser;
    /**
     * 更新用户名称
     */
	@TableField("update_user_name")
	@ApiModelProperty(required= true,value = "更新用户名称")
	private String updateUserName;
    /**
     * 更新时间
     */
	@TableField("update_time")
	@ApiModelProperty(required= true,value = "更新时间")
	private Date updateTime;
    /**
     * 创建人机构
     */
	@TableField("create_org_code")
	@ApiModelProperty(required= true,value = "创建人机构")
	private String createOrgCode;
    /**
     * 创建人机构名称
     */
	@TableField("create_org_name")
	@ApiModelProperty(required= true,value = "创建人机构名称")
	private String createOrgName;
    /**
     * 更新人机构
     */
	@TableField("update_org_code")
	@ApiModelProperty(required= true,value = "更新人机构")
	private String updateOrgCode;
    /**
     * 更新人机构名
     */
	@TableField("update_org_name")
	@ApiModelProperty(required= true,value = "更新人机构名")
	private String updateOrgName;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileTypeCode() {
		return fileTypeCode;
	}

	public void setFileTypeCode(String fileTypeCode) {
		this.fileTypeCode = fileTypeCode;
	}

	public String getFileTypeName() {
		return fileTypeName;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}

	public Integer getLfileTypeLevel() {
		return lfileTypeLevel;
	}

	public void setLfileTypeLevel(Integer lfileTypeLevel) {
		this.lfileTypeLevel = lfileTypeLevel;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getFiltTypePath() {
		return filtTypePath;
	}

	public void setFiltTypePath(String filtTypePath) {
		this.filtTypePath = filtTypePath;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLockNo() {
		return lockNo;
	}

	public void setLockNo(String lockNo) {
		this.lockNo = lockNo;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getUpdateOrgCode() {
		return updateOrgCode;
	}

	public void setUpdateOrgCode(String updateOrgCode) {
		this.updateOrgCode = updateOrgCode;
	}

	public String getUpdateOrgName() {
		return updateOrgName;
	}

	public void setUpdateOrgName(String updateOrgName) {
		this.updateOrgName = updateOrgName;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ActivitiFileType{" +
			"id=" + id +
			", fileTypeCode=" + fileTypeCode +
			", fileTypeName=" + fileTypeName +
			", lfileTypeLevel=" + lfileTypeLevel +
			", parentCode=" + parentCode +
			", filtTypePath=" + filtTypePath +
			", orderNo=" + orderNo +
			", remark=" + remark +
			", lockNo=" + lockNo +
			", createUser=" + createUser +
			", createUserName=" + createUserName +
			", createTime=" + createTime +
			", updateUser=" + updateUser +
			", updateUserName=" + updateUserName +
			", updateTime=" + updateTime +
			", createOrgCode=" + createOrgCode +
			", createOrgName=" + createOrgName +
			", updateOrgCode=" + updateOrgCode +
			", updateOrgName=" + updateOrgName +
			"}";
	}
}
