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
 * AttributeResponseTypeChoice.java This file was auto-generated from WSDL by
 * the Apache Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
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
import org.apache.axis2.databinding.ADBDataSource;
import org.apache.axis2.databinding.ADBException;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter;

/**
 * AttributeResponseTypeChoice bean class.
 */
public class AttributeResponseTypeChoice implements org.apache.axis2.databinding.ADBBean
{
    /*
     * This type was generated from the piece of schema that had name =
     * AttributeResponseTypeChoice Namespace URI =
     * http://remotelabs.eng.uts.edu.au/rigclient/protocol Namespace Prefix =
     * ns1
     */

    private static final long serialVersionUID = 4138195165704992689L;
    
    protected boolean valueTracker = false;
    protected String value;
    
    protected boolean errorTracker = false;
    protected ErrorType error;

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

    public OMElement getOMElement(final QName parentQName, final OMFactory factory) throws ADBException
    {
        OMDataSource dataSource = new ADBDataSource(this, parentQName)
        {
            @Override
            public void serialize(MTOMAwareXMLStreamWriter xmlWriter)
                    throws XMLStreamException
            {
                AttributeResponseTypeChoice.this.serialize(this.parentQName, factory, xmlWriter);
            }
        };
        return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(parentQName, factory, dataSource);
    }

    public XMLStreamReader getPullParser(QName qName) throws ADBException
    {
        ArrayList<Serializable> elementList = new ArrayList<Serializable>();
        ArrayList<QName> attribList = new ArrayList<QName>();

        if (this.valueTracker)
        {
            elementList.add(new QName("", "value"));
            if (this.value != null)
            {
                elementList.add(ConverterUtil.convertToString(this.value));
            }
            else
            {
                throw new ADBException("value cannot be null.");
            }
        }
        if (this.errorTracker)
        {
            elementList.add(new QName("", "error"));
            if (this.error == null)
            {
                throw new ADBException("error cannot be null.");
            }
            elementList.add(this.error);
        }

        return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(),
                attribList.toArray());
    }

    private String registerPrefix(XMLStreamWriter xmlWriter, String namespace) throws XMLStreamException
    {
        String prefix = xmlWriter.getPrefix(namespace);
        if (prefix == null)
        {
            prefix = AttributeResponseTypeChoice.generatePrefix(namespace);
            while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null)
            {
                prefix = BeanUtil.getUniquePrefix();
            }

            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }
        return prefix;
    }

    public void serialize(final QName parentQName, final OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter)
            throws XMLStreamException, ADBException
    {
        this.serialize(parentQName, factory, xmlWriter, false);
    }

    public void serialize(final QName parentQName, final OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter, boolean serializeType)
            throws XMLStreamException, ADBException
    {
        String prefix = null;
        String namespace = null;

        if (serializeType)
        {
            String namespacePrefix = this.registerPrefix(xmlWriter, "http://remotelabs.eng.uts.edu.au/rigclient/protocol");
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
        if (this.valueTracker)
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

            if (this.value == null)
            {
                throw new ADBException("value cannot be null.");
            }
            else
            {
                xmlWriter.writeCharacters(this.value);
            }
            xmlWriter.writeEndElement();
        }
        
        if (this.errorTracker)
        {
            if (this.error == null)
            {
                throw new ADBException("error cannot be null!!");
            }
            this.error.serialize(new QName("", "error"), factory, xmlWriter);
        }
    }

    public ErrorType getError()
    {
        return this.error;
    }
    
    public String getValue()
    {
        return this.value;
    }

    private void clearAllSettingTrackers()
    {
        this.valueTracker = false;
        this.errorTracker = false;
    }

    public void setError(ErrorType param)
    {
        this.clearAllSettingTrackers();
        if (param != null)
        {
            this.errorTracker = true;
        }

        this.error = param;
    }

    public void setValue(String param)
    {

        this.clearAllSettingTrackers();
        if (param != null)
        {
            this.valueTracker = true;
        }
        else
        {
            this.valueTracker = false;
        }
        this.value = param;
    }

    private void writeAttribute(String prefix, String namespace, String attName, String attValue, XMLStreamWriter xmlWriter)
            throws XMLStreamException
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
        public static AttributeResponseTypeChoice parse(XMLStreamReader reader) throws Exception
        {
            AttributeResponseTypeChoice object = new AttributeResponseTypeChoice();
            try
            {
                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement() && new QName("", "value").equals(reader.getName()))
                {
                    String content = reader.getElementText();
                    object.setValue(ConverterUtil.convertToString(content));
                    reader.next();
                }
                else if (reader.isStartElement() && new QName("", "error").equals(reader.getName()))
                {
                    object.setError(ErrorType.Factory.parse(reader));
                    reader.next();
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
