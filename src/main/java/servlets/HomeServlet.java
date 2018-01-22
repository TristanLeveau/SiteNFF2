package servlets;

import pojos.Semestre;
import services.LivraisonService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends AbstractGenericServlet {

	private static final long serialVersionUID = 5402133218271984030L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TemplateEngine templateEngine = this.createTemplateEngine(req);

		Semestre semestreFilter = (Semestre) req.getSession().getAttribute("semestreFilter");


		WebContext context = new WebContext(req, resp, getServletContext());
		context.setVariable("livraisons", LivraisonService.getInstance().listAllLivraisons(semestreFilter));
		context.setVariable("semestres", Semestre.values());
		context.setVariable("semestreFilter", semestreFilter);

		templateEngine.process("home", context, resp.getWriter());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String villeName = req.getParameter("semestre");

		Semestre semestre = null;
		try {
			semestre = Semestre.valueOf(villeName);
		} catch (IllegalArgumentException ignored) {}

		req.getSession().setAttribute("villeFilter", semestre);

		resp.sendRedirect("home");
	}
	
}
