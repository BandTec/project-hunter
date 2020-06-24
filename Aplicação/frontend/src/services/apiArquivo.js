import axios from 'axios'
import { getToken } from "../auth";

  const apiArquivo = axios.create({
    baseURL: "https://apiarquivo.herokuapp.com/",
    mode: 'cors',
    headers:{
        'Access-Control-Allow-Origin':'*'
    },
  });

export default apiArquivo;
