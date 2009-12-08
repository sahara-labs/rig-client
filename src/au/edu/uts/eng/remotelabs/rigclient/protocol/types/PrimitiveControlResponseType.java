/**
 * PrimitiveControlResponseType.java This file was auto-generated from WSDL by
 * the Apache Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
 */

package au.edu.uts.eng.remotelabs.rigclient.protocol.types;

/**
 * PrimitiveControlResponseType bean class
 */

public class PrimitiveControlResponseType extends
        au.edu.uts.eng.remotelabs.rigclient.protocol.types.OperationResponseType implements
        org.apache.axis2.databinding.ADBBean
{
    /*
     * This type was generated from the piece of schema that had name =
     * PrimitiveControlResponseType Namespace URI =
     * http://remotelabs.eng.uts.edu.au/rigclient/protocol Namespace Prefix =
     * ns1
     */

    private static java.lang.String generatePrefix(java.lang.String namespace)
    {
        if (namespace.equals("http://remotelabs.eng.uts.edu.au/rigclient/protocol")) { return "ns1"; }
        return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * field for WasSuccessful
     */

    protected java.lang.String localWasSuccessful;

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getWasSuccessful()
    {
        return localWasSuccessful;
    }

    /**
     * Auto generated setter method
     * 
     * @param param WasSuccessful
     */
    public void setWasSuccessful(java.lang.String param)
    {

        this.localWasSuccessful = param;

    }

    /**
     * field for Result This was an Array!
     */

    protected au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[] localResult;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean localResultTracker = false;

    /**
     * Auto generated getter method
     * 
     * @return au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[]
     */
    public au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[] getResult()
    {
        return localResult;
    }

    /**
     * validate the array for Result
     */
    protected void validateResult(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[] param)
    {

    }

    /**
     * Auto generated setter method
     * 
     * @param param Result
     */
    public void setResult(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[] param)
    {

        validateResult(param);

        if (param != null)
        {
            // update the setting tracker
            localResultTracker = true;
        }
        else
        {
            localResultTracker = false;

        }

        this.localResult = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * 
     * @param param au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType
     */
    public void addResult(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType param)
    {
        if (localResult == null)
        {
            localResult = new au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[] {};
        }

        // update the setting tracker
        localResultTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localResult);
        list.add(param);
        this.localResult = (au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[]) list
                .toArray(new au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[list.size()]);

    }

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
     * Auto generated getter method
     * 
     * @return au.edu.uts.eng.remotelabs.rigclient.protocol.types.ErrorType
     */
    public au.edu.uts.eng.remotelabs.rigclient.protocol.types.ErrorType getError()
    {
        return localError;
    }

    /**
     * Auto generated setter method
     * 
     * @param param Error
     */
    public void setError(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ErrorType param)
    {

        if (param != null)
        {
            // update the setting tracker
            localErrorTracker = true;
        }
        else
        {
            localErrorTracker = false;

        }

        this.localError = param;

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
                PrimitiveControlResponseType.this.serialize(parentQName, factory, xmlWriter);
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

        java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                "http://remotelabs.eng.uts.edu.au/rigclient/protocol");
        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0))
        {
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix
                    + ":PrimitiveControlResponseType", xmlWriter);
        }
        else
        {
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "PrimitiveControlResponseType",
                    xmlWriter);
        }

        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "success", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }
            else
            {
                xmlWriter.writeStartElement(namespace, "success");
            }

        }
        else
        {
            xmlWriter.writeStartElement("success");
        }

        if (false)
        {

            throw new org.apache.axis2.databinding.ADBException("success cannot be null!!");

        }
        else
        {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSuccess));
        }

        xmlWriter.writeEndElement();
        if (localErrorTracker)
        {
            if (localError == null) { throw new org.apache.axis2.databinding.ADBException("error cannot be null!!"); }
            localError.serialize(new javax.xml.namespace.QName("http://remotelabs.eng.uts.edu.au/rigclient/protocol",
                    "error"), factory, xmlWriter);
        }
        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "wasSuccessful", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }
            else
            {
                xmlWriter.writeStartElement(namespace, "wasSuccessful");
            }

        }
        else
        {
            xmlWriter.writeStartElement("wasSuccessful");
        }

        if (localWasSuccessful == null)
        {
            // write the nil attribute

            throw new org.apache.axis2.databinding.ADBException("wasSuccessful cannot be null!!");

        }
        else
        {

            xmlWriter.writeCharacters(localWasSuccessful);

        }

        xmlWriter.writeEndElement();
        if (localResultTracker)
        {
            if (localResult != null)
            {
                for (int i = 0; i < localResult.length; i++)
                {
                    if (localResult[i] != null)
                    {
                        localResult[i].serialize(new javax.xml.namespace.QName("", "result"), factory, xmlWriter);
                    }
                    else
                    {

                        // we don't have to do any thing since minOccures is
                        // zero

                    }

                }
            }
            else
            {

                throw new org.apache.axis2.databinding.ADBException("result cannot be null!!");

            }
        }
        if (localErrorTracker)
        {
            if (localError == null) { throw new org.apache.axis2.databinding.ADBException("error cannot be null!!"); }
            localError.serialize(new javax.xml.namespace.QName("", "error"), factory, xmlWriter);
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

        attribList.add(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema-instance", "type"));
        attribList.add(new javax.xml.namespace.QName("http://remotelabs.eng.uts.edu.au/rigclient/protocol",
                "PrimitiveControlResponseType"));

        elementList.add(new javax.xml.namespace.QName("", "success"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSuccess));
        if (localErrorTracker)
        {
            elementList.add(new javax.xml.namespace.QName("http://remotelabs.eng.uts.edu.au/rigclient/protocol",
                    "error"));

            if (localError == null) { throw new org.apache.axis2.databinding.ADBException("error cannot be null!!"); }
            elementList.add(localError);
        }
        elementList.add(new javax.xml.namespace.QName("", "wasSuccessful"));

        if (localWasSuccessful != null)
        {
            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWasSuccessful));
        }
        else
        {
            throw new org.apache.axis2.databinding.ADBException("wasSuccessful cannot be null!!");
        }
        if (localResultTracker)
        {
            if (localResult != null)
            {
                for (int i = 0; i < localResult.length; i++)
                {

                    if (localResult[i] != null)
                    {
                        elementList.add(new javax.xml.namespace.QName("", "result"));
                        elementList.add(localResult[i]);
                    }
                    else
                    {

                        // nothing to do

                    }

                }
            }
            else
            {

                throw new org.apache.axis2.databinding.ADBException("result cannot be null!!");

            }

        }
        if (localErrorTracker)
        {
            elementList.add(new javax.xml.namespace.QName("", "error"));

            if (localError == null) { throw new org.apache.axis2.databinding.ADBException("error cannot be null!!"); }
            elementList.add(localError);
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
        public static PrimitiveControlResponseType parse(javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception
        {
            PrimitiveControlResponseType object = new PrimitiveControlResponseType();

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

                        if (!"PrimitiveControlResponseType".equals(type))
                        {
                            // find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                            return (PrimitiveControlResponseType) au.edu.uts.eng.remotelabs.rigclient.protocol.types.ExtensionMapper
                                    .getTypeObject(nsUri, type, reader);
                        }

                    }

                }

                // Note all attributes that were handled. Used to differ normal
                // attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                reader.next();

                java.util.ArrayList list4 = new java.util.ArrayList();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "success").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setSuccess(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));

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

                if (reader.isStartElement()
                        && new javax.xml.namespace.QName("http://remotelabs.eng.uts.edu.au/rigclient/protocol", "error")
                                .equals(reader.getName()))
                {

                    object.setError(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ErrorType.Factory.parse(reader));

                    reader.next();

                } // End of if for expected property start element

                else
                {

                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement()
                        && new javax.xml.namespace.QName("", "wasSuccessful").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setWasSuccessful(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

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

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "result").equals(reader.getName()))
                {

                    // Process the array and step past its final element's end.
                    list4.add(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType.Factory.parse(reader));

                    // loop until we find a start element that is not part of
                    // this array
                    boolean loopDone4 = false;
                    while (!loopDone4)
                    {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();
                        // Step out of this element
                        reader.next();
                        // Step to next element event.
                        while (!reader.isStartElement() && !reader.isEndElement())
                            reader.next();
                        if (reader.isEndElement())
                        {
                            // two continuous end elements means we are exiting
                            // the xml structure
                            loopDone4 = true;
                        }
                        else
                        {
                            if (new javax.xml.namespace.QName("", "result").equals(reader.getName()))
                            {
                                list4.add(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType.Factory
                                        .parse(reader));

                            }
                            else
                            {
                                loopDone4 = true;
                            }
                        }
                    }
                    // call the converter utility to convert and set the array

                    object
                            .setResult((au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[]) org.apache.axis2.databinding.utils.ConverterUtil
                                    .convertToArray(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType.class,
                                            list4));

                } // End of if for expected property start element

                else
                {

                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "error").equals(reader.getName()))
                {

                    object.setError(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ErrorType.Factory.parse(reader));

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
