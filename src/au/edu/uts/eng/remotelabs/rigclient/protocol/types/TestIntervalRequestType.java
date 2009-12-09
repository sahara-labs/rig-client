/**
 * TestIntervalRequestType.java This file was auto-generated from WSDL by the
 * Apache Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
 */

package au.edu.uts.eng.remotelabs.rigclient.protocol.types;

/**
 * TestIntervalRequestType bean class
 */

public class TestIntervalRequestType extends au.edu.uts.eng.remotelabs.rigclient.protocol.types.AuthRequiredRequestType
        implements org.apache.axis2.databinding.ADBBean
{
    /*
     * This type was generated from the piece of schema that had name =
     * TestIntervalRequestType Namespace URI =
     * http://remotelabs.eng.uts.edu.au/rigclient/protocol Namespace Prefix =
     * ns1
     */

    /**
     * Factory class that keeps the parse method
     */
    public static class Factory
    {

        /**
         * static method to create the object Precondition: If this object is an
         * element, the current or next start element starts this object and any
         * intervening reader events are ignorable If this object is not an
         * element, it is a complex type and the reader is at the event just
         * after the outer start element Postcondition: If this object is an
         * element, the reader is positioned at its end element If this object
         * is a complex type, the reader is positioned at the end element of its
         * outer element
         */
        public static TestIntervalRequestType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception
        {
            TestIntervalRequestType object = new TestIntervalRequestType();

            try
            {

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null)
                {
                    java.lang.String fullTypeName = reader.getAttributeValue(
                            "http://www.w3.org/2001/XMLSchema-instance", "type");
                    if (fullTypeName != null)
                    {
                        java.lang.String nsPrefix = null;
                        if (fullTypeName.indexOf(":") > -1)
                        {
                            nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
                        }
                        nsPrefix = nsPrefix == null ? "" : nsPrefix;

                        java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);

                        if (!"TestIntervalRequestType".equals(type))
                        {
                            // find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                            return (TestIntervalRequestType) au.edu.uts.eng.remotelabs.rigclient.protocol.types.ExtensionMapper
                                    .getTypeObject(nsUri, type, reader);
                        }

                    }

                }

                new java.util.Vector();

                reader.next();

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement()
                        && new javax.xml.namespace.QName("", "identityToken").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setIdentityToken(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                    reader.next();

                } // End of if for expected property start element

                else
                {

                }

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "requestor").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setRequestor(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                    reader.next();

                } // End of if for expected property start element

                else
                {

                }

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "interval").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setInterval(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));

                    reader.next();

                } // End of if for expected property start element
                else
                    // A start element we are not expecting indicates an invalid
                    // parameter was passed
                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement "
                            + reader.getLocalName());

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement())
                // A start element we are not expecting indicates a trailing
                    // invalid property
                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement "
                            + reader.getLocalName());

            }
            catch (javax.xml.stream.XMLStreamException e)
            {
                throw new java.lang.Exception(e);
            }

            return object;
        }

    }// end of factory class

    /**
     * 
     */
    private static final long serialVersionUID = 6748991599014263029L;

    private static java.lang.String generatePrefix(java.lang.String namespace)
    {
        if (namespace.equals("http://remotelabs.eng.uts.edu.au/rigclient/protocol")) return "ns1";
        return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * isReaderMTOMAware
     * 
     * @return true if the reader supports MTOM
     */
    public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader)
    {
        boolean isReaderMTOMAware = false;

        try
        {
            isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader
                    .getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }
        catch (java.lang.IllegalArgumentException e)
        {
            isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
    }

    /**
     * field for Interval
     */

    protected int localInterval;

    /**
     * Auto generated getter method
     * 
     * @return int
     */
    public int getInterval()
    {
        return this.localInterval;
    }

    /**
     * @param parentQName
     * @param factory
     * @return org.apache.axiom.om.OMElement
     */
    @Override
    public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException
    {

        org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(this, parentQName)
        {

            @Override
            public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                    throws javax.xml.stream.XMLStreamException
            {
                TestIntervalRequestType.this.serialize(this.parentQName, factory, xmlWriter);
            }
        };
        return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(parentQName, factory, dataSource);

    }

    /**
     * databinding method to get an XML representation of this object
     */
    @Override
    public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
            throws org.apache.axis2.databinding.ADBException
    {

        java.util.ArrayList elementList = new java.util.ArrayList();
        java.util.ArrayList attribList = new java.util.ArrayList();

        attribList.add(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema-instance", "type"));
        attribList.add(new javax.xml.namespace.QName("http://remotelabs.eng.uts.edu.au/rigclient/protocol",
                "TestIntervalRequestType"));
        if (this.identityTokenTracker)
        {
            elementList.add(new javax.xml.namespace.QName("", "identityToken"));

            if (this.identityToken != null)
            {
                elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
                        .convertToString(this.identityToken));
            }
            else
                throw new org.apache.axis2.databinding.ADBException("identityToken cannot be null!!");
        }
        if (this.requestorTracker)
        {
            elementList.add(new javax.xml.namespace.QName("", "requestor"));

            if (this.requestor != null)
            {
                elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(this.requestor));
            }
            else
                throw new org.apache.axis2.databinding.ADBException("requestor cannot be null!!");
        }
        elementList.add(new javax.xml.namespace.QName("", "interval"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(this.localInterval));

        return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(),
                attribList.toArray());

    }

    /**
     * Register a namespace prefix
     */
    private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException
    {
        java.lang.String prefix = xmlWriter.getPrefix(namespace);

        if (prefix == null)
        {
            prefix = TestIntervalRequestType.generatePrefix(namespace);

            while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null)
            {
                prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
            }

            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }

        return prefix;
    }

    @Override
    public void serialize(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory,
            org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException
    {
        this.serialize(parentQName, factory, xmlWriter, false);
    }

    @Override
    public void serialize(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory,
            org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException
    {

        java.lang.String prefix = null;
        java.lang.String namespace = null;

        prefix = parentQName.getPrefix();
        namespace = parentQName.getNamespaceURI();

        if (namespace != null && namespace.trim().length() > 0)
        {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null)
            {
                xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
            }
            else
            {
                if (prefix == null)
                {
                    prefix = TestIntervalRequestType.generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        else
        {
            xmlWriter.writeStartElement(parentQName.getLocalPart());
        }

        java.lang.String namespacePrefix = this.registerPrefix(xmlWriter,
                "http://remotelabs.eng.uts.edu.au/rigclient/protocol");
        if (namespacePrefix != null && namespacePrefix.trim().length() > 0)
        {
            this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix
                    + ":TestIntervalRequestType", xmlWriter);
        }
        else
        {
            this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "TestIntervalRequestType",
                    xmlWriter);
        }

        if (this.identityTokenTracker)
        {
            namespace = "";
            if (!namespace.equals(""))
            {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null)
                {
                    prefix = TestIntervalRequestType.generatePrefix(namespace);

                    xmlWriter.writeStartElement(prefix, "identityToken", namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);

                }
                else
                {
                    xmlWriter.writeStartElement(namespace, "identityToken");
                }

            }
            else
            {
                xmlWriter.writeStartElement("identityToken");
            }

            if (this.identityToken == null)
                throw new org.apache.axis2.databinding.ADBException("identityToken cannot be null!!");
            else
            {

                xmlWriter.writeCharacters(this.identityToken);

            }

            xmlWriter.writeEndElement();
        }
        if (this.requestorTracker)
        {
            namespace = "";
            if (!namespace.equals(""))
            {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null)
                {
                    prefix = TestIntervalRequestType.generatePrefix(namespace);

                    xmlWriter.writeStartElement(prefix, "requestor", namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);

                }
                else
                {
                    xmlWriter.writeStartElement(namespace, "requestor");
                }

            }
            else
            {
                xmlWriter.writeStartElement("requestor");
            }

            if (this.requestor == null)
                throw new org.apache.axis2.databinding.ADBException("requestor cannot be null!!");
            else
            {

                xmlWriter.writeCharacters(this.requestor);

            }

            xmlWriter.writeEndElement();
        }
        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = TestIntervalRequestType.generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "interval", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }
            else
            {
                xmlWriter.writeStartElement(namespace, "interval");
            }

        }
        else
        {
            xmlWriter.writeStartElement("interval");
        }

        if (this.localInterval == java.lang.Integer.MIN_VALUE)
            throw new org.apache.axis2.databinding.ADBException("interval cannot be null!!");
        else
        {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
                    .convertToString(this.localInterval));
        }

        xmlWriter.writeEndElement();

        xmlWriter.writeEndElement();

    }

    /**
     * Auto generated setter method
     * 
     * @param param Interval
     */
    public void setInterval(int param)
    {

        this.localInterval = param;

    }

    /**
     * Util method to write an attribute with the ns prefix
     */
    private void writeAttribute(java.lang.String prefix, java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException
    {
        if (xmlWriter.getPrefix(namespace) == null)
        {
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);

        }

        xmlWriter.writeAttribute(namespace, attName, attValue);

    }

}
