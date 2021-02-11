package servlet;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HelloServlet" , urlPatterns = { "hello"}, loadOnStartup = 1)
public class HelloServlet  extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().print( "Hello, World!" );
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext servletContext = getServletConfig().getServletContext();
        WebContext ctx = new WebContext(request, response, servletContext,
                request.getLocale());
        ServletContextTemplateResolver templateResolver = new
                ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode("XHTML");
        templateResolver.setPrefix("");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(3600000L);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        String name = request.getParameter("name");
        request.setAttribute("user", name);
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 1000);
        templateEngine.process("response", ctx, response.getWriter());
    }
}
