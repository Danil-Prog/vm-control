import { makeAutoObservable } from 'mobx';

class ThemeStore {
  isDarkMode = false;

  constructor() {
    makeAutoObservable(this);
  }

  toggleTheme() {
    this.isDarkMode = !this.isDarkMode;
  }
}

export default ThemeStore;

export interface IThemeStore extends ThemeStore {
}