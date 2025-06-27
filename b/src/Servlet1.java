import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/servlet1")
public class Servlet1 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html;charset=utf-8");
        /*
        加上这句解决AJAX跨域的问题
            http://localhost:8081表示允许这个服务的资源来访问
         */
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        writer.println("<h1>这是来自B服务器的资源</h1>");
    }
}
