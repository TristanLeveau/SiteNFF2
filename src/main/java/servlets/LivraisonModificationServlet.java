package servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import pojos.Livraison;
import pojos.Semestre;
import services.LivraisonService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/livraisonmodif")
public class LivraisonModificationServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        TemplateEngine templateEngine = this.createTemplateEngine(req);
        WebContext context = new WebContext(req, resp, getServletContext());
        context.setVariable("semestres", Semestre.values());
        context.setVariable("livraison", new Livraison(null,null,null,null));
        templateEngine.process("livraisonmodif", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        String date = req.getParameter("date");
        String contenu = req.getParameter("contenu");
        String ville = req.getParameter("semestre");
        Part image = req.getPart("image");
        Livraison newLivraison = new Livraison(id, date, contenu, Semestre.valueOf(ville));

        try {
            LivraisonService.getInstance().updateLivraison(newLivraison, image, id);
            resp.sendRedirect("home");
        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("Livraison Update Error", e.getMessage());
            req.getSession().setAttribute("Livraison Update Data", newLivraison);
            resp.sendRedirect("home");
        }
    }
}
