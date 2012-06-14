package com.phform.server.web.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.util.PatternMatchUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.util.Set;
import java.util.Iterator;
import java.io.IOException;

public class StaticFilter extends OncePerRequestFilter
{
    private final static String DEFAULT_INCLUDES = "*.html";
    
    private final static String DEFAULT_EXCLUDES = "";
    
    private static final String INCLUDES_PARAMETER = "includes";
    
    private static final String EXCLUDES_PARAMETER = "excludes";
    
    private static final String SERVLETNAME_PARAMETER = "servletName";
    
    private String[] excludes;
    
    private String[] includes;
    
    private String servletName = null;
    
    /**
     * Read the includes/excludes parameters and set the filter accordingly.
     */
    public void initFilterBean()
    {
        String includesParam =
            getFilterConfig().getInitParameter(INCLUDES_PARAMETER);
        if (StringUtils.isEmpty(includesParam))
        {
            includes = parsePatterns(DEFAULT_INCLUDES);
        }
        else
        {
            includes = parsePatterns(includesParam);
        }
        
        String excludesParam =
            getFilterConfig().getInitParameter(EXCLUDES_PARAMETER);
        if (StringUtils.isEmpty(excludesParam))
        {
            excludes = parsePatterns(DEFAULT_EXCLUDES);
        }
        else
        {
            excludes = parsePatterns(excludesParam);
        }
        // if servletName is specified, set it
        servletName = getFilterConfig().getInitParameter(SERVLETNAME_PARAMETER);
    }
    
    private String[] parsePatterns(String delimitedPatterns)
    {
        // make sure no patterns are repeated.
        Set patternSet =
            org.springframework.util.StringUtils
                    .commaDelimitedListToSet(delimitedPatterns);
        String[] patterns = new String[patternSet.size()];
        int i = 0;
        for (Iterator iterator = patternSet.iterator(); iterator.hasNext(); i++)
        {
            // no trailing/leading white space.
            String pattern = (String) iterator.next();
            patterns[i] = pattern.trim();
        }
        return patterns;
    }
    
    /**
     * This method checks to see if the current path matches includes or
     * excludes. If it matches includes and not excludes, it forwards to the
     * static resource and ends the filter chain. Otherwise, it forwards to the
     * next filter in the chain.
     * 
     * @param request
     *            the current request
     * @param response
     *            the current response
     * @param chain
     *            the filter chain
     * @throws javax.servlet.ServletException
     *             when something goes wrong
     * @throws java.io.IOException
     *             when something goes terribly wrong
     */
    public void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String path = urlPathHelper.getPathWithinApplication(request);
        boolean pathExcluded = PatternMatchUtils.simpleMatch(excludes, path);
        boolean pathIncluded = PatternMatchUtils.simpleMatch(includes, path);
        
        if (pathIncluded && !pathExcluded)
        {
            if (logger.isDebugEnabled())
            {
                // logger.debug("Forwarding to static resource: " + path);
            }
            
            if (path.contains(".html"))
            {
                response.setContentType("text/html");
            }
            
            RequestDispatcher rd =
                getServletContext().getRequestDispatcher(path);
            rd.include(request, response);
            return;
        }
        
        if (servletName != null)
        {
            RequestDispatcher rd =
                getServletContext().getNamedDispatcher(servletName);
            rd.forward(request, response);
            return;
        }
        
        chain.doFilter(request, response);
    }
}
