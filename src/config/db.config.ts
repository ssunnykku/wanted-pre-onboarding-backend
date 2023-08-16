import 'dotenv/config';

const mysql = require('mysql2');

const connection = mysql.createConnection({
  host: process.env.DB_HOST,
  user: process.env.DB_USER_NAME,
  password: process.env.DB_USER_PASSWORD,
  port: process.env.DB_PORT,
  database: process.env.DB_NAME,
});
// 접속
connection.connect((error: any) => {
  if (error) throw error;
  console.log('Successfully connected to the database.');
});

export default connection;
