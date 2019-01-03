#!/bin/bash
/usr/bin/mysqld_safe --skip-grant-tables &
sleep 5
mysql -u root -e "CREATE DATABASE projekt4"
mysql -u root projekt4 < /tmp/persondb.sql
