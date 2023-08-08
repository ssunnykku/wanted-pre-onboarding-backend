import express from 'express';
import cors from 'cors';
import 'dotenv/config';

//**middleware */
import errorMiddleware from './middlewares/error';

//routes
import authRouter from './routes/auth.route';
import postRouter from './routes/post.route';

const app: express.Express = express();

//** logging middleware */
// app.use((req, res, next) => {
//   console.log(req.rawHeaders[1]);
//   console.log('this is logging middleware');
//   next();
// });

app.use(express.json());
app.use(cors());

//routes
app.use(authRouter);
app.use(postRouter);

//* 404 middleware
app.use((req, res, next) => {
  console.log('this is error middleware');
  res.send({ error: '404 not found error' });
  next();
});

app.use(errorMiddleware);

const PORT = process.env.SEVER_PORT;

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}.`);
});