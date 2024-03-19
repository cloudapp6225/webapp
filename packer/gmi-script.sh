#!/bin/bash
sudo dnf upgrade -y
sudo groupadd csye6225
sudo useradd -s /usr/sbin/nologin -g csye6225 -d /opt/webapp -m csye6225
sudo mv /tmp/web-app-0.0.1-SNAPSHOT.jar /opt/webapp/
sudo mv /tmp/webapp.service /etc/systemd/system/
touch /tmp/application.properties
sudo mv /tmp/application.properties /opt/webapp/
sudo chown csye6225:csye6225 /opt/webapp/application.properties
sudo chmod 750 /opt/webapp/application.properties
sudo dnf install java-17-openjdk-devel -y
sudo alternatives --set java /usr/lib/jvm/java-17-openjdk-17.0.6.0.9-0.3.ea.el8.x86_64/bin/java
curl -sSO https://dl.google.com/cloudagents/add-google-cloud-ops-agent-repo.sh
sudo bash add-google-cloud-ops-agent-repo.sh --also-install
touch /tmp/webapp.log
sudo mv /tmp/webapp.log /var/log/
sudo chown csye6225:csye6225 /var/log/webapp.log
sudo chmod 750 /var/log/webapp.log
java -version
sudo chown csye6225:csye6225 /opt/webapp/web-app-0.0.1-SNAPSHOT.jar
sudo chmod 750 /opt/webapp/web-app-0.0.1-SNAPSHOT.jar
sudo systemctl daemon-reload
sudo systemctl enable webapp.service
sudo systemctl start webapp.service
sudo mv /tmp/config.yaml /etc/google-cloud-ops-agent/config.yaml
sudo systemctl restart google-cloud-ops-agent

