package com.zyp.springboot.learn.test.minio;


import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.credentials.AssumeRoleProvider;
import io.minio.credentials.Credentials;
import io.minio.credentials.StaticProvider;

import java.io.File;
import java.io.FileInputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class MinioStsTokenTest {
    //服务所在ip地址和端口
    public static final String ENDPOINT = "http://192.168.2.99:9000/";

    //mc的用户名
    public static final String ACCESS_KEY_COMPANY = "minioadmin";
    //mc的密码
    public static final String SECRET_KEY_COMPANY = "minioadmin";
    //上传的bucket名
    public static final String BUCKET = "zyp-bucket";
    //aws服务端点
    public static final String REGION = null; //"cn-north-1";
    //授权策略，允许访问名为bucket的桶的目录
    public static final String ROLE_ARN = null; //"arn:aws:s3:::test/*";
    public static final String ROLE_SESSION_NAME = null; //"anysession";

    public static final String POLICY_GET_AND_PUT = """
            {
             "Version": "2012-10-17",
             "Statement": [
              {
               "Effect": "Allow",
               "Action": [
                "admin:*"
               ]
              },
              {
               "Effect": "Allow",
               "Action": [
                "kms:*"
               ]
              },
              {
               "Effect": "Allow",
               "Action": [
                "s3:*"
               ],
               "Resource": [
                "arn:aws:s3:::*"
               ]
              }
             ]
            }
            """;


    public static void main(String[] args) {
        try {
            //创建签名对象
            AssumeRoleProvider provider = new AssumeRoleProvider(
                    ENDPOINT,
                    ACCESS_KEY_COMPANY,
                    SECRET_KEY_COMPANY,
                    360000,//默认3600秒失效，设置小于这个就是3600，大于3600就实际值
                    POLICY_GET_AND_PUT,
                    REGION,
                    ROLE_ARN,
                    ROLE_SESSION_NAME,
                    null,
                    null);

            Credentials credentials = provider.fetch();

            /**
             * 打印provider签名属性
             */
            System.out.println("sessionToken=" + credentials.sessionToken());
            System.out.println("accessKey=" + credentials.accessKey());
            System.out.println("secretKey=" + credentials.secretKey());
            System.out.println("isExpired=" + credentials.isExpired());


            System.out.println("credentials: " + credentials);


            StaticProvider staticProvider = new StaticProvider(credentials.accessKey(), credentials.secretKey(), credentials.sessionToken());
            MinioClient minioClient = MinioClient.builder().endpoint(ENDPOINT).credentialsProvider(staticProvider).build();
            File file = new File("/Users/aecg/IdeaProjects/SpringBootLearn/images/icon.png");
            //这个objectName的值是经过上面的policy授权的
            String objectName = "mx/pic1.jpg";
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                minioClient.putObject(PutObjectArgs.builder().bucket(BUCKET)
                        .object(objectName)
                        .contentType("image/jpg")
                        .stream(fileInputStream, fileInputStream.available(), -1).build());

                File file2 = new File("/Users/aecg/IdeaProjects/SpringBootLearn/images/icon.png");
                //这个objectName的值是经过上面的policy授权的
                String objectName2 = "mx/pic2.jpg";
                FileInputStream fileInputStream2 = new FileInputStream(file2);
                minioClient.putObject(PutObjectArgs.builder().bucket(BUCKET)
                        .object(objectName2)
                        .contentType("image/jpg")
                        .stream(fileInputStream2, fileInputStream2.available(), -1).build());

                System.out.println("上传成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}