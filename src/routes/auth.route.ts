import authController from '../controllers/auth.controller';
import { Router } from 'express';

const authRouter = Router();

//* 과제 1. 회원가입
authRouter.post('/signup', authController.register);
//* 과제 2. 로그인
authRouter.post('/login', authController.login);

export default authRouter;
