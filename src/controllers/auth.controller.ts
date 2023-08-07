import { NextFunction, Request, Response } from 'express';
import authService from '../services/auth.service';
import Joi from 'joi';

const registerValidator = Joi.object({
  name: Joi.string().trim().required(),
  email: Joi.string().trim().email().required(),
  password: Joi.string().trim().required().min(8),
  checkPassword: Joi.any().valid(Joi.ref('password')).required(),
});

interface newUserType {
  email: string;
  password: string;
  name: string;
  checkPassword: string;
  errorMessage?: string;
}

class authController {
  //* CREATE 회원가입
  static async register(req: Request, res: Response, next: NextFunction) {
    try {
      const { name, email, password, checkPassword } =
        await registerValidator.validateAsync(req.body as newUserType);

      const newUser = await authService.addUser({
        email,
        password,
        checkPassword,
        name,
      });

      // if (newUser.errorMessage) {
      //   throw new Error(newUser, errorMessage);
      // }

      return res.status(201).send({ message: 'User created successfully' });
    } catch (error) {
      if (error instanceof Error) {
        console.log('이거, ctrl', error);
        return res.status(400).send({ success: false, error: error.message });
      }
      next(error);
    }
    // return res.status(400).send({
    //   success: false,
    //   error: 'An unknown error occurred',
    // });
  }
}

export default authController;
