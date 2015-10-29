package com.pageprovider.util;


import com.pageprovider.domain.Page;

import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by antoniop on 25/02/15.
 */
public class CachePageService {


    private final static WeakHashMap<String, Page> cache = new WeakHashMap<String, Page>();

    private final static Logger LOG = Logger.getLogger(CachePageService.class.getName());


    private static String getKey(int contentId, int pageType){
        return contentId+"-"+pageType;
    }


    public static Page get(int contentId, int pageType){
        return cache.get(getKey(contentId, pageType));
    }


    public static void put(int contentId, int pageType, Page page){
        //It doesn't need to be synchronized as it's final
        cache.put(getKey(contentId, pageType),page);
        LOG.log(Level.INFO, "ADDED from Cache page{ contentId="+contentId+", pageType="+pageType+"}");
    }


    public static void delete(int contentId, int pageType){
        synchronized (cache){
            cache.remove(getKey(contentId, pageType));
            LOG.log(Level.INFO, "DELETED from Cache page{ contentId="+contentId+", pageType="+pageType+"}");
        }
    }
}
