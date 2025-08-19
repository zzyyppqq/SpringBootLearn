#keytool 支持的 -storetype 类型主要包括以下几种，具体取决于 JDK 版本和安全提供者（Provider）的实现：
# - JKS Java KeyStore（默认类型） JDK 内置，仅支持 Java 环境，私钥受口令保护，但安全性较弱（使用简单加密）。
# - PKCS12 通用标准格式（.p12 或 .pfx） 跨平台兼容（如 Windows、OpenSSL），支持存储私钥和证书链，安全性更高。
# - JCEKS Java Cryptography Extension KeyStore 比 JKS 更安全（支持 TripleDES 加密私钥），适用于高安全需求场景。
# - BKS BouncyCastle Provider 提供的类型 支持更严格的完整性校验，常用于 Android 开发。
# - UBER 高安全密钥库（需通过命令行提供密码） 全库加密（PBE/SHA1/Twofish），防止误改或查看。

# java17 keytool默认是PKCS12
keytool -genkeypair -alias server -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650 \
  -storepass "123456" -keypass "123456" \
  -dname "CN=zyp.com, OU=ZYP, O=ZYP, L=NanJing, ST=JiangSu, C=CN"

keytool -list -keystore keystore.p12 -storepass "123456" -v
