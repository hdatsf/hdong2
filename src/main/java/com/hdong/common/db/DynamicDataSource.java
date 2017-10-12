package com.hdong.common.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源（数据源切换）
 * Created by hdong on 2017/1/15.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	private final static Logger _log = LoggerFactory.getLogger(DynamicDataSource.class);

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	@Override
	protected Object determineCurrentLookupKey() {
		String dataSource = getDataSource();
		_log.debug("use db：{}", dataSource);
		return dataSource;
	}

	/**
	 * 设置数据源
	 * @param dataSource
	 */
	public static void setDataSource(DataSourceEnum dataSource) {
		contextHolder.set(dataSource.getName());
	}

	/**
	 * 获取数据源
	 * @return
	 */
	public static String getDataSource() {
		String dataSource = contextHolder.get();
		// 如果没有指定数据源，使用默认数据源
		if (null == dataSource) {
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getDefault());
		}
		return contextHolder.get();
	}

	/**
	 * 清除数据源
	 */
	public static void clearDataSource() {
		contextHolder.remove();
	}

}
