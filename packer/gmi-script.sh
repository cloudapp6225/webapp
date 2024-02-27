#!/bin/bash
sudo dnf upgrade -y
sudo groupadd csye6225
sudo useradd -s /usr/sbin/nologin -g csye6225 -d /opt/webapp -m csye6225
sudo mv /tmp/web-app-0.0.1-SNAPSHOT.jar /opt/webapp/
sudo mv /tmp/webapp.service /etc/systemd/system/
sudo dnf install java-17-openjdk-devel -y
sudo alternatives --set java /usr/lib/jvm/java-17-openjdk-17.0.6.0.9-0.3.ea.el8.x86_64/bin/java
#sudo dnf install maven -y
#echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-17.0.6.0.9-0.3.ea.el8.x86_64/' >> ~/.bashrc
#echo 'export M2_HOME=/opt/maven' >> ~/.bashrc
#echo 'export PATH=${M2_HOME}/bin:${PATH}' >> ~/.bashrc
#source ~/.bashrc
#mvn -version
java -version
sudo chown csye6225:csye6225 /opt/webapp/web-app-0.0.1-SNAPSHOT.jar
sudo chmod 750 /opt/webapp/web-app-0.0.1-SNAPSHOT.jar
sudo systemctl daemon-reload
sudo systemctl enable webapp.service
sudo systemctl start webapp.service

