<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Students</title>
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
        <h2>Add Students</h2>
        <form action="AddStudentServlet" method="post">
            <% 
                int numStudents = (int) request.getAttribute("numStudents");
                for (int i = 1; i <= numStudents; i++) {
            %>
            <div>
                <h3>Student <%= i %></h3>
                <label for="studentId_<%= i %>">Student ID:</label>
                <input type="text" id="studentId_<%= i %>" name="studentId_<%= i %>" required>

                <label for="firstName_<%= i %>">First Name:</label>
                <input type="text" id="firstName_<%= i %>" name="firstName_<%= i %>" required>

                <label for="lastName_<%= i %>">Last Name:</label>
                <input type="text" id="lastName_<%= i %>" name="lastName_<%= i %>" required>

                
                <label for="gender_<%= i %>">Gender:</label>
                <select id="gender_<%= i %>" name="gender_<%= i %>" required>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                </select>

                <label for="gpa_<%= i %>">GPA:</label>
                <input type="number" step="0.01" id="gpa_<%= i %>" name="gpa_<%= i %>" required>

                <label for="level_<%= i %>">Level:</label>
                <input type="text" id="level_<%= i %>" name="level_<%= i %>" required>

                <label for="address_<%= i %>">Address:</label>
                <input type="text" id="address_<%= i %>" name="address_<%= i %>" required>

            </div>
            <% } %>

            <input type="hidden" name="numStudents" value="<%= numStudents %>">
            <input type="submit" value="Add Students">
        </form>
    </div>
</body>
</html>
