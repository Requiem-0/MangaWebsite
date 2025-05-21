import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manga.controllers.dao.HomeMangaDAO;
import com.manga.models.Manga;

@WebServlet("/VolumeServlet")
public class VolumeServelt extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HomeMangaDAO mangaDAO;

    @Override
    public void init() {
        mangaDAO = new HomeMangaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect("HomeMangaServlet");
            return;
        }

        int mangaId = Integer.parseInt(idStr);
        Manga manga = mangaDAO.getMangaById(mangaId);

        if (manga == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Manga not found");
            return;
        }

        request.setAttribute("manga", manga);
        request.getRequestDispatcher("/pages/volume.jsp").forward(request, response);
    }
}
