package com.zmm.reggie.redis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * redis管理类
 */
@SpringBootTest
public class RedisManage {

    /**
     * 删除redis以TOKEN_开头的所有key
     */
    @Test
    public void deleteRedisKeys() {
        Jedis jedis = new Jedis("localhost", 6379);
        for (String key : jedis.keys("TOKEN_*")) {
            jedis.del(key);
        }
        jedis.close();
    }
}
