package com.pageprovider.dao.impl;


import com.pageprovider.dao.PageDao;
import com.pageprovider.domain.Page;
import com.pageprovider.util.persistence.GenericRowMapperList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: andyw
 * Date: 24/04/13
 * Time: 17:08
 */
@Repository
public class PageDaoImpl implements PageDao {

    private static final Logger LOG = Logger.getLogger(PageDaoImpl.class.getName());

    @Inject
    private JdbcTemplate jdbcTemplate;


    @Override
    public long create(Page page){

        LOG.log(Level.INFO, "Inserting page "+page.toString());
        String sql = "INSERT INTO payment_pages(aggregators_id,payment_page_type_id,content_id,html_file,factor)";
        sql += " VALUES(?,?,?,?,?)";

        Object[] params = {page.getAggregatorsId(),page.getPaymentPageTypeId(),page.getContentId(), page.getHtmlFile(),page.getFactor()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR);
        factory.setReturnGeneratedKeys(true);
        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(factory.newPreparedStatementCreator(params), keyholder);
        long id = keyholder.getKey().longValue();
        return id;

    }

    @Override
    public Page find(int contentId, int pageType, String factor) throws Exception{

        Page page = null;//CachePageService.get(contentId, pageType);
        if(page == null){
            LOG.log(Level.INFO, "Goes to Database for {contentId: "+contentId+" , PageType: "+pageType+"}");
            String sql = "SELECT * FROM payment_pages p WHERE content_id = ? and payment_page_type_id = ? AND factor=?";

            List<Page> pageList = null;
            try{
                pageList = (List)jdbcTemplate.queryForObject(sql, new Object[]{contentId, pageType, factor}, new GenericRowMapperList(Page.class));
            }catch(Exception ex) {

                LOG.log(Level.WARNING, ex.getMessage());

            }

            if(pageList != null && pageList.size() >0){
                return pageList.get(0);

            }else{
                return null;

            }
        }
        return page;

    }

    @Override
    public List<Page> findAll()throws Exception{

        String sql = "SELECT * FROM payment_pages";
        List<Page> pageList = null;
        try{
            pageList = (List)jdbcTemplate.queryForObject(sql, new Object[]{}, new GenericRowMapperList(Page.class));
        }catch(Exception ex) {}

        return pageList;

    }

}
