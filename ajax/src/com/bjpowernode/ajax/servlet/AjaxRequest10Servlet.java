package com.bjpowernode.ajax.servlet;

import com.alibaba.fastjson2.JSON;
import com.bjpowernode.ajax.beans.Area;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/ajaxrequest10")
public class AjaxRequest10Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/xml");
        PrintWriter writer = resp.getWriter();
        String connStr = "jdbc:mysql://localhost:3306/bjpowernode";
        String username = "root";
        String password = "123456789";

        String parentId = req.getParameter("parentId");
        // 用来接数据库返回的信息
        ArrayList<Area> areas = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(connStr,username,password);
            String sql = "select * from t_area where parent_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,parentId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                long areaId = resultSet.getLong(1);
                String areaName = resultSet.getString(2);
                long areaParentId = resultSet.getLong(3);
                areas.add(new Area(areaId,areaName,areaParentId));
            }
            // 返回数据
            writer.print(JSON.toJSONString(areas));
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
