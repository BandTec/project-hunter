import axios from 'axios'
import { getToken } from "../auth";

  const apiArquivo = axios.create({
    baseURL: "http://localhost:8081",
    mode: 'cors',
    headers:{
        'Access-Control-Allow-Origin':'*'
    },
  });

export default apiArquivo;
