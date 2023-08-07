import sql from '../config/db.config';
import { PostInfoType, PostType, PostParamsType } from '../types/post';

class postService {
  //* 1. 게시물 생성
  static async savePost({ title, description }: PostType) {
    try {
      const [newPost] = await sql
        .promise()
        .query('INSERT INTO posts (title, description) VALUES (?, ?)', [
          title,
          description,
        ]);
      return {
        success: true,
        status: 201,
        message: '게시물 생성이 완료되었습니다.',
      };
    } catch (error) {
      if (error instanceof Error) {
        throw error;
      }
    }
  }
  //* 2. 게시물 목록 조회
  static async getLists() {
    try {
      const [getPosts] = await sql.promise().query('SELECT * FROM posts;');

      return getPosts;
    } catch (error) {
      if (error instanceof Error) {
        throw error;
      }
    }
  }

  //* 3. 특정 게시물 조회
  static async findById({ id }: PostParamsType) {
    try {
      const [getPost] = await sql
        .promise()
        .query('SELECT * FROM posts WHERE id = ? ', [id]);

      return getPost;
    } catch (error) {
      if (error instanceof Error) {
        throw error;
      }
    }
  }
  //* 4. 특정 게시물 수정
  static async updatePost(postInfo: PostInfoType) {
    try {
      const [post] = await sql
        .promise()
        .query('UPDATE posts SET title=?, description=? WHERE id = ? ', [
          postInfo.title,
          postInfo.description,
          postInfo.id,
        ]);

      return {
        success: true,
        status: 200,
        message: '게시물 수정이 완료되었습니다.',
      };
    } catch (error) {
      if (error instanceof Error) {
        throw error;
      }
    }
  }
  //* 5. 특정 게시물 삭제
  static async removePost({ id }: PostParamsType) {
    try {
      const post = await sql
        .promise()
        .query('DELETE FROM posts WHERE id = ?', [id]);

      return {
        success: true,
        status: 200,
        message: '게시물이 삭제되었습니다',
      };
    } catch (error) {
      if (error instanceof Error) {
        throw error;
      }
    }
  }
}

export default postService;
