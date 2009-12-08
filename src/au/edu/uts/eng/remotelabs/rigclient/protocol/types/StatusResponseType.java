/**
 * StatusResponseType.java This file was auto-generated from WSDL by the Apache
 * Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
 */

package au.edu.uts.eng.remotelabs.rigclient.protocol.types;

/**
 * StatusResponseType bean class
 */

public class StatusResponseType implements org.apache.axis2.databinding.ADBBean
{
    /*
     * This type was generated from the piece of schema that had name =
     * StatusResponseType Namespace URI =
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
        public static StatusResponseType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception
        {
            StatusResponseType object = new StatusResponseType();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix = "";
            java.lang.String namespaceuri = "";
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

                        if (!"StatusResponseType".equals(type))
                        {
                            // find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                            return (StatusResponseType) au.edu.uts.eng.remotelabs.rigclient.protocol.types.ExtensionMapper
                                    .getTypeObject(nsUri, type, reader);
                        }

                    }

                }

                // Note all attributes that were handled. Used to differ normal
                // attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                reader.next();

                java.util.ArrayList list7 = new java.util.ArrayList();

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement()
                        && new javax.xml.namespace.QName("", "isMonitorFailed").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setIsMonitorFailed(org.apache.axis2.databinding.utils.ConverterUtil
                            .convertToBoolean(content));

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
                        && new javax.xml.namespace.QName("", "monitorReason").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setMonitorReason(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                    reader.next();

                } // End of if for expected property start element

                else
                {

                }

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement()
                        && new javax.xml.namespace.QName("", "isInMaintenance").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setIsInMaintenance(org.apache.axis2.databinding.utils.ConverterUtil
                            .convertToBoolean(content));

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
                        && new javax.xml.namespace.QName("", "maintenanceReason").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setMaintenanceReason(org.apache.axis2.databinding.utils.ConverterUtil
                            .convertToString(content));

                    reader.next();

                } // End of if for expected property start element

                else
                {

                }

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement()
                        && new javax.xml.namespace.QName("", "isInSession").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setIsInSession(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));

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
                        && new javax.xml.namespace.QName("", "sessionUser").equals(reader.getName()))
                {

                    java.lang.String content = reader.getElementText();

                    object.setSessionUser(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                    reader.next();

                } // End of if for expected property start element

                else
                {

                }

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement() && new javax.xml.namespace.QName("", "slaveUsers").equals(reader.getName()))
                {

                    // Process the array and step past its final element's end.
                    list7.add(reader.getElementText());

                    // loop until we find a start element that is not part of
                    // this array
                    boolean loopDone7 = false;
                    while (!loopDone7)
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
                            loopDone7 = true;
                        }
                        else
                        {
                            if (new javax.xml.namespace.QName("", "slaveUsers").equals(reader.getName()))
                            {
                                list7.add(reader.getElementText());

                            }
                            else
                            {
                                loopDone7 = true;
                            }
                        }
                    }
                    // call the converter utility to convert and set the array

                    object.setSlaveUsers((java.lang.String[]) list7.toArray(new java.lang.String[list7.size()]));

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
    private static final long serialVersionUID = 6597119032817306694L;

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
     * field for IsMonitorFailed
     */

    protected boolean localIsMonitorFailed;

    /**
     * field for MonitorReason
     */

    protected java.lang.String localMonitorReason;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean localMonitorReasonTracker = false;

    /**
     * field for IsInMaintenance
     */

    protected boolean localIsInMaintenance;

    /**
     * field for MaintenanceReason
     */

    protected java.lang.String localMaintenanceReason;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean localMaintenanceReasonTracker = false;

    /**
     * field for IsInSession
     */

    protected boolean localIsInSession;

    /**
     * field for SessionUser
     */

    protected java.lang.String localSessionUser;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean localSessionUserTracker = false;

    /**
     * field for SlaveUsers This was an Array!
     */

    protected java.lang.String[] localSlaveUsers;

    /*
     * This tracker boolean wil be used to detect whether the user called the
     * set method for this attribute. It will be used to determine whether to
     * include this field in the serialized XML
     */
    protected boolean localSlaveUsersTracker = false;

    /**
     * Auto generated add method for the array for convenience
     * 
     * @param param java.lang.String
     */
    public void addSlaveUsers(java.lang.String param)
    {
        if (this.localSlaveUsers == null)
        {
            this.localSlaveUsers = new java.lang.String[] {};
        }

        // update the setting tracker
        this.localSlaveUsersTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(this.localSlaveUsers);
        list.add(param);
        this.localSlaveUsers = (java.lang.String[]) list.toArray(new java.lang.String[list.size()]);

    }

    /**
     * Auto generated getter method
     * 
     * @return boolean
     */
    public boolean getIsInMaintenance()
    {
        return this.localIsInMaintenance;
    }

    /**
     * Auto generated getter method
     * 
     * @return boolean
     */
    public boolean getIsInSession()
    {
        return this.localIsInSession;
    }

    /**
     * Auto generated getter method
     * 
     * @return boolean
     */
    public boolean getIsMonitorFailed()
    {
        return this.localIsMonitorFailed;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getMaintenanceReason()
    {
        return this.localMaintenanceReason;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getMonitorReason()
    {
        return this.localMonitorReason;
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
                StatusResponseType.this.serialize(this.parentQName, factory, xmlWriter);
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

        elementList.add(new javax.xml.namespace.QName("", "isMonitorFailed"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(this.localIsMonitorFailed));
        if (this.localMonitorReasonTracker)
        {
            elementList.add(new javax.xml.namespace.QName("", "monitorReason"));

            if (this.localMonitorReason != null)
            {
                elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
                        .convertToString(this.localMonitorReason));
            }
            else
                throw new org.apache.axis2.databinding.ADBException("monitorReason cannot be null!!");
        }
        elementList.add(new javax.xml.namespace.QName("", "isInMaintenance"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(this.localIsInMaintenance));
        if (this.localMaintenanceReasonTracker)
        {
            elementList.add(new javax.xml.namespace.QName("", "maintenanceReason"));

            if (this.localMaintenanceReason != null)
            {
                elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
                        .convertToString(this.localMaintenanceReason));
            }
            else
                throw new org.apache.axis2.databinding.ADBException("maintenanceReason cannot be null!!");
        }
        elementList.add(new javax.xml.namespace.QName("", "isInSession"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(this.localIsInSession));
        if (this.localSessionUserTracker)
        {
            elementList.add(new javax.xml.namespace.QName("", "sessionUser"));

            if (this.localSessionUser != null)
            {
                elementList
                        .add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(this.localSessionUser));
            }
            else
                throw new org.apache.axis2.databinding.ADBException("sessionUser cannot be null!!");
        }
        if (this.localSlaveUsersTracker)
        {
            if (this.localSlaveUsers != null)
            {
                for (String localSlaveUser : this.localSlaveUsers)
                {

                    if (localSlaveUser != null)
                    {
                        elementList.add(new javax.xml.namespace.QName("", "slaveUsers"));
                        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil
                                .convertToString(localSlaveUser));
                    }
                    else
                    {

                        // have to do nothing

                    }

                }
            }
            else
                throw new org.apache.axis2.databinding.ADBException("slaveUsers cannot be null!!");

        }

        return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(),
                attribList.toArray());

    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String
     */
    public java.lang.String getSessionUser()
    {
        return this.localSessionUser;
    }

    /**
     * Auto generated getter method
     * 
     * @return java.lang.String[]
     */
    public java.lang.String[] getSlaveUsers()
    {
        return this.localSlaveUsers;
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
            prefix = StatusResponseType.generatePrefix(namespace);

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
                    prefix = StatusResponseType.generatePrefix(namespace);
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
                        + ":StatusResponseType", xmlWriter);
            }
            else
            {
                this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "StatusResponseType",
                        xmlWriter);
            }

        }

        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = StatusResponseType.generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "isMonitorFailed", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }
            else
            {
                xmlWriter.writeStartElement(namespace, "isMonitorFailed");
            }

        }
        else
        {
            xmlWriter.writeStartElement("isMonitorFailed");
        }

        if (false)
            throw new org.apache.axis2.databinding.ADBException("isMonitorFailed cannot be null!!");
        else
        {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
                    .convertToString(this.localIsMonitorFailed));
        }

        xmlWriter.writeEndElement();
        if (this.localMonitorReasonTracker)
        {
            namespace = "";
            if (!namespace.equals(""))
            {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null)
                {
                    prefix = StatusResponseType.generatePrefix(namespace);

                    xmlWriter.writeStartElement(prefix, "monitorReason", namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);

                }
                else
                {
                    xmlWriter.writeStartElement(namespace, "monitorReason");
                }

            }
            else
            {
                xmlWriter.writeStartElement("monitorReason");
            }

            if (this.localMonitorReason == null)
                throw new org.apache.axis2.databinding.ADBException("monitorReason cannot be null!!");
            else
            {

                xmlWriter.writeCharacters(this.localMonitorReason);

            }

            xmlWriter.writeEndElement();
        }
        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = StatusResponseType.generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "isInMaintenance", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }
            else
            {
                xmlWriter.writeStartElement(namespace, "isInMaintenance");
            }

        }
        else
        {
            xmlWriter.writeStartElement("isInMaintenance");
        }

        if (false)
            throw new org.apache.axis2.databinding.ADBException("isInMaintenance cannot be null!!");
        else
        {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
                    .convertToString(this.localIsInMaintenance));
        }

        xmlWriter.writeEndElement();
        if (this.localMaintenanceReasonTracker)
        {
            namespace = "";
            if (!namespace.equals(""))
            {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null)
                {
                    prefix = StatusResponseType.generatePrefix(namespace);

                    xmlWriter.writeStartElement(prefix, "maintenanceReason", namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);

                }
                else
                {
                    xmlWriter.writeStartElement(namespace, "maintenanceReason");
                }

            }
            else
            {
                xmlWriter.writeStartElement("maintenanceReason");
            }

            if (this.localMaintenanceReason == null)
                throw new org.apache.axis2.databinding.ADBException("maintenanceReason cannot be null!!");
            else
            {

                xmlWriter.writeCharacters(this.localMaintenanceReason);

            }

            xmlWriter.writeEndElement();
        }
        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = StatusResponseType.generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "isInSession", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }
            else
            {
                xmlWriter.writeStartElement(namespace, "isInSession");
            }

        }
        else
        {
            xmlWriter.writeStartElement("isInSession");
        }

        if (false)
            throw new org.apache.axis2.databinding.ADBException("isInSession cannot be null!!");
        else
        {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
                    .convertToString(this.localIsInSession));
        }

        xmlWriter.writeEndElement();
        if (this.localSessionUserTracker)
        {
            namespace = "";
            if (!namespace.equals(""))
            {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null)
                {
                    prefix = StatusResponseType.generatePrefix(namespace);

                    xmlWriter.writeStartElement(prefix, "sessionUser", namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);

                }
                else
                {
                    xmlWriter.writeStartElement(namespace, "sessionUser");
                }

            }
            else
            {
                xmlWriter.writeStartElement("sessionUser");
            }

            if (this.localSessionUser == null)
                throw new org.apache.axis2.databinding.ADBException("sessionUser cannot be null!!");
            else
            {

                xmlWriter.writeCharacters(this.localSessionUser);

            }

            xmlWriter.writeEndElement();
        }
        if (this.localSlaveUsersTracker)
        {
            if (this.localSlaveUsers != null)
            {
                namespace = "";
                boolean emptyNamespace = namespace == null || namespace.length() == 0;
                prefix = emptyNamespace ? null : xmlWriter.getPrefix(namespace);
                for (String localSlaveUser : this.localSlaveUsers)
                {

                    if (localSlaveUser != null)
                    {

                        if (!emptyNamespace)
                        {
                            if (prefix == null)
                            {
                                java.lang.String prefix2 = StatusResponseType.generatePrefix(namespace);

                                xmlWriter.writeStartElement(prefix2, "slaveUsers", namespace);
                                xmlWriter.writeNamespace(prefix2, namespace);
                                xmlWriter.setPrefix(prefix2, namespace);

                            }
                            else
                            {
                                xmlWriter.writeStartElement(namespace, "slaveUsers");
                            }

                        }
                        else
                        {
                            xmlWriter.writeStartElement("slaveUsers");
                        }

                        xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
                                .convertToString(localSlaveUser));

                        xmlWriter.writeEndElement();

                    }
                    else
                    {

                        // we have to do nothing since minOccurs is zero

                    }

                }
            }
            else
                throw new org.apache.axis2.databinding.ADBException("slaveUsers cannot be null!!");

        }
        xmlWriter.writeEndElement();

    }

    /**
     * Auto generated setter method
     * 
     * @param param IsInMaintenance
     */
    public void setIsInMaintenance(boolean param)
    {

        this.localIsInMaintenance = param;

    }

    /**
     * Auto generated setter method
     * 
     * @param param IsInSession
     */
    public void setIsInSession(boolean param)
    {

        this.localIsInSession = param;

    }

    /**
     * Auto generated setter method
     * 
     * @param param IsMonitorFailed
     */
    public void setIsMonitorFailed(boolean param)
    {

        this.localIsMonitorFailed = param;

    }

    /**
     * Auto generated setter method
     * 
     * @param param MaintenanceReason
     */
    public void setMaintenanceReason(java.lang.String param)
    {

        if (param != null)
        {
            // update the setting tracker
            this.localMaintenanceReasonTracker = true;
        }
        else
        {
            this.localMaintenanceReasonTracker = false;

        }

        this.localMaintenanceReason = param;

    }

    /**
     * Auto generated setter method
     * 
     * @param param MonitorReason
     */
    public void setMonitorReason(java.lang.String param)
    {

        if (param != null)
        {
            // update the setting tracker
            this.localMonitorReasonTracker = true;
        }
        else
        {
            this.localMonitorReasonTracker = false;

        }

        this.localMonitorReason = param;

    }

    /**
     * Auto generated setter method
     * 
     * @param param SessionUser
     */
    public void setSessionUser(java.lang.String param)
    {

        if (param != null)
        {
            // update the setting tracker
            this.localSessionUserTracker = true;
        }
        else
        {
            this.localSessionUserTracker = false;

        }

        this.localSessionUser = param;

    }

    /**
     * Auto generated setter method
     * 
     * @param param SlaveUsers
     */
    public void setSlaveUsers(java.lang.String[] param)
    {

        this.validateSlaveUsers(param);

        if (param != null)
        {
            // update the setting tracker
            this.localSlaveUsersTracker = true;
        }
        else
        {
            this.localSlaveUsersTracker = false;

        }

        this.localSlaveUsers = param;
    }

    /**
     * validate the array for SlaveUsers
     */
    protected void validateSlaveUsers(java.lang.String[] param)
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
            this.registerPrefix(xmlWriter, namespace);
            xmlWriter.writeAttribute(namespace, attName, attValue);
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
                prefix = StatusResponseType.generatePrefix(namespaceURI);
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
            attributePrefix = this.registerPrefix(xmlWriter, attributeNamespace);
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
            this.registerPrefix(xmlWriter, namespace);
            xmlWriter.writeAttribute(namespace, attName, attributeValue);
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
                    if (prefix == null || prefix.length() == 0)
                    {
                        prefix = StatusResponseType.generatePrefix(namespaceURI);
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

}
