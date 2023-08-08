import { NextFunction, Request, Response } from 'express';
import postService from '../services/post.service';
import { PostType, PostParamsType, PostInfoType } from '../types/post';
import Joi from 'joi';

const postReqValidator = Joi.object({
  title: Joi.string().required().trim(),
  description: Joi.string().required(),
});

class postController {
  //* 1. 게시물 생성
  static async createPost(req: Request, res: Response, next: NextFunction) {
    try {
      const { title, description } = await postReqValidator.validateAsync(
        req.body,
      );
      const post = await postService.savePost({ title, description });
      return res.status(201).send(post);
    } catch (error) {
      next(error);
    }
  }
  //* 2. 게시물 목록 조회
  static async getPosts(req: Request, res: Response, next: NextFunction) {
    try {
      const posts = await postService.getLists();
      return res.status(200).send(posts);
    } catch (error) {
      next(error);
    }
  }
  //* 3. 특정 게시물 조회
  static async getPost(req: Request, res: Response, next: NextFunction) {
    try {
      const { id } = req.params as unknown as PostParamsType;

      const post = await postService.findById({ id });
      return res.status(200).send(post);
    } catch (error) {
      next(error);
    }
  }
  //* 4. 특정 게시물 수정
  static async editPost(req: Request, res: Response, next: NextFunction) {
    try {
      const { id } = req.params as PostParamsType;
      const { title, description } = await postReqValidator.validateAsync(
        req.body,
      );
      const postInfo: PostInfoType = { id, title, description };
      const post = await postService.updatePost(postInfo);
      return res.status(200).send(post);
    } catch (error) {
      next(error);
    }
  }
  //* 5. 특정 게시물 삭제
  static async deletePost(req: Request, res: Response, next: NextFunction) {
    try {
      const { id } = req.params as unknown as PostParamsType;
      const post = await postService.removePost({ id });
      return res.status(200).send(post);
    } catch (error) {
      next(error);
    }
  }
}

export default postController;
