package com.library;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ReturnBookServlet")
public class ReturnBookServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        int bookId = Integer.parseInt(
                request.getParameter("book_id")
        );

        int memberId = Integer.parseInt(
                request.getParameter("member_id")
        );

        Connection con = null;
        PreparedStatement psCheck = null;
        PreparedStatement psReturn = null;
        PreparedStatement psUpdate = null;
        ResultSet rs = null;

        try {

            con = DBConnection.getConnection();

            // Check whether this book was borrowed by this member
            String checkSql =
                    "SELECT borrow_id FROM borrow "
                    + "WHERE book_id = ? "
                    + "AND member_id = ? "
                    + "AND return_date IS NULL";

            psCheck = con.prepareStatement(checkSql);

            psCheck.setInt(1, bookId);
            psCheck.setInt(2, memberId);

            rs = psCheck.executeQuery();

            if (!rs.next()) {

                response.getWriter().println(
                        "No active borrowing record found."
                );

                return;
            }

            int borrowId = rs.getInt("borrow_id");

            // Update return date
            String returnSql =
                    "UPDATE borrow "
                    + "SET return_date = SYSDATE "
                    + "WHERE borrow_id = ?";

            psReturn = con.prepareStatement(returnSql);

            psReturn.setInt(1, borrowId);

            psReturn.executeUpdate();

            // Increase book quantity
            String updateSql =
                    "UPDATE book "
                    + "SET quantity = quantity + 1, "
                    + "status = 'Available' "
                    + "WHERE book_id = ?";

            psUpdate = con.prepareStatement(updateSql);

            psUpdate.setInt(1, bookId);

            psUpdate.executeUpdate();

            response.getWriter().println(
                    "Book returned successfully."
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "Error: " + e.getMessage()
            );

        } finally {

            try {

                if (rs != null) rs.close();

                if (psCheck != null) psCheck.close();
                if (psReturn != null) psReturn.close();
                if (psUpdate != null) psUpdate.close();

                if (con != null) con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}