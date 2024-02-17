#!/bin/bash
sudo dnf upgrade -y
#sudo dnf install vim -y
#sudo dnf install unzip -y
sudo dnf install postgresql-server postgresql-contrib -y
sudo postgresql-setup initdb
sudo systemctl start postgresql
sudo systemctl enable postgresql
sudo -u postgres psql -c "CREATE DATABASE app_db;"
sudo -u postgres psql -c "CREATE USER web_app with PASSWORD 'ZLce#E3O0oSw51@+h@d';"
sudo -u postgres psql -c "ALTER USER web_app WITH SUPERUSER;"
sudo sed -i '82 s/ident/md5/' /var/lib/pgsql/data/pg_hba.conf
sudo systemctl restart postgresql
sudo dnf install java-17-openjdk-devel -y
sudo alternatives --set java /usr/lib/jvm/java-17-openjdk-17.0.6.0.9-0.3.ea.el8.x86_64/bin/java
sudo dnf install maven -y
echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-17.0.6.0.9-0.3.ea.el8.x86_64/' >> ~/.bashrc
echo 'export M2_HOME=/opt/maven' >> ~/.bashrc
echo 'export PATH=${M2_HOME}/bin:${PATH}' >> ~/.bashrc
source ~/.bashrc
mvn -version
java -version
