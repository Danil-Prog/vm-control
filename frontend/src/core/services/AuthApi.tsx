import axios, { AxiosHeaders, InternalAxiosRequestConfig } from 'axios';
import { REACT_APP_URL_API, ROUTE_PREFIX } from '~/core/config/api.config';

export const userApi = {
  login,
  signup,
  getProfile,
  getUserProfile,
  changeUserData
};

const configContentType: InternalAxiosRequestConfig = {
  headers: new AxiosHeaders({
    'Content-Type': 'application/json'
  })
};

function login(username: string, password: string) {
  return instance.post(
    'login',
    { username, password },
    configContentType
  );
}

function signup(user) {
  return instance.post('/auth/signup', user, configContentType);
}

function getProfile(authdata) {
  const configAuthContentType: InternalAxiosRequestConfig = {
    headers: new AxiosHeaders({
      'Authorization': `Bearer ${authdata}`,
      'Content-Type': 'application/json'
    })
  };
  return instance.get(`${ROUTE_PREFIX}/user`, configAuthContentType);
}

function getUserProfile(authdata, id) {
  const configAuthContentType: InternalAxiosRequestConfig = {
    headers: new AxiosHeaders({
      'Authorization': `Bearer ${authdata}`,
      'Content-Type': 'application/json'
    })
  };
  return instance.get(`${ROUTE_PREFIX}/user/${id}`, configAuthContentType);
}

function changeUserData(authdata, user, id) {
  const configAuthContentType: InternalAxiosRequestConfig = {
    headers: new AxiosHeaders({
      'Authorization': `Bearer ${authdata}`,
      'Content-Type': 'application/json'
    })
  };
  return instance.put(`${ROUTE_PREFIX}/user/${id}`, user, configAuthContentType);
}

export const instance = axios.create({
  baseURL: REACT_APP_URL_API
});