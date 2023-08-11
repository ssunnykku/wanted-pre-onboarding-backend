export interface PostType {
  title: string;
  description: string;
  userId?: string;
}

export interface PostParamsType {
  id?: number;
  userId?: string;
}

export interface PostInfoType extends PostType {
  id?: number;
}
