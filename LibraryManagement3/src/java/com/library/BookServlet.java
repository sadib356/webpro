package com.library;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/BookServlet")
public class BookServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Connection con = null;
        PreparedStatement ps = null;

        try {

            con = DBConnection.getConnection();

            String sql = "INSERT INTO book "
                    + "(book_id, title, author, quantity, status) "
                    + "VALUES (book_seq.NEXTVAL, ?, ?, ?, ?)";

            ps = con.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setInt(3, quantity);
            ps.setString(4, "Available");

            ps.executeUpdate();

            response.sendRedirect("viewBook.jsp");

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