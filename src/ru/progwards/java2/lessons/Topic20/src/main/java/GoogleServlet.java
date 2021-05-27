import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/google")

public class GoogleServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String finfLine = req.getParameter("findLine");
        finfLine = finfLine.replaceAll(" ","+");
        System.out.println(finfLine);
        resp.sendRedirect("https://www.google.co.in/#q=" + finfLine);
    }
}
