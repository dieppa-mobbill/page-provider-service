package com.pageprovider.util.persistence;

import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Column;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by dieppa-mint on 09/05/15.
 */
public class GenericRowMapper implements RowMapper{

    Logger log = Logger.getLogger(GenericRowMapper.class.toString());

    private Class clazz;

    public GenericRowMapper(Class clazz){
        this.clazz = clazz;
    }

    public Object mapRow(ResultSet rs, int rowColumn) throws SQLDataException{

        Object object = null;
        try{
            Constructor<?> ctor = clazz.getConstructor();
            object = ctor.newInstance();

            for(Field field : clazz.getDeclaredFields()){
                Column columnAn = field.getAnnotation(Column.class);

                if(columnAn != null){
                    String columnName = columnAn.name();

                    Class type = field.getType();

                    field.setAccessible(true);
                    if("int".equals(type.toString())){
                        field.set(object, rs.getInt(columnName));

                    }else if("long".equals(type.toString())){
                        field.set(object, rs.getLong(columnName));

                    }else if("double".equals(type.toString())){
                        field.set(object, rs.getDouble(columnName));

                    }else if("boolean".equals(type.toString())){
                        field.set(object, rs.getBoolean(columnName));

                    }else if(String.class.equals(type)){
                        field.set(object, rs.getString(columnName));

                    }else if(Integer.class.equals(type)){
                        field.set(object, rs.getInt(columnName));

                    }else if(Long.class.equals(type)){
                        field.set(object, rs.getLong(columnName));

                    }else if(Double.class.equals(type)){
                        field.set(object, rs.getDouble(columnName));

                    }else if(BigDecimal.class.equals(type)){
                        field.set(object, rs.getBigDecimal(columnName));

                    }else if(Timestamp.class.equals(type)){
                        field.set(object, rs.getTimestamp(columnName));

                    }else if(Date.class.equals(type)){
                        field.set(object, rs.getDate(columnName));

                    }else{
                        log.warning("Type not allowed for column "+this.clazz.getSimpleName()+"."+field.getName());
                    }

//                    else if(BigInteger.class.equals(type)){
//                        field.set(object, rs.getBigI(columnName));
//
//                    }else if("char".equals(type.toString())){
//                        field.set(object, rs.getChar(columnName));
//
//                    }
                }

            }
        }catch(Exception ex){
            log.warning(ex.getMessage());
        }

        return object;

    }
}
