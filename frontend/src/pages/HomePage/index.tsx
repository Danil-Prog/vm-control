import $api from '~/core/services/http';
import { AuthResponse } from '~/core/models/response/AuthResponse';
import { ROUTE_PREFIX } from '~/core/config/api.config';
import React from 'react';

interface IHomePage {

}

const HomePage = () => {
  React.useEffect(() => {
  }, [localStorage.getItem('token')]);
  const handleClick = () => {
    $api.get<AuthResponse>(`${ROUTE_PREFIX}/user/ew`);
  };

  return (

    <div>
      HomePage
      <input type={'button'} onClick={handleClick} value={'Получить юзера'} />
    </div>
  );
};

export default HomePage;