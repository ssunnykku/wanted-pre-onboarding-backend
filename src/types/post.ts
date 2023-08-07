export interface PostType {
  title: string;
  description: string;
}

export interface PostParamsType {
  id?: number;
}

export interface PostInfoType extends PostType {
  id?: number;
}
