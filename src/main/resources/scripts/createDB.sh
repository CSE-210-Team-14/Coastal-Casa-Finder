#!/bin/bash

# be sure to not to include your DB password when committing
DB_HOST="localhost"
DB_NAME="CoastalCasaFinder"
DB_USER="root"
DB_PASS=""
SQL_FILE="createDB.sql"

# Check if the database exists
if mysql -h $DB_HOST -u $DB_USER -p$DB_PASS -e "use $DB_NAME" 2>/dev/null; then
  echo "Database $DB_NAME already exists."
else
  echo "Database $DB_NAME does not exist. Creating it..."
  mysql -h $DB_HOST -u $DB_USER -p$DB_PASS -e "CREATE DATABASE $DB_NAME"
fi

# Execute SQL file
mysql -h $DB_HOST -u $DB_USER -p$DB_PASS $DB_NAME < $SQL_FILE
