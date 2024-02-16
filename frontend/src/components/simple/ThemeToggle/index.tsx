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
  const [isChecked, setIsChecked] = React.useState(false);

  React.useEffect(() => {
    if (localStorage.getItem('theme') && localStorage.getItem('theme') === 'dark') {
      setIsChecked(true);
      themeStore.toggleTheme();
    }
  }, []);
  React.useEffect(() => {
  }, [isChecked]);

  const handleToggle = (e) => {
    localStorage.setItem('theme', themeStore.isDarkMode ? 'light' : 'dark');
    setIsChecked(e.target.checked);
    themeStore.toggleTheme();
    document.body.dataset.theme = themeStore.isDarkMode ? 'dark' : 'light';
  };

  return (
    <div className={styles.container}>
      <Sun />
      <label className={styles.switch}>
        <input type="checkbox" onChange={handleToggle}
               checked={isChecked} />
        <span className={[styles.slider, styles.round].join(' ')}></span>
      </label>
      <Moon />
    </div>
  );
};

export default inject('themeStore')(observer(ThemeToggle));
