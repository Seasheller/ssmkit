package com.ssmkit.common.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity基类
 * @author 曹亚普
 * @version 2018-05-09
 */
public abstract class BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -3066469880313548379L;

	public static final Integer DEL_FLAG_NORMAL = 0;
	
	public static final Integer DEL_FLAG_DELETED = 1;

	/**
	 * 主键
	 */
	@Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
	/**
	 * 页码
	 */
    @Transient
    protected Integer page;

    /**
	 * 页数
	 */
    @Transient
    protected Integer rows;

    /**
	 * 版本号
	 */
    protected Integer version;
    
    /**
     * 创建人
     */
    protected String createBy;
    
    /**
     * 创建日期
     */
    protected Date createDate;
    
    /**
     * 更新人
     */
    protected String updateBy;
    
    /**
     * 更新日期
     */
    protected Date updateDate;
    
    /**
     * 逻辑删除标志位
     */
    protected Integer deleteFlag;
    
    /**
     * 备注
     */
    protected String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer i) {
        this.version = i;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}