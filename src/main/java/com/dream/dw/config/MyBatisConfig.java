package com.dream.dw.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@ConfigurationProperties(prefix = "mybatis")
public class MyBatisConfig {

    private String typeAliasesPackage;

    private String mapperLocations;

    @Autowired
    private DruidConfig druidConfig;

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(druidConfig.mysqlDataSource());
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocations));
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public DataSourceTransactionManager transactionManager() throws Exception {
        return new DataSourceTransactionManager(druidConfig.mysqlDataSource());
    }

    public String getTypeAliasesPackage()
    {
        return typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage)
    {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public String getMapperLocations()
    {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations)
    {
        this.mapperLocations = mapperLocations;
    }
}
