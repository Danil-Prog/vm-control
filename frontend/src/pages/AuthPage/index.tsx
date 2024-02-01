import BaseInput from "~/components/ui/BaseInput";
import styles from './AuthPage.module.scss'
import BaseButton from "~/components/ui/BaseButton";

const AuthPage = () => {
    return (
        <div className={styles.container}>
            <div className={styles.formContainer}>
                <BaseInput onInput={() => console.log('test')} name={'test'} />
                <BaseInput onInput={() => console.log('test')} name={'test'} />
                <BaseButton value={'Вход'} name={'sendAuth'}/>
            </div>
        </div>
    )
}

export default AuthPage;