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

// const createUsersTableSql =
//   'CREATE TABLE users (userId INT NOT NULL PRIMARY KEY, name VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, createdAt datetime not null default CURRENT_TIMESTAMP,updatedAt datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,)';
// connection.query(createUsersTableSql, (err: any, result: any) => {
//   if (err) throw err;
//   console.log('table created');
// });

// connection.query('SELECT * FROM users', (error: any, result: any) => {
//   if (error) return console.log(error, 'check');

//   console.log(result);
// });

// connection.end();

module.exports = connection;
