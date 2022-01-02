#ALTER USER 'root'@'localhost' IDENTIFIED VIA mysql_native_password USING PASSWORD('root');

#CREATE USER 'mysqluser'@'comunio-stats-backend' IDENTIFIED BY '123456';

GRANT ALL PRIVILEGES ON *.* TO 'root'@'172.18.0.%' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON *.* TO 'mysqluser'@'172.18.0.%' IDENTIFIED BY '123456';

FLUSH PRIVILEGES;
