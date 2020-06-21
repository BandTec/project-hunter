import axios from 'axios'
import { getToken } from "../auth";

  const apiEmail = axios.create({
    baseURL: "http://localhost:8082",
    mode: 'cors',
    headers:{
        'Access-Control-Allow-Origin':'*'
    },
  });

export default apiEmail;