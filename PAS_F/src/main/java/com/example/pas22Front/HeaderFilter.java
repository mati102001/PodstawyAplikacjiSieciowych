package com.example.pas22Front;

import com.example.pas22Front.beans.login.Jwt;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class HeaderFilter implements Filter
{
    public void init(FilterConfig fc) throws ServletException {}

    @Inject
    private Jwt jwt;

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain fc) throws IOException, ServletException
    {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("TestHeader", "hello");
        fc.doFilter(req, res);
    }

    public void destroy() {}
}