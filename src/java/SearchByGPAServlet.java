import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@WebServlet("/searchByGPA")
public class SearchByGPAServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchGPA = request.getParameter("gpa");
        List<String> searchResults = searchStudentByGPA(searchGPA);
        request.setAttribute("searchResults", searchResults);
        request.getRequestDispatcher("/searchByGPAResult.jsp").forward(request, response);
    }

    private List<String> searchStudentByGPA(String gpa) {
        List<String> searchResults = new ArrayList<>();

        try {
            // Load the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(getServletContext().getRealPath("/WEB-INF/university.xml"));

            // Get all Student elements
            NodeList studentNodes = document.getElementsByTagName("Student");

            // Iterate through each Student element
            for (int i = 0; i < studentNodes.getLength(); i++) {
                Node studentNode = studentNodes.item(i);

                if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element studentElement = (Element) studentNode;

                    // Check if the GPA matches
                    String studentGPA = getElementTextContent(studentElement, "GPA");

                    if (studentGPA.equals(gpa)) {
                        // Build a result string for the matching student
                        String result = "Student ID: " + studentElement.getAttribute("ID") +
                                        ", Name: " + getElementTextContent(studentElement, "FirstName") + " " + getElementTextContent(studentElement, "LastName") +
                                        ", Gender: " + getElementTextContent(studentElement, "Gender") +
                                        ", GPA: " + studentGPA +
                                        ", Level: " + getElementTextContent(studentElement, "Level") +
                                        ", Address: " + getElementTextContent(studentElement, "Address");

                        searchResults.add(result);
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

    private String getElementTextContent(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        } else {
            return "";
        }
    }
}
