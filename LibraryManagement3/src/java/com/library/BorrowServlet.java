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

@WebServlet("/BorrowServlet")
public class BorrowServlet extends HttpServlet {

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
        PreparedStatement psBook = null;
        PreparedStatement psMember = null;
        PreparedStatement psBorrow = null;
        PreparedStatement psUpdate = null;
        ResultSet rsBook = null;
        ResultSet rsMember = null;

        try {

            con = DBConnection.getConnection();

            // Check Book
            String bookSql =
                    "SELECT quantity FROM book WHERE book_id = ?";

            psBook = con.prepareStatement(bookSql);
            psBook.setInt(1, bookId);

            rsBook = psBook.executeQuery();

            if (!rsBook.next()) {
                response.getWriter().println("Book not found.");
                return;
            }

            int quantity = rsBook.getInt("quantity");

            if (quantity <= 0) {
                response.getWriter().println(
                        "Book is not available."
                );
                return;
            }

            // Check Member
            String memberSql =
                    "SELECT member_id FROM member WHERE member_id = ?";

            psMember = con.prepareStatement(memberSql);
            psMember.setInt(1, memberId);

            rsMember = psMember.executeQuery();

            if (!rsMember.next()) {
                response.getWriter().println(
                        "Member not found."
                );
                return;
            }

            // Insert Borrow Record
            String borrowSql =
                    "INSERT INTO borrow "
                    + "(borrow_id, book_id, member_id, borrow_date) "
                    + "VALUES (borrow_seq.NEXTVAL, ?, ?, SYSDATE)";

            psBorrow = con.prepareStatement(borrowSql);

            psBorrow.setInt(1, bookId);
            psBorrow.setInt(2, memberId);

            psBorrow.executeUpdate();

            // Reduce Book Quantity
            int newQuantity = quantity - 1;

            String updateSql;

            if (newQuantity == 0) {

                updateSql =
                        "UPDATE book SET quantity = ?, "
                        + "status = 'Unavailable' "
                        + "WHERE book_id = ?";

            } else {

                updateSql =
                        "UPDATE book SET quantity = ?, "
                        + "status = 'Available' "
                        + "WHERE book_id = ?";
            }

            psUpdate = con.prepareStatement(updateSql);

            psUpdate.setInt(1, newQuantity);
            psUpdate.setInt(2, bookId);

            psUpdate.executeUpdate();

            response.getWriter().println(
                    "Book borrowed successfully."
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "Error: " + e.getMessage()
            );

        } finally {

            try {
                if (rsBook != null) rsBook.close();
                if (rsMember != null) rsMember.close();

                if (psBook != null) psBook.close();
                if (psMember != null) psMember.close();
                if (psBorrow != null) psBorrow.close();
                if (psUpdate != null) psUpdate.close();

                if (con != null) con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}