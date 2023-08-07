import postController from '../controllers/post.controller';
import { Router } from 'express';

const postRouter = Router();

//* 1. 게시물 생성
postRouter.post('/posts', postController.createPost);
//* 2. 게시물 목록 조회
postRouter.get('/posts', postController.getPosts);
//* 3. 특정 게시물 조회
postRouter.get('/posts/:id', postController.getPost);
//* 4. 특정 게시물 수정
postRouter.put('/posts/:id', postController.editPost);
//* 5. 특정 게시물 삭제
postRouter.delete('/posts/:id', postController.deletePost);

export default postRouter;
