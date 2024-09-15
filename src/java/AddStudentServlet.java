import java.io.File;
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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@WebServlet("/addStudent")
public class AddStudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String numStudentsParam = request.getParameter("numStudents");
        int numStudents = Integer.parseInt(numStudentsParam);
        
        // Create or load the XML file
        File xmlFile = new File(getServletContext().getRealPath("/WEB-INF/university.xml"));
        Document document = getOrCreateDocument(xmlFile);
        
        
        for (int i = 1; i <= numStudents; i++) {
        // Retrieve form data for each student
        String studentId = request.getParameter("studentId_" + i);
        String firstName = request.getParameter("firstName_" + i);
        String lastName = request.getParameter("lastName_" + i);
        String gender = request.getParameter("gender_" + i);
        String gpa = request.getParameter("gpa_" + i);
        String level = request.getParameter("level_" + i);
        String address = request.getParameter("address_" + i);

        // Validate student data
        if (validateStudentData(studentId, firstName, lastName, gender, gpa, level, address, document)) {
            appendStudentData(document, studentId, firstName, lastName, gender, gpa, level, address);
        } else {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body><h2>Error: Invalid data for student " + i + ".</h2></body></html>");
            return; 
        }
    }
        
        saveDocument(document, xmlFile);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body><h2>Students added successfully!</h2></body></html>");
    }

    private Document getOrCreateDocument(File xmlFile) {
        Document document = null;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Check if the XML file already exists
            if (xmlFile.exists()) {
                document = dBuilder.parse(xmlFile);
            } else {
                // Create a new document if the file doesn't exist
                document = dBuilder.newDocument();
                Element universityElement = document.createElement("University");
                document.appendChild(universityElement);
            }
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            e.printStackTrace();
        }

        return document;
    }

    private void appendStudentData(Document document, String studentId, String firstName, String lastName,
            String gender, String gpa, String level, String address) {

        Element universityElement = document.getDocumentElement();
        Element studentElement = document.createElement("Student");
        studentElement.setAttribute("ID", studentId);

        // Create child elements for student data
        createElement(document, studentElement, "FirstName", firstName);
        createElement(document, studentElement, "LastName", lastName);
        createElement(document, studentElement, "Gender", gender);
        createElement(document, studentElement, "GPA", gpa);
        createElement(document, studentElement, "Level", level);
        createElement(document, studentElement, "Address", address);

        // Append student element to the university element
        universityElement.appendChild(studentElement);
    }

    private void createElement(Document document, Element parentElement, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.appendChild(document.createTextNode(textContent));
        parentElement.appendChild(element);
    }

    private void saveDocument(Document document, File xmlFile) {
        try {
            // Use a transformer to write the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(xmlFile);

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private boolean validateStudentData(String studentId, String firstName, String lastName,
        String gender, String gpa, String level, String address, Document document) {

    if (isEmptyOrNull(studentId) || isEmptyOrNull(firstName) || isEmptyOrNull(lastName) ||
            isEmptyOrNull(gender) || isEmptyOrNull(gpa) || isEmptyOrNull(level) || isEmptyOrNull(address)) {
        return false;
    }

    if (isStudentIdDuplicate(studentId, document)) {
        return false;
    }

    if (!isValidCharacters(firstName) || !isValidCharacters(lastName) || !isValidCharacters(address)) {
        return false;
    }

    try {
        double gpaValue = Double.parseDouble(gpa);
        if (gpaValue < 0 || gpaValue > 4) {
            return false;
        }
    } catch (NumberFormatException e) {
        return false; 
    }

    return true;
}
    
    private boolean isEmptyOrNull(String value) {
    return value == null || value.trim().isEmpty();
}

private boolean isValidCharacters(String value) {
    return value.matches("[a-zA-Z]+");
}
    
    private boolean isStudentIdDuplicate(String studentId, Document document) {
    NodeList studentNodes = document.getElementsByTagName("Student");
    for (int i = 0; i < studentNodes.getLength(); i++) {
        Node studentNode = studentNodes.item(i);
        if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
            Element studentElement = (Element) studentNode;
            String existingId = studentElement.getAttribute("ID");
            if (existingId.equals(studentId)) {
                return true; 
            }
        }
    }
    return false; 
}
    
}
