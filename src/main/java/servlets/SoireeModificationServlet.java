package servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import pojos.Soiree;
import pojos.Ville;
import services.SoireeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/soireemodif")
public class SoireeModificationServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        TemplateEngine templateEngine = this.createTemplateEngine(req);
        WebContext context = new WebContext(req, resp, getServletContext());
        context.setVariable("villes", Ville.values());
        context.setVariable("soiree", new Soiree(null,null,null,null, null));
        templateEngine.process("soireemodif", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String resume = req.getParameter("resume");
        String ville = req.getParameter("ville");
        String date = req.getParameter("dateSoiree");
        Part image = req.getPart("image");
        Soiree newSoiree = new Soiree(id, name, resume, Ville.valueOf(ville),date);

        try {
            SoireeService.getInstance().updateSoiree(newSoiree, image, id);
            resp.sendRedirect("home");
        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("soiree Update Error", e.getMessage());
            req.getSession().setAttribute("soiree Update Data", newSoiree);
            resp.sendRedirect("home");
        }
    }
}
