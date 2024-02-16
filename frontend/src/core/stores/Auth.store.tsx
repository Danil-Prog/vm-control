import { makeAutoObservable } from 'mobx';
import axios from 'axios';
import { REACT_APP_BASE_URL } from '~/core/config/api.config';
import AuthService from '~/core/services/AuthService';
import { AuthResponse } from '~/core/models/response/AuthResponse';
import { toast } from 'react-hot-toast';

class AuthStore {
  token: string;
  refresh: string;
  isAuthenticated: boolean = false;
  isLoading: boolean = false;

  constructor() {
    if (localStorage.getItem('token')) {
      this.setAuth(true);
    }
    makeAutoObservable(this);
  }

  setAuth(bool: boolean) {
    this.isAuthenticated = bool;
  }

  setLoading(bool: boolean) {
    this.isLoading = bool;
  }

  async login(username: string, password: string) {
    try {
      this.setLoading(true);
      const response = await AuthService.login(username, password);
      await localStorage.setItem('token', response.data.token);
      await this.setAuth(true);
      toast.success(`Успешный вход`);
    } catch (error) {
      console.error('*---login', error);
      toast.error(`${error}`);
    } finally {
      this.setLoading(false);
    }
  }

  async logout() {
    try {
      this.setLoading(true);
      await AuthService.logout();
      this.setAuth(false);
    } catch (error) {
      console.error('*---login', error);
    } finally {
      this.setLoading(false);
    }
  }

  async checkAuth() {
    this.setLoading(true);
    try {
      const response = await axios.get<AuthResponse>(`${REACT_APP_BASE_URL}/auth/refresh`, { withCredentials: true });
      localStorage.setItem('token', response.data.token);
      this.setAuth(true);
    } catch (error) {
      if (error.response && error.response.status == 401) {
        localStorage.removeItem('token');
        this.setAuth(false);
      }
      console.error('*---checkAuth', error);
    } finally {
      this.setLoading(false);
    }
  }

  public clearAll() {
    this.isAuthenticated = false;
  }
}

export default AuthStore;

export interface IAuthStore extends AuthStore {
}