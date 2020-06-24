import axios from 'axios'
import { getToken } from "../auth";

  const apiPartida = axios.create({
    baseURL: "http://localhost:8085",
    mode: 'cors',
    headers:{
        'Access-Control-Allow-Origin':'*'
    },
  });

export default apiPartida;
