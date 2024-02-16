// components/ThemeToggle.tsx
import React from 'react';
import { observer } from 'mobx-react-lite';
import ThemeStore from '~/core/stores/ThemeStore';
import { inject } from 'mobx-react';
import styles from './ThemeToggle.module.scss';
import { Moon } from '~/components/icons/Moon';
import { Sun } from '~/components/icons/Sun';

interface IThemeToggle {
  themeStore: ThemeStore;
}

const ThemeToggle: React.FC<IThemeToggle> = ({ themeStore }) => {

  const handleToggle = () => {
    themeStore.toggleTheme();
    document.body.dataset.theme = themeStore.isDarkMode ? 'dark' : 'light';
  };

  return (
    <div className={styles.container}>
      <Moon />
      <label className={styles.switch}>
        <input type="checkbox" onChange={handleToggle} />
        <span className={[styles.slider, styles.round].join(' ')}></span>
      </label>
      <Sun />
    </div>
  );
};

export default inject('themeStore')(observer(ThemeToggle));
