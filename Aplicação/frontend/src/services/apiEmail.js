import axios from 'axios'
import { getToken } from "../auth";

  const apiEmail = axios.create({
    baseURL: "https://apirecsenha.herokuapp.com/",
    mode: 'cors',
    headers:{
        'Access-Control-Allow-Origin':'*'
    },
  });

export default apiEmail;