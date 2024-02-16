import React from 'react';
import styles from './Header.module.scss';
import { Logout } from '../../icons/Logout';
import { inject } from 'mobx-react';
import AuthStore from '../../../core/stores/Auth.store';
import { observer } from 'mobx-react-lite';
import ThemeToggle from '~/components/simple/ThemeToggle';

interface HeaderProps {
  authStore?: AuthStore;
}

const Header: React.FC<HeaderProps> = ({ authStore }) => {
  const handleClickLogout = async () => {
    await localStorage.removeItem('token');
    await authStore?.setAuth(false);
  };

  return (
    <>
      <div className={styles.container}>
        <div className={styles.logo}>
          <div className={styles.firstLetters}>VM</div>
          <div className={styles.mainLetters}>Control</div>
        </div>
        <div className={styles.rightMenu}>
          <ThemeToggle />
          <a className={styles.logout} onClick={handleClickLogout}>
            <Logout />
          </a>
        </div>
      </div>
    </>
  );
};

export default inject('authStore')(observer(Header));