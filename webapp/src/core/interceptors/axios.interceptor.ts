import axios from "axios";
import interceptors from "@/core/interceptors/interceptors";

const service = axios.create({
  // baseURL: '/api', // url = base url + request url
  // timeout: 5000 // request timeout
});

interceptors(service);

export default service;
