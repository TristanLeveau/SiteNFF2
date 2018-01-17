package servlets;

import pojos.Participant;
import pojos.Soiree;
import services.SoireeService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/inscription")
public class InscriptionServlet extends AbstractGenericServlet {
    private static final long serialVersionUID = -3497793006266174453L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = this.createTemplateEngine(req);
        WebContext context = new WebContext(req, resp, getServletContext());
        if(req.getSession().getAttribute("utilisateurCreationError") != null) {
            context.setVariable("errorMessage", req.getSession().getAttribute("utilisateurCreationError"));
            context.setVariable("city", (Soiree) req.getSession().getAttribute("utilisateurCreationData"));

            req.getSession().removeAttribute("cityCreationError");
            req.getSession().removeAttribute("cityCreationData");
        } else {
            context.setVariable("utilisateur", new Participant(0,null,null, null, null));
        }

        templateEngine.process("inscription", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         String nom = req.getParameter("nom");
         String prenom = req.getParameter("prenom");
         String email = req.getParameter("email");
         String motDePasse = req.getParameter("motDePasse");

         Integer idSoiree = Integer.parseInt(req.getParameter("id"));

        Participant newParticipant = new Participant(null, nom, prenom,email, motDePasse);

        try {
            SoireeService.getInstance().addParticipant(newParticipant,idSoiree);
            resp.sendRedirect(String.format("detail?id=%d",idSoiree  ));
        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("cityCreationError", e.getMessage());
            req.getSession().setAttribute("cityCreationData", newParticipant);
            resp.sendRedirect(String.format("detail?id=%d",idSoiree  ));
        }

    }

}
