import { NextFunction, Request, Response } from 'express';
import authService from '../services/auth.service';
import Joi from 'joi';

//type
import { UserType, RegisterType } from '../types/auth';

const registerValidator = Joi.object({
  name: Joi.string().trim().required(),
  email: Joi.string().trim().email().required(),
  password: Joi.string().trim().required().min(8),
  checkPassword: Joi.any().valid(Joi.ref('password')).required(),
});

const loginValidator = Joi.object({
  email: Joi.string().trim().email().required(),
  password: Joi.string().trim().required().min(8),
});

class authController {
  //* CREATE 회원가입
  static async register(req: Request, res: Response, next: NextFunction) {
    try {
      const { name, email, password, checkPassword } =
        await registerValidator.validateAsync(req.body as RegisterType);

      const newUser = await authService.addUser({
        email,
        password,
        checkPassword,
        name,
      });

      return res.status(201).send({ message: 'User created successfully' });
    } catch (error) {
      if (error instanceof Error) {
        return res.status(400).send({ success: false, error: error.message });
      }
      next(error);
    }
    return res.status(400).send({
      success: false,
      error: 'An unknown error occurred',
    });
  }

  static async login(req: Request, res: Response, next: NextFunction) {
    try {
      const { email, password } = await loginValidator.validateAsync(req.body);
      const user = await authService.findUser({
        email,
        password,
      });

      // if (user?.errorMessage) {
      //   throw Error(user.errorMessage);
      // }

      return res.status(201).send(user);
    } catch (error) {
      next(error);
    }
  }
}

export default authController;
