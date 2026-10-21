<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Add Book</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #43cea2, #185a9d);
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
            color: #185a9d;
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
            background: #185a9d;
            color: white;
            border: none;
            cursor: pointer;
            font-weight: bold;
        }

        input[type="submit"]:hover {
            background: #43cea2;
        }

        .back {
            display: block;
            text-align: center;
            margin-top: 20px;
            text-decoration: none;
            color: #185a9d;
            font-weight: bold;
        }
    </style>
</head>

<body>

    <div class="container">

        <h1>Add New Book</h1>

        <form action="BookServlet" method="post">

            <label>Book Title</label>
            <input type="text" name="title" required>

            <label>Author</label>
            <input type="text" name="author" required>

            <label>Quantity</label>
            <input type="number" name="quantity" min="1" required>

            <input type="submit" value="Add Book">

        </form>

        <a class="back" href="index.html">Back to Home</a>

    </div>

</body>
</html>