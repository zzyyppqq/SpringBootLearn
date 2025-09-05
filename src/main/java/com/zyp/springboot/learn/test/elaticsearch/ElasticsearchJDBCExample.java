package com.zyp.springboot.learn.test.elaticsearch;

import org.elasticsearch.lz4.ESLZ4Decompressor;
import org.elasticsearch.xpack.sql.jdbc.EsDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class ElasticsearchJDBCExample {

    /**
     * https://artifacts.elastic.co/maven/org/elasticsearch/plugin/x-pack-sql-jdbc/7.17.3/x-pack-sql-jdbc-7.17.3.jar
     * @param args
     */
    public static void main(String[] args) {
        // JDBC 驱动类名
        String driverClass = "org.elasticsearch.xpack.sql.jdbc.EsDriver";
        
        // 加载 JDBC 驱动 (JDBC 4.0 之后通常会自动注册，显式加载可确保兼容性)
        try {
            Class.forName(driverClass);
            System.out.println("JDBC 驱动加载成功.");
        } catch (ClassNotFoundException e) {
            System.err.println("无法加载 JDBC 驱动: " + e.getMessage());
            return;
        }

        // Elasticsearch 服务器连接信息
        String esHost = "192.168.2.99";
        int esPort = 9200;
        
        // 构建 JDBC URL
//        String jdbcUrl = "jdbc:es://http://" + esHost + ":" + esPort + "/?connect.timeout=30000&network.timeout=60000&query.timeout=90000";
         // 另一种等效格式:
         String jdbcUrl = "jdbc:es://" + esHost + ":" + esPort + "/";

        // 认证信息 (如果Elasticsearch配置了安全认证)
        String user = "elastic"; // 默认超级用户名
        String password = "12345678"; // 替换为实际密码

        Properties connectionProperties = new Properties();
        connectionProperties.put("user", user); //[4](@ref)
        connectionProperties.put("password", password); //[4](@ref)

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 建立连接
            connection = DriverManager.getConnection(jdbcUrl, connectionProperties); //[1,4](@ref)
            System.out.println("成功连接到 Elasticsearch!");

            // 创建 Statement 对象
            statement = connection.createStatement();

            // 执行 SQL 查询 (Elasticsearch SQL)
            String sqlQuery = "SELECT * FROM your_index_name LIMIT 10"; // 替换为您要查询的索引名
            resultSet = statement.executeQuery(sqlQuery); //[4](@ref)

            // 处理查询结果
            while (resultSet.next()) {
                // 根据您的索引字段类型和名称获取数据
                // 例如，如果您的索引有一个名为 'title' 的字段
                // String title = resultSet.getString("title");
                // System.out.println("Title: " + title);
                // 示例：获取第一列（按查询顺序）和第二列的值
                System.out.println("Field 1: " + resultSet.getString(1) + ", Field 2: " + resultSet.getObject(2));
            }

        } catch (Exception e) {
            System.err.println("操作过程中发生异常: " + e.getMessage());
            e.printStackTrace();
            // 特别注意：如果出现许可证错误，请检查ES的X-Pack许可证状态[7](@ref)
        } finally {
            // 关闭资源，释放连接
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                System.out.println("数据库连接已关闭.");
            } catch (Exception e) {
                System.err.println("关闭资源时发生异常: " + e.getMessage());
            }
        }
    }
}