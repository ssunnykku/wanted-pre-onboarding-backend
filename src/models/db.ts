const mysql = require('mysql2');
const dbConfig = require('../config/db.config');

const connection = mysql.createConnection({
  host: dbConfig.host,
  user: dbConfig.user,
  password: dbConfig.password,
  database: dbConfig.database,
});

connection.connect((error: any) => {
  if (error) throw error;
  console.log('Successfully connected to the database.');
});

module.exports = connection;
