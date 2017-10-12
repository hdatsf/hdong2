package com.hdong.upms.dao.generator;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * 代码生成类
 * Created by hdong on 2017/1/10.
 */
public class MybatisGeneratorUtil {
    public static void main(String[] args) throws Exception {
        MybatisGeneratorUtil.generator();
    }
    
	public static void generator() throws Exception{
		String generatorConfig_xml = MybatisGeneratorUtil.class.getResource("/").getPath()+"com/hdong/upms/dao/generator/generatorConfig.xml";
		
		System.out.println("========== 开始运行MybatisGenerator ==========");
		List<String> warnings = new ArrayList<String>();
		File configFile = new File(generatorConfig_xml);
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(true);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
		for (String warning : warnings) {
			System.out.println(warning);
		}
		System.out.println("========== 结束运行MybatisGenerator ==========");
	}

	// 递归删除非空文件夹
	public static void deleteDir(File dir) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteDir(files[i]);
			}
		}
		dir.delete();
	}

}
