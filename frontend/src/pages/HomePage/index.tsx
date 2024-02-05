import $api from '~/core/services/http';
import { AuthResponse } from '~/core/models/response/AuthResponse';
import { ROUTE_PREFIX } from '~/core/config/api.config';
import React from 'react';

const HomePage = () => {
  React.useEffect(() => {
  }, [localStorage.getItem('token')]);
  const handleClick = () => {
    $api.get<AuthResponse>(`${ROUTE_PREFIX}/user/ew`);
  };
  const handleClickLogout = () => {
    localStorage.clear();
  };

  return (
    <div>
      <div style={{ height: 100, backgroundColor: '#000000' }}>
        HomePage
      </div>
      <input type={'button'} onClick={handleClick} value={'Получить юзера'} />
      <input type={'button'} onClick={handleClickLogout} value={'Выход'} />
    </div>
  );
};

export default HomePage;