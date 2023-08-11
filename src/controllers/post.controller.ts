import Joi from 'joi';

import { NextFunction, Request, Response } from 'express';
import postService from '../services/post.service';
import ApiError from '../utils/ApiError';

//type
import { PostParamsType, PostInfoType } from '../types/postType';
import { UserIdReqType } from '../types/loginRequiredType';

const postReqValidator = Joi.object({
  title: Joi.string().trim(),
  description: Joi.string().trim(),
});

class postController {
  //* 과제 3. 게시물 생성
  static async createPost(
    req: UserIdReqType,
    res: Response,
    next: NextFunction,
  ) {
    const userId = req.currentUserId;
    try {
      const { title, description } = await postReqValidator.validateAsync(
        req.body,
      );
      const post = await postService.savePost({ userId, title, description });
      return res.status(201).send(post);
    } catch (error) {
      next(error);
    }
  }
  //* 과제 4. 게시물 목록 조회
  static async getPosts(req: Request, res: Response, next: NextFunction) {
    try {
      const page = Number(req.query.page);
      const posts = await postService.getLists({ page });
      return res.status(200).send(posts);
    } catch (error) {
      next(error);
    }
  }
  //* 과제 5. 특정 게시글 조회
  static async getPost(req: Request, res: Response, next: NextFunction) {
    try {
      const { id } = req.params as unknown as PostParamsType;

      const post = await postService.findById({ id });
      return res.status(200).send(post);
    } catch (error) {
      next(error);
    }
  }
  //* 과제 6. 특정 게시글 수정
  static async editPost(req: UserIdReqType, res: Response, next: NextFunction) {
    try {
      const userId = req.currentUserId;
      const { id } = req.params as PostParamsType;
      const { title, description } = await postReqValidator.validateAsync(
        req.body,
      );

      const postInfo: PostInfoType = {
        id,
        userId,
        title,
        description,
      };
      const post = await postService.updatePost(postInfo);

      if (typeof post === 'string') {
        throw ApiError.setBadRequest(post);
      }
      return res.status(200).send(post);
    } catch (error) {
      next(error);
    }
  }
  //* 과제 7. 특정 게시글 삭제
  static async deletePost(
    req: UserIdReqType,
    res: Response,
    next: NextFunction,
  ) {
    try {
      const userId = req.currentUserId;
      const { id } = req.params as unknown as PostParamsType;
      const post = await postService.removePost({ id, userId });

      if (typeof post === 'string') {
        throw ApiError.setBadRequest(post);
      }

      return res.status(200).send(post);
    } catch (error) {
      next(error);
    }
  }
}

export default postController;
