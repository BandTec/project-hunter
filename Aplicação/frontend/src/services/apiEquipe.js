import axios from 'axios'
import { getToken } from "../auth";

  const apiEquipe = axios.create({
    baseURL: "http://localhost:8083",
    mode: 'cors',
    headers:{
        'Access-Control-Allow-Origin':'*'
    },
  });

export default apiEquipe;
