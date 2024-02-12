import { Outlet } from 'react-router-dom';
import styles from './Header.module.scss';
import { Logout } from '~/components/icons/Logout';
import { useContext } from 'react';
import { Context } from '~/index';

const Header = () => {
  const { authStore } = useContext(Context);
  const handleClickLogout = async () => {
    await localStorage.removeItem('token');
    await authStore.setAuth(false);
  };

  return (
    <>
      <div className={styles.container}>
        <div className={styles.logo}>
          <div className={styles.firstLetters}>VM</div>
          <div className={styles.mainLetters}>Control</div>
        </div>
        <div className={styles.rightMenu}>
          <a className={styles.logout} onClick={handleClickLogout}>
            <Logout />
          </a>
        </div>
      </div>
      <Outlet />
    </>
  );
};

export default Header;