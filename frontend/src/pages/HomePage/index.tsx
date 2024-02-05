import $api from '~/core/services/http';
import { AuthResponse } from '~/core/models/response/AuthResponse';
import { ROUTE_PREFIX } from '~/core/config/api.config';

const HomePage = () => {
  const handleClick = () => {
    $api.get<AuthResponse>(`${ROUTE_PREFIX}/user/ew`);
  };

  return (
    <div>
      <div style={{ height: 100, backgroundColor: '#000000' }}>
        HomePage
      </div>
      <input type={'button'} onClick={handleClick} />
    </div>
  );
};

export default HomePage;