<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete a Record</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            text-align: center;
        }

        #form-container {
            background-color: #fff;
            max-width: 400px;
            margin: 20px auto;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 8px;
            text-align: left;
        }

        input {
            width: 100%;
            padding: 8px;
            margin-bottom: 16px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #333;
            color: #fff;
            border: none;
            padding: 10px;
            border-radius: 5px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #555;
        }
    </style>
</head>
<body>
    <div id="form-container">
        <h2>Delete a Record</h2>
        <form action="DeleteRecordServlet" method="post">
            <label for="studentId">Enter Student ID to Delete:</label>
            <input type="text" id="studentId" name="studentId" required>

            <input type="submit" value="Delete Record">
        </form>
    </div>
</body>
</html>
