package servlets;

import services.LivraisonService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/suppression")
public class LivraisonSuppressionServlet extends AbstractGenericServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = this.createTemplateEngine(req);
        Integer id = Integer.parseInt(req.getParameter("id"));
        String confirmation = req.getParameter("confirmation");
        if (confirmation!=null && Boolean.parseBoolean(confirmation)){
            LivraisonService.getInstance().supprimerLivraison(id);
            resp.sendRedirect("home");
            return;
        }

        WebContext context = new WebContext(req,resp, req.getServletContext());
        context.setVariable("soiree", LivraisonService.getInstance().getLivraison(id));
        templateEngine.process("confirmationsuppr",context,resp.getWriter());
    }

}
