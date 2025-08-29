package com.zyp.springboot.learn.test.druid;

import com.alibaba.druid.pool.DruidDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManualDruidExample {
    public static void main(String[] args) {
        // 1. 创建DruidDataSource实例
        DruidDataSource dataSource = new DruidDataSource();

        // 2. 设置数据库连接配置
        dataSource.setUrl("jdbc:mysql://192.168.2.99:3306/power_admin?useUnicode=true&characterEncoding=utf8&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("12345678");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // 或根据数据库选择

        // 3. 配置连接池参数
        dataSource.setInitialSize(5);      // 初始连接数
        dataSource.setMinIdle(5);          // 最小空闲连接数
        dataSource.setMaxActive(20);       // 最大活跃连接数
        dataSource.setMaxWait(60000);      // 获取连接的最大等待时间（毫秒）

        // 4. (可选) 高级配置
        dataSource.setValidationQuery("SELECT 1"); // 用于检查连接有效性的SQL
        dataSource.setTestWhileIdle(true);         // 建议开启空闲连接检测
        // dataSource.setFilters("stat,wall");     // 添加统计、防御SQL注入等过滤器（需要额外配置）

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            // 5. 获取连接 (连接池的初始化通常在第一次调用getConnection()时触发)
            connection = dataSource.getConnection();
            System.out.println("dataSource connection success");
            // 6. 使用连接执行数据库操作
            String sql = "SELECT * FROM user WHERE uid = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1000000);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                // 处理你的查询结果
                System.out.println("username: " + resultSet.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 7. 释放资源，关闭连接（实际是归还到连接池）
            if (resultSet != null) {
                try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (preparedStatement != null) {
                try { preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            
            // 8. 在应用程序关闭时，关闭数据源（连接池）
            dataSource.close();
        }
    }
}