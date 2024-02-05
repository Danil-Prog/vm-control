import axios, { AxiosResponse } from 'axios';
import $api from '~/core/services/http';
import { AuthResponse } from '~/core/models/response/AuthResponse';

export default class AuthService {
  static async login(username: string, password: string): Promise<AxiosResponse<AuthResponse>> {
    return $api.post<AuthResponse>('/login', { username, password });
  }

  static async logout(): Promise<axios.AxiosResponse<any>> {
    return $api.post('/logout');
  }
};