import $api from '~/core/services/http';
import { AuthResponse } from '~/core/models/response/AuthResponse';
import { ROUTE_PREFIX } from '~/core/config/api.config';
import React from 'react';
import { toast } from 'react-hot-toast';

interface IHomePage {

}

const HomePage: React.FC<IHomePage> = () => {
  React.useEffect(() => {
  }, [localStorage.getItem('token')]);
  const handleClick = async () => {
    try {
      await $api.get<AuthResponse>(`${ROUTE_PREFIX}/user/test`);
    } catch (e: any) {
      toast.error(`${e?.response?.data?.message}`);
    }
  };

  return (

    <div>
      HomePage
      <input type={'button'} onClick={handleClick} value={'Получить юзера'} />
    </div>
  );
};

export default HomePage;