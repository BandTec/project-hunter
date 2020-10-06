import axios from 'axios'
import { getToken } from "../auth";

  const apiHunter = axios.create({
    baseURL: "ec2-52-0-122-72.compute-1.amazonaws.com:8080/",
    mode: 'cors',
    headers:{
        'Access-Control-Allow-Origin':'*'
    },
  });

export default apiHunter;
