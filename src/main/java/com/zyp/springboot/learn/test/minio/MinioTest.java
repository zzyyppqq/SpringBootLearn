package com.zyp.springboot.learn.test.minio;

import com.zyp.springboot.learn.util.MinioUtils;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.messages.Bucket;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;


/**
 * minio:
 *   endpoint: http://192.168.2.99:9000 #MinIO服务所在地址
 *   bucketName: zyp-bucket  #存储桶名称
 *   accessKey: XorhHtWNw3kxBVAC12Hv #访问的key
 *   secretKey: 115mQ7SaaejbAh6uTnnQZF9ZmHENFwXbrC35UfhI #访问的秘钥
 */
@Slf4j
@Data
public class MinioTest {

    private static String endpoint = "http://192.168.2.99:9000";
    private static String bucketName = "zyp-bucket";
    private static String accessKey = "minioadmin";
    private static String secretKey = "minioadmin";

    public static void main(String[] args) {
        accessKey = "XorhHtWNw3kxBVAC12Hv";
        secretKey = "115mQ7SaaejbAh6uTnnQZF9ZmHENFwXbrC35UfhI";
        upload("/Users/aecg/IdeaProjects/SpringBootLearn/HELP.md");
    }

    private static void upload(String filePath) {
        try {
            File file = new File(filePath);
            MinioClient client = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();

            MinioUtils minioUtils = new MinioUtils(client);
            List<Bucket> allBuckets = minioUtils.getAllBuckets();
            for (Bucket bucket : allBuckets) {
                log.info("Bucket name: {}, creationDate: {}, bucketPolicy: {}", bucket.name(), bucket.creationDate(), minioUtils.getBucketPolicy(bucket.name()));
            }

            ObjectWriteResponse response = minioUtils.uploadFile(bucketName, file.getName(), new FileInputStream(filePath));
            log.info("etag: {}, versionId: {}", response.etag(), response.versionId());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
