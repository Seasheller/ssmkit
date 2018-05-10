package com.ssmkit.common.dao;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

/**
 * Dao基类
 * @author 曹亚普
 * @param <T>
 * @version 2018-05-09
 */
public interface BaseDao<T> extends Mapper<T>, MySqlMapper<T>, IdsMapper<T> {

	List<T> findByPage(Map<String, Object> map);
    
}
