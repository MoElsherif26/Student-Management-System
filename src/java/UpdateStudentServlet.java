import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@WebServlet("/updateStudent")
public class UpdateStudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String studentIdToUpdate = request.getParameter("studentId");
        String updatedFirstName = request.getParameter("firstName");
        String updatedLastName = request.getParameter("lastName");
        String updatedGender = request.getParameter("gender");
        String updatedGPA = request.getParameter("gpa");
        String updatedLevel = request.getParameter("level");
        String updatedAddress = request.getParameter("address");

        // Update the student details in the XML file
        updateStudentDetails(studentIdToUpdate, updatedFirstName, updatedLastName, updatedGender, updatedGPA, updatedLevel, updatedAddress);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body><h2>Student details updated successfully!</h2></body></html>");
    }

    private void updateStudentDetails(String studentId, String updatedFirstName, String updatedLastName,
            String updatedGender, String updatedGPA, String updatedLevel, String updatedAddress) {
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

                    // Check if the student ID matches
                    String existingStudentId = studentElement.getAttribute("ID");

                    if (existingStudentId.equals(studentId)) {
                        // Update the student attributes
                        updateElementTextContent(studentElement, "FirstName", updatedFirstName);
                        updateElementTextContent(studentElement, "LastName", updatedLastName);
                        updateElementTextContent(studentElement, "Gender", updatedGender);
                        updateElementTextContent(studentElement, "GPA", updatedGPA);
                        updateElementTextContent(studentElement, "Level", updatedLevel);
                        updateElementTextContent(studentElement, "Address", updatedAddress);

                        // Save the updated XML file
                        saveDocument(document, getServletContext().getRealPath("/WEB-INF/university.xml"));

                        break; 
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private void updateElementTextContent(Element parentElement, String tagName, String updatedValue) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node textNode = nodeList.item(0).getFirstChild();
            if (textNode != null) {
                textNode.setNodeValue(updatedValue);
            }
        }
    }

    private void saveDocument(Document document, String filePath) {
        try {
            // Use a transformer to write the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(filePath);

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
