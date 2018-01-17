package rmproject.filters;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {
    public void destroy() {
    }
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        resp.setContentType("text/html;charset=UTF-8");
        chain.doFilter(req, resp);
    }
    public void init(FilterConfig config) throws ServletException {
    }
}
