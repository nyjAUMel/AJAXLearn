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

@WebServlet("/ajaxrequest7")
public class AjaxRequest7Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 这里要写成xml
        resp.setContentType("text/xml;charset=utf-8");
        PrintWriter writer = resp.getWriter();


        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode", "root", "123456789");
            ps = connection.prepareStatement("select * from t_student");
            rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            sb.append("<students>");
            while (rs.next()) {
                sb.append("<student>").append("<no>").append(rs.getInt("no")).append("</no>")
                        .append("<name>").append(rs.getString("name")).append("</name>").append("</student>");
            }
            sb.append("</students>");
            System.out.println(sb);
            writer.print(sb);
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
