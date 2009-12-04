/**
 * PrimitiveControlRequestType.java This file was auto-generated from WSDL by
 * the Apache Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
 */

package au.edu.uts.eng.remotelabs.rigclient.protocol.types;

/**
 * PrimitiveControlRequestType bean class
 */

public class PrimitiveControlRequestType extends au.edu.uts.eng.remotelabs.rigclient.protocol.types.UserType implements
        org.apache.axis2.databinding.ADBBean
{
    /*
     * This type was generated from the piece of schema that had name =
     * PrimitiveControlRequestType Namespace URI =
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
        public static PrimitiveControlRequestType parse(javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception
        {
            PrimitiveControlRequestType object = new PrimitiveControlRequestType();

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

                        if (!"PrimitiveControlRequestType".equals(type))
                        {
                            // find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                            return (PrimitiveControlRequestType) au.edu.uts.eng.remotelabs.rigclient.protocol.types.ExtensionMapper
                                    .getTypeObject(nsUri, type, reader);
                        }

                    }

                }

                new java.util.Vector();

                reader.next();

                java.util.ArrayList list4 = new java.util.ArrayList();

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "user").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setUser(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

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

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "controller").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setController(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

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

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "action").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setAction(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

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

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "param").equals(reader.getName()))
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
                            loopDone4 = true;
                        }
                        else
                        {
                            if (new javax.xml.namespace.QName("", "param").equals(reader.getName()))
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
                            .setParam((au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[]) org.apache.axis2.databinding.utils.ConverterUtil
                                    .convertToArray(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType.class,
                                            list4));

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
    private static final long serialVersionUID = -780667459288663133L;

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
     * field for Controller
     */

    protected java.lang.String localController;

    /**
     * field for Action
     */

    protected java.lang.String localAction;

    /**
     * field for Param This was an Array!
     */

    protected au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[] localParam;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean localParamTracker = false;

    /**
     * Auto generated add method for the array for convenience
     * 
     * @param param au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType
     */
    public void addParam(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType param)
    {
        if (this.localParam == null)
        {
            this.localParam = new au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[] {};
        }

        // update the setting tracker
        this.localParamTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(this.localParam);
        list.add(param);
        this.localParam = (au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[]) list
                .toArray(new au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[list.size()]);

    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getAction()
    {
        return this.localAction;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getController()
    {
        return this.localController;
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
                PrimitiveControlRequestType.this.serialize(this.parentQName, factory, xmlWriter);
            }
        };
        return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(parentQName, factory, dataSource);

    }

    /**
     * Auto generated getter method
     * 
     * @return au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[]
     */
    public au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[] getParam()
    {
        return this.localParam;
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
                "PrimitiveControlRequestType"));

        elementList.add(new javax.xml.namespace.QName("", "user"));

        if (this.localUser != null)
        {
            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(this.localUser));
        }
        else
            throw new org.apache.axis2.databinding.ADBException("user cannot be null!!");

        elementList.add(new javax.xml.namespace.QName("", "controller"));

        if (this.localController != null)
        {
            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(this.localController));
        }
        else
            throw new org.apache.axis2.databinding.ADBException("controller cannot be null!!");

        elementList.add(new javax.xml.namespace.QName("", "action"));

        if (this.localAction != null)
        {
            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(this.localAction));
        }
        else
            throw new org.apache.axis2.databinding.ADBException("action cannot be null!!");
        if (this.localParamTracker)
        {
            if (this.localParam != null)
            {
                for (ParamType element : this.localParam)
                {

                    if (element != null)
                    {
                        elementList.add(new javax.xml.namespace.QName("", "param"));
                        elementList.add(element);
                    }
                    else
                    {

                        // nothing to do

                    }

                }
            }
            else
                throw new org.apache.axis2.databinding.ADBException("param cannot be null!!");

        }

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
            prefix = PrimitiveControlRequestType.generatePrefix(namespace);

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
                    prefix = PrimitiveControlRequestType.generatePrefix(namespace);
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
                    + ":PrimitiveControlRequestType", xmlWriter);
        }
        else
        {
            this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "PrimitiveControlRequestType", xmlWriter);
        }

        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = PrimitiveControlRequestType.generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "user", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }
            else
            {
                xmlWriter.writeStartElement(namespace, "user");
            }

        }
        else
        {
            xmlWriter.writeStartElement("user");
        }

        if (this.localUser == null)
            throw new org.apache.axis2.databinding.ADBException("user cannot be null!!");
        else
        {

            xmlWriter.writeCharacters(this.localUser);

        }

        xmlWriter.writeEndElement();

        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = PrimitiveControlRequestType.generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "controller", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }
            else
            {
                xmlWriter.writeStartElement(namespace, "controller");
            }

        }
        else
        {
            xmlWriter.writeStartElement("controller");
        }

        if (this.localController == null)
            throw new org.apache.axis2.databinding.ADBException("controller cannot be null!!");
        else
        {

            xmlWriter.writeCharacters(this.localController);

        }

        xmlWriter.writeEndElement();

        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = PrimitiveControlRequestType.generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "action", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }
            else
            {
                xmlWriter.writeStartElement(namespace, "action");
            }

        }
        else
        {
            xmlWriter.writeStartElement("action");
        }

        if (this.localAction == null)
            throw new org.apache.axis2.databinding.ADBException("action cannot be null!!");
        else
        {

            xmlWriter.writeCharacters(this.localAction);

        }

        xmlWriter.writeEndElement();
        if (this.localParamTracker)
        {
            if (this.localParam != null)
            {
                for (ParamType element : this.localParam)
                {
                    if (element != null)
                    {
                        element.serialize(new javax.xml.namespace.QName("", "param"), factory, xmlWriter);
                    }
                    else
                    {

                        // we don't have to do any thing since minOccures is
                        // zero

                    }

                }
            }
            else
                throw new org.apache.axis2.databinding.ADBException("param cannot be null!!");
        }
        xmlWriter.writeEndElement();

    }

    /**
     * Auto generated setter method
     * 
     * @param param Action
     */
    public void setAction(java.lang.String param)
    {

        this.localAction = param;

    }

    /**
     * Auto generated setter method
     * 
     * @param param Controller
     */
    public void setController(java.lang.String param)
    {

        this.localController = param;

    }

    /**
     * Auto generated setter method
     * 
     * @param param Param
     */
    public void setParam(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[] param)
    {

        this.validateParam(param);

        if (param != null)
        {
            // update the setting tracker
            this.localParamTracker = true;
        }
        else
        {
            this.localParamTracker = false;

        }

        this.localParam = param;
    }

    /**
     * validate the array for Param
     */
    protected void validateParam(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType[] param)
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
