package com.bjpowernode.ajax.servlet;

import com.alibaba.fastjson2.JSON;
import com.bjpowernode.ajax.beans.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/ajaxrequest6")
public class AjaxRequest6Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 用于拼接的字符串
        StringBuilder sb = new StringBuilder();

        resp.setContentType("text/html;charset=utf-8");
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();


        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode", "root", "123456789");
            ps = connection.prepareStatement("select * from t_student");
            rs = ps.executeQuery();

//            sb.append("[");
            ArrayList<Student> students = new ArrayList<>();
            while (rs.next()) {
                // 这样在后端又写了HTML代码，不利于维护
                /*sb.append("<tr>\n" +
                        " <th>" + rs.getInt("no") + "</th>\n" +
                        " <th>" + rs.getString("name") + "</th>\n" +
                        "</tr>");*/

                // 改用JSON格式传递
                /*sb.append("{" + "no" + ":" + rs.getInt("no") + ",");
                sb.append("no" + ":" + rs.getString("name") + "},");*/

                // 使用原生方式
                /*sb.append('{')
                        .append("\"no\":")
                        .append(rs.getInt("no"))
                        .append(",\"name\":\"")
                        .append(rs.getString("name"))
                        .append("\"}");
                if(!rs.isLast()) {
                    sb.append(",");
                }*/

                // 使用FASTJSON
                students.add(new Student(rs.getInt("no"), rs.getString("name")));
            }
//            sb.append("]");

            writer.print(JSON.toJSONString(students));
        } catch (SQLException | ClassNotFoundException e) {
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
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
