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
 * AttributeRequestType.java This file was auto-generated from WSDL by the
 * Apache Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
 */

package au.edu.uts.eng.remotelabs.rigclient.protocol.types;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.axiom.om.OMConstants;
import org.apache.axiom.om.OMDataSource;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axis2.databinding.ADBBean;
import org.apache.axis2.databinding.ADBDataSource;
import org.apache.axis2.databinding.ADBException;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter;

/**
 * AttributeRequestType bean class.
 */
public class AttributeRequestType extends AuthRequiredRequestType implements ADBBean
{
    /*
     * This type was generated from the piece of schema that had name =
     * AttributeRequestType Namespace URI =
     * http://remotelabs.eng.uts.edu.au/rigclient/protocol Namespace Prefix =
     * ns1
     */

    private static final long serialVersionUID = -552285943406478233L;
    
    protected String attribute;

    private static String generatePrefix(String namespace)
    {
        if (namespace.equals("http://remotelabs.eng.uts.edu.au/rigclient/protocol")) return "ns1";
        return BeanUtil.getUniquePrefix();
    }

    public static boolean isReaderMTOMAware(XMLStreamReader reader)
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

    @Override
    public OMElement getOMElement(final QName parentQName, final OMFactory factory) throws ADBException
    {
        OMDataSource dataSource = new ADBDataSource(this, parentQName)
        {
            @Override
            public void serialize(MTOMAwareXMLStreamWriter xmlWriter)
                    throws XMLStreamException
            {
                AttributeRequestType.this.serialize(this.parentQName, factory, xmlWriter);
            }
        };
        return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(parentQName, factory, dataSource);

    }

    @Override
    public XMLStreamReader getPullParser(QName qName) throws ADBException
    {

        ArrayList<Serializable> elementList = new ArrayList<Serializable>();
        ArrayList<QName> attribList = new ArrayList<QName>();

        attribList.add(new QName("http://www.w3.org/2001/XMLSchema-instance", "type"));
        attribList.add(new QName("http://remotelabs.eng.uts.edu.au/rigclient/protocol", "AttributeRequestType"));
        
        if (this.identityTokenTracker)
        {
            elementList.add(new QName("", "identityToken"));
            if (this.identityToken != null)
            {
                elementList.add(ConverterUtil.convertToString(this.identityToken));
            }
            else
            {
                throw new ADBException("identityToken cannot be null.");
            }
        }
        
        if (this.requestorTracker)
        {
            elementList.add(new QName("", "requestor"));
            if (this.requestor != null)
            {
                elementList.add(ConverterUtil.convertToString(this.requestor));
            }
            else
            {
                throw new ADBException("requestor cannot be null.");
            }
        }
        elementList.add(new QName("", "attribute"));

        if (this.attribute != null)
        {
            elementList.add(ConverterUtil.convertToString(this.attribute));
        }
        else
        {
            throw new ADBException("attribute cannot be null.");
        }

        return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(),
                attribList.toArray());
    }

    private String registerPrefix(XMLStreamWriter xmlWriter, String namespace) throws XMLStreamException
    {
        String prefix = xmlWriter.getPrefix(namespace);
        if (prefix == null)
        {
            prefix = AttributeRequestType.generatePrefix(namespace);
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
    public void serialize(final QName parentQName, final OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter)
            throws XMLStreamException, ADBException
    {
        this.serialize(parentQName, factory, xmlWriter, false);
    }

    @Override
    public void serialize(final QName parentQName, final OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter, 
            boolean serializeType) throws XMLStreamException, ADBException
    {
        String prefix = null;
        String namespace = null;

        prefix = parentQName.getPrefix();
        namespace = parentQName.getNamespaceURI();

        if (namespace != null && namespace.trim().length() > 0)
        {
            String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null)
            {
                xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
            }
            else
            {
                if (prefix == null)
                {
                    prefix = AttributeRequestType.generatePrefix(namespace);
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

        String namespacePrefix = this.registerPrefix(xmlWriter, "http://remotelabs.eng.uts.edu.au/rigclient/protocol");
        if (namespacePrefix != null && namespacePrefix.trim().length() > 0)
        {
            this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix
                    + ":AttributeRequestType", xmlWriter);
        }
        else
        {
            this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "AttributeRequestType",
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
                    prefix = AttributeRequestType.generatePrefix(namespace);

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
            {
                throw new ADBException("identityToken cannot be null");
            }
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
                    prefix = AttributeRequestType.generatePrefix(namespace);

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
            {
                throw new ADBException("requestor cannot be null.");
            }
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
                prefix = AttributeRequestType.generatePrefix(namespace);
                xmlWriter.writeStartElement(prefix, "attribute", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            else
            {
                xmlWriter.writeStartElement(namespace, "attribute");
            }
        }
        else
        {
            xmlWriter.writeStartElement("attribute");
        }

        if (this.attribute == null)
        {
            throw new ADBException("attribute cannot be null.");
        }
        else
        {
            xmlWriter.writeCharacters(this.attribute);
        }

        xmlWriter.writeEndElement();
        xmlWriter.writeEndElement();

    }
    
    public String getAttribute()
    {
        return this.attribute;
    }

    public void setAttribute(String param)
    {
        this.attribute = param;
    }

    private void writeAttribute(String prefix, String namespace, String attName, String attValue, 
            XMLStreamWriter xmlWriter) throws XMLStreamException
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
        public static AttributeRequestType parse(XMLStreamReader reader) throws Exception
        {
            AttributeRequestType object = new AttributeRequestType();
            try
            {
                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null)
                {
                    String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
                    if (fullTypeName != null)
                    {
                        String nsPrefix = null;
                        if (fullTypeName.indexOf(":") > -1)
                        {
                            nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
                        }
                        nsPrefix = nsPrefix == null ? "" : nsPrefix;
                        String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
                        
                        if (!"AttributeRequestType".equals(type))
                        {
                            String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                            return (AttributeRequestType) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
                    String content = reader.getElementText();
                    object.setIdentityToken(ConverterUtil.convertToString(content));
                    reader.next();

                }

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement() && new QName("", "requestor").equals(reader.getName()))
                {
                    String content = reader.getElementText();
                    object.setRequestor(ConverterUtil.convertToString(content));
                    reader.next();
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }
                if (reader.isStartElement() && new QName("", "attribute").equals(reader.getName()))
                {
                    String content = reader.getElementText();
                    object.setAttribute(ConverterUtil.convertToString(content));
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
