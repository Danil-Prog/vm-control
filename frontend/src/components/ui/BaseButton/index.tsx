import styles from './baseButton.module.scss';

interface IBaseButton {
  name?: string;
  value: string;
  onClick: () => void;
  type: 'submit' | 'button';
}

const BaseButton = (props: IBaseButton) => {
  return (
    <div>
      <button type={props.type} className={styles.baseButton} onClick={props.onClick}>
        {props.value}
      </button>
    </div>
  );
};

export default BaseButton;