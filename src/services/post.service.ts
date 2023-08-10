import sql from '../config/db.config';
import { PostInfoType, PostType, PostParamsType } from '../types/post';

interface PageType {
  page?: number;
}
class postService {
  //* 과제 3. 게시물 생성
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

  //* 과제 4. 게시물 목록 조회
  static async getLists({ page }: PageType) {
    try {
      // 페이지네이션 : 게시물 8개씩 보여주기
      const [cursorPage] = await sql
        .promise()
        .query(`SELECT * FROM posts ORDER BY id desc LIMIT ?;`, [
          page ? page * 8 - 1 : 1,
        ]);
      const cursor = cursorPage.slice(-1)[0].id;

      const [getPosts] = await sql
        .promise()
        .query(`SELECT * FROM posts WHERE id < ? ORDER BY id desc limit 8`, [
          cursor,
        ]);

      return getPosts;
    } catch (error) {
      if (error instanceof Error) {
        throw error;
      }
    }
  }

  //* 과제 5. 특정 게시글 조회
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
  //* 과제 6. 특정 게시글 수정
  static async updatePost(postInfo: PostInfoType) {
    try {
      // 입력값이 없을 경우 에러 보내기
      // 작성자만 수정 가능
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
  //* 과제 7. 특정 게시글 삭제
  // 작성자만 삭제 가능
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
