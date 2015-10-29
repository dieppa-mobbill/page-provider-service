package com.pageprovider.services;

import com.pageprovider.dao.PageDao;
import com.pageprovider.domain.Page;
import com.pageprovider.util.CachePageService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by antoniop on 26/02/15.
 */
@Service
public class PageProviderService {

    @Inject
    private PageDao pageDao;


    public PageProviderService(){
    }


    public long createPage(Page page){

        page.setAggregatorsId(24);
        return this.pageDao.create(page);

    }

    public Page getPage(int content, int pageType, String factor) throws Exception{
        return this.pageDao.find(content, pageType, factor);
    }


    public void refreshCachePage(int contentId, int pageType, String factor) throws Exception{
        CachePageService.delete(contentId, pageType);
        this.pageDao.find(contentId, pageType, factor);
    }

    public void deleteCachePage(int contentId, int pageType) throws Exception{
        CachePageService.delete(contentId, pageType);
    }
}
