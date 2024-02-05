import { Outlet } from 'react-router-dom';
import styles from './Header.module.scss';
import { Logout } from '~/components/icons/Logout';

const Header = () => {
  const handleClickLogout = () => {
    localStorage.removeItem('token');
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