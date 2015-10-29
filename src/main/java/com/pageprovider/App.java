package com.pageprovider;

import com.pageprovider.domain.Page;
import com.pageprovider.services.PageProviderService;
import com.pageprovider.util.exceptions.NotFoundException;
import com.pageprovider.util.validation.SimpleValidator;
import com.sun.xml.internal.ws.encoding.ContentType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
@EnableAutoConfiguration
@ComponentScan
//@EnableWebMvcSecurity
public class App
{

    private final static Logger LOG = Logger.getLogger(App.class.getName());

    @Inject
    private PageProviderService pageProviderService;


    @ResponseBody
    @RequestMapping(value = "/page/{id}/{contentId}/{paymentPageTypeId}/{factor}", method = RequestMethod.POST, consumes = "text/html",produces = "application/json")
    public String getPage(
            @PathVariable("id") long pageId,
            @PathVariable("contentId") long contentId,
            @PathVariable("paymentPageTypeId") int paymentPageTypeId,
            @PathVariable("factor") String factor,
            @RequestBody String html) throws Exception{

        Page page = new Page();
        page.setId(pageId);
        page.setContentId(contentId);
        page.setPaymentPageTypeId(paymentPageTypeId);
        page.setFactor(factor);
        page.setHtmlFile(html);


        new SimpleValidator()
                .notNull("id","contentId", "htmlFile","paymentPageTypeId","factor")
                .validate(page);



        try{
            pageProviderService.createPage(page);
            return "{\"result\" :\"OK\"}";
        }catch(Exception ex){
            return "{\"result\" :\"ERROR\"," +
                    "\"reason\":\""+ex.getMessage()+"\"}";
        }




    }


    @ResponseBody
    @RequestMapping(value = "/page/{contentId}/{pageType}/{factor}", method = RequestMethod.GET, produces = "text/html")
    public String getPage(
            @PathVariable("contentId") int content,
            @PathVariable("pageType") int pageType,
            @PathVariable("factor") String factor) throws Exception{

        ResponseEntity<String> r = new ResponseEntity<String>("", HttpStatus.OK);
        LOG.log(Level.INFO, "TRYING TO GET page/" + content+"/"+pageType);
        Page p = pageProviderService.getPage(content, pageType, factor);
        if(p != null){
            return p.getHtmlFile();
        }else{
            throw new NotFoundException();
        }

    }

    @ResponseBody
    @RequestMapping(value = "/page/cache/update/{contentId}/{pageType}/{factor}", method =  RequestMethod.GET, produces = "text/html")
    public String updatePage(@PathVariable("contentId") int content,
                             @PathVariable("pageType") int pageType,
                             @PathVariable("factor") String factor) throws Exception{

        this.pageProviderService.refreshCachePage(content, pageType, factor);
        LOG.log(Level.INFO, "REFRESHED {contentId: " + content + " , PageType: " + pageType + "}");
        return "OK";

    }

    @ResponseBody
    @RequestMapping(value = "/page/{contentId}/{pageType}", method = RequestMethod.DELETE, produces = "text/plain")
    public String deletepage(@PathVariable("contentId") int content, @PathVariable("pageType") int pageType) throws Exception{

        this.pageProviderService.deleteCachePage(content, pageType);
        LOG.log(Level.INFO, "DELETED {contentId: "+content+" , PageType: "+pageType+"}");
        return "OK";

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptionException(Exception ex) {

        LOG.log(Level.SEVERE, ex.getMessage());
        return new ResponseEntity("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    }



    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {

        LOG.log(Level.SEVERE, ex.getMessage());
        return new ResponseEntity("Page NOT FOUND for those parameters", HttpStatus.BAD_REQUEST);

    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // Prevent the HTTP response header of "Pragma: no-cache".
//        http.headers().cacheControl().disable();
//    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }
}
