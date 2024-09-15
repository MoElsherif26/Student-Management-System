import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HandleNumberStudentsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String numStudentsParam = request.getParameter("numStudents");
        int numStudents = Integer.parseInt(numStudentsParam);
        request.setAttribute("numStudents", numStudents);
        request.getRequestDispatcher("/students.jsp").forward(request, response);
    }
}