import * as express from 'express';
import * as cors from 'cors';
import 'dotenv/config';

//routes
import authRouter from './routes/auth.route';

const app = express();

app.use(express.json());
app.use(cors());

//routes
app.use(authRouter);

app.get('/', (req, res) => {
  res.send('test server');
});

const PORT = process.env.SEVER_PORT;

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}.`);
});
