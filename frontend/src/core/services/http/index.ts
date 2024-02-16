import axios from 'axios';
import { REACT_APP_BASE_URL } from '~/core/config/api.config';
import { AuthResponse } from '~/core/models/response/AuthResponse';

const $api = axios.create({
  withCredentials: true,
  baseURL: REACT_APP_BASE_URL
});

$api.interceptors.request.use((config) => {
  try {
    if (config.headers['WWW-Authenticate']) {
      delete config.headers['WWW-Authenticate'];
    }
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
  console.log(`<---11111 ${config.config.url}`, config);
  if (config.config.headers['WWW-Authenticate']) {
    delete config.config.headers['WWW-Authenticate'];
  }
  return config;
}, async (error) => {
  console.error(`<---2222 ${error.config.url}`, error);
  const originalRequest = error.config;
  if (error.response.status == 401 && error.config && !error.config._isRetry) {
    originalRequest._isRetry = true;
    try {
      const response = await axios.get<AuthResponse>(`${REACT_APP_BASE_URL}/refresh`, { withCredentials: true });
      localStorage.setItem('token', response.data.token);
      return $api.request(originalRequest);
    } catch (e) {
      console.log(`<--- ${e}`);
      localStorage.removeItem('token');
    }
  }
  throw error;
});

export default $api;