import styles from './baseButton.module.scss';

interface IBaseButton {
  name?: string;
  value: string;
  onClick: (event: Event) => void;
  type: 'submit' | 'button';
}

const BaseButton = (props: IBaseButton) => {
  const handleClick = () => {
    props.onClick;
  };
  return (
    <div>
      <input
        className={styles.baseButton}
        type={props.type}
        value={props.value}
        name={props.name}
        onClick={handleClick}
      />
    </div>
  );
};

export default BaseButton;