package com.library;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String memberName = request.getParameter("member_name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        Connection con = null;
        PreparedStatement ps = null;

        try {

            con = DBConnection.getConnection();

            String sql = "INSERT INTO member "
                    + "(member_id, member_name, email, phone) "
                    + "VALUES (member_seq.NEXTVAL, ?, ?, ?)";

            ps = con.prepareStatement(sql);

            ps.setString(1, memberName);
            ps.setString(2, email);
            ps.setString(3, phone);

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