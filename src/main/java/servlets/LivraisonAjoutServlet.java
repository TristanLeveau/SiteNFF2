package servlets;

import pojos.Semestre;
import pojos.Livraison;
import services.LivraisonService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/livraisonadd")
@MultipartConfig
public class LivraisonAjoutServlet extends AbstractGenericServlet {

	private static final long serialVersionUID = -3497793006266174453L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TemplateEngine templateEngine = this.createTemplateEngine(req);
		WebContext context = new WebContext(req, resp, getServletContext());
		context.setVariable("semestres", Semestre.values());
		if(req.getSession().getAttribute("cityCreationError") != null) {
			context.setVariable("errorMessage", req.getSession().getAttribute("livraisonCreationError"));
			context.setVariable("livraison", (Livraison) req.getSession().getAttribute("livraisonCreationData"));

			req.getSession().removeAttribute("livraisonCreationError");
			req.getSession().removeAttribute("livraisonCreationData");
		} else {
			context.setVariable("livraison", new Livraison(null,null,null,null));
		}
		
		templateEngine.process("livraisonadd", context, resp.getWriter());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String date = req.getParameter("date");
		String contenu = req.getParameter("contenu");
		String semestre = req.getParameter("semestre");
		Livraison newLivraison = new Livraison(null, date, contenu, Semestre.valueOf(semestre));
		Part image = req.getPart("image");
		
		try {
			LivraisonService.getInstance().addLivraison(newLivraison, image);
			resp.sendRedirect("home");
		} catch (IllegalArgumentException e) {
			req.getSession().setAttribute("Livraison Update Error", e.getMessage());
			req.getSession().setAttribute("Livraison Update Data", newLivraison);
			resp.sendRedirect("livraisonadd");
		}

	}

	
}
