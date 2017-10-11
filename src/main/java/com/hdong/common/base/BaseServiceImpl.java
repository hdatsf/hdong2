package com.hdong.common.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageHelper;
import com.hdong.common.db.DataSourceEnum;
import com.hdong.common.db.DynamicDataSource;
import com.hdong.common.util.SpringContextUtil;

/**
 * 实现BaseService抽象类
 * Created by hdong on 2017/01/07.
 */
public abstract class BaseServiceImpl<Mapper, Record, Example> implements BaseService<Record, Example> {
    
	public Mapper mapper;

	@Override
	public int countByExample(Example example) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
			Method countByExample = mapper.getClass().getDeclaredMethod("countByExample", example.getClass());
			Object result = countByExample.invoke(mapper, example);
			return Integer.parseInt(String.valueOf(result));
		} catch (IllegalAccessException e) {
		    getLogger().error("countByExample error:", e);
		} catch (InvocationTargetException e) {
		    getLogger().error("countByExample error:", e);
		} catch (NoSuchMethodException e) {
		    getLogger().error("countByExample error:", e);
		} finally{
		    DynamicDataSource.clearDataSource();
		}
		return 0;
	}

	@Override
	public int deleteByExample(Example example) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method deleteByExample = mapper.getClass().getDeclaredMethod("deleteByExample", example.getClass());
			Object result = deleteByExample.invoke(mapper, example);
			return Integer.parseInt(String.valueOf(result));
		} catch (IllegalAccessException e) {
			getLogger().error("deleteByExample error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("deleteByExample error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("deleteByExample error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method deleteByPrimaryKey = mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", id.getClass());
			Object result = deleteByPrimaryKey.invoke(mapper, id);
			return Integer.parseInt(String.valueOf(result));
		} catch (IllegalAccessException e) {
			getLogger().error("deleteByPrimaryKey error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("deleteByPrimaryKey error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("deleteByPrimaryKey error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return 0;
	}

	@Override
	public int insert(Record record) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method insert = mapper.getClass().getDeclaredMethod("insert", record.getClass());
			Object result = insert.invoke(mapper, record);
			return Integer.parseInt(String.valueOf(result));
		} catch (IllegalAccessException e) {
			getLogger().error("insert error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("insert error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("insert error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return 0;
	}

	@Override
	public int insertSelective(Record record) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method insertSelective = mapper.getClass().getDeclaredMethod("insertSelective", record.getClass());
			Object result = insertSelective.invoke(mapper, record);
			return Integer.parseInt(String.valueOf(result));
		} catch (IllegalAccessException e) {
			getLogger().error("insertSelective error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("insertSelective error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("insertSelective error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return 0;
	}

	@Override
	public List<Record> selectByExampleWithBLOBs(Example example) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
			Method selectByExampleWithBLOBs = mapper.getClass().getDeclaredMethod("selectByExampleWithBLOBs", example.getClass());
			Object result = selectByExampleWithBLOBs.invoke(mapper, example);
			return (List<Record>) result;
		} catch (IllegalAccessException e) {
			getLogger().error("selectByExampleWithBLOBs error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("selectByExampleWithBLOBs error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("selectByExampleWithBLOBs error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return null;
	}

	@Override
	public List<Record> selectByExample(Example example) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
			Method selectByExample = mapper.getClass().getDeclaredMethod("selectByExample", example.getClass());
			Object result = selectByExample.invoke(mapper, example);
			return (List<Record>) result;
		} catch (IllegalAccessException e) {
			getLogger().error("selectByExample error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("selectByExample error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("selectByExample error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return null;
	}

	@Override
	public List<Record> selectByExampleWithBLOBsForStartPage(Example example, Integer pageNum, Integer pageSize) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
			Method selectByExampleWithBLOBs = mapper.getClass().getDeclaredMethod("selectByExampleWithBLOBs", example.getClass());
			PageHelper.startPage(pageNum, pageSize, false);
			Object result = selectByExampleWithBLOBs.invoke(mapper, example);
			return (List<Record>) result;
		} catch (IllegalAccessException e) {
			getLogger().error("selectByExampleWithBLOBsForStartPage error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("selectByExampleWithBLOBsForStartPage error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("selectByExampleWithBLOBsForStartPage error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return null;
	}

	@Override
	public List<Record> selectByExampleForStartPage(Example example, Integer pageNum, Integer pageSize) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
			Method selectByExample = mapper.getClass().getDeclaredMethod("selectByExample", example.getClass());
			PageHelper.startPage(pageNum, pageSize, false);
			Object result = selectByExample.invoke(mapper, example);
			return (List<Record>) result;
		} catch (IllegalAccessException e) {
			getLogger().error("selectByExampleForStartPage error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("selectByExampleForStartPage error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("selectByExampleForStartPage error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return null;
	}

	@Override
	public List<Record> selectByExampleWithBLOBsForOffsetPage(Example example, Integer offset, Integer limit) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
			Method selectByExampleWithBLOBs = mapper.getClass().getDeclaredMethod("selectByExampleWithBLOBs", example.getClass());
			PageHelper.offsetPage(offset, limit, false);
			Object result = selectByExampleWithBLOBs.invoke(mapper, example);
			return (List<Record>) result;
		} catch (IllegalAccessException e) {
			getLogger().error("selectByExampleWithBLOBsForOffsetPage error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("selectByExampleWithBLOBsForOffsetPage error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("selectByExampleWithBLOBsForOffsetPage error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return null;
	}

	@Override
	public List<Record> selectByExampleForOffsetPage(Example example, Integer offset, Integer limit) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
			Method selectByExample = mapper.getClass().getDeclaredMethod("selectByExample", example.getClass());
			PageHelper.offsetPage(offset, limit, false);
			Object result = selectByExample.invoke(mapper, example);
			return (List<Record>) result;
		} catch (IllegalAccessException e) {
			getLogger().error("selectByExampleForOffsetPage error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("selectByExampleForOffsetPage error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("selectByExampleForOffsetPage error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return null;
	}

	@Override
	public Record selectFirstByExample(Example example) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
			Method selectByExample = mapper.getClass().getDeclaredMethod("selectByExample", example.getClass());
			List<Record> result = (List<Record>) selectByExample.invoke(mapper, example);
			if (null != result && result.size() > 0) {
				return result.get(0);
			}
		} catch (IllegalAccessException e) {
			getLogger().error("selectFirstByExample error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("selectFirstByExample error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("selectFirstByExample error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return null;
	}

	@Override
	public Record selectFirstByExampleWithBLOBs(Example example) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
			Method selectByExampleWithBLOBs = mapper.getClass().getDeclaredMethod("selectByExampleWithBLOBs", example.getClass());
			List<Record> result = (List<Record>) selectByExampleWithBLOBs.invoke(mapper, example);
			if (null != result && result.size() > 0) {
				return result.get(0);
			}
		} catch (IllegalAccessException e) {
			getLogger().error("selectFirstByExampleWithBLOBs error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("selectFirstByExampleWithBLOBs error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("selectFirstByExampleWithBLOBs error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return null;
	}

	@Override
	public Record selectByPrimaryKey(Integer id) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
			Method selectByPrimaryKey = mapper.getClass().getDeclaredMethod("selectByPrimaryKey", id.getClass());
			Object result = selectByPrimaryKey.invoke(mapper, id);
			return (Record) result;
		} catch (IllegalAccessException e) {
			getLogger().error("selectByPrimaryKey error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("selectByPrimaryKey error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("selectByPrimaryKey error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return null;
	}

	@Override
	public int updateByExampleSelective(@Param("record") Record record, @Param("example") Example example) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method updateByExampleSelective = mapper.getClass().getDeclaredMethod("updateByExampleSelective", record.getClass(), example.getClass());
			Object result = updateByExampleSelective.invoke(mapper, record, example);
			return Integer.parseInt(String.valueOf(result));
		} catch (IllegalAccessException e) {
			getLogger().error("updateByExampleSelective error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("updateByExampleSelective error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("updateByExampleSelective error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return 0;
	}

	@Override
	public int updateByExampleWithBLOBs(@Param("record") Record record, @Param("example") Example example) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method updateByExampleWithBLOBs = mapper.getClass().getDeclaredMethod("updateByExampleWithBLOBs", record.getClass(), example.getClass());
			Object result = updateByExampleWithBLOBs.invoke(mapper, record, example);
			return Integer.parseInt(String.valueOf(result));
		} catch (IllegalAccessException e) {
			getLogger().error("updateByExampleWithBLOBs error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("updateByExampleWithBLOBs error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("updateByExampleWithBLOBs error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return 0;
	}

	@Override
	public int updateByExample(@Param("record") Record record, @Param("example") Example example) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method updateByExample = mapper.getClass().getDeclaredMethod("updateByExample", record.getClass(), example.getClass());
			Object result = updateByExample.invoke(mapper, record, example);
			return Integer.parseInt(String.valueOf(result));
		} catch (IllegalAccessException e) {
			getLogger().error("updateByExample error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("updateByExample error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("updateByExample error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(Record record) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method updateByPrimaryKeySelective = mapper.getClass().getDeclaredMethod("updateByPrimaryKeySelective", record.getClass());
			Object result = updateByPrimaryKeySelective.invoke(mapper, record);
			return Integer.parseInt(String.valueOf(result));
		} catch (IllegalAccessException e) {
			getLogger().error("updateByPrimaryKeySelective error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("updateByPrimaryKeySelective error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("updateByPrimaryKeySelective error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return 0;
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Record record) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method updateByPrimaryKeyWithBLOBs = mapper.getClass().getDeclaredMethod("updateByPrimaryKeyWithBLOBs", record.getClass());
			Object result = updateByPrimaryKeyWithBLOBs.invoke(mapper, record);
			return Integer.parseInt(String.valueOf(result));
		} catch (IllegalAccessException e) {
			getLogger().error("updateByPrimaryKeyWithBLOBs error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("updateByPrimaryKeyWithBLOBs error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("updateByPrimaryKeyWithBLOBs error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Record record) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method updateByPrimaryKey = mapper.getClass().getDeclaredMethod("updateByPrimaryKey", record.getClass());
			Object result = updateByPrimaryKey.invoke(mapper, record);
			return Integer.parseInt(String.valueOf(result));
		} catch (IllegalAccessException e) {
			getLogger().error("updateByPrimaryKey error:", e);
		} catch (InvocationTargetException e) {
			getLogger().error("updateByPrimaryKey error:", e);
		} catch (NoSuchMethodException e) {
			getLogger().error("updateByPrimaryKey error:", e);
		} finally{
            DynamicDataSource.clearDataSource();
        }
		return 0;
	}

	@Override
	public void initMapper() {
		this.mapper = SpringContextUtil.getBean(getMapperClass());
	}

	/**
	 * 获取类泛型class
	 * @return
	 */
	public Class<Mapper> getMapperClass() {
		return (Class<Mapper>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

}