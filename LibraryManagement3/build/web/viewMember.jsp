<%@page import="java.sql.*"%>
<%@page import="com.library.DBConnection"%>

<!DOCTYPE html>
<html>
<head>
    <title>View Members</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #ff9966, #ff5e62);
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
            color: #ff5e62;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 25px;
        }

        th {
            background: #ff5e62;
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

        .remove {
            background: #e74c3c;
            color: white;
            padding: 8px 15px;
            text-decoration: none;
            border-radius: 5px;
        }

        .remove:hover {
            background: #c0392b;
        }

        .back {
            display: inline-block;
            margin-top: 25px;
            background: #ff5e62;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Library Members</h1>

    <table>

        <tr>
            <th>Member ID</th>
            <th>Member Name</th>
            <th>Email</th>
            <th>Phone</th>
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
                    "SELECT * FROM member ORDER BY member_id"
                );

                while (rs.next()) {
        %>

        <tr>

            <td><%= rs.getInt("member_id") %></td>

            <td><%= rs.getString("member_name") %></td>

            <td><%= rs.getString("email") %></td>

            <td><%= rs.getString("phone") %></td>

            <td>
                <a class="remove"
                   href="RemoveMemberServlet?member_id=<%= rs.getInt("member_id") %>">
                    Remove
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