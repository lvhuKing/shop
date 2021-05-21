package com.basic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Configuration
public class RedisUtils {

    @Resource
    private RedisTemplate redisTemplate;

    /**给指定key设置过期时间*/
    public void expire(String key, Long timeout, TimeUnit unit){
        if(timeout > 0){
            redisTemplate.expire(key, timeout, unit);
        }
    }
    /**获取key过期时间*/
    public Long getTimeOut(String key){
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**指定删除*/
    public void remove(String... keys){
        if(keys != null && keys.length > 0){
            if(keys.length == 1){
                redisTemplate.delete(keys[0]);
                String key = keys[0];
                if(redisTemplate.hasKey(key)){
                    redisTemplate.delete(key);
                }
            }else{
                redisTemplate.delete(CollectionUtils.arrayToList(keys));
            }
        }
    }

    /**模糊删除*/
    public void removeBlear(String... blears){
        for(String blear : blears){
            Set values = redisTemplate.keys(blear);
            redisTemplate.delete(values);
        }
    }

    public void addObject(String key, Object object, Long timeout, TimeUnit unit){
        redisTemplate.boundValueOps(key).set(object ,timeout ,unit);
    }

    public Object getObject(String key){
        return redisTemplate.boundValueOps(key).get();
    }

    /**Hash*/
    public void addMap(String key, Map map){
        redisTemplate.boundHashOps(key).put(key, map);
    }

    public Map getMap(String key){
        return redisTemplate.boundHashOps(key).entries();
    }

    /**List：左右 push存 pop取*/
    public void addLeftList(String key, Object object){
        redisTemplate.boundListOps(key).leftPushAll(object);
    }

    public void addRightList(String key, Object object, Long timeout, TimeUnit unit){
        redisTemplate.boundListOps(key).rightPushAll(object);
        expire(key, timeout, unit);
    }

    public List getList(String key){
        return redisTemplate.boundListOps(key).range(0, redisTemplate.boundListOps(key).size());
    }

}
