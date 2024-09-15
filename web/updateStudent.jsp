<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Student Details</title>
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

        input, select {
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
        <h2>Update Student Details</h2>
        <form action="UpdateStudentServlet" method="post">
            <%
                String existingStudentId = (String) request.getAttribute("existingStudentId");
                String existingFirstName = (String) request.getAttribute("existingFirstName");
                String existingLastName = (String) request.getAttribute("existingLastName");
                String existingGender = (String) request.getAttribute("existingGender");
                String existingGPA = (String) request.getAttribute("existingGPA");
                String existingLevel = (String) request.getAttribute("existingLevel");
                String existingAddress = (String) request.getAttribute("existingAddress");
            %>

            <label for="studentId">Student ID:</label>
            <input type="text" id="studentId" name="studentId" value="<%= existingStudentId %>" readonly>

            <label for="firstName">Updated First Name:</label>
            <input type="text" id="firstName" name="firstName" value="<%= existingFirstName %>">

            <label for="lastName">Updated Last Name:</label>
            <input type="text" id="lastName" name="lastName" value="<%= existingLastName %>">

            <label for="gender">Updated Gender:</label>
            <select id="gender" name="gender">
                <option value="Male" <%= existingGender.equals("Male") ? "selected" : "" %>>Male</option>
                <option value="Female" <%= existingGender.equals("Female") ? "selected" : "" %>>Female</option>
            </select>

            <label for="gpa">Updated GPA:</label>
            <input type="number" step="0.01" id="gpa" name="gpa" value="<%= existingGPA %>">

            <label for="level">Updated Level:</label>
            <input type="text" id="level" name="level" value="<%= existingLevel %>">

            <label for="address">Updated Address:</label>
            <input type="text" id="address" name="address" value="<%= existingAddress %>">

            <input type="submit" value="Update Student">
        </form>
    </div>
</body>
</html>
