package com.koral.vister;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class TestAlogo {

    @Test
    public void testLRUCache() {
        LRUCache lruCache = new LRUCache(2);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.get(1);
        lruCache.put(3, 3);
        lruCache.get(2);
        lruCache.put(4, 4);
        lruCache.get(1);
        lruCache.get(3);
        lruCache.get(4);
    }

    @Test
    public void testLinkedHashMap(){
        LinkedHashMap<Integer, Integer> lhmap = new LinkedHashMap<>();
        lhmap.put(1,1);
        lhmap.put(2,2);
        lhmap.put(3,3);
        lhmap.forEach((k,v) ->{
            System.out.println(k + ":" + v);
        });

    }

    class LRUCache {
        private HashMap<Integer, Integer> lRUCacheMap;
        private int capacity;
        private Queue<Integer> usedKey = new LinkedList<>();

        public LRUCache(int capacity) {
            lRUCacheMap = new HashMap<>(capacity);
            this.capacity = capacity;
        }

        public int get(int key) {
            Integer value = -1;
            if ((value = lRUCacheMap.get(key)) != null) {
                // 入对记录
                usedKey.add(key);
            } else {
                value = -1;
            }
            return value;
        }

        public void put(int key, int value) {
            if (lRUCacheMap.size() == capacity) {
                // 需要淘汰缓存
                Integer removeKey = usedKey.remove();
                lRUCacheMap.remove(removeKey);
            }
            lRUCacheMap.put(key, value);
            usedKey.add(key);
        }
    }

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
}
