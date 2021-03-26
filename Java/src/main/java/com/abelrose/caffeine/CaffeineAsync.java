package com.matrix.caffeine;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * @Author: yihaosun
 * @Date: 3/26/2021 11:53
 */
public class CaffeineAsync {
    public static void main(String[] args) throws Exception {

        AsyncLoadingCache<String, String> cache = Caffeine.newBuilder()
                // 数量上限
                .maximumSize(2)
                // 失效时间
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .refreshAfterWrite(1, TimeUnit.MINUTES)
                // 异步加载机制
                .buildAsync(new CacheLoader<String, String>() {
                    @Override
                    public @Nullable String load(@NonNull String s) throws Exception {
//                        return getValue(key); // 没有数据的话 就从数据库或者其他缓存中找
                        return "";
                    }
                });
        System.out.println("==============================");
        System.out.println(cache.get("username").get());
        System.out.println(cache.get("username").get(10, TimeUnit.MINUTES));
        System.out.println(cache.get("password").get(10, TimeUnit.MINUTES));
//        System.out.println(cache.get("blog").get());

    }
}
