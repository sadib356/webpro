package com.library;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RemoveMemberServlet")
public class RemoveMemberServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        int memberId = Integer.parseInt(
                request.getParameter("member_id")
        );

        Connection con = null;
        PreparedStatement ps = null;

        try {

            con = DBConnection.getConnection();

            String sql = "DELETE FROM member WHERE member_id = ?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, memberId);

            ps.executeUpdate();

            response.sendRedirect("viewMember.jsp");

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "Error: " + e.getMessage()
            );

        } finally {

            try {

                if (ps != null) {
                    ps.close();
                }

                if (con != null) {
                    con.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}