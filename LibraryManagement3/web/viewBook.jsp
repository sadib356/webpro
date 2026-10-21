<%@page import="java.sql.*"%>
<%@page import="com.library.DBConnection"%>

<!DOCTYPE html>
<html>
<head>
    <title>View Books</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #667eea, #764ba2);
        }

        .container {
            width: 90%;
            margin: 40px auto;
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
        }

        h1 {
            text-align: center;
            color: #764ba2;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 25px;
        }

        th {
            background: #764ba2;
            color: white;
            padding: 14px;
        }

        td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background: #f2f2f2;
        }

        .delete {
            background: #e74c3c;
            color: white;
            padding: 8px 15px;
            text-decoration: none;
            border-radius: 5px;
        }

        .delete:hover {
            background: #c0392b;
        }

        .back {
            display: inline-block;
            margin-top: 25px;
            background: #667eea;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Library Books</h1>

    <table>

        <tr>
            <th>Book ID</th>
            <th>Title</th>
            <th>Author</th>
            <th>Quantity</th>
            <th>Status</th>
            <th>Action</th>
        </tr>

        <%
            Connection con = null;
            Statement st = null;
            ResultSet rs = null;

            try {

                con = DBConnection.getConnection();

                st = con.createStatement();

                rs = st.executeQuery(
                    "SELECT * FROM book ORDER BY book_id"
                );

                while (rs.next()) {
        %>

        <tr>

            <td><%= rs.getInt("book_id") %></td>

            <td><%= rs.getString("title") %></td>

            <td><%= rs.getString("author") %></td>

            <td><%= rs.getInt("quantity") %></td>

            <td><%= rs.getString("status") %></td>

            <td>
                <a class="delete"
                   href="DeleteBookServlet?book_id=<%= rs.getInt("book_id") %>">
                    Delete
                </a>
            </td>

        </tr>

        <%
                }

            } catch (Exception e) {

                out.println(
                    "<p style='color:red;'>Error: "
                    + e.getMessage()
                    + "</p>"
                );

            } finally {

                try {
                    if (rs != null) rs.close();
                    if (st != null) st.close();
                    if (con != null) con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        %>

    </table>

    <a class="back" href="index.html">Back to Home</a>

</div>

</body>
</html>