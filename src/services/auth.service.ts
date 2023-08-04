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
      const { email, password, name } = user;
      sql.query(
        ` INSERT INTO Users (userId, email, password, name) VALUES (UUID_TO_BIN(UUID()), '${email}', '${password}', '${name}');`,
        (err: any, res: any) => {
          if (err) {
            console.log('error', err);
            return;
          }
        },
      );
      // `INSERT INTO users(email,password,name) VALUES (${email}, ${password}, ${name}
      // });`,
      //   INSERT INTO Posts (id, title, content) VALUES (UUID_TO_BIN(UUID()), 'Post Title', 'Post Content');
    } catch (error) {}
  }
}

export default authService;
