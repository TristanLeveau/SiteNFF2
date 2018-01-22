package servlets;

import services.LivraisonService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/image")
public class ImageServlet extends AbstractGenericServlet{

    private Map<String, String> mimeTypes;


    @Override
    public void init() throws ServletException {
        mimeTypes = new HashMap<>();
        mimeTypes.put("jpg", "image/jpeg");
        mimeTypes.put("jpeg", "image/jpeg");
        mimeTypes.put("png", "image/png");
        mimeTypes.put("gif", "image/gif");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

        Integer id = Integer.parseInt(req.getParameter("id"));

        Path imagePath = LivraisonService.getInstance().getLivraisonImage(id);

        String pictureFileName = imagePath.getFileName().toString();
        String pictureFileExtension = pictureFileName.substring(pictureFileName.lastIndexOf(".") + 1);

        resp.setContentType("image/jpeg");
        Files.copy(imagePath, resp.getOutputStream());
    }
}
