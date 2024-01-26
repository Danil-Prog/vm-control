import styles from './baseButton.module.scss'

interface IBaseButton {
    value: string;
    name: string;
}

const BaseButton = (props: IBaseButton) => {
    return (
        <div>
            <input className={styles.baseButton} type={'button'} value={props.value} name={props.name} />
        </div>
    )
}

export default BaseButton;