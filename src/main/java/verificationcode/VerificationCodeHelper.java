package verificationcode;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class VerificationCodeHelper {

    public static void verifyCode(String phone,String code) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        String codeValue = jedis.get(phone + "-code");

        if(code.equals(codeValue)){
            System.out.println("验证成功！");
        } else {
            System.out.println("验证失败！");
        }

        jedis.close();
    }

    public static String setVerificationCode(String phone) throws Exception {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        String countKey = phone + "-count";
        String codeKey = phone + "-code";
        String countValue = jedis.get(countKey);

        if(countValue == null) {
            jedis.setex(countKey,24*60*60,"1");
        } else if(Integer.parseInt(countValue)<3){
            jedis.incr(countKey);
        } else {
            jedis.close();
            throw new Exception("今天验证码次数已超过3次");
        }

        String randomCode = getRandomCode();
        jedis.setex(codeKey,120,randomCode);
        jedis.close();
        return randomCode;

    }


    public static String getRandomCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
           int num = random.nextInt(10);
           sb.append(num);
        }
        return sb.toString();
    }
}
