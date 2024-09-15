<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Results</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            text-align: center;
        }

        h2 {
            color: #333;
        }

        p {
            margin: 10px 0;
            padding: 10px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        p.no-results {
            color: #888;
        }
    </style>
</head>
<body>
    <h2>Search Results</h2>
    
    <%@ page import="java.util.List" %>
    <%
        List<String> searchResults = (List<String>) request.getAttribute("searchResults");
        
        if (searchResults != null && !searchResults.isEmpty()) {
            for (String result : searchResults) {
                out.println("<p>" + result + "</p>");
            }
        } else {
            out.println("<p class=\"no-results\">No results found.</p>");
        }
    %>
</body>
</html>
