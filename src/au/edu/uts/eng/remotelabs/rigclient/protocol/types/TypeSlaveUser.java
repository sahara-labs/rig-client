/**
 * TypeSlaveUser.java This file was auto-generated from WSDL by the Apache Axis2
 * version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
 */

package au.edu.uts.eng.remotelabs.rigclient.protocol.types;

/**
 * TypeSlaveUser bean class
 */

public class TypeSlaveUser implements org.apache.axis2.databinding.ADBBean
{

    /**
     * Factory class that keeps the parse method
     */
    public static class Factory
    {

        public static TypeSlaveUser fromString(java.lang.String value, java.lang.String namespaceURI)
                throws java.lang.IllegalArgumentException
        {
            try
            {

                return Factory.fromValue(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(value));

            }
            catch (java.lang.Exception e)
            {
                throw new java.lang.IllegalArgumentException();
            }
        }

        public static TypeSlaveUser fromString(javax.xml.stream.XMLStreamReader xmlStreamReader, java.lang.String content)
        {
            if (content.indexOf(":") > -1)
            {
                java.lang.String prefix = content.substring(0, content.indexOf(":"));
                java.lang.String namespaceUri = xmlStreamReader.getNamespaceContext().getNamespaceURI(prefix);
                return TypeSlaveUser.Factory.fromString(content, namespaceUri);
            }
            else
                return TypeSlaveUser.Factory.fromString(content, "");
        }

        public static TypeSlaveUser fromValue(java.lang.String value) throws java.lang.IllegalArgumentException
        {
            TypeSlaveUser enumeration = (TypeSlaveUser)

            TypeSlaveUser._table_.get(value);

            if (enumeration == null) throw new java.lang.IllegalArgumentException();
            return enumeration;
        }

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
        public static TypeSlaveUser parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception
        {
            TypeSlaveUser object = null;
            new java.util.HashMap();
            new java.util.ArrayList();

            java.lang.String prefix = "";
            java.lang.String namespaceuri = "";
            try
            {

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                new java.util.Vector();

                while (!reader.isEndElement())
                {
                    if (reader.isStartElement() || reader.hasText())
                    {

                        java.lang.String content = reader.getElementText();

                        if (content.indexOf(":") > 0)
                        {
                            // this seems to be a Qname so find the namespace
                            // and send
                            prefix = content.substring(0, content.indexOf(":"));
                            namespaceuri = reader.getNamespaceURI(prefix);
                            object = TypeSlaveUser.Factory.fromString(content, namespaceuri);
                        }
                        else
                        {
                            // this seems to be not a qname send and empty
                            // namespace incase of it is
                            // check is done in fromString method
                            object = TypeSlaveUser.Factory.fromString(content, "");
                        }

                    }
                    else
                    {
                        reader.next();
                    }
                } // end of while loop

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
    private static final long serialVersionUID = -3708607818704145877L;

    public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
            "http://remotelabs.eng.uts.edu.au/rigclient/protocol", "type_type1", "ns2");

    private static java.lang.String generatePrefix(java.lang.String namespace)
    {
        if (namespace.equals("http://remotelabs.eng.uts.edu.au/rigclient/protocol")) return "ns2";
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

    // Constructor

    /**
     * field for Type_type0
     */

    protected java.lang.String localType_type0;

    private static java.util.HashMap _table_ = new java.util.HashMap();

    public static final java.lang.String _Active = org.apache.axis2.databinding.utils.ConverterUtil
            .convertToString("Active");

    public static final java.lang.String _Passive = org.apache.axis2.databinding.utils.ConverterUtil
            .convertToString("Passive");

    public static final TypeSlaveUser Active = new TypeSlaveUser(TypeSlaveUser._Active, true);

    public static final TypeSlaveUser Passive = new TypeSlaveUser(TypeSlaveUser._Passive, true);

    protected TypeSlaveUser(java.lang.String value, boolean isRegisterValue)
    {
        this.localType_type0 = value;
        if (isRegisterValue)
        {

            TypeSlaveUser._table_.put(this.localType_type0, this);

        }

    }
    
    public TypeSlaveUser(String value)
    {
        this(value, false);
    }
    

    @Override
    public boolean equals(java.lang.Object obj)
    {
        return obj == this;
    }

    /**
     * @param parentQName
     * @param factory
     * @return org.apache.axiom.om.OMElement
     */
    public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException
    {

        org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(this,
                TypeSlaveUser.MY_QNAME)
        {

            @Override
            public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                    throws javax.xml.stream.XMLStreamException
            {
                TypeSlaveUser.this.serialize(TypeSlaveUser.MY_QNAME, factory, xmlWriter);
            }
        };
        return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(TypeSlaveUser.MY_QNAME, factory, dataSource);

    }

    /**
     * databinding method to get an XML representation of this object
     */
    public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
            throws org.apache.axis2.databinding.ADBException
    {

        // We can safely assume an element has only one type associated with it
        return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(TypeSlaveUser.MY_QNAME,
                new java.lang.Object[] { org.apache.axis2.databinding.utils.reader.ADBXMLStreamReader.ELEMENT_TEXT,
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(this.localType_type0) }, null);

    }

    public java.lang.String getValue()
    {
        return this.localType_type0;
    }

    @Override
    public int hashCode()
    {
        return this.toString().hashCode();
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
            prefix = TypeSlaveUser.generatePrefix(namespace);

            while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null)
            {
                prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
            }

            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }

        return prefix;
    }

    public void serialize(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory,
            org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException
    {
        this.serialize(parentQName, factory, xmlWriter, false);
    }

    public void serialize(final javax.xml.namespace.QName parentQName, final org.apache.axiom.om.OMFactory factory,
            org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException
    {

        // We can safely assume an element has only one type associated with it

        java.lang.String namespace = parentQName.getNamespaceURI();
        java.lang.String localName = parentQName.getLocalPart();

        if (!namespace.equals(""))
        {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = TypeSlaveUser.generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, localName, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }
            else
            {
                xmlWriter.writeStartElement(namespace, localName);
            }

        }
        else
        {
            xmlWriter.writeStartElement(localName);
        }

        // add the type details if this is used in a simple type
        if (serializeType)
        {
            java.lang.String namespacePrefix = this.registerPrefix(xmlWriter,
                    "http://remotelabs.eng.uts.edu.au/rigclient/protocol");
            if (namespacePrefix != null && namespacePrefix.trim().length() > 0)
            {
                this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix
                        + ":type_type1", xmlWriter);
            }
            else
            {
                this
                        .writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "type_type1",
                                xmlWriter);
            }
        }

        if (this.localType_type0 == null)
            throw new org.apache.axis2.databinding.ADBException("Value cannot be null !!");
        else
        {

            xmlWriter.writeCharacters(this.localType_type0);

        }

        xmlWriter.writeEndElement();

    }

    @Override
    public java.lang.String toString()
    {

        return this.localType_type0.toString();

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
