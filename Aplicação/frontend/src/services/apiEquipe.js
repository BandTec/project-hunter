import axios from 'axios'
import { getToken } from "../auth";

  const apiEquipe = axios.create({
    baseURL: "https://apiequipe.herokuapp.com/",
    mode: 'cors',
    headers:{
        'Access-Control-Allow-Origin':'*'
    },
  });

export default apiEquipe;
