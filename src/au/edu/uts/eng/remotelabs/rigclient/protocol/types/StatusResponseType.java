/**
 * SAHARA Rig Client
 *
 * Software abstraction of physical rig to provide rig session control
 * and rig device control. Automatically tests rig hardware and reports
 * the rig status to ensure rig goodness.
 *
 * @license See LICENSE in the top level directory for complete license terms.
 *
 * Copyright (c) 2009, University of Technology, Sydney
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of the University of Technology, Sydney nor the names
 *    of its contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * @author Michael Diponio (mdiponio)
 * @date 8th December 2009
 *
 * Changelog:
 * - 08/12/2009 - mdiponio - Initial file creation.
 */

/**
 * StatusResponseType.java This file was auto-generated from WSDL by the Apache
 * Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
 */

package au.edu.uts.eng.remotelabs.rigclient.protocol.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.axiom.om.OMConstants;
import org.apache.axiom.om.OMDataSource;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.impl.llom.OMSourcedElementImpl;
import org.apache.axis2.databinding.ADBBean;
import org.apache.axis2.databinding.ADBDataSource;
import org.apache.axis2.databinding.ADBException;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl;
import org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter;

/**
 * StatusResponseType bean class
 */

public class StatusResponseType implements ADBBean
{
    /*
     * This type was generated from the piece of schema that had name =
     * StatusResponseType Namespace URI =
     * http://remotelabs.eng.uts.edu.au/rigclient/protocol Namespace Prefix =
     * ns1
     */

    private static final long serialVersionUID = 6597119032817306694L;

    protected boolean isMonitorFailed;

    protected String monitorReason;
    protected boolean monitorReasonTracker = false;

    protected boolean isInMaintenance;

    protected String maintenanceReason;
    protected boolean maintenanceReasonTracker = false;

    protected boolean isInSession;

    protected String sessionUser;
    protected boolean sessionUserTracker = false;

    protected String[] slaveUsers;
    protected boolean slaveUsersTracker = false;

    private static String generatePrefix(final String namespace)
    {
        if (namespace.equals("http://remotelabs.eng.uts.edu.au/rigclient/protocol")) return "ns1";
        return BeanUtil.getUniquePrefix();
    }

    public static boolean isReaderMTOMAware(final XMLStreamReader reader)
    {
        boolean isReaderMTOMAware = false;
        try
        {
            isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(OMConstants.IS_DATA_HANDLERS_AWARE));
        }
        catch (final IllegalArgumentException e)
        {
            isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
    }

    @SuppressWarnings("unchecked")
    public void addSlaveUsers(final String param)
    {
        if (this.slaveUsers == null)
        {
            this.slaveUsers = new String[] {};
        }

        // update the setting tracker
        this.slaveUsersTracker = true;
        final List<String> list = ConverterUtil.toList(this.slaveUsers);
        list.add(param);
        this.slaveUsers = list.toArray(new String[list.size()]);
    }

    public boolean getIsInMaintenance()
    {
        return this.isInMaintenance;
    }

    public boolean getIsInSession()
    {
        return this.isInSession;
    }

    public boolean getIsMonitorFailed()
    {
        return this.isMonitorFailed;
    }

    public String getMaintenanceReason()
    {
        return this.maintenanceReason;
    }

    public String getMonitorReason()
    {
        return this.monitorReason;
    }

    public OMElement getOMElement(final QName parentQName, final OMFactory factory) throws ADBException
    {
        final OMDataSource dataSource = new ADBDataSource(this, parentQName)
        {
            public void serialize(final MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException
            {
                StatusResponseType.this.serialize(this.parentQName, factory, xmlWriter);
            }
        };
        return new OMSourcedElementImpl(parentQName, factory, dataSource);
    }

    public XMLStreamReader getPullParser(final QName qName) throws ADBException
    {

        final ArrayList<Serializable> elementList = new ArrayList<Serializable>();
        final ArrayList<QName> attribList = new ArrayList<QName>();

        elementList.add(new QName("", "isMonitorFailed"));
        elementList.add(ConverterUtil.convertToString(this.isMonitorFailed));

        if (this.monitorReasonTracker)
        {
            elementList.add(new QName("", "monitorReason"));
            if (this.monitorReason != null)
            {
                elementList.add(ConverterUtil.convertToString(this.monitorReason));
            }
            else
            {
                throw new ADBException("monitorReason cannot be null!!");
            }
        }

        elementList.add(new QName("", "isInMaintenance"));
        elementList.add(ConverterUtil.convertToString(this.isInMaintenance));

        if (this.maintenanceReasonTracker)
        {
            elementList.add(new QName("", "maintenanceReason"));
            if (this.maintenanceReason != null)
            {
                elementList.add(ConverterUtil.convertToString(this.maintenanceReason));
            }
            else
            {
                throw new ADBException("maintenanceReason cannot be null!!");
            }
        }

        elementList.add(new QName("", "isInSession"));
        elementList.add(ConverterUtil.convertToString(this.isInSession));

        if (this.sessionUserTracker)
        {
            elementList.add(new QName("", "sessionUser"));
            if (this.sessionUser != null)
            {
                elementList.add(ConverterUtil.convertToString(this.sessionUser));
            }
            else
            {
                throw new ADBException("sessionUser cannot be null!!");
            }
        }

        if (this.slaveUsersTracker)
        {
            if (this.slaveUsers != null)
            {
                for (final String localSlaveUser : this.slaveUsers)
                {
                    if (localSlaveUser != null)
                    {
                        elementList.add(new QName("", "slaveUsers"));
                        elementList.add(ConverterUtil.convertToString(localSlaveUser));
                    }
                }
            }
            else
            {
                throw new ADBException("slaveUsers cannot be null!!");
            }
        }
        return new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }

    public String getSessionUser()
    {
        return this.sessionUser;
    }

    public String[] getSlaveUsers()
    {
        return this.slaveUsers;
    }

    private String registerPrefix(final XMLStreamWriter xmlWriter, final String namespace) throws XMLStreamException
    {
        String prefix = xmlWriter.getPrefix(namespace);
        if (prefix == null)
        {
            prefix = StatusResponseType.generatePrefix(namespace);
            while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null)
            {
                prefix = BeanUtil.getUniquePrefix();
            }
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }
        return prefix;
    }

    public void serialize(final QName parentQName, final OMFactory factory, final MTOMAwareXMLStreamWriter xmlWriter)
            throws XMLStreamException, ADBException
    {
        this.serialize(parentQName, factory, xmlWriter, false);
    }

    public void serialize(final QName parentQName, final OMFactory factory, final MTOMAwareXMLStreamWriter xmlWriter,
            final boolean serializeType) throws XMLStreamException, ADBException
    {
        String prefix = parentQName.getPrefix();
        String namespace = parentQName.getNamespaceURI();

        if (namespace != null && namespace.trim().length() > 0)
        {
            final String writerPrefix = xmlWriter.getPrefix(namespace);
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
            final String namespacePrefix = this.registerPrefix(xmlWriter,
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

        xmlWriter.writeCharacters(ConverterUtil.convertToString(this.isMonitorFailed));
        xmlWriter.writeEndElement();

        if (this.monitorReasonTracker)
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

            if (this.monitorReason == null)
            {
                throw new ADBException("monitorReason cannot be null!!");
            }
            else
            {
                xmlWriter.writeCharacters(this.monitorReason);
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
        xmlWriter.writeCharacters(ConverterUtil.convertToString(this.isInMaintenance));
        xmlWriter.writeEndElement();

        if (this.maintenanceReasonTracker)
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

            if (this.maintenanceReason == null)
            {
                throw new ADBException("maintenanceReason cannot be null!!");
            }
            else
            {
                xmlWriter.writeCharacters(this.maintenanceReason);
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
        xmlWriter.writeCharacters(ConverterUtil.convertToString(this.isInSession));
        xmlWriter.writeEndElement();

        if (this.sessionUserTracker)
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

            if (this.sessionUser == null)
            {
                throw new ADBException("sessionUser cannot be null!!");
            }
            else
            {
                xmlWriter.writeCharacters(this.sessionUser);
            }
            xmlWriter.writeEndElement();
        }

        if (this.slaveUsersTracker)
        {
            if (this.slaveUsers != null)
            {
                namespace = "";
                final boolean emptyNamespace = namespace == null || namespace.length() == 0;
                prefix = emptyNamespace ? null : xmlWriter.getPrefix(namespace);
                for (final String localSlaveUser : this.slaveUsers)
                {
                    if (localSlaveUser != null)
                    {
                        if (!emptyNamespace)
                        {
                            if (prefix == null)
                            {
                                final String prefix2 = StatusResponseType.generatePrefix(namespace);
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

                        xmlWriter.writeCharacters(ConverterUtil.convertToString(localSlaveUser));
                        xmlWriter.writeEndElement();
                    }
                }
            }
            else
            {
                throw new ADBException("slaveUsers cannot be null!!");
            }
        }
        xmlWriter.writeEndElement();
    }

    public void setIsInMaintenance(final boolean param)
    {
        this.isInMaintenance = param;
    }

    public void setIsInSession(final boolean param)
    {
        this.isInSession = param;
    }

    public void setIsMonitorFailed(final boolean param)
    {
        this.isMonitorFailed = param;
    }

    public void setMaintenanceReason(final String param)
    {
        if (param != null)
        {
            this.maintenanceReasonTracker = true;
        }
        else
        {
            this.maintenanceReasonTracker = false;
        }
        this.maintenanceReason = param;
    }

    public void setMonitorReason(final String param)
    {
        if (param != null)
        {
            this.monitorReasonTracker = true;
        }
        else
        {
            this.monitorReasonTracker = false;
        }
        this.monitorReason = param;
    }

    public void setSessionUser(final String param)
    {
        if (param != null)
        {
            this.sessionUserTracker = true;
        }
        else
        {
            this.sessionUserTracker = false;
        }
        this.sessionUser = param;
    }

    public void setSlaveUsers(final String[] param)
    {
        if (param != null)
        {
            this.slaveUsersTracker = true;
        }
        else
        {
            this.slaveUsersTracker = false;
        }
        this.slaveUsers = param;
    }

    private void writeAttribute(final String prefix, final String namespace, final String attName, final String attValue,
            final XMLStreamWriter xmlWriter) throws XMLStreamException
    {
        if (xmlWriter.getPrefix(namespace) == null)
        {
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }
        xmlWriter.writeAttribute(namespace, attName, attValue);
    }

    public static class Factory
    {
        public static StatusResponseType parse(final XMLStreamReader reader) throws Exception
        {
            final StatusResponseType object = new StatusResponseType();

            try
            {
                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null)
                {
                    final String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
                    if (fullTypeName != null)   {
                        String nsPrefix = null;
                        if (fullTypeName.indexOf(":") > -1)
                        {
                            nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
                        }
                        nsPrefix = nsPrefix == null ? "" : nsPrefix;
                        final String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
                        if (!"StatusResponseType".equals(type))
                        {
                            // find namespace for the prefix
                            final String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                            return (StatusResponseType) au.edu.uts.eng.remotelabs.rigclient.protocol.types.ExtensionMapper
                                    .getTypeObject(nsUri, type, reader);
                        }
                    }
                }

                reader.next();
                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement() && new QName("", "isMonitorFailed").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setIsMonitorFailed(ConverterUtil.convertToBoolean(content));
                    reader.next();

                }
                else
                {
                    throw new ADBException("Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }
                if (reader.isStartElement() && new QName("", "monitorReason").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setMonitorReason(ConverterUtil.convertToString(content));
                    reader.next();
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }
                if (reader.isStartElement() && new QName("", "isInMaintenance").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setIsInMaintenance(ConverterUtil.convertToBoolean(content));
                    reader.next();
                }
                else
                {
                    throw new ADBException("Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }
                if (reader.isStartElement() && new QName("", "maintenanceReason").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setMaintenanceReason(ConverterUtil.convertToString(content));
                    reader.next();
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement() && new QName("", "isInSession").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setIsInSession(ConverterUtil.convertToBoolean(content));
                    reader.next();
                }
                else
                {
                    throw new ADBException("Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement() && new QName("", "sessionUser").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setSessionUser(ConverterUtil.convertToString(content));
                    reader.next();
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                final ArrayList<String> slaveUserList = new ArrayList<String>();
                if (reader.isStartElement() && new QName("", "slaveUsers").equals(reader.getName()))
                {
                    slaveUserList.add(reader.getElementText());
                    boolean hasSiblings = false;
                    while (!hasSiblings)
                    {
                        while (!reader.isEndElement())
                        {
                            reader.next();
                        }
                        reader.next();
                        while (!reader.isStartElement() && !reader.isEndElement())
                        {
                            reader.next();
                        }
                        if (reader.isEndElement())
                        {
                            hasSiblings = true;
                        }
                        else
                        {
                            if (new QName("", "slaveUsers").equals(reader.getName()))
                            {
                                slaveUserList.add(reader.getElementText());
                            }
                            else
                            {
                                hasSiblings = true;
                            }
                        }
                    }
                    object.setSlaveUsers(slaveUserList.toArray(new String[slaveUserList.size()]));
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement())
                {
                    throw new ADBException("Unexpected subelement " + reader.getLocalName());
                }
            }
            catch (final XMLStreamException e)
            {
                throw new Exception(e);
            }

            return object;
        }
    }
}
