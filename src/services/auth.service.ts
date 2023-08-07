const bcrypt = require('bcrypt');

const sql = require('../config/db.config.ts');

interface newUserType {
  email: string;
  password: string;
  name: string;
  checkPassword: string;
}

class authService {
  static async addUser(user: newUserType) {
    try {
      const [findByEmail] = await sql
        .promise()
        .query(
          `SELECT * FROM users WHERE email = ?`,
          [user.email],
          (err: Error | null, res: Response[]) => {
            if (err) {
              console.log('error: ', err);
              return;
            }

            if (res.length) {
              console.log('found user: ', res[0]);
              // const errorMessage = '사용중인 이메일입니다.';
              // return errorMessage;
              throw new Error('사용중인 이메일입니다.');
              return;
            }
          },
        );

      // 비밀번호 해쉬화
      const hashedPassword = await bcrypt.hash(user.password, 10);

      const newUser = await sql
        .promise()
        .query(
          'INSERT INTO Users (userId, email, password, name) VALUES (UUID_TO_BIN(UUID()), ?, ?, ?)',
          [user.email, hashedPassword, user.name],
        );

      return;
    } catch (error) {
      if (error instanceof Error) {
        console.log('이거, server', error);
        throw error;
      }
    }
  }
}

export default authService;
