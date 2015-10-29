package com.pageprovider.util;

/**
 * Created by dieppa-mint on 31/05/15.
 */
public class ArrayUtil {


    public static boolean notNull(Object[] array){
        return array !=null;
    }

    public static boolean nullEmpty(Object[] array){
        return array ==null || array.length <= 0;
    }


}
