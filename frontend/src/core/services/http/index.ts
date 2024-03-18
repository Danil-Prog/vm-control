import axios from 'axios';
import { REACT_APP_BASE_URL } from '~/core/config/api.config';
import { AuthResponse } from '~/core/models/response/AuthResponse';
import { toast } from 'react-hot-toast';

const $api = axios.create({
  withCredentials: true,
  baseURL: REACT_APP_BASE_URL
});

$api.interceptors.request.use((config) => {
  try {
    if (localStorage.getItem('token')) {
      config.headers.Authorization = `Bearer ${localStorage.getItem('token')}`;
    }
    console.log(`---> ${config.url}`, config.headers);
    return config;
  } catch (e) {
    console.log('ERROR', e);
  }
});

$api.interceptors.response.use((config) => {
  console.log(`<--- ${config.config.url}`, config);
  return config;
}, async (error) => {
  console.error(`<--- ${error.config.url}`, error);
  const originalRequest = error.config;
  if (error.response.status == 401 && error.config && !error.config._isRetry) {
    originalRequest._isRetry = true;
    try {
      const response = await axios.get<AuthResponse>(`${REACT_APP_BASE_URL}/auth/refresh`, { withCredentials: true });
      localStorage.setItem('token', response.data.token);
      return $api.request(originalRequest);
    } catch (e) {
      console.log(`<--- ${e}`);
      localStorage.removeItem('token');
    }
  } else {
    toast.success(`${error.response.message}`);
  }
  throw error;
});

export default $api;