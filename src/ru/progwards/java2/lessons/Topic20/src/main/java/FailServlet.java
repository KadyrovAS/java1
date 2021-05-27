import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/fail")
public class FailServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String msg  = "<a href=\"index.jsp\">" +
                "логин и пароль введены неверно, попробуйте еще раз" +
                "</a>";
        req.setAttribute("message",msg);
        req.getRequestDispatcher("message.jsp").forward(req, resp);
    }
}
