import express from 'express';
import cors from 'cors';
import 'dotenv/config';

//**middleware */
import errorMiddleware from './middlewares/error';

//routes
import authRouter from './routes/auth.route';
import postRouter from './routes/post.route';

const app: express.Express = express();

app.use(express.json());
app.use(cors());

//routes
app.use(authRouter);
app.use(postRouter);

app.use(errorMiddleware);

const PORT = process.env.SEVER_PORT;

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}.`);
});
