import jwt from 'jsonwebtoken';
import 'dotenv/config';
import sql from '../config/db.config';
import bcrypt from 'bcrypt';

//type
import { UserType, RegisterType } from '../types/auth';

class authService {
  //* 1. 회원가입
  static async addUser(user: RegisterType) {
    try {
      // 비밀번호 해쉬화
      const hashedPassword = await bcrypt.hash(user.password, 10);

      const newUser = await sql
        .promise()
        .query(
          'INSERT INTO Users (userId, email, password, name) VALUES (UUID_TO_BIN(UUID()), ?, ?, ?)',
          [user.email, hashedPassword, user.name],
        );

      return {
        success: true,
        status: 201,
        message: 'User created successfully',
      };
    } catch (error) {
      if (error instanceof Error) {
        console.log(error);
        throw error;
      }
    }
  }
  //* 2. 로그인
  static async findUser({ email, password }: UserType) {
    try {
      const [findByEmail] = await sql
        .promise()
        .query('SELECT * FROM users WHERE email = ? ', [email]);

      if (findByEmail.length === 0) {
        const errorMessage = '존재하지 않는 회원입니다.';
        return { errorMessage };
      }

      const userPassword = findByEmail[0].password;
      const isPasswordCorrect = await bcrypt.compare(password, userPassword);

      if (!isPasswordCorrect) {
        const errorMessage =
          '비밀번호가 일치하지 않습니다. 다시 한 번 확인해 주세요.';
        return { errorMessage };
      }
      const jwtSecretKey = process.env.JWT_SECRET_KEY!;

      const userToken = jwt.sign(
        {
          userId: findByEmail[0].userId,
          email: findByEmail[0].email,
          name: findByEmail[0].name,
        },
        jwtSecretKey,
        {
          expiresIn: process.env.JWT_EXPIRES,
        },
      );
      return {
        success: true,
        status: 201,
        token: userToken,
      };
    } catch (error) {
      console.error(error);
      throw error;
    }
  }
}

export default authService;
