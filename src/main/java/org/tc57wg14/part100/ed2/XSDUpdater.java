package org.tc57wg14.part100.ed2;

import java.io.IOException;
import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Converts a XSD profile file to its equivalent and which is suitable for use
 * with the 2nd edition of IEC 61968-100.
 */
public class XSDUpdater
{
    public static void main(String[] argv)
    {
        final Namespace ns = Namespace.getNamespace("xs", "http://www.w3.org/2001/XMLSchema");
        final String part100namespaceName = "http://iec.ch/TC57/2021/schema/message";

        // Read input XML document
        Document document = null;
        try {
            document = new SAXBuilder().build(System.in);
        } catch (JDOMException | IOException ex) {
            System.err.println("Exception: " + ex.getMessage());
            return;
        }
        // Munge the document
        Element root = document.detachRootElement();
        List<Element> children = root.getChildren();
        Element child0 = children.get(0);
        assert child0.getName().equals("annotation");
        assert child0.getNamespace().equals(ns);
        Element child1 = children.get(1);
        assert child1.getName().equals("element");
        assert child1.getNamespace().equals(ns);
        Element child2 = children.get(2);
        assert child2.getName().equals("complexType");
        assert child2.getNamespace().equals(ns);
        Element child20 = child2.getChildren().get(0);
        assert child20.getName().equals("sequence");
        assert child20.getNamespace().equals(ns);
        root.addContent(2, child1.detach().setAttribute("substitutionGroup", "part100:AbstractPayload"));
        root.addContent(4,
            child2.detach().setContent(0, new Element("complexContent", ns)
                .setContent(new Element("extension", ns)
                    .setAttribute("base", "part100:AbstractPayloadType").setContent(child20.detach()))));
        root.addContent(2,
            new Element("import", ns)
                .setAttribute(new Attribute("namespace", part100namespaceName))
                .setAttribute(new Attribute("schemaLocation", "AbstractPayloadType.xsd")));
        root.removeNamespaceDeclaration(Namespace.getNamespace("http://langdale.com.au/2005/Message#"));
        root.removeNamespaceDeclaration(Namespace.getNamespace("a", "http://langdale.com.au/2005/Message#"));
        root.addNamespaceDeclaration(Namespace.getNamespace("part100", part100namespaceName));
        // Write output XML document
        document.setContent(root);
        try {
            XMLOutputter outputter = new XMLOutputter();
            outputter.setFormat(Format.getPrettyFormat());
            outputter.output(document, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
