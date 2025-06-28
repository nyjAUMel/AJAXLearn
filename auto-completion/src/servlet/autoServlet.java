package servlet;

import beans.TSearchSuggestions;
import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/auto")
public class autoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // 获取参数
        String val = req.getParameter("val");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        // 返回的集合
        ArrayList<TSearchSuggestions> tArray = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode", "root", "123456789");
            String sql = "select * from t_search_suggestions where keyword like ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + val + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                long id = rs.getLong(1);
                String keyword = rs.getString(2);
                String category = rs.getString(3);
                tArray.add(new TSearchSuggestions(id, keyword, category));
            }
            resp.getWriter().print(JSON.toJSONString(tArray));

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
