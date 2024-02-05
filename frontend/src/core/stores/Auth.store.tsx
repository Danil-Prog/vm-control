import { makeAutoObservable } from 'mobx';
import axios from 'axios';
import { REACT_APP_BASE_URL } from '~/core/config/api.config';
import AuthService from '~/core/services/AuthService';
import { AuthResponse } from '~/core/models/response/AuthResponse';

export default class AuthStore {
  success_token: string;
  refresh_token: string;
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
      localStorage.setItem('token', response.data.token);
      this.setAuth(true);
    } catch (error) {
      console.log('*---login', error);
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
      console.log('*---login', error);
    } finally {
      this.setLoading(false);
    }
  }

  async checkAuth() {
    this.setLoading(true);
    try {
      await axios.post<AuthResponse>(`${REACT_APP_BASE_URL}/refresh`, { withCredentials: true });
    } catch (e) {
      console.log('*---checkAuth', e);
    } finally {
      this.setLoading(false);
    }
  }

  public clearAll() {
    this.isAuthenticated = false;
    this.success_token = '';
    this.refresh_token = '';
  }
}