
package com.library;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteBookServlet")
public class DeleteBookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String bookIdParameter = request.getParameter("book_id");

        if (bookIdParameter == null || bookIdParameter.trim().isEmpty()) {
            response.getWriter().println("Book ID is required.");
            return;
        }

        int bookId;

        try {
            bookId = Integer.parseInt(bookIdParameter);
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid Book ID.");
            return;
        }

        Connection con = null;
        PreparedStatement psBorrow = null;
        PreparedStatement psBook = null;

        try {

            con = DBConnection.getConnection();

           /* if (con == null) {
                response.getWriter().println(
                        "Database connection failed."
                );
                return;
            }

            /*
             * Start transaction
             */
            con.setAutoCommit(false);


            /*
             * Step 1:
             * Delete borrow records related to this book.
             */
            String borrowSql =
                    "DELETE FROM borrow WHERE book_id = ?";

            psBorrow = con.prepareStatement(borrowSql);

            psBorrow.setInt(1, bookId);

            psBorrow.executeUpdate();


            /*
             * Step 2:
             * Delete the book.
             */
            String bookSql =
                    "DELETE FROM book WHERE book_id = ?";

            psBook = con.prepareStatement(bookSql);

            psBook.setInt(1, bookId);

            int rowsDeleted = psBook.executeUpdate();


            if (rowsDeleted == 0) {

                con.rollback();

                response.getWriter().println(
                        "Book not found."
                );

                return;
            }


            /*
             * If everything is successful,
             * save the changes.
             */
            con.commit();

            response.sendRedirect("viewBook.jsp");


        } catch (Exception e) {

            /*
             * If any error occurs,
             * undo all changes.
             */
            try {

                if (con != null) {
                    con.rollback();
                }

            } catch (Exception rollbackException) {
                rollbackException.printStackTrace();
            }

            e.printStackTrace();

            response.getWriter().println(
                    "Error: " + e.getMessage()
            );


        } finally {

            try {

                if (psBorrow != null) {
                    psBorrow.close();
                }

                if (psBook != null) {
                    psBook.close();
                }

                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }

            } catch (Exception e) {

                e.printStackTrace();

            }
        }
    }
}

