import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisTest {

    private static Jedis jedis = new Jedis("127.0.0.1",6379);

    @Test
    public void test_keys() {
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
    }

    @Test
    public void test_set_value() {
        jedis.flushAll();
        jedis.set("student-1","danny");
        jedis.mset("student-2","eric","student-3","tom");

        System.out.println(jedis.get("student-2"));
        System.out.println(jedis.mget("student-1","student-3"));
    }

    @Test
    public void test_list() {
        jedis.flushAll();
        jedis.lpush("city","beijing","guangzhou","zhuhai");
        List<String> cities = jedis.lrange("city", 0, -1);
        System.out.println(cities.toString());
        System.out.println(jedis.rpop("city"));
    }

    @Test
    public void test_set() {
        jedis.flushAll();
        jedis.sadd("language", "c", "c++", "java", "php", "js","java");
        System.out.println(jedis.smembers("language"));
        jedis.srem("language","php");
        System.out.println(jedis.smembers("language"));
    }

    @Test
    public void test_hash() {
        jedis.flushAll();
        jedis.hset("user-1","name","danny");
        jedis.hset("user","age","24");
        jedis.hset("user","phone","13322344543");
        System.out.println(jedis.hget("user-1","name"));

        Map<String,String> map = new HashMap<>();
        map.put("name","tom");
        map.put("age","30");
        jedis.hset("user-2",map);
        System.out.println(jedis.hget("user-2","age"));
    }

    @Test
    public void test_zset() {
        jedis.flushAll();
        jedis.zadd("phone",100,"iphone");
        jedis.zadd("phone",95,"huawei");
        jedis.zadd("phone",82,"xiaomi");
        jedis.zadd("phone",70,"oppo");

        System.out.println(jedis.zrange("phone",0,-1));
    }
}
