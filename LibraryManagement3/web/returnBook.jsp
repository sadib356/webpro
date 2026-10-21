<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Return Book</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #11998e, #38ef7d);
        }

        .container {
            width: 450px;
            margin: 70px auto;
            background: white;
            padding: 35px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
        }

        h1 {
            text-align: center;
            color: #11998e;
            margin-bottom: 30px;
        }

        label {
            display: block;
            margin-top: 18px;
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
            background: #11998e;
            color: white;
            border: none;
            cursor: pointer;
            font-weight: bold;
        }

        input[type="submit"]:hover {
            background: #38ef7d;
        }

        .back {
            display: block;
            text-align: center;
            margin-top: 20px;
            text-decoration: none;
            color: #11998e;
            font-weight: bold;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Return Book</h1>

    <form action="ReturnBookServlet" method="post">

        <label>Book ID</label>
        <input type="number" name="book_id" required>

        <label>Member ID</label>
        <input type="number" name="member_id" required>

        <input type="submit" value="Return Book">

    </form>

    <a class="back" href="index.html">Back to Home</a>

</div>

</body>
</html>