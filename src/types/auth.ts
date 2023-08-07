export interface UserType {
  email: string;
  password: string;
}

export interface RegisterType extends UserType {
  name: string;
  checkPassword: string;
}
