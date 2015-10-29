package com.pageprovider;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by antoniop on 01/09/15.
 */
@Component
public class CacheFilter implements Filter {

    public static final String PRAGMA = "Pragma";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String EXPIRES = "Expires";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader(CACHE_CONTROL, "no-cache");
        response.setHeader(PRAGMA, "no-cache");
        response.setHeader(EXPIRES, expirationDate());
        chain.doFilter(req, res);
    }


    private String expirationDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        DateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return httpDateFormat.format(calendar.getTime());
    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig arg0) throws ServletException {}

}
