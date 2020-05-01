import axios from 'axios'
import { getToken } from "../auth";

// const username = 'root'
// const password = 'root'

// const auth = Buffer.from(`${username}:${password}`, 'utf8').toString('base64')

const api = axios.create({
    baseURL: "http://localhost:8080/"
  });

  // api.interceptors.request.use(async config => {
  //   const token = getToken();
  //   if (token) {
  //     config.headers.Authorization = `Bearer ${token}`;
  //   }
  //   return config;
  // });

export default api;