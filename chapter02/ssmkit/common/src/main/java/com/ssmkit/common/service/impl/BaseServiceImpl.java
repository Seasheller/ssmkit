package com.ssmkit.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.ssmkit.common.dao.BaseDao;
import com.ssmkit.common.entity.BaseEntity;
import com.ssmkit.common.service.BaseService;
import com.ssmkit.common.utils.SpringSecurityUtil;
import com.ssmkit.common.utils.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 基础的BaseService 实现类 所有继承BaseService接口的实现类都必须继承此类 提供了许多有用的方法
 * 
 * @author 曹亚普
 * @version 2018-05-09
 * @param <T>
 *            Entity 实体类
 */
public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

	private static final long serialVersionUID = -7945764868834846973L;

	/** 执行sql的SQLMapID常量 */
	public static final String PRE_EXECUTE_SQL = "executeSql";

	/** 执行插入的SQLMapID常量 */
	public static final String PRE_INSERT = "insert";

	/** 执行批量插入的SQLMapID常量 */
	public static final String PRE_INSERT_BATCH = "insertAllByBatch";

	/** 执行更新的SQLMapID常量 */
	public static final String PRE_UPDATE = "update";

	/** 执行批量更新的SQLMapID常量 */
	public static final String PRE_UPDATE_BATCH = "updateAllByBatch";

	/** 执行逻辑删除的SQLMapID常量 */
	public static final String PRE_DELETE_LOGIC = "deleteLogic";
	
	/** 执行物理删除的SQLMapID常量 */
	public static final String PRE_DELETE_PHYSICAL = "deletePhysical";

	/** 执行分页查询的SQLMapID常量 */
	public static final String PRE_FIND_BY_PAGE = "findByPage";

	/** 执行根据具备唯一性的属性查询的SQLMapID常量 */
	public static final String PRE_FIND_BY_PROP = "findUniqueByProperty";
	
	/** 执行根据主键查询的SQLMapID常量 */
	public static final String PRE_FIND_BY_PRIMARY_KEY = "findByPrimaryKey";


	private static final String PRE_FINDLIST = "findList";

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/** 实体类 */
	protected Class<T> entityClass;

	/** 实体类名 */
	protected String entityClassName;

	private BaseDao<T> genericBaseDao;

	private String baseDaoBeanId;

	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		try {
			Object genericClz = getClass().getGenericSuperclass();
			if (genericClz instanceof ParameterizedType) {
				entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
						.getActualTypeArguments()[0];
				entityClassName = entityClass.getSimpleName();
				if (this.entityClass != null) {
					String pname = entityClass.getPackage().getName();
					pname = pname.substring(0, pname.lastIndexOf("."));
					baseDaoBeanId = pname + ".dao." + entityClassName + "Mapper";
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	protected BaseDao<T> getGenericBaseDao() {
		if (genericBaseDao == null) {
			if (baseDaoBeanId != null) {
				String daoName = baseDaoBeanId;
				if (daoName.indexOf(".") >= -1) {
					daoName = daoName.substring(daoName.lastIndexOf(".") + 1, daoName.length());
				}
				daoName = StringUtils.uncapitalize(daoName);
				genericBaseDao = (BaseDao<T>) SpringUtil.getBean(daoName);
				if (logger.isDebugEnabled()) {
					logger.debug("genericBaseDao=" + genericBaseDao);
				}
			}
		}
		return genericBaseDao;
	}

	@Override
	public int save(T entity) {
		Assert.notNull(entity, "实体不能为空");
		if (entity.getId() == null) {
			return insert(entity);
		} else {
			return update(entity);
		}
	}
	
	@Override
	public int saveSelective(T entity) {
		Assert.notNull(entity, "实体不能为空");
		if (entity.getId() == null) {
			return insertSelective(entity);
		} else {
			return updateSelective(entity);
		}
	}

	@Override
	public T findByPrimaryKey(Long id) {
		Assert.notNull(id, "主键不能为空");
		String selectSqlMapId = entityClass.getName() + "." + PRE_FIND_BY_PRIMARY_KEY;
		if (logger.isDebugEnabled()) {
			logger.debug("selectSqlMapId=" + selectSqlMapId);
		}
		return getGenericBaseDao().selectByPrimaryKey(id);
	}
	
	@Override
	public List<T> findList(T entity) {
		Assert.notNull(entity, "查询实体不能为空");
		if(entity.getDeleteFlag() == null) {//判断实体逻辑删除标志位
			entity.setDeleteFlag(BaseEntity.DEL_FLAG_NORMAL);
		}
		
		String selectSqlMapId = entityClass.getName() + "." + PRE_FINDLIST;
		if (logger.isDebugEnabled()) {
			logger.debug("selectSqlMapId=" + selectSqlMapId);
		}
		return getGenericBaseDao().select(entity);
	}
	
	@Override
	public List<T> findList(Map<String, Object> param) {
		Assert.notEmpty(param, "查询参数不能为空");
		if(param.get("deleteFlag") == null) {//判断实体逻辑删除标志位 是否存在于map中
			param.put("deleteFlag",BaseEntity.DEL_FLAG_NORMAL);
		}
		Example example = new Example(entityClass);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(param);
		
		String selectSqlMapId = entityClass.getName() + "." + PRE_FINDLIST;
		if (logger.isDebugEnabled()) {
			logger.debug("selectSqlMapId=" + selectSqlMapId);
		}
		
		return getGenericBaseDao().selectByExample(example);
	}

	@Override
	public int insert(T entity) {

		Assert.notNull(entity, "实体不能为空");

		String insertSqlMapId = entityClass.getName() + "." + PRE_INSERT;
		if (logger.isDebugEnabled()) {
			logger.debug("insertSqlMapId=" + insertSqlMapId);
		}
		if (entity.getCreateBy() == null) {
			entity.setCreateBy(SpringSecurityUtil.getCurrentUsername());
		}
		if (entity.getCreateDate() == null) {
			entity.setCreateDate(new Date());
		}
		if (entity.getVersion() == null) {
			entity.setVersion(1);
		}
		if (entity.getDeleteFlag() == null) {
			entity.setDeleteFlag(BaseEntity.DEL_FLAG_NORMAL);
		}
		return getGenericBaseDao().insert(entity);
	}
	
	@Override
	public int insertSelective(T entity) {

		Assert.notNull(entity, "实体不能为空");

		String insertSqlMapId = entityClass.getName() + "." + PRE_INSERT;
		if (logger.isDebugEnabled()) {
			logger.debug("insertSqlMapId=" + insertSqlMapId);
		}
		if (entity.getCreateBy() == null) {
			entity.setCreateBy(SpringSecurityUtil.getCurrentUsername());
		}
		if (entity.getCreateDate() == null) {
			entity.setCreateDate(new Date());
		}
		if (entity.getVersion() == null) {
			entity.setVersion(1);
		}
		if (entity.getDeleteFlag() == null) {
			entity.setDeleteFlag(BaseEntity.DEL_FLAG_NORMAL);
		}
		return getGenericBaseDao().insertSelective(entity);
	}

	@Override
	public int update(T entity) {
		Assert.notNull(entity, "实体不能为空");
		Assert.notNull(entity.getId(), "实体的主键不能为空");

		String updateSqlMapId = entityClass.getName() + "." + PRE_UPDATE;
		if (logger.isDebugEnabled()) {
			logger.debug("updateSqlMapId=" + updateSqlMapId);
		}

		// 设置更新者
		entity.setUpdateBy(SpringSecurityUtil.getCurrentUsername());
		// 设置更新时间
		entity.setUpdateDate(new Date());

		return getGenericBaseDao().updateByPrimaryKey(entity);
	}
	
	@Override
	public int updateSelective(T entity) {
		Assert.notNull(entity, "实体不能为空");
		Assert.notNull(entity.getId(), "实体的主键不能为空");

		String updateSqlMapId = entityClass.getName() + "." + PRE_UPDATE;
		if (logger.isDebugEnabled()) {
			logger.debug("updateSqlMapId=" + updateSqlMapId);
		}

		// 设置更新者
		entity.setUpdateBy(SpringSecurityUtil.getCurrentUsername());
		// 设置更新时间
		entity.setUpdateDate(new Date());

		return getGenericBaseDao().updateByPrimaryKeySelective(entity);
	}
	
	@Override
	public int deleteByIdLogically(Long id) throws InstantiationException, IllegalAccessException {
		Assert.notNull(id, "实体不能为空");
		
		T entity = entityClass.newInstance();
		entity.setId(id);// 设置ID属性
		entity.setDeleteFlag(BaseEntity.DEL_FLAG_DELETED);// 设置逻辑删除位
		
		// 设置更新者
		entity.setUpdateBy(SpringSecurityUtil.getCurrentUsername());
		// 设置更新时间
		entity.setUpdateDate(new Date());

		String deleteSqlMapId = entityClass.getName() + "." + PRE_DELETE_LOGIC;
		if (logger.isDebugEnabled()) {
			logger.debug("deleteSqlMapId=" + deleteSqlMapId);
		}
		return getGenericBaseDao().updateByPrimaryKeySelective(entity);
	}
	
	@Override
	public int deleteByColumnLogically(Map<String, Object> param) throws InstantiationException, IllegalAccessException {
		Assert.notEmpty(param, "实体不能为空");

		T entity = entityClass.newInstance();
		entity.setDeleteFlag(BaseEntity.DEL_FLAG_DELETED);// 设置逻辑删除位
		// 设置更新者
		entity.setUpdateBy(SpringSecurityUtil.getCurrentUsername());
		// 设置更新时间
		entity.setUpdateDate(new Date());
		
		Example example = new Example(entityClass);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(param);
		
		String deleteSqlMapId = entityClass.getName() + "." + PRE_DELETE_LOGIC;
		if (logger.isDebugEnabled()) {
			logger.debug("deleteSqlMapId=" + deleteSqlMapId);
		}
		
		return getGenericBaseDao().updateByExampleSelective(entity, example);
	}
	
	@Override
	public int deleteByIdPhysical (Long id) throws InstantiationException, IllegalAccessException {
		Assert.notNull(id, "实体主键不能为空");
		String deleteSqlMapId = entityClass.getName() + "." + PRE_DELETE_PHYSICAL;
		if (logger.isDebugEnabled()) {
			logger.debug("deleteSqlMapId=" + deleteSqlMapId);
		}
		return getGenericBaseDao().deleteByPrimaryKey(id);
	}
	
	@Override
	public int deleteByColumnPhysical (Map<String, Object> param) throws InstantiationException, IllegalAccessException {
		Assert.notEmpty(param, "参数列表不能为空");
		Example example = new Example(entityClass);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(param);
		return getGenericBaseDao().deleteByExample(example);
	}

	@Override
	public List<T> findByPage(Map<String, Object> param, int page, int rows) {
		Assert.notEmpty(param, "参数列表不能为空");
		Assert.notNull(page, "分页查询页码不能为空");
		Assert.notNull(rows, "分页查询行数不能为空");
		String selectSqlMapId = entityClass.getName() + "." + PRE_FIND_BY_PAGE;
		if (logger.isDebugEnabled()) {
			logger.debug("deleteSqlMapId=" + selectSqlMapId);
		}
		
		PageHelper.startPage(page, rows);
		return getGenericBaseDao().findByPage(param);
	}

	@Override
	public List<T> findByPage(T entity) {
		Assert.notNull(entity, "实体不能为空");
		Assert.notNull(entity.getPage(), "分页查询页码不能为空");
		Assert.notNull(entity.getRows(), "分页查询行数不能为空");
		
		String selectSqlMapId = entityClass.getName() + "." + PRE_FIND_BY_PAGE;
		if (logger.isDebugEnabled()) {
			logger.debug("deleteSqlMapId=" + selectSqlMapId);
		}
		
        PageHelper.startPage(entity.getPage(), entity.getRows());
		return findList(entity);
	}

}
