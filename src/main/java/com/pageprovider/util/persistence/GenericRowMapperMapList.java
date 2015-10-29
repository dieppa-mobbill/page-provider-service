package com.pageprovider.util.persistence;

import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Column;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by dieppa-mint on 09/05/15.
 */
public class GenericRowMapperMapList implements RowMapper{

    Logger log = Logger.getLogger(GenericRowMapperMapList.class.toString());

    private Class clazz;
    private List<String> indexes;

    public GenericRowMapperMapList(Class clazz, String... indexes){
        this.clazz  = clazz;
        this.indexes= Arrays.asList(indexes);
    }

    public Map mapRow(ResultSet rs, int rowColumn) throws SQLDataException{

        Map<String,List> result = new HashMap();
        try{
            Constructor<?> ctor = clazz.getConstructor();
            do{
                Object object = ctor.newInstance();
                StringBuilder indexBuilder = new StringBuilder();
                Set<String> insertedIndex = new HashSet<String>();
                for(Field field : clazz.getDeclaredFields()){


                    Column columnAn = field.getAnnotation(Column.class);

                    if(columnAn != null){
                        String columnName = columnAn.name();

                        Class type = field.getType();

                        insertedIndex.add(columnName);

                        field.setAccessible(true);
                        if("int".equals(type.toString())){
                            field.set(object, rs.getInt(columnName));
                            if(indexes.contains(columnName)){
                                indexBuilder.append(rs.getInt(columnName));
                            }

                        }else if("long".equals(type.toString())){
                            field.set(object, rs.getLong(columnName));
                            if(indexes.contains(columnName)){
                                indexBuilder.append(rs.getLong(columnName));
                            }

                        }else if("double".equals(type.toString())){
                            field.set(object, rs.getDouble(columnName));
                            if(indexes.contains(columnName)){
                                indexBuilder.append(rs.getDouble(columnName));
                            }

                        }else if("boolean".equals(type.toString())){
                            field.set(object, rs.getBoolean(columnName));
                            if(indexes.contains(columnName)){
                                indexBuilder.append(rs.getBoolean(columnName));
                            }

                        }else if(String.class.equals(type)){
                            field.set(object, rs.getString(columnName));
                            if(indexes.contains(columnName)){
                                indexBuilder.append(rs.getString(columnName));
                            }

                        }else if(Integer.class.equals(type)){
                            field.set(object, rs.getInt(columnName));
                            if(indexes.contains(columnName)){
                                indexBuilder.append(rs.getInt(columnName));
                            }

                        }else if(Long.class.equals(type)){
                            field.set(object, rs.getLong(columnName));
                            if(indexes.contains(columnName)){
                                indexBuilder.append(rs.getLong(columnName));
                            }

                        }else if(Double.class.equals(type)){
                            field.set(object, rs.getDouble(columnName));
                            if(indexes.contains(columnName)){
                                indexBuilder.append(rs.getDouble(columnName));
                            }

                        }else if(BigDecimal.class.equals(type)){
                            field.set(object, rs.getBigDecimal(columnName));
                            if(indexes.contains(columnName)){
                                indexBuilder.append(rs.getBigDecimal(columnName));
                            }

                        }else if(Timestamp.class.equals(type)){
                            field.set(object, rs.getTimestamp(columnName));
                            if(indexes.contains(columnName)){
                                indexBuilder.append(rs.getTimestamp(columnName));
                            }

                        }else if(Date.class.equals(type)){
                            field.set(object, rs.getDate(columnName));
                            if(indexes.contains(columnName)){
                                indexBuilder.append(rs.getDate(columnName));
                            }

                        }else{
                            log.warning("Type not allowed for column "+this.clazz.getSimpleName()+"."+field.getName());
                        }
                    }
                }

                for(String index : indexes){
                    if(!insertedIndex.contains(index)){
                        Object notInserted = rs.getObject(index);
                        indexBuilder.append(String.valueOf(notInserted));


                    }
                }

                String indexValue    = indexBuilder.toString();
                    if(result.containsKey(indexValue)){
                    result.get(indexValue).add(object);

                }else{
                    List newItem = new ArrayList<Object>();
                    newItem.add(object);
                    result.put(indexValue, newItem);

                }

            }while(rs.next());


        }catch(Exception ex){
            log.warning(ex.getMessage());
        }

        return result;

    }



}
