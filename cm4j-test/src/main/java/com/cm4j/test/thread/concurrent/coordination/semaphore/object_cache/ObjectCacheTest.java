package com.cm4j.test.thread.concurrent.coordination.semaphore.object_cache;

import java.util.Random;

public class ObjectCacheTest {

    class TObjectFactory implements com.cm4j.test.thread.concurrent.coordination.semaphore.object_cache.MN_ObjectCache.ObjectFactory<Object> {
        @Override
        public Object makeObject() {
            return new Random().nextInt(1000);
        }
    }

    public static void main(String[] args) {
        ObjectCacheTest test = new ObjectCacheTest();
        MN_ObjectCache<Object> cache = new MN_ObjectCache<Object>(3, test.new TObjectFactory());
        try {
            Object obj = null;
            for (int i = 0; i < 3; i++) {
                obj = cache.getObject();
                System.out.println(obj);
            }
            cache.returnObject(obj);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
