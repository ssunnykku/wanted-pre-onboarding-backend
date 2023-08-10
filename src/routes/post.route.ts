import postController from '../controllers/post.controller';
import { Router } from 'express';

const postRouter = Router();

//* 과제 3. 게시물 생성
postRouter.post('/posts', postController.createPost);
//* 과제 4. 게시물 목록 조회
postRouter.get('/posts', postController.getPosts);
//* 과제 5. 특정 게시글 조회
postRouter.get('/posts/:id', postController.getPost);
//* 과제 6. 특정 게시글 수정
postRouter.put('/posts/:id', postController.editPost);
//* 과제 7. 특정 게시글 삭제
postRouter.delete('/posts/:id', postController.deletePost);

export default postRouter;
