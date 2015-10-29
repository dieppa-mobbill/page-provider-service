package com.pageprovider.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dieppa-mint on 08/10/15.
 */
public class GenericHiddenBuilder<T> {

    protected T obj;
    protected List<String> hiddenFields;

    public GenericHiddenBuilder(T obj){
        this.obj = obj;

    }

    public GenericHiddenBuilder<T> hide(String... hiddenFields){
        if(this.hiddenFields == null)
            this.hiddenFields = new ArrayList<String>();
        for(String field : hiddenFields){
            this.hiddenFields.add(field.toLowerCase());
        }
        return this;
    }

    public T build(){

        try{
            Class clazz = obj.getClass();
            Constructor<T> ctor = clazz.getConstructor();
            T copy = ctor.newInstance();
            for(Field field : clazz.getDeclaredFields()){
                if(!this.hiddenFields.contains(field.getName().toLowerCase())) {
                    field.setAccessible(true);
                    field.set(copy, field.get(obj));
                }
            }

            return copy;

        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }

    }
}