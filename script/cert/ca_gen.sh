# 生成ca私钥
openssl genrsa -out ca.key 2048
# 验证私钥
openssl rsa -in ca.key -check
# 生成自签名证书: 使用 ca.key 中的私钥对证书请求进行签名，生成一个自签名的 X.509 证书, 将生成的证书写入 ca.crt 文件中
openssl req -new -x509 -key ca.key -out ca.crt -days 36500 -config san.cnf
# 验证CA证书
openssl x509 -in ca.crt -text -noout
# 创建包含私钥和证书的 PEM 文件, pem包含私钥和证书，通常用于某些需要同时提供私钥和证书的场景（如服务器配置）。
cat ca.key ca.crt > ca.pem
# 验证私钥
openssl rsa -in ca.key -check
openssl rsa -in ca.pem -check

# ----- java中使用 -------
# 将 `ca.crt` 导入到 p12 文件中
keytool -importcert -trustcacerts -alias myca -file ca.crt -storetype PKCS12 -keystore ca.p12 -storepass "123456"
# 验证 PKCS12 文件
keytool -list -keystore ca.p12 -storetype PKCS12 -storepass "123456" -v


# 服务端证书
openssl genrsa -out server.key 2048
openssl req -new -key server.key -out server.csr -config san.cnf
openssl x509 -req -in server.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out server.crt -days 36500 -extfile <(printf "subjectAltName=DNS:zyp.com,IP:127.0.0.1")


#客户端证书
openssl genrsa -out client.key 2048
openssl req -new -key client.key -out client.csr -config san.cnf
openssl x509 -req -in client.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out client.crt -days 36500 -extfile <(printf "subjectAltName=DNS:zyp.com,IP:127.0.0.1")


# ----- java中使用 -------
# client crt转p12
openssl pkcs12 -export -out client.p12 -inkey client.key -in client.crt -certfile ca.crt
# server crt转p12
openssl pkcs12 -export -out server.p12 -inkey server.key -in server.crt -certfile ca.crt
# 查看p12文件
openssl pkcs12 -info -in ca.p12
# 计算server.crt指纹
openssl x509 -noout -fingerprint -sha256 -in server.crt
# 验证 PKCS12 文件
keytool -list -keystore client.p12 -storetype PKCS12 -storepass "123456" -v
keytool -list -keystore server.p12 -storetype PKCS12 -storepass "123456" -v