import axios from 'axios';
import { REACT_APP_BASE_URL } from '~/core/config/api.config';
import { AuthResponse } from '~/core/models/response/AuthResponse';

const $api = axios.create({
  withCredentials: true,
  baseURL: REACT_APP_BASE_URL
});

$api.interceptors.request.use((config) => {
  config.headers.Authorization = `Bearer ${localStorage.getItem('token')}`;
  console.log(`---> ${config.url}`, config.headers);
  return config;
});

$api.interceptors.response.use((config) => {
  console.log(`<--- ${config.config.url}`, config.data);
  return config;
}, async (error) => {
  console.log(`<--- ${error.config.url}`, error);
  const originalRequest = error.config;
  if (error.response.status == 401 && error.config && !error.config._isRetry) {
    originalRequest._isRetry = true;
    try {
      const response = await axios.get<AuthResponse>(`${REACT_APP_BASE_URL}/refresh`, { withCredentials: true });
      localStorage.setItem('token', response.data.access);
      return $api.request(originalRequest);
    } catch (e) {
      console.log('Не авторизован');
    }
  }
  throw error;
});

export default $api;