package nosql;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Author: yoosan, SYSUDNLP Group
 * Date: 16/1/1, 2016.
 * Licence MIT
 */
public class RedisClient {

    private static Jedis jedis = null;
    public static Jedis getInstance(){
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
        if (jedis == null) {
            synchronized (Jedis.class) {
                jedis = pool.getResource();
            }
        }
        return jedis;
    }

    public static boolean addDocument(String docname, String document) {
        jedis = getInstance();
        if (jedis == null) return false;
        jedis.sadd(docname, document);
        return true;
    }

    public static boolean freeJedis() {
        if (jedis == null) return false;
        jedis.close();
        return true;
    }

    public static void main(String[] args) {
        Jedis jedis = RedisClient.getInstance();
        jedis.set("test", "value");
        jedis.close();
    }

}
