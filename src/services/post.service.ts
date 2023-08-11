import sql from '../config/db.config';
import { PostInfoType, PostType, PostParamsType } from '../types/postType';
import ApiError from '../utils/ApiError';

interface PageType {
  page?: number;
}
class postService {
  //* 과제 3. 게시물 생성
  static async savePost({ userId, title, description }: PostType) {
    try {
      const [newPost] = await sql
        .promise()
        .query(
          'INSERT INTO posts (userId, title, description) VALUES (?, ?, ?)',
          [userId, title, description],
        );
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
      const POSTS_PER_PAGE = 8;
      let cursor: number | undefined;
      if (page && page > 1) {
        const [cursorData] = await sql
          .promise()
          .query(`SELECT id FROM posts ORDER BY id desc LIMIT ?,1;`, [
            (page - 1) * POSTS_PER_PAGE - 1,
          ]);
        cursor = cursorData[0].id;
      }

      const queryParams = cursor ? [cursor, POSTS_PER_PAGE] : [POSTS_PER_PAGE];

      const queryStr = cursor
        ? `SELECT * FROM posts WHERE id < ? ORDER BY id desc LIMIT ?`
        : `SELECT * FROM posts ORDER BY id desc LIMIT ?`;

      const [getPosts] = await sql.promise().query(queryStr, queryParams);

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
      // 작성자가 아닐경우 에러메세지
      const findUserQuery = 'SELECT * FROM posts WHERE id = ?';
      const [getUserInfo] = await sql
        .promise()
        .query(findUserQuery, [postInfo.id]);

      if (postInfo.userId != getUserInfo[0].userId) {
        const errorMessage = '작성자만 수정할 수 있습니다.';
        return errorMessage;
      }

      const postQuery = 'UPDATE posts SET title=?, description=? WHERE id = ? ';
      const postQueryParams = [
        postInfo.title,
        postInfo.description,
        postInfo.id,
      ];
      const [post] = await sql.promise().query(postQuery, postQueryParams);

      return {
        success: true,
        status: 200,
        message: '게시물 수정이 완료되었습니다.',
      };
    } catch (error) {
      throw error;
    }
  }
  //* 과제 7. 특정 게시글 삭제
  static async removePost({ id, userId }: PostParamsType) {
    try {
      // 작성자가 아닐경우 에러메세지
      const findUserId = 'SELECT userId FROM posts WHERE id = ?';
      const [getUserId] = await sql.promise().query(findUserId, [id]);

      if (userId != getUserId[0].userId) {
        const errorMessage = '작성자만 삭제할 수 있습니다.';
        return errorMessage;
      }

      const post = await sql
        .promise()
        .query('DELETE FROM posts WHERE id = ?', [id]);

      return {
        success: true,
        status: 200,
        message: '게시물이 삭제되었습니다',
      };
    } catch (error) {
      throw error;
    }
  }
}

export default postService;
