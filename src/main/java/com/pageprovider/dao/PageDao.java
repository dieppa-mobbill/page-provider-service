package com.pageprovider.dao;

import com.pageprovider.domain.Page;

import java.util.List;


public interface PageDao {

    void create(Page page);

    Page find(int contentId, int pageType, String factor) throws Exception;

    List<Page> findAll() throws Exception;
}
