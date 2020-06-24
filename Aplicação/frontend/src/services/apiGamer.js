import axios from 'axios'
import { getToken } from "../auth";

  const apiGamer = axios.create({
    baseURL: "https://apigamer.herokuapp.com/",
    mode: 'cors',
    headers:{
        'Access-Control-Allow-Origin':'*'
    },
  });

export default apiGamer;
