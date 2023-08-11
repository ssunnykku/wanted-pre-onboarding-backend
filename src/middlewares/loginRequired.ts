import jwt from 'jsonwebtoken';
import dotenv from 'dotenv';
import { NextFunction, Response } from 'express';
import ApiError from '../utils/ApiError';
dotenv.config();

//type
import { JwtDecodedType, UserIdReqType } from '../types/loginRequiredType';

function loginRequired(req: UserIdReqType, res: Response, next: NextFunction) {
  const userToken = req.headers['authorization']?.split(' ')[1] ?? 'null';

  if (userToken === 'null') {
    console.log('userToken이 없습니다.');
    throw ApiError.setUnauthorized('userToken이 없습니다.');
    // res.status(400).send('로그인 후 이용 가능합니다.');
    return;
  }

  try {
    const secretKey = process.env.JWT_SECRET_KEY!;
    const jwtDecoded = jwt.verify(userToken, secretKey) as JwtDecodedType;
    const userId = jwtDecoded.userId;

    req.currentUserId = userId;
    next();
  } catch (error) {
    res.status(400).send('정상적인 토큰이 아닙니다.');
    if (error instanceof ApiError) {
      next(error);
    }
    return;
  }
}

export default loginRequired;
