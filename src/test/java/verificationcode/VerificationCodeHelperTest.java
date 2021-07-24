package verificationcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class VerificationCodeHelperTest {

    @Test
    public void sendVerificationCode() throws Exception {
        String code = VerificationCodeHelper.setVerificationCode("15625144334");
        System.out.println("验证码为："+code+",请在2分钟内完成验证。");
    }

    @Test
    public void verifyCode() {
        VerificationCodeHelper.verifyCode("15625144334","165670");
    }

}