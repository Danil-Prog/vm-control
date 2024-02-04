import { action, makeAutoObservable, observable } from 'mobx';
import axios, { AxiosHeaders, InternalAxiosRequestConfig } from 'axios';
import { REACT_APP_URL_API, ROUTE_PREFIX } from '~/core/config/api.config';

class AuthStore {
  constructor() {
    makeAutoObservable(this);
  }

  @observable isAuthenticated: boolean = false;
  public success_token: string = '123';
  public refresh_token: string;
  public loading: boolean = false;

  public instance = axios.create({
    baseURL: REACT_APP_URL_API
  });

  public configContentType: InternalAxiosRequestConfig = {
    headers: new AxiosHeaders({
      'Content-Type': 'application/json'
    })
  };
  public configAuthContentType: InternalAxiosRequestConfig = {
    headers: new AxiosHeaders({
      'Authorization': `Bearer ${this.success_token}`,
      'Content-Type': 'application/json'
    })
  };

  @action
  async authenticate(username: string, password: string) {
    try {
      this.loading = true;
      console.log('logpass----->', username, password);
      const response = await this.instance.post(
        'login',
        { username, password },
        this.configContentType
      );
      if (username && password) {
        this.isAuthenticated = true;
      }
      // return response
    } catch (error) {
      console.log('*---authenticate', error);
    } finally {
      this.loading = false;
      this.isAuthenticated = true;
    }
  }

  public getProfile() {
    return this.instance.get(`${ROUTE_PREFIX}/user`, this.configAuthContentType);
  }

  public getUserProfile(id) {
    return this.instance.get(`${ROUTE_PREFIX}/user/${id}`, this.configAuthContentType);
  }

  public changeUserData(user, id) {
    return this.instance.put(`${ROUTE_PREFIX}/user/${id}`, user, this.configAuthContentType);
  }

  public clearAll() {
    this.isAuthenticated = false;
    this.success_token = '';
    this.refresh_token = '';
  }

  public logout() {
    this.clearAll();
  }
}

export default new AuthStore();