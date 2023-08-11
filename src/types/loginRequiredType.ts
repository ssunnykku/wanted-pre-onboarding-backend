import { Request } from 'express';

export interface JwtDecodedType {
  userId: string;
  email: string;
  name: string;
  iat: number;
  exp: number;
}

export interface UserIdReqType extends Request {
  currentUserId?: string;
}
