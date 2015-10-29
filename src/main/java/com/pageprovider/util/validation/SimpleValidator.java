package com.pageprovider.util.validation;



import com.pageprovider.util.validation.exception.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by antoniop on 26/10/15.
 */
public class SimpleValidator {


    private List<String> notNullList;
    private List<String> emailList;
    private Map<String, Integer> minLengthMap;
    private Map<String, Integer> maxLengthMap;
    private Map<String, BigDecimal> graterThanMap;
    private Map<String, BigDecimal> equalMap;
    private Map<String, BigDecimal> lessThanMap;

    private Class clazz;
    private Object obj;




    public SimpleValidator notNull(String... notNulls){
        if(notNullList == null) notNullList = new ArrayList();
        for(int i=0 ; i< notNulls.length ; i++){
            String item = notNulls[i];
            notNullList.add(item);
        }

        return this;

    }

    public SimpleValidator equal(String fieldName, Integer value){
        if(equalMap == null)
            equalMap = new HashMap<String, BigDecimal>();

        equalMap.put(fieldName, new BigDecimal(value));
        return this;
    }

    public SimpleValidator equal(String fieldName, Long value){
        if(equalMap == null)
            equalMap = new HashMap<String, BigDecimal>();

        equalMap.put(fieldName, new BigDecimal(value));
        return this;
    }

    public SimpleValidator equal(String fieldName, Double value){
        if(equalMap == null)
            equalMap = new HashMap<String, BigDecimal>();

        equalMap.put(fieldName, new BigDecimal(value));
        return this;
    }

    public SimpleValidator graterThan(String fieldName, Integer value){
        if(graterThanMap == null)
            graterThanMap = new HashMap<String, BigDecimal>();

        graterThanMap.put(fieldName, new BigDecimal(value));
        return this;
    }

    public SimpleValidator graterThan(String fieldName, Long value){
        if(graterThanMap == null)
            graterThanMap = new HashMap<String, BigDecimal>();

        graterThanMap.put(fieldName, new BigDecimal(value));
        return this;
    }

    public SimpleValidator graterThan(String fieldName, Double value){
        if(graterThanMap == null)
            graterThanMap = new HashMap<String, BigDecimal>();

        graterThanMap.put(fieldName, new BigDecimal(value));
        return this;
    }

    public SimpleValidator lessThan(String fieldName, Integer value){
        if(lessThanMap == null)
            lessThanMap = new HashMap<String, BigDecimal>();

        lessThanMap.put(fieldName, new BigDecimal(value));
        return this;
    }

    public SimpleValidator lessThan(String fieldName, Long value){
        if(lessThanMap == null)
            lessThanMap = new HashMap<String, BigDecimal>();

        lessThanMap.put(fieldName, new BigDecimal(value));
        return this;
    }

    public SimpleValidator lessThan(String fieldName, Double value){
        if(lessThanMap == null)
            lessThanMap = new HashMap<String, BigDecimal>();

        lessThanMap.put(fieldName, new BigDecimal(value));
        return this;
    }

    public SimpleValidator minLength(String fieldName, int minLength){
        if(minLengthMap == null)
            minLengthMap = new HashMap<String, Integer>();

        minLengthMap.put(fieldName, minLength);
        return this;
    }

    public SimpleValidator email(String... emailFieldName){

        if(emailList == null) emailList = new ArrayList();
        for(int i=0 ; i< emailFieldName.length ; i++){
            String item = emailFieldName[i];
            emailList.add(item);
        }

        return this;

    }

    public SimpleValidator maxLength(String fieldName, int minLength){
        if(maxLengthMap == null)
            maxLengthMap = new HashMap<String, Integer>();

        maxLengthMap.put(fieldName, minLength);
        return this;
    }




    public void validate(Object obj){

        try{
            this.obj = obj;
            this.clazz = obj.getClass();
            checkNulls();
            checkMinLength();
            checkMaxLength();
            checkGraterThan();
            checkEqual();

        }catch(NoSuchFieldException ex){
            throw new RuntimeException("Internal error");

        }catch(IllegalAccessException ex){
            throw new RuntimeException("Internal error");

        }
    }

    private void checkNulls() throws NoSuchFieldException, IllegalAccessException{

        if(notNullList == null) return;
        for(String notNullField : notNullList){
            Field field = clazz.getDeclaredField(notNullField);
            field.setAccessible(true);

            field.setAccessible(true);
            if(field.get(obj) == null){
                throw new ValidatorNullException(notNullField, null);
            }
        }
    }

    private void checkEqual() throws NoSuchFieldException, IllegalAccessException{
        if(equalMap == null) return;
        Iterator<Map.Entry<String, BigDecimal>> iter = equalMap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, BigDecimal> entry = iter.next();
            String fieldName = entry.getKey();
            BigDecimal numValue = entry.getValue();

            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            Class type = field.getType();

            field.setAccessible(true);
            BigDecimal objValue = null;

            if("int".equals(type.toString())){
                objValue = new BigDecimal((Integer)field.get(obj));


            }else if("long".equals(type.toString())){
                objValue = new BigDecimal((Long)field.get(obj));

            }else if("double".equals(type.toString())){
                objValue = new BigDecimal((Double)field.get(obj));

            }else if(type.equals(BigDecimal.class)){
                objValue = (BigDecimal)field.get(obj);
            }

            int comparison = objValue.compareTo(numValue);
            if(comparison != 0){
                throw new ValidatorGraterThanException(fieldName, objValue, numValue);
            }

        }

    }

    private void checkGraterThan() throws NoSuchFieldException, IllegalAccessException{
        if(graterThanMap == null) return;
        Iterator<Map.Entry<String, BigDecimal>> iter = graterThanMap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, BigDecimal> entry = iter.next();
            String fieldName = entry.getKey();
            BigDecimal numValue = entry.getValue();

            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            Class type = field.getType();

            field.setAccessible(true);
            BigDecimal objValue = null;

            if("int".equals(type.toString())){
                objValue = new BigDecimal((Integer)field.get(obj));


            }else if("long".equals(type.toString())){
                objValue = new BigDecimal((Long)field.get(obj));

            }else if("double".equals(type.toString())){
                objValue = new BigDecimal((Double)field.get(obj));

            }else if(type.equals(BigDecimal.class)){
                objValue = (BigDecimal)field.get(obj);
            }

            int comparison = objValue.compareTo(numValue);
            if(comparison <= 0){
                throw new ValidatorGraterThanException(fieldName, objValue, numValue);
            }

        }

    }


    private void checkLessThan() throws NoSuchFieldException, IllegalAccessException{
        if(graterThanMap == null) return;
        Iterator<Map.Entry<String, BigDecimal>> iter = graterThanMap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, BigDecimal> entry = iter.next();
            String fieldName = entry.getKey();
            BigDecimal numValue = entry.getValue();

            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            Class type = field.getType();

            field.setAccessible(true);
            BigDecimal objValue = null;

            if("int".equals(type.toString())){
                objValue = new BigDecimal((Integer)field.get(obj));


            }else if("long".equals(type.toString())){
                objValue = new BigDecimal((Long)field.get(obj));

            }else if("double".equals(type.toString())){
                objValue = new BigDecimal((Double)field.get(obj));

            }else if(type.equals(BigDecimal.class)){
                objValue = (BigDecimal)field.get(obj);
            }

            int comparison = objValue.compareTo(numValue);
            if(comparison >= 0){
                throw new ValidatorLessThanException(fieldName, objValue, numValue);
            }

        }

    }


    private void checkMinLength() throws NoSuchFieldException, IllegalAccessException{
        if(minLengthMap == null) return;
        Iterator<Map.Entry<String, Integer>> iter = minLengthMap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, Integer> entry = iter.next();
            String fieldName = entry.getKey();
            Integer length = entry.getValue();

            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            Object value = field.get(obj);


            if(value instanceof CharSequence){
                if(((CharSequence)value).length() < entry.getValue()){
                    throw  new ValidatorMinLengthException(fieldName, value, length);
                }

            }else if(value instanceof Collection){
                if(((Collection)value).size() < length){
                    throw  new ValidatorMinLengthException(fieldName, value, length);
                }

            }else if(value instanceof Map){
                if(((Map)value).size() < length){
                    throw  new ValidatorMinLengthException(fieldName, value, length);
                }

            }

        }


    }

    private void checkMaxLength() throws NoSuchFieldException, IllegalAccessException{
        if(maxLengthMap == null) return;
        Iterator<Map.Entry<String, Integer>> iter = maxLengthMap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, Integer> entry = iter.next();
            String fieldName = entry.getKey();
            Integer length = entry.getValue();

            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            Object value = field.get(obj);


            if(value instanceof CharSequence){
                if(((CharSequence)value).length() > entry.getValue()){
                    throw  new ValidatorMaxLengthException(fieldName, value, length);
                }

            }else if(value instanceof Collection){
                if(((Collection)value).size() > length){
                    throw  new ValidatorMaxLengthException(fieldName, value, length);
                }

            }else if(value instanceof Map){
                if(((Map)value).size() > length){
                    throw  new ValidatorMaxLengthException(fieldName, value, length);
                }

            }

        }
    }

}
