package com.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
* @Description 作用
* @Author ccl
* @CreateDate 2021/3/18 9:35
**/
public class MapToObjectUtil {
    
    /**
     * 实体类转Map
     * @param object
     * @return
     */
    public static Map<String, Object> entityToMap(Object object) {
        HashMap<String, Object> map = new HashMap<>();
        for (Field field : object.getClass().getDeclaredFields()){
            try {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(object);
                map.put(field.getName(), o);
                field.setAccessible(flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * Map转实体类
     * @param map 需要初始化的数据，key字段必须与实体类的成员名字一样，否则赋值为空
     * @param entity  需要转化成的实体类
     * @return
     */
    public static <T> T mapToEntity(Map<String, Object> map, Class<T> entity) {
        if(Objects.isNull(map)){
            return null;
        }
        T t = null;
        try {
            t = entity.newInstance();
            for(Field field : entity.getDeclaredFields()) {
                if (map.containsKey(field.getName())) {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object object = map.get(field.getName());
                    if (object!= null && field.getType().isAssignableFrom(object.getClass())) {
                        field.set(t, object);
                    }
                    field.setAccessible(flag);
                }
            }
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
    
}
