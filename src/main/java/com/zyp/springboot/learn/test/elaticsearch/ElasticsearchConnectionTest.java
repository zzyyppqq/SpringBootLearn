package com.zyp.springboot.learn.test.elaticsearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;

import java.io.IOException;

public class ElasticsearchConnectionTest {

    public static void main(String[] args) {
        // 在创建 RestClient.builder 后配置认证
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "12345678")); // 替换为实际的用户名和密码

        // 1. 创建 RestHighLevelClient 客户端实例
        // 替换 "localhost" 为你的 ES 服务器地址，9200 为端口（通常是 9200）
        // 如果使用 HTTPS，请将 "http" 改为 "https"
        HttpHost httpHost = new HttpHost("192.168.2.99", 9200, "http");
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost)
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider)));

        try {
            // 2. 可选：发送一个 ping 请求测试基本连通性 (方法一)
            // boolean isConnected = client.ping(RequestOptions.DEFAULT);
            // System.out.println("Ping 连接状态: " + (isConnected ? "成功" : "失败"));

            // 3. 发送集群健康状态请求 (方法二)，这能提供更多信息
            ClusterHealthRequest request = new ClusterHealthRequest();
            ClusterHealthResponse response = client.cluster().health(request, RequestOptions.DEFAULT);
            ClusterHealthStatus status = response.getStatus();

            // 根据状态判断连接和集群健康状况
            if (status != ClusterHealthStatus.RED) { // RED 状态通常表示至少一个主分片未分配，连接可能有问题
                System.out.println("✅ 连接成功，集群健康状态: " + status);
                // 可以打印更多详细信息
                System.out.println("集群名称: " + response.getClusterName());
                System.out.println("节点数: " + response.getNumberOfNodes());
            } else {
                System.out.println("❌ 连接存在问题或集群健康状态为 RED。");
            }

        } catch (IOException e) {
            System.err.println("❌ 连接测试失败，发生异常: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 4. 关闭客户端，释放资源
            try {
                client.close();
                System.out.println("客户端连接已关闭.");
            } catch (IOException e) {
                System.err.println("关闭客户端时发生异常: " + e.getMessage());
            }
        }
    }
}