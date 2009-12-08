/**
 * ErrorType.java This file was auto-generated from WSDL by the Apache Axis2
 * version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
 */

package au.edu.uts.eng.remotelabs.rigclient.protocol.types;

/**
 * ErrorType bean class
 */

public class ErrorType implements org.apache.axis2.databinding.ADBBean
{
    /*
     * This type was generated from the piece of schema that had name =
     * ErrorType Namespace URI =
     * http://remotelabs.eng.uts.edu.au/rigclient/protocol Namespace Prefix =
     * ns1
     */

    private static java.lang.String generatePrefix(java.lang.String namespace)
    {
        if (namespace.equals("http://remotelabs.eng.uts.edu.au/rigclient/protocol")) { return "ns1"; }
        return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * field for Code
     */

    protected int localCode;

    /**
     * Auto generated getter method
     * 
     * @return int
     */
    public int getCode()
    {
        return localCode;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Code
     */
    public void setCode(int param)
    {

        this.localCode = param;

    }

    /**
     * field for Operation
     */

    protected java.lang.String localOperation;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getOperation()
    {
        return localOperation;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Operation
     */
    public void setOperation(java.lang.String param)
    {

        this.localOperation = param;

    }

    /**
     * field for Reason
     */

    protected java.lang.String localReason;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getReason()
    {
        return localReason;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Reason
     */
    public void setReason(java.lang.String param)
    {

        this.localReason = param;

    }

    /**
     * field for Trace
     */

    protected java.lang.String localTrace;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean localTraceTracker = false;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getTrace()
    {
        return localTrace;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Trace
     */
    public void setTrace(java.lang.String param)
    {

        if (param != null)
        {
            // update the setting tracker
            localTraceTracker = true;
        }
        else
        {
            localTraceTracker = false;

        }

        this.localTrace = param;

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
     * @param parentQName
     * @param factory
     * @return org.apache.axiom.om.OMElement
     */
    public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException
    {

        org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(this, parentQName)
        {

            public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                    throws javax.xml.stream.XMLStreamException
            {
                ErrorType.this.serialize(parentQName, factory, xmlWriter);
            }
        };
        return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(parentQName, factory, dataSource);

    }

    public void serialize(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory,
            org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException
    {
        serialize(parentQName, factory, xmlWriter, false);
    }

    public void serialize(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory,
            org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException
    {

        java.lang.String prefix = null;
        java.lang.String namespace = null;

        prefix = parentQName.getPrefix();
        namespace = parentQName.getNamespaceURI();

        if ((namespace != null) && (namespace.trim().length() > 0))
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
                    prefix = generatePrefix(namespace);
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

        if (serializeType)
        {

            java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                    "http://remotelabs.eng.uts.edu.au/rigclient/protocol");
            if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0))
            {
                writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix
                        + ":ErrorType", xmlWriter);
            }
            else
            {
                writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "ErrorType", xmlWriter);
            }

        }

        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "code", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }
            else
            {
                xmlWriter.writeStartElement(namespace, "code");
            }

        }
        else
        {
            xmlWriter.writeStartElement("code");
        }

        if (localCode == java.lang.Integer.MIN_VALUE)
        {

            throw new org.apache.axis2.databinding.ADBException("code cannot be null!!");

        }
        else
        {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCode));
        }

        xmlWriter.writeEndElement();

        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "operation", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }
            else
            {
                xmlWriter.writeStartElement(namespace, "operation");
            }

        }
        else
        {
            xmlWriter.writeStartElement("operation");
        }

        if (localOperation == null)
        {
            // write the nil attribute

            throw new org.apache.axis2.databinding.ADBException("operation cannot be null!!");

        }
        else
        {

            xmlWriter.writeCharacters(localOperation);

        }

        xmlWriter.writeEndElement();

        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "reason", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }
            else
            {
                xmlWriter.writeStartElement(namespace, "reason");
            }

        }
        else
        {
            xmlWriter.writeStartElement("reason");
        }

        if (localReason == null)
        {
            // write the nil attribute

            throw new org.apache.axis2.databinding.ADBException("reason cannot be null!!");

        }
        else
        {

            xmlWriter.writeCharacters(localReason);

        }

        xmlWriter.writeEndElement();
        if (localTraceTracker)
        {
            namespace = "";
            if (!namespace.equals(""))
            {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null)
                {
                    prefix = generatePrefix(namespace);

                    xmlWriter.writeStartElement(prefix, "trace", namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);

                }
                else
                {
                    xmlWriter.writeStartElement(namespace, "trace");
                }

            }
            else
            {
                xmlWriter.writeStartElement("trace");
            }

            if (localTrace == null)
            {
                // write the nil attribute

                throw new org.apache.axis2.databinding.ADBException("trace cannot be null!!");

            }
            else
            {

                xmlWriter.writeCharacters(localTrace);

            }

            xmlWriter.writeEndElement();
        }
        xmlWriter.writeEndElement();

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

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeAttribute(java.lang.String namespace, java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException
    {
        if (namespace.equals(""))
        {
            xmlWriter.writeAttribute(attName, attValue);
        }
        else
        {
            registerPrefix(xmlWriter, namespace);
            xmlWriter.writeAttribute(namespace, attName, attValue);
        }
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
            javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException
    {

        java.lang.String attributeNamespace = qname.getNamespaceURI();
        java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
        if (attributePrefix == null)
        {
            attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
        }
        java.lang.String attributeValue;
        if (attributePrefix.trim().length() > 0)
        {
            attributeValue = attributePrefix + ":" + qname.getLocalPart();
        }
        else
        {
            attributeValue = qname.getLocalPart();
        }

        if (namespace.equals(""))
        {
            xmlWriter.writeAttribute(attName, attributeValue);
        }
        else
        {
            registerPrefix(xmlWriter, namespace);
            xmlWriter.writeAttribute(namespace, attName, attributeValue);
        }
    }

    /**
     * method to handle Qnames
     */

    private void writeQName(javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException
    {
        java.lang.String namespaceURI = qname.getNamespaceURI();
        if (namespaceURI != null)
        {
            java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
            if (prefix == null)
            {
                prefix = generatePrefix(namespaceURI);
                xmlWriter.writeNamespace(prefix, namespaceURI);
                xmlWriter.setPrefix(prefix, namespaceURI);
            }

            if (prefix.trim().length() > 0)
            {
                xmlWriter.writeCharacters(prefix + ":"
                        + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
            else
            {
                // i.e this is the default namespace
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }

        }
        else
        {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
        }
    }

    private void writeQNames(javax.xml.namespace.QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException
    {

        if (qnames != null)
        {
            // we have to store this data until last moment since it is not
            // possible to write any
            // namespace data after writing the charactor data
            java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
            java.lang.String namespaceURI = null;
            java.lang.String prefix = null;

            for (int i = 0; i < qnames.length; i++)
            {
                if (i > 0)
                {
                    stringToWrite.append(" ");
                }
                namespaceURI = qnames[i].getNamespaceURI();
                if (namespaceURI != null)
                {
                    prefix = xmlWriter.getPrefix(namespaceURI);
                    if ((prefix == null) || (prefix.length() == 0))
                    {
                        prefix = generatePrefix(namespaceURI);
                        xmlWriter.writeNamespace(prefix, namespaceURI);
                        xmlWriter.setPrefix(prefix, namespaceURI);
                    }

                    if (prefix.trim().length() > 0)
                    {
                        stringToWrite.append(prefix).append(":").append(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                    else
                    {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil
                                .convertToString(qnames[i]));
                    }
                }
                else
                {
                    stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                }
            }
            xmlWriter.writeCharacters(stringToWrite.toString());
        }

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
            prefix = generatePrefix(namespace);

            while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null)
            {
                prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
            }

            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }

        return prefix;
    }

    /**
     * databinding method to get an XML representation of this object
     */
    public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
            throws org.apache.axis2.databinding.ADBException
    {

        java.util.ArrayList elementList = new java.util.ArrayList();
        java.util.ArrayList attribList = new java.util.ArrayList();

        elementList.add(new javax.xml.namespace.QName("", "code"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCode));

        elementList.add(new javax.xml.namespace.QName("", "operation"));

        if (localOperation != null)
        {
            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOperation));
        }
        else
        {
            throw new org.apache.axis2.databinding.ADBException("operation cannot be null!!");
        }

        elementList.add(new javax.xml.namespace.QName("", "reason"));

        if (localReason != null)
        {
            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReason));
        }
        else
        {
            throw new org.apache.axis2.databinding.ADBException("reason cannot be null!!");
        }
        if (localTraceTracker)
        {
            elementList.add(new javax.xml.namespace.QName("", "trace"));

            if (localTrace != null)
            {
                elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTrace));
            }
            else
            {
                throw new org.apache.axis2.databinding.ADBException("trace cannot be null!!");
            }
        }

        return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(),
                attribList.toArray());

    }

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
        public static ErrorType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception
        {
            ErrorType object = new ErrorType();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix = "";
            java.lang.String namespaceuri = "";
            try
            {

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

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

                        if (!"ErrorType".equals(type))
                        {
                            // find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                            return (ErrorType) au.edu.uts.eng.remotelabs.rigclient.protocol.types.ExtensionMapper
                                    .getTypeObject(nsUri, type, reader);
                        }

                    }

                }

                // Note all attributes that were handled. Used to differ normal
                // attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                reader.next();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "code").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));

                    reader.next();

                } // End of if for expected property start element

                else
                {
                    // A start element we are not expecting indicates an invalid
                    // parameter was passed
                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement "
                            + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "operation").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setOperation(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                    reader.next();

                } // End of if for expected property start element

                else
                {
                    // A start element we are not expecting indicates an invalid
                    // parameter was passed
                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement "
                            + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "reason").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setReason(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                    reader.next();

                } // End of if for expected property start element

                else
                {
                    // A start element we are not expecting indicates an invalid
                    // parameter was passed
                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement "
                            + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "trace").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setTrace(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                    reader.next();

                } // End of if for expected property start element

                else
                {

                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

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

}
