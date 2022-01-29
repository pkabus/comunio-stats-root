import axios from "axios";
import backend_properties from "./../backend_properties"

const HttpMethods = {
  GET: 'GET',
  POST: 'POST',
  DELETE: 'DELETE'
};

axios.defaults.baseURL = backend_properties.url;
const _axios = axios.create();

const getAxiosClient = () => _axios;

const HttpService = {
  HttpMethods,
  getAxiosClient,
};

export default HttpService;