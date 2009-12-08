/**
 * AttributeResponseTypeChoice.java This file was auto-generated from WSDL by
 * the Apache Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
 */

package au.edu.uts.eng.remotelabs.rigclient.protocol.types;

/**
 * AttributeResponseTypeChoice bean class
 */

public class AttributeResponseTypeChoice implements org.apache.axis2.databinding.ADBBean
{
    /*
     * This type was generated from the piece of schema that had name =
     * AttributeResponseTypeChoice Namespace URI =
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
        public static AttributeResponseTypeChoice parse(javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception
        {
            AttributeResponseTypeChoice object = new AttributeResponseTypeChoice();

            try
            {

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                new java.util.Vector();

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "value").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setValue(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                    reader.next();

                } // End of if for expected property start element

                else

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "error").equals(reader.getName()))
                {

                    object.setError(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ErrorType.Factory.parse(reader));

                    reader.next();

                } // End of if for expected property start element

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
    private static final long serialVersionUID = 4138195165704992689L;

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
     * field for Value
     */

    protected java.lang.String localValue;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean localValueTracker = false;

    /**
     * field for Error
     */

    protected au.edu.uts.eng.remotelabs.rigclient.protocol.types.ErrorType localError;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean localErrorTracker = false;

    /**
     * Whenever a new property is set ensure all others are unset There can be
     * only one choice and the last one wins
     */
    private void clearAllSettingTrackers()
    {

        this.localValueTracker = false;

        this.localErrorTracker = false;

    }

    /**
     * Auto generated getter method
     * 
     * @return au.edu.uts.eng.remotelabs.rigclient.protocol.types.ErrorType
     */
    public au.edu.uts.eng.remotelabs.rigclient.protocol.types.ErrorType getError()
    {
        return this.localError;
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

            @Override
            public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                    throws javax.xml.stream.XMLStreamException
            {
                AttributeResponseTypeChoice.this.serialize(this.parentQName, factory, xmlWriter);
            }
        };
        return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(parentQName, factory, dataSource);

    }

    /**
     * databinding method to get an XML representation of this object
     */
    public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
            throws org.apache.axis2.databinding.ADBException
    {

        java.util.ArrayList elementList = new java.util.ArrayList();
        java.util.ArrayList attribList = new java.util.ArrayList();

        if (this.localValueTracker)
        {
            elementList.add(new javax.xml.namespace.QName("", "value"));

            if (this.localValue != null)
            {
                elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(this.localValue));
            }
            else
                throw new org.apache.axis2.databinding.ADBException("value cannot be null!!");
        }
        if (this.localErrorTracker)
        {
            elementList.add(new javax.xml.namespace.QName("", "error"));

            if (this.localError == null) throw new org.apache.axis2.databinding.ADBException("error cannot be null!!");
            elementList.add(this.localError);
        }

        return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(),
                attribList.toArray());

    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getValue()
    {
        return this.localValue;
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
            prefix = AttributeResponseTypeChoice.generatePrefix(namespace);

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

        java.lang.String prefix = null;
        java.lang.String namespace = null;

        if (serializeType)
        {

            java.lang.String namespacePrefix = this.registerPrefix(xmlWriter,
                    "http://remotelabs.eng.uts.edu.au/rigclient/protocol");
            if (namespacePrefix != null && namespacePrefix.trim().length() > 0)
            {
                this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix
                        + ":AttributeResponseTypeChoice", xmlWriter);
            }
            else
            {
                this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
                        "AttributeResponseTypeChoice", xmlWriter);
            }

        }
        if (this.localValueTracker)
        {
            namespace = "";
            if (!namespace.equals(""))
            {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null)
                {
                    prefix = AttributeResponseTypeChoice.generatePrefix(namespace);

                    xmlWriter.writeStartElement(prefix, "value", namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);

                }
                else
                {
                    xmlWriter.writeStartElement(namespace, "value");
                }

            }
            else
            {
                xmlWriter.writeStartElement("value");
            }

            if (this.localValue == null)
                throw new org.apache.axis2.databinding.ADBException("value cannot be null!!");
            else
            {

                xmlWriter.writeCharacters(this.localValue);

            }

            xmlWriter.writeEndElement();
        }
        if (this.localErrorTracker)
        {
            if (this.localError == null) throw new org.apache.axis2.databinding.ADBException("error cannot be null!!");
            this.localError.serialize(new javax.xml.namespace.QName("", "error"), factory, xmlWriter);
        }

    }

    /**
     * Auto generated setter method
     * 
     * @param param Error
     */
    public void setError(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ErrorType param)
    {

        this.clearAllSettingTrackers();

        if (param != null)
        {
            // update the setting tracker
            this.localErrorTracker = true;
        }
        else
        {
            this.localErrorTracker = false;

        }

        this.localError = param;

    }

    /**
     * Auto generated setter method
     * 
     * @param param Value
     */
    public void setValue(java.lang.String param)
    {

        this.clearAllSettingTrackers();

        if (param != null)
        {
            // update the setting tracker
            this.localValueTracker = true;
        }
        else
        {
            this.localValueTracker = false;

        }

        this.localValue = param;

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
