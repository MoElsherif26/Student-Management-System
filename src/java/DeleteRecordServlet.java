import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@WebServlet("/deleteRecord")
public class DeleteRecordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String studentIdToDelete = request.getParameter("studentId");

        deleteStudentRecordById(studentIdToDelete);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body><h2>Student deleted successfully!</h2></body></html>");
    }

    private void deleteStudentRecordById(String studentId) {
        try {
            // Load the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(getServletContext().getRealPath("/WEB-INF/university.xml"));

            // Get all Student elements
            NodeList studentNodes = document.getElementsByTagName("Student");

            for (int i = 0; i < studentNodes.getLength(); i++) {
                Node studentNode = studentNodes.item(i);

                if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element studentElement = (Element) studentNode;

                    // Check if the student ID matches
                    String existingStudentId = studentElement.getAttribute("ID");

                    if (existingStudentId.equals(studentId)) {
                        // Remove the matching student element
                        studentNode.getParentNode().removeChild(studentNode);
                        break; 
                    }
                }
            }

            saveDocument(document, getServletContext().getRealPath("/WEB-INF/university.xml"));

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDocument(Document document, String filePath) {
        try {
            // Use a transformer to write the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(filePath);

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
