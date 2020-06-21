import axios from 'axios'
import { getToken } from "../auth";

  const apiGamer = axios.create({
    baseURL: "http://localhost:8084",
    mode: 'cors',
    headers:{
        'Access-Control-Allow-Origin':'*'
    },
  });

export default apiGamer;
