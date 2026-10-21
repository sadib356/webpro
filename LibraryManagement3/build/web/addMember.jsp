<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Add Member</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #ff9966, #ff5e62);
        }

        .container {
            width: 450px;
            margin: 60px auto;
            background: white;
            padding: 35px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
        }

        h1 {
            text-align: center;
            color: #ff5e62;
            margin-bottom: 30px;
        }

        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
            color: #333;
        }

        input {
            width: 100%;
            padding: 12px;
            margin-top: 7px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 7px;
            font-size: 15px;
        }

        input[type="submit"] {
            margin-top: 25px;
            background: #ff5e62;
            color: white;
            border: none;
            cursor: pointer;
            font-weight: bold;
        }

        input[type="submit"]:hover {
            background: #ff9966;
        }

        .back {
            display: block;
            text-align: center;
            margin-top: 20px;
            text-decoration: none;
            color: #ff5e62;
            font-weight: bold;
        }
    </style>
</head>

<body>

    <div class="container">

        <h1>Add New Member</h1>

        <form action="MemberServlet" method="post">

            <label>Member Name</label>
            <input type="text" name="member_name" required>

            <label>Email</label>
            <input type="email" name="email">

            <label>Phone</label>
            <input type="text" name="phone">

            <input type="submit" value="Add Member">

        </form>

        <a class="back" href="index.html">Back to Home</a>

    </div>

</body>
</html>