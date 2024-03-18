// components/ThemeToggle.tsx
import React from 'react';
import { observer } from 'mobx-react-lite';
import ThemeStore from '~/core/stores/Theme.store';
import { inject } from 'mobx-react';
import styles from './ThemeToggle.module.scss';
import { IconMoon } from '~/components/icons/IconMoon';
import { IconSun } from '~/components/icons/IconSun';

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
      <IconMoon />
      <p>Dark mode</p>
      <label className={styles.switch}>
        <input type="checkbox" onChange={handleToggle}
               checked={isChecked} />
        <span className={[styles.slider, styles.round].join(' ')}></span>
      </label>
    </div>
  );
};

export default inject('themeStore')(observer(ThemeToggle));
