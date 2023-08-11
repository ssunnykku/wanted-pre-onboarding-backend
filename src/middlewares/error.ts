import { Request, Response, NextFunction } from 'express';
import ApiError from '../utils/ApiError';

export default (err: any, req: Request, res: Response, next: NextFunction) => {
  if (err instanceof ApiError) {
    console.log(
      '\x1b[31m',
      '-----------------------------------------------------------------------------------------------------------------------------------------',
    );
    console.log('\x1b[33m%s\x1b[0m', err);

    res.status(err.status).json({
      success: false,
      status: err.status,
      message: err.message,
    });
    // console.error(err);
    return;
  }
  console.log(
    '\x1b[31m',
    '-----------------------------------------------------------------------------------------------------------------------------------------',
  );
  console.log('\x1b[33m%s\x1b[0m', err);

  res.status(500).json({
    success: false,
    status: 500,
    message: err?.message,
  });
};
