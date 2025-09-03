# MySQL
dpkg -l | grep mysql
sudo systemctl status mysql

netsh interface portproxy add v4tov4 listenport=3306 listenaddress=0.0.0.0 connectport=3306 connectaddress=172.19.220.103
New-NetFirewallRule -DisplayName "mysql port" -Direction Inbound -Protocol TCP -LocalPort 3306  -Action Allow

# Redis 6379
# 无账号密码
dpkg -l | grep redis
#service redis-server status
systemctl start redis-server
systemctl status redis-server


# MongoDB
# 无账号密码
dpkg -l | grep mongodb
sudo systemctl start mongod
sudo systemctl status mongod
sudo systemctl enable mongod

# ==== Elastic Stack =====
# Elasticsearc
sudo ufw allow 9200
curl -X GET "localhost:9200"
# 验证 Elasticsearch 是否运行
dpkg -l | grep elasticsearch
sudo systemctl start elasticsearch.service
sudo systemctl status elasticsearch.service
sudo systemctl restart elasticsearch.service
# 开机启动elasticsearch.service
sudo systemctl enable elasticsearch.service


# kibana
# http://localhost:5601
dpkg -l | grep kibana
sudo systemctl start kibana.service
sudo systemctl stop kibana.service
sudo systemctl status kibana.service
sudo systemctl enable kibana.service

# logstash
dpkg -l | grep logstash
sudo systemctl start logstash
sudo systemctl enable logstash
sudo systemctl status logstash


# RabbitMQ 5672
# http://192.168.2.99:15672  zyp 12345678
dpkg -l | grep rabbitmq-server
sudo systemctl status rabbitmq-server
sudo systemctl start rabbitmq-server
sudo systemctl enable rabbitmq-server


# minio 9000 9001
minio --version
sudo systemctl status minio.service
sudo systemctl start minio.service
sudo systemctl enable minio.service
# minio-credentials.json
# Access Key: XorhHtWNw3kxBVAC12HvXorhHtWNw3kxBVAC12Hv
# Secret Key: 115mQ7SaaejbAh6uTnnQZF9ZmHENFwXbrC35UfhI


netsh interface portproxy add v4tov4 listenport=9000 listenaddress=0.0.0.0 connectport=9000 connectaddress=172.19.220.103
New-NetFirewallRule -DisplayName "RabbitMQ port" -Direction Inbound -Protocol TCP -LocalPort 9000 -Action Allow
Get-NetFirewallRule -DisplayName "RabbitMQ port"
# 删除现有规则：
netsh interface portproxy delete v4tov4 listenport=9001 listenaddress=0.0.0.0
# 添加新规则（指向你的 Windows 主机 IP 192.168.2.99）
netsh interface portproxy add v4tov4 listenport=9001 listenaddress=192.168.2.99 connectport=9001 connectaddress=172.19.220.103
# 验证规则
netsh interface portproxy show all