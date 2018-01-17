package servlets;

import pojos.Soiree;
import services.SoireeService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/detail")
public class SoireeDetailsServlet extends AbstractGenericServlet {

	private static final long serialVersionUID = 8559083626521311046L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


		WebContext context = new WebContext(req, resp, getServletContext());


		Integer idSoiree = Integer.parseInt(req.getParameter("id"));
		Soiree soiree = SoireeService.getInstance().getSoiree(idSoiree);
		context.setVariable("soiree", soiree);
		context.setVariable("participants", SoireeService.getInstance().ListeParticipantsSoiree(idSoiree));
		if (soiree == null) {
			resp.sendRedirect("home");
			return;
		}

		TemplateEngine templateEngine = this.createTemplateEngine(req);


		templateEngine.process("soireedetail", context, resp.getWriter());
	}


}
