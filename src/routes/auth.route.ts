import authController from '../controllers/auth.controller';
import { Router } from 'express';

const authRouter = Router();

// 1. 회원가입
authRouter.post('/signup', authController.register);
// 2. 로그인
// authRouter.post('/signin', authController.login);
// 3. 토큰

export default authRouter;
