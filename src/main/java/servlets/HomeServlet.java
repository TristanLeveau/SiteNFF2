package servlets;

import pojos.Ville;
import services.SoireeService;
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

		Ville villeFilter = (Ville) req.getSession().getAttribute("villeFilter");


		WebContext context = new WebContext(req, resp, getServletContext());
		context.setVariable("soirees", SoireeService.getInstance().listAllSoirees(villeFilter));
		context.setVariable("villes", Ville.values());
		context.setVariable("villeFilter", villeFilter);

		templateEngine.process("home", context, resp.getWriter());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String villeName = req.getParameter("ville");

		Ville ville = null;
		try {
			ville = Ville.valueOf(villeName);
		} catch (IllegalArgumentException ignored) {}

		req.getSession().setAttribute("villeFilter", ville);

		resp.sendRedirect("home");
	}
	
}
