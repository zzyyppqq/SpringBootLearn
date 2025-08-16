package com.zyp.springboot.learn.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zyp.springboot.learn.constant.GeneralFieldName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 数据源的配置
 */
@Configuration
@MapperScan(value = "com.zyp.springboot.learn", annotationClass = Mapper.class)
@EnableTransactionManagement
public class MybatisPlusConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        // 配置Mapper文件所在位置
        factoryBean.setMapperLocations(applicationContext.getResources("classpath*:/mapper/**/*.xml"));
        // 配置插件
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 防止全表更新
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 设置 MyBatis-Plus 分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        factoryBean.setPlugins(interceptor);
        var globalConfig = GlobalConfigUtils.defaults();
        // 自定义SqlSessionFactory的情况下，会影响Mybatis Plus的自动配置，这里需要手工设置
        globalConfig.setMetaObjectHandler(metaObjectHandler());
        factoryBean.setGlobalConfig(globalConfig);
        return factoryBean.getObject();
    }

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        // 新增或更新表的记录时自动填充createTime和updateTime
        // 需要在实体类中应用@TableField(fill = FieldFill.XXX)
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject, GeneralFieldName.CreateTime, Long.class, System.currentTimeMillis());
                this.strictInsertFill(metaObject, GeneralFieldName.UpdateTime, Long.class, System.currentTimeMillis());
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictUpdateFill(metaObject, GeneralFieldName.UpdateTime, Long.class, System.currentTimeMillis());
            }
        };
    }
}