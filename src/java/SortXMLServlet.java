import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
import org.xml.sax.SAXException;

@WebServlet("/SortXMLServlet")
public class SortXMLServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder");

        // Sort the XML file based on the received input
        sortXML(sortBy, sortOrder);

        response.sendRedirect("index.html");
    }

    private void sortXML(String sortBy, String sortOrder) {
        try {
            // Load the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(getServletContext().getRealPath("/WEB-INF/university.xml"));

            // Get all Student elements
            NodeList studentNodes = document.getElementsByTagName("Student");

            // Convert NodeList to List for easy sorting
            List<Node> studentList = nodeListToList(studentNodes);

            // Define comparator based on the selected attribute
            Comparator<Node> comparator = getComparator(sortBy);

            // Sort the list
            if ("descending".equalsIgnoreCase(sortOrder)) {
                Collections.sort(studentList, Collections.reverseOrder(comparator));
            } else {
                Collections.sort(studentList, comparator);
            }

            // Remove existing Student elements from the document
            Node universityNode = document.getElementsByTagName("University").item(0);
            for (Node studentNode : studentList) {
                universityNode.removeChild(studentNode);
            }

            // Append the sorted Student elements back to the document
            for (Node studentNode : studentList) {
                universityNode.appendChild(studentNode);
            }

            // Save the updated XML file
            saveDocument(document, getServletContext().getRealPath("/WEB-INF/university.xml"));

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private List<Node> nodeListToList(NodeList nodeList) {
        List<Node> list = new java.util.ArrayList<>(nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            list.add(nodeList.item(i));
        }
        return list;
    }

    private Comparator<Node> getComparator(String sortBy) {
        Comparator<Node> comparator;
        switch (sortBy) {
            case "ID":
                comparator = Comparator.comparingInt(node -> Integer.parseInt(getAttributeValue(node, "ID")));
                break;
            case "FirstName":
                comparator = Comparator.comparing(node -> getElementTextContent((Element) node, "FirstName"));
                break;
            case "LastName":
                comparator = Comparator.comparing(node -> getElementTextContent((Element) node, "LastName"));
                break;
            case "Gender":
                comparator = Comparator.comparing(node -> getElementTextContent((Element) node, "Gender"));
                break;
            case "GPA":
                comparator = Comparator.comparingDouble(node -> Double.parseDouble(getElementTextContent((Element) node, "GPA")));
                break;
            case "Level":
                comparator = Comparator.comparing(node -> getElementTextContent((Element) node, "Level"));
                break;
            case "Address":
                comparator = Comparator.comparing(node -> getElementTextContent((Element) node, "Address"));
                break;
            default:
                throw new IllegalArgumentException("Invalid sortBy parameter: " + sortBy);
        }
        return comparator;
    }

    private String getElementTextContent(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        } else {
            return "";
        }
    }

    private String getAttributeValue(Node node, String attributeName) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            return element.getAttribute(attributeName);
        }
        return "";
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
