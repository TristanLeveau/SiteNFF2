package servlets;

import pojos.Livraison;
import services.LivraisonService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/detail")
public class LivraisonDetailsServlet extends AbstractGenericServlet {

	private static final long serialVersionUID = 8559083626521311046L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


		WebContext context = new WebContext(req, resp, getServletContext());


		Integer idLivraison = Integer.parseInt(req.getParameter("id"));
		Livraison livraison = LivraisonService.getInstance().getLivraison(idLivraison);
		context.setVariable("livraison", livraison);
		context.setVariable("participants", LivraisonService.getInstance().ListeParticipantsLivraison(idLivraison));
		if (livraison == null) {
			resp.sendRedirect("home");
			return;
		}

		TemplateEngine templateEngine = this.createTemplateEngine(req);


		templateEngine.process("livraisondetail", context, resp.getWriter());
	}


}
