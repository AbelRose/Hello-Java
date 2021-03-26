package com.matrix.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * @Author: yihaosun
 * @Date: 3/26/2021 11:27
 */
public class CaffeineDemo {
    public static void main(String[] args) {
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(1024) // 数量上限
                .expireAfterWrite(5, TimeUnit.MINUTES) // 过期时间
                .weakKeys()  // 弱引用 key
                .weakValues() // 弱引用 value
                .removalListener((RemovalListener<String, String>) (key, value, cause) -> { // 剔除监听
                    System.out.println("key:" + key + ", value:" + value + ", 删除原因:" + cause.toString());
                })
//                .build();
                // 增加刷新机制
                .build(new CacheLoader<String, String>() {
                    @Override
                    public @Nullable String load(@NonNull String s) throws Exception {
                        return null; // 此处可以从数据库或者其他的地方查询数据
                    }
                });
        // 放入本地缓存
        cache.put("username", "abelrose" );
        cache.put("password", "123456");
        // 从本地缓存取数据
        System.out.println(cache.getIfPresent("username"));
        System.out.println(cache.getIfPresent("password"));
//        System.out.println(cache.get("blog", key -> {
//            return getValue(key);  // 没有数据的话 就从数据库或者其他缓存中找
//        }));
    }
}
