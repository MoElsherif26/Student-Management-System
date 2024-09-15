<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sort XML</title>
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

        select, input[type="submit"] {
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
        <h2>Sort XML</h2>
        <form action="SortXMLServlet" method="post">
            <label for="sortBy">Sort By:</label>
            <select id="sortBy" name="sortBy" required>
                <option value="ID">ID</option>
                <option value="FirstName">First Name</option>
                <option value="LastName">Last Name</option>
                <option value="Gender">Gender</option>
                <option value="GPA">GPA</option>
                <option value="Level">Level</option>
                <option value="Address">Address</option>
            </select>

            <label for="sortOrder">Sort Order:</label>
            <select id="sortOrder" name="sortOrder" required>
                <option value="ascending">Ascending</option>
                <option value="descending">Descending</option>
            </select>

            <input type="submit" value="Sort XML">
        </form>
    </div>
</body>
</html>
