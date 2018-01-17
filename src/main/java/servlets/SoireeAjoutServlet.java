package servlets;

import pojos.Soiree;
import pojos.Ville;
import services.SoireeService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/soireeadd")
@MultipartConfig
public class SoireeAjoutServlet extends AbstractGenericServlet {

	private static final long serialVersionUID = -3497793006266174453L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TemplateEngine templateEngine = this.createTemplateEngine(req);
		WebContext context = new WebContext(req, resp, getServletContext());
		context.setVariable("villes", Ville.values());
		if(req.getSession().getAttribute("cityCreationError") != null) {
			context.setVariable("errorMessage", req.getSession().getAttribute("cityCreationError"));
			context.setVariable("soiree", (Soiree) req.getSession().getAttribute("cityCreationData"));

			req.getSession().removeAttribute("cityCreationError");
			req.getSession().removeAttribute("cityCreationData");
		} else {
			context.setVariable("soiree", new Soiree(null,null,null,null, null));
		}
		
		templateEngine.process("soireeadd", context, resp.getWriter());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String resume = req.getParameter("resume");
		String ville = req.getParameter("ville");
		String date = req.getParameter("dateSoiree");
		Soiree newSoiree = new Soiree(null, name, resume,Ville.valueOf(ville),date);
		Part image = req.getPart("image");
		
		try {
			SoireeService.getInstance().addSoiree(newSoiree, image);
			resp.sendRedirect("home");
		} catch (IllegalArgumentException e) {
			req.getSession().setAttribute("soiree Update Error", e.getMessage());
			req.getSession().setAttribute("soiree Update Data", newSoiree);
			resp.sendRedirect("soireeadd");
		}

	}

	
}
