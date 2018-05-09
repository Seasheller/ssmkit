package com.ssmkit.common.service;

import com.ssmkit.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Service基类
 * 
 * @author 曹亚普
 * @version 2018-05-09
 */
public interface BaseService<T extends BaseEntity> extends Serializable {

	/**
	 * 保存实体，新增或更新 
	 *
	 * 根据实体主键是否为空: 	null ==> insert 保存一个实体，null的属性也会保存，不会使用数据库默认值
	 * 					not null ==> update 根据主键更新实体全部字段，null值会被更新 
	 * 
	 * @param entity
	 *            序列主键
	 * @return 	操作结果
	 */
	public int save(T entity);
	
	/**
	 * 保存实体，新增或更新 
	 *
	 * 根据实体主键是否为空: 	null ==> insert 保存一个实体，null的属性也会保存，不会使用数据库默认值
	 * 					not null ==> update 根据主键更新实体全部字段，null值会被更新 
	 * 
	 * @param entity
	 *            实体对象
	 * @return 操作结果
	 */
	public int saveSelective(T entity);

	/**
	 * 根据主键返回唯一对象
	 * 
	 * @param id
	 *            序列主键
	 * @return 实体对象
	 */
	public T findByPrimaryKey(Long id);
	
	/**
	 * 根据实体中不为空的属性进行条件'='查询 
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象列表
	 */
	public List<T> findList(T entity);
	
	/** 
	 * 根据实体属性参数筛选,逻辑删除符合要求实体对象 参数筛选用 "="连接
	 * 参数传递一个Map: key 是所需匹配的对应实体的属性名, value 是对应的属性名
	 * eg: map.put('createBy', "admin"); 相当于查询所有" creact_by = 'admin' " 的实体
	 * 
	 * @param param
	 *            Map
	 * @return 实体对象列表
	 */
	public List<T> findList(Map<String, Object> param);

	/**
	 * insert Entity
	 * 保存一个实体，null的属性也会保存，不会使用数据库默认值
	 * 
	 * @param entity
	 *            实体对象
	 * @return int
	 */
	public int insert(T entity);
	
	/**
	 * insert Entity
	 * 保存一个实体，null的属性不会保存，会使用数据库默认值
	 * 
	 * @param entity
	 *            entity
	 * @return int
	 */
	public int insertSelective(T entity);

	/**
	 * update Entity
	 * 根据主键更新实体全部字段，null值会被更新 
	 * 
	 * @param entity
	 *            entity
	 * @return int
	 */
	public int update(T entity);

	/**
	 * update Entity
	 * 根据主键更新属性不为null的值
	 * 
	 * @param entity
	 *            entity
	 * @return int
	 */
	public int updateSelective(T entity);
	
	/**
	 * delete by primary key
	 * 根据主键逻辑删除实体对象 ,
	 * 
	 * @param id
	 *            Long
	 * @return int
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public int deleteByIdLogically(Long id) throws InstantiationException, IllegalAccessException;

	/**
	 * delete Entity 逻辑删除 
	 * 根据实体属性参数筛选,逻辑删除符合要求实体对象 参数筛选用 "="连接
	 * 参数传递一个Map: key 是所需匹配的对应实体的属性名, value 是对应的属性名
	 * eg: map.put('id', 1); 相当于物理删除id=1实体
	 * @param Map
	 *            map
	 * @return int
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public int deleteByColumnLogically(Map<String, Object> Map) throws InstantiationException, IllegalAccessException;
	
	/**
	 * delete by primary key
	 * 根据主键 "物理删除"  实体对象 ,
	 * 
	 * @param id
	 *            Long
	 * @return int
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public int deleteByIdPhysical(Long id) throws InstantiationException, IllegalAccessException;

	/**
	 * delete Entities 物理删除
	 * 根据实体属性参数筛选,物理删除符合要求实体对象 参数筛选用 "="连接
	 * 参数传递一个Map: key 是所需匹配的对应实体的属性名, value 是对应的属性名
	 * eg: map.put('id', 1); 相当于物理删除id=1实体
	 *
	 * @param map
	 * 			Map<String, Object> map
	 * @return int
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public int deleteByColumnPhysical(Map<String, Object> map) throws InstantiationException, IllegalAccessException;
	
	/**
	 * 分页查询 
	 * 根据实体中不为空的属性作为查询条件
	 * 同时请保证实体中 page rows 两个属性不为空
	 * @param entity
	 * @return
	 */
	public List<T> findByPage(T entity);

	/**
	 * 分页查询 
	 * 需要自己在xml文件中配置 id = "findByPage" 的动态SQL, 
	 * 注意，分页插件不支持嵌套结果映射  SQL返回映射集中避免出现嵌套
	 * @param param Map<String, Object> 查询参数
	 * @param page 分页页码
	 * @param rows 分页行数
	 * @return
	 */
	public List<T> findByPage(Map<String, Object> param, int page, int rows);
}
