import authService from '../services/auth.service';

class authController {
  static async register(req: any, res: any, next: any) {
    try {
      const { email, password, name, checkPassword } = await req.body;

      interface newUserType {
        email: string;
        password: string;
        name: string;
        checkPassword: string;
      }
      const user: newUserType = {
        email,
        password,
        name,
        checkPassword,
      };
      const newUser = await authService.addUser(user);

      //   if (newUser.errorMessage) {
      //     throw new Error(newUser, errorMessage);
      //   }
      return res.status(201).json(newUser);
    } catch (error) {
      next(error);
    }
  }
}

export default authController;
