import axios from 'axios'
import { getToken } from "../auth";

  const apiPartida = axios.create({
    baseURL: "https://apipartida.herokuapp.com/",
    mode: 'cors',
    headers:{
        'Access-Control-Allow-Origin':'*'
    },
  });

export default apiPartida;
