package com.ugc.sso.server.redis.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * fastjson序列化
 * Created with IntelliJ IDEA.
 * User: ChenYu
 * Date: 2018/5/25
 */
public class FastJsonRedisSerializer implements RedisSerializer<Object> {

    private static Logger logger = LoggerFactory.getLogger(FastJsonRedisSerializer.class);


    @Override
    public byte[] serialize(Object source) throws SerializationException {
        if (source == null) {
            return new byte[0];
        }

        return JSON.toJSONBytes(source, SerializerFeature.WriteClassName);
    }

    @Override
    public Object deserialize(byte[] source) throws SerializationException {
        if(source == null){
            return null;
        }
        try {
            return JSON.parse(source);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
