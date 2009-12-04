/**
 * BatchStatusResponseType.java This file was auto-generated from WSDL by the
 * Apache Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
 */

package au.edu.uts.eng.remotelabs.rigclient.protocol.types;

/**
 * BatchStatusResponseType bean class
 */

public class BatchStatusResponseType implements org.apache.axis2.databinding.ADBBean
{
    /*
     * This type was generated from the piece of schema that had name =
     * BatchStatusResponseType Namespace URI =
     * http://remotelabs.eng.uts.edu.au/rigclient/protocol Namespace Prefix =
     * ns2
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
        public static BatchStatusResponseType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception
        {
            BatchStatusResponseType object = new BatchStatusResponseType();

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

                        if (!"BatchStatusResponseType".equals(type))
                        {
                            // find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                            return (BatchStatusResponseType) au.edu.uts.eng.remotelabs.rigclient.protocol.types.ExtensionMapper
                                    .getTypeObject(nsUri, type, reader);
                        }

                    }

                }

                new java.util.Vector();

                reader.next();

                java.util.ArrayList list3 = new java.util.ArrayList();

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "state").equals(reader.getName()))
                {

                    object.setState(au.edu.uts.eng.remotelabs.rigclient.protocol.types.State_type1.Factory
                            .parse(reader));

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

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "progress").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setProgress(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

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

                if (reader.isStartElement()
                        && new javax.xml.namespace.QName("", "resultFilePath").equals(reader.getName()))
                {

                    // Process the array and step past its final element's end.
                    list3.add(reader.getElementText());

                    // loop until we find a start element that is not part of
                    // this array
                    boolean loopDone3 = false;
                    while (!loopDone3)
                    {
                        // Ensure we are at the EndElement
                        while (!reader.isEndElement())
                        {
                            reader.next();
                        }
                        // Step out of this element
                        reader.next();
                        // Step to next element event.
                        while (!reader.isStartElement() && !reader.isEndElement())
                        {
                            reader.next();
                        }
                        if (reader.isEndElement())
                        {
                            // two continuous end elements means we are exiting
                            // the xml structure
                            loopDone3 = true;
                        }
                        else
                        {
                            if (new javax.xml.namespace.QName("", "resultFilePath").equals(reader.getName()))
                            {
                                list3.add(reader.getElementText());

                            }
                            else
                            {
                                loopDone3 = true;
                            }
                        }
                    }
                    // call the converter utility to convert and set the array

                    object.setResultFilePath((java.lang.String[]) list3.toArray(new java.lang.String[list3.size()]));

                } // End of if for expected property start element

                else
                {

                }

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
    private static final long serialVersionUID = -3154318364201993566L;

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

    /**
     * field for State
     */

    protected au.edu.uts.eng.remotelabs.rigclient.protocol.types.State_type1 localState;

    /**
     * field for Progress
     */

    protected java.lang.String localProgress;

    /**
     * field for ResultFilePath This was an Array!
     */

    protected java.lang.String[] localResultFilePath;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean localResultFilePathTracker = false;

    /**
     * Auto generated add method for the array for convenience
     * 
     * @param param java.lang.String
     */
    public void addResultFilePath(java.lang.String param)
    {
        if (this.localResultFilePath == null)
        {
            this.localResultFilePath = new java.lang.String[] {};
        }

        // update the setting tracker
        this.localResultFilePathTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(this.localResultFilePath);
        list.add(param);
        this.localResultFilePath = (java.lang.String[]) list.toArray(new java.lang.String[list.size()]);

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
                BatchStatusResponseType.this.serialize(this.parentQName, factory, xmlWriter);
            }
        };
        return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(parentQName, factory, dataSource);

    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getProgress()
    {
        return this.localProgress;
    }

    /**
     * databinding method to get an XML representation of this object
     */
    public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
            throws org.apache.axis2.databinding.ADBException
    {

        java.util.ArrayList elementList = new java.util.ArrayList();
        java.util.ArrayList attribList = new java.util.ArrayList();

        elementList.add(new javax.xml.namespace.QName("", "state"));

        if (this.localState == null) throw new org.apache.axis2.databinding.ADBException("state cannot be null!!");
        elementList.add(this.localState);

        elementList.add(new javax.xml.namespace.QName("", "progress"));

        if (this.localProgress != null)
        {
            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(this.localProgress));
        }
        else
            throw new org.apache.axis2.databinding.ADBException("progress cannot be null!!");
        if (this.localResultFilePathTracker)
        {
            if (this.localResultFilePath != null)
            {
                for (String element : this.localResultFilePath)
                {

                    if (element != null)
                    {
                        elementList.add(new javax.xml.namespace.QName("", "resultFilePath"));
                        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(element));
                    }
                    else
                    {

                        // have to do nothing

                    }

                }
            }
            else
                throw new org.apache.axis2.databinding.ADBException("resultFilePath cannot be null!!");

        }

        return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(),
                attribList.toArray());

    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String[]
     */
    public java.lang.String[] getResultFilePath()
    {
        return this.localResultFilePath;
    }

    /**
     * Auto generated getter method
     * 
     * @return au.edu.uts.eng.remotelabs.rigclient.protocol.types.State_type1
     */
    public au.edu.uts.eng.remotelabs.rigclient.protocol.types.State_type1 getState()
    {
        return this.localState;
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
            prefix = BatchStatusResponseType.generatePrefix(namespace);

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
                    prefix = BatchStatusResponseType.generatePrefix(namespace);
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

            java.lang.String namespacePrefix = this.registerPrefix(xmlWriter,
                    "http://remotelabs.eng.uts.edu.au/rigclient/protocol");
            if (namespacePrefix != null && namespacePrefix.trim().length() > 0)
            {
                this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix
                        + ":BatchStatusResponseType", xmlWriter);
            }
            else
            {
                this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
                        "BatchStatusResponseType", xmlWriter);
            }

        }

        if (this.localState == null) throw new org.apache.axis2.databinding.ADBException("state cannot be null!!");
        this.localState.serialize(new javax.xml.namespace.QName("", "state"), factory, xmlWriter);

        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = BatchStatusResponseType.generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "progress", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }
            else
            {
                xmlWriter.writeStartElement(namespace, "progress");
            }

        }
        else
        {
            xmlWriter.writeStartElement("progress");
        }

        if (this.localProgress == null)
            throw new org.apache.axis2.databinding.ADBException("progress cannot be null!!");
        else
        {

            xmlWriter.writeCharacters(this.localProgress);

        }

        xmlWriter.writeEndElement();
        if (this.localResultFilePathTracker)
        {
            if (this.localResultFilePath != null)
            {
                namespace = "";
                boolean emptyNamespace = namespace == null || namespace.length() == 0;
                prefix = emptyNamespace ? null : xmlWriter.getPrefix(namespace);
                for (String element : this.localResultFilePath)
                {

                    if (element != null)
                    {

                        if (!emptyNamespace)
                        {
                            if (prefix == null)
                            {
                                java.lang.String prefix2 = BatchStatusResponseType.generatePrefix(namespace);

                                xmlWriter.writeStartElement(prefix2, "resultFilePath", namespace);
                                xmlWriter.writeNamespace(prefix2, namespace);
                                xmlWriter.setPrefix(prefix2, namespace);

                            }
                            else
                            {
                                xmlWriter.writeStartElement(namespace, "resultFilePath");
                            }

                        }
                        else
                        {
                            xmlWriter.writeStartElement("resultFilePath");
                        }

                        xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
                                .convertToString(element));

                        xmlWriter.writeEndElement();

                    }
                    else
                    {

                        // we have to do nothing since minOccurs is zero

                    }

                }
            }
            else
                throw new org.apache.axis2.databinding.ADBException("resultFilePath cannot be null!!");

        }
        xmlWriter.writeEndElement();

    }

    /**
     * Auto generated setter method
     * 
     * @param param Progress
     */
    public void setProgress(java.lang.String param)
    {

        this.localProgress = param;

    }

    /**
     * Auto generated setter method
     * 
     * @param param ResultFilePath
     */
    public void setResultFilePath(java.lang.String[] param)
    {

        this.validateResultFilePath(param);

        if (param != null)
        {
            // update the setting tracker
            this.localResultFilePathTracker = true;
        }
        else
        {
            this.localResultFilePathTracker = false;

        }

        this.localResultFilePath = param;
    }

    /**
     * Auto generated setter method
     * 
     * @param param State
     */
    public void setState(au.edu.uts.eng.remotelabs.rigclient.protocol.types.State_type1 param)
    {

        this.localState = param;

    }

    /**
     * validate the array for ResultFilePath
     */
    protected void validateResultFilePath(java.lang.String[] param)
    {

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
