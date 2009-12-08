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
 * UserType.java This file was auto-generated from WSDL by the Apache Axis2
 * version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
 */

package au.edu.uts.eng.remotelabs.rigclient.protocol.types;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.axiom.om.OMConstants;
import org.apache.axis2.databinding.ADBBean;
import org.apache.axis2.databinding.ADBDataSource;
import org.apache.axis2.databinding.ADBException;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl;

/**
 * UserType bean class.
 */
public class UserType extends AuthRequiredRequestType implements ADBBean
{
    /*
     * This type was generated from the piece of schema that had name = UserType
     * Namespace URI = http://remotelabs.eng.uts.edu.au/rigclient/protocol
     * Namespace Prefix = ns1
     */

    /** Serializable class. */
    private static final long serialVersionUID = 8325159414836793539L;
    
    /** User. */
    protected String localUser;

    private static String generatePrefix(final String namespace)
    {
        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespace)) return "ns1";
        return BeanUtil.getUniquePrefix();
    } 

    /**
     * @param parentQName
     * @param factory
     * @return org.apache.axiom.om.OMElement
     */
    @Override
    public org.apache.axiom.om.OMElement getOMElement(final QName parentQName,
            final org.apache.axiom.om.OMFactory factory) throws ADBException
    {

        final  org.apache.axiom.om.OMDataSource dataSource = new ADBDataSource(this, parentQName)
        {

            @Override
            public void serialize(final org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                    throws XMLStreamException
            {
                UserType.this.serialize(this.parentQName, factory, xmlWriter);
            }
        };
        return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(parentQName, factory, dataSource);
    }

    /**
     * Method to get an XML representation of this object.
     */
    @Override
    public XMLStreamReader getPullParser(final QName qName) throws ADBException
    {

        final ArrayList<Serializable> elementList = new ArrayList<Serializable>();
        final ArrayList<QName> attribList = new ArrayList<QName>();

        attribList.add(new QName("http://www.w3.org/2001/XMLSchema-instance", "type"));
        attribList.add(new QName("http://remotelabs.eng.uts.edu.au/rigclient/protocol", "UserType"));
        
        if (this.localIdentityTokenTracker)
        {
            elementList.add(new QName("", "identityToken"));
            if (this.localIdentityToken != null)
            {
                elementList.add(ConverterUtil.convertToString(this.localIdentityToken));
            }
            else
            {
                throw new ADBException("identityToken cannot be null.");
            }
        }
        
        if (this.localRequestorTracker)
        {
            elementList.add(new QName("", "requestor"));

            if (this.localRequestor != null)
            {
                elementList.add(ConverterUtil.convertToString(this.localRequestor));
            }
            else
            {
                throw new ADBException("requestor cannot be null.");
            }
        }
        elementList.add(new QName("", "user"));

        if (this.localUser != null)
        {
            elementList.add(ConverterUtil.convertToString(this.localUser));
        }
        else
        {
            throw new ADBException("user cannot be null.");
        }

        return new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }



    /**
     * Register a namespace prefix
     */
    private String registerPrefix(final XMLStreamWriter xmlWriter, final String namespace) throws XMLStreamException
    {
        String prefix = xmlWriter.getPrefix(namespace);

        if (prefix == null)
        {
            prefix = UserType.generatePrefix(namespace);

            while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null)
            {
                prefix = BeanUtil.getUniquePrefix();
            }

            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }

        return prefix;
    }

    @Override
    public void serialize(final QName parentQName, final org.apache.axiom.om.OMFactory factory,
            final org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException,
            ADBException
    {
        this.serialize(parentQName, factory, xmlWriter, false);
    }

    @Override
    public void serialize(final QName parentQName, final org.apache.axiom.om.OMFactory factory,
            final org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter, 
            final boolean serializeType)
            throws XMLStreamException, ADBException
    {

        String prefix = null;
        String namespace = null;

        prefix = parentQName.getPrefix();
        namespace = parentQName.getNamespaceURI();

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
                    prefix = UserType.generatePrefix(namespace);
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

        final String namespacePrefix = this.registerPrefix(xmlWriter, "http://remotelabs.eng.uts.edu.au/rigclient/protocol");
        if (namespacePrefix != null && namespacePrefix.trim().length() > 0)
        {
            this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix
                    + ":UserType", xmlWriter);
        }
        else
        {
            this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "UserType", xmlWriter);
        }

        if (this.localIdentityTokenTracker)
        {
            namespace = "";
            if (!namespace.equals(""))
            {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null)
                {
                    prefix = UserType.generatePrefix(namespace);

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

            if (this.localIdentityToken == null)
            {
                throw new ADBException("identityToken cannot be null.");
            }
            else
            {
                xmlWriter.writeCharacters(this.localIdentityToken);
            }
            xmlWriter.writeEndElement();
        }
        
        if (this.localRequestorTracker)
        {
            namespace = "";
            if (!"".equals(namespace))
            {
                prefix = xmlWriter.getPrefix(namespace);
                if (prefix == null)
                {
                    prefix = UserType.generatePrefix(namespace);
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

            if (this.localRequestor == null)
            {
                throw new ADBException("requestor cannot be null.");
            }
            else
            {
                xmlWriter.writeCharacters(this.localRequestor);
            }
            xmlWriter.writeEndElement();
        }
        
        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null)
            {
                prefix = UserType.generatePrefix(namespace);

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
        {
            throw new ADBException("user cannot be null.");
        }
        else
        {
            xmlWriter.writeCharacters(this.localUser);
        }

        xmlWriter.writeEndElement();
        xmlWriter.writeEndElement();
    }

    /**
     * @param param User
     */
    public void setUser(final String param)
    {
        this.localUser = param;
    }
    
    /**
     * @return String user
     */
    public String getUser()
    {
        return this.localUser;
    }

    /**
     * Utility method to write an attribute with the ns prefix.
     */
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
    
    /**
     * True if reader is MTOM aware.
     * 
     * @return true if the reader supports MTOM
     */
    public static boolean isReaderMTOMAware(final XMLStreamReader reader)
    {
        boolean isReaderMTOMAware = false;

        try
        {
            isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(OMConstants.IS_DATA_HANDLERS_AWARE));
        }
        catch (IllegalArgumentException e)
        {
            isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
    }
    
    /**
     * Factory class that contains a static parse method.
     */
    public static class Factory
    {
        /**
         * Parses an XML representation of the surrounding and generates an instance
         * of the surrounding class.
         * 
         * @param reader stream containing an XML representation of this class
         * @return instance of surrounding class
         * @throws Exception XML representation is invalid
         */
        public static UserType parse(XMLStreamReader reader) throws Exception
        {
            final UserType object = new UserType();
            try
            {
                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null)
                {
                    final String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
                    if (fullTypeName != null)
                    {
                        String nsPrefix = null;
                        if (fullTypeName.indexOf(':') > -1)
                        {
                            nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(':'));
                        }
                        nsPrefix = nsPrefix == null ? "" : nsPrefix;

                        final String type = fullTypeName.substring(fullTypeName.indexOf(':') + 1);
                        if (!"UserType".equals(type))
                        {
                            /* Find namespace for the prefix. */
                            final String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                            return (UserType) ExtensionMapper.getTypeObject(nsUri, type, reader);
                        }
                    }
                }

                reader.next();
                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement() && new QName("", "identityToken").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setIdentityToken(ConverterUtil.convertToString(content));
                    reader.next();
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement() && new QName("", "requestor").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setRequestor(ConverterUtil.convertToString(content));
                    reader.next();
                }


                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement() && new QName("", "user").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setUser(ConverterUtil.convertToString(content));
                    reader.next();
                }
                else
                {
                    // A start element we are not expecting indicates an invalid
                    // parameter was passed
                    throw new ADBException("Unexpected subelement " + reader.getLocalName());
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
            catch (XMLStreamException e)
            {
                throw new Exception(e);
            }

            return object;
        }
    }
}
