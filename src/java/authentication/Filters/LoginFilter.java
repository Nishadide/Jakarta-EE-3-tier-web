package authentication.Filters;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import authentication.Beans.AutenticationBean;
import jakarta.inject.Inject;

public class LoginFilter implements Filter {
    
    //Use the following for GlassFish 7.0.9  
    @Inject
    AutenticationBean session;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {  }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       HttpServletRequest req = (HttpServletRequest) request;
       HttpServletResponse resp = (HttpServletResponse) response;
       //Use the following for GlassFish 6.2.5  
        //Retrieve the authtication bean
       //AutenticationBean session = (AutenticationBean) req.getSession(true).getAttribute("authBean");
       // The whitelist of pages an un-authenticated user is allowed to reach.
       // Everything NOT on this list is a protected function page, so a deep
       // URL typed directly (e.g. newPhone.xhtml) is protected as well.
       String[] publicPages = {"login.xhtml", "register.xhtml",
               "emailVerification.xhtml", "emailRecovery.xhtml",
               "userRecovery.xhtml", "index.xhtml"};
       String url = req.getRequestURI();
       if (session == null || !session.isLogged()) {
           // The user is NOT logged in.
           if (isPublicResource(url, publicPages)) {
               // A public page or a static/JSF resource: allow it through.
               chain.doFilter(request, response);
           } else {
               // Any protected function page is redirected to the login page.
               resp.sendRedirect(req.getServletContext().getContextPath()+"/login.xhtml");
           }
       } else {
           if (url.indexOf("register.xhtml")>=0 || url.indexOf("login.xhtml")>=0
                   || url.indexOf("emailVerification.xhtml")>=0 || url.indexOf("emailRecovery.xhtml")>=0
                   || url.indexOf("userRecovery.xhtml")>=0 ) {
               resp.sendRedirect(req.getServletContext().getContextPath()+"/default.xhtml");
           }
           else if (url.indexOf("logout.xhtml")>=0) {
               //Use the following for GlassFish 6.2.5  
                //req.getSession().removeAttribute("authBean");
                //Use the following for GlassFish 7.0.9  
               req.getSession(false).invalidate();
               resp.sendRedirect(req.getServletContext().getContextPath()+"/login.xhtml");
           }
           else {
               chain.doFilter(request, response);
           }
       }
    }
    /**
     * Returns true if the requested URL is one of the public pages or a
     * static/JSF resource (stylesheet, image, script) that an un-authenticated
     * user must still be able to load so the public pages render correctly.
     */
    private boolean isPublicResource(String url, String[] publicPages) {
        for (String page : publicPages) {
            if (url.indexOf(page) >= 0) {
                return true;
            }
        }
        return url.contains("jakarta.faces.resource")
                || url.endsWith(".css") || url.endsWith(".js")
                || url.endsWith(".png") || url.endsWith(".jpg")
                || url.endsWith(".gif") || url.endsWith(".ico");
    }

    @Override
    public void destroy() {  }
    
}
