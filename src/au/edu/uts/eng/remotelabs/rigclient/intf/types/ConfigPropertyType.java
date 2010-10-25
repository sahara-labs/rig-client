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
 * @date 13th September 2009
 */

package au.edu.uts.eng.remotelabs.rigclient.intf.types;

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
import org.apache.axiom.om.impl.llom.OMSourcedElementImpl;
import org.apache.axis2.databinding.ADBBean;
import org.apache.axis2.databinding.ADBDataSource;
import org.apache.axis2.databinding.ADBException;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl;
import org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter;

/**
 * ConfigPropertyType bean class.
 */
public class ConfigPropertyType implements ADBBean
{
    /*
     * This type was generated from the piece of schema that had
     * name = ConfigPropertyType
     * Namespace URI = http://remotelabs.eng.uts.edu.au/rigclient/protocol
     * Namespace Prefix = ns1
     */

    private static final long serialVersionUID = -8434918532964128693L;

    private static String generatePrefix(final String namespace)
    {
        if (namespace.equals("http://remotelabs.eng.uts.edu.au/rigclient/protocol"))
        {
            return "ns1";
        }
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

    protected String key;

    protected String value;

    protected String stanza;
    protected boolean stanzaTracker = false;

    protected String description;
    protected boolean descriptionTracker = false;

    protected ConfigPropertyTypeEnum type;
    protected boolean typeTracker = false;

    protected String format;
    protected boolean formatTracker = false;

    protected String _default;
    protected boolean _defaultTracker = false;

    protected boolean restart;
    protected boolean localRestartTracker = false;

    protected String example;
    protected boolean exampleTracker = false;

    public String getDefault()
    {
        return this._default;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getExample()
    {
        return this.example;
    }

    public String getFormat()
    {
        return this.format;
    }

    public String getKey()
    {
        return this.key;
    }

    public OMElement getOMElement(final QName parentQName, final OMFactory factory) throws ADBException
    {
        final OMDataSource dataSource = new ADBDataSource(this, parentQName)
        {
            @Override
            public void serialize(final MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException
            {
                ConfigPropertyType.this.serialize(this.parentQName, factory, xmlWriter);
            }
        };
        return new OMSourcedElementImpl(parentQName, factory, dataSource);
    }

    @Override
    public XMLStreamReader getPullParser(final QName qName) throws ADBException
    {

        final ArrayList<Serializable> elementList = new ArrayList<Serializable>();

        elementList.add(new QName("", "key"));
        if (this.key != null)
        {
            elementList.add(ConverterUtil.convertToString(this.key));
        }
        else
        {
            throw new ADBException("key cannot be null!!");
        }

        elementList.add(new QName("", "value"));
        if (this.value != null)
        {
            elementList.add(ConverterUtil.convertToString(this.value));
        }
        else
        {
            throw new ADBException("value cannot be null!!");
        }
        
        if (this.stanzaTracker)
        {
            elementList.add(new QName("", "stanza"));
            if (this.stanza != null)
            {
                elementList.add(ConverterUtil.convertToString(this.stanza));
            }
            else
            {
                throw new ADBException("stanza cannot be null!!");
            }
        }
        
        if (this.descriptionTracker)
        {
            elementList.add(new QName("", "description"));
            if (this.description != null)
            {
                elementList.add(ConverterUtil.convertToString(this.description));
            }
            else
            {
                throw new ADBException("description cannot be null!!");
            }
        }
        
        if (this.typeTracker)
        {
            elementList.add(new QName("", "type"));

            if (this.type == null)
            {
                throw new ADBException("type cannot be null!!");
            }
            elementList.add(this.type);
        }
        
        if (this.formatTracker)
        {
            elementList.add(new QName("", "format"));
            if (this.format != null)
            {
                elementList.add(ConverterUtil.convertToString(this.format));
            }
            else
            {
                throw new ADBException("format cannot be null!!");
            }
        }
        
        if (this._defaultTracker)
        {
            elementList.add(new QName("", "default"));
            if (this._default != null)
            {
                elementList.add(ConverterUtil.convertToString(this._default));
            }
            else
            {
                throw new ADBException("default cannot be null!!");
            }
        }
        
        if (this.localRestartTracker)
        {
            elementList.add(new QName("", "restart"));
            elementList.add(ConverterUtil.convertToString(this.restart));
        }
        
        if (this.exampleTracker)
        {
            elementList.add(new QName("", "example"));
            if (this.example != null)
            {
                elementList.add(ConverterUtil.convertToString(this.example));
            }
            else
            {
                throw new ADBException("example cannot be null!!");
            }
        }

        return new ADBXMLStreamReaderImpl(qName, elementList.toArray(), new Object[0]);
    }

    public boolean getRestart()
    {
        return this.restart;
    }

    public String getStanza()
    {
        return this.stanza;
    }

    public ConfigPropertyTypeEnum getType()
    {
        return this.type;
    }

    public String getValue()
    {
        return this.value;
    }

    private String registerPrefix(final XMLStreamWriter xmlWriter, final String namespace) throws XMLStreamException
    {
        String prefix = xmlWriter.getPrefix(namespace);
        if (prefix == null)
        {
            prefix = ConfigPropertyType.generatePrefix(namespace);
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
    public void serialize(final QName parentQName, final OMFactory factory, final MTOMAwareXMLStreamWriter xmlWriter)
            throws XMLStreamException, ADBException
    {
        this.serialize(parentQName, factory, xmlWriter, false);
    }

    @Override
    public void serialize(final QName parentQName, final OMFactory factory, final MTOMAwareXMLStreamWriter xmlWriter,
            final boolean serializeType) throws XMLStreamException, ADBException
    {
        String prefix = parentQName.getPrefix();
        String namespace = parentQName.getNamespaceURI();

        if ((namespace != null) && (namespace.trim().length() > 0))
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
                    prefix = ConfigPropertyType.generatePrefix(namespace);
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
            if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0))
            {
                this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix
                        + ":ConfigPropertyType", xmlWriter);
            }
            else
            {
                this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "ConfigPropertyType",
                        xmlWriter);
            }
        }

        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null)
            {
                prefix = ConfigPropertyType.generatePrefix(namespace);
                xmlWriter.writeStartElement(prefix, "key", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            else
            {
                xmlWriter.writeStartElement(namespace, "key");
            }
        }
        else
        {
            xmlWriter.writeStartElement("key");
        }

        if (this.key == null)
        {
            throw new ADBException("key cannot be null!!");
        }
        else
        {
            xmlWriter.writeCharacters(this.key);
        }
        xmlWriter.writeEndElement();
        
        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null)
            {
                prefix = ConfigPropertyType.generatePrefix(namespace);
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
            throw new ADBException("value cannot be null!!");
        }
        else
        {
            xmlWriter.writeCharacters(this.value);
        }

        xmlWriter.writeEndElement();
        if (this.stanzaTracker)
        {
            namespace = "";
            if (!namespace.equals(""))
            {
                prefix = xmlWriter.getPrefix(namespace);
                if (prefix == null)
                {
                    prefix = ConfigPropertyType.generatePrefix(namespace);
                    xmlWriter.writeStartElement(prefix, "stanza", namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }
                else
                {
                    xmlWriter.writeStartElement(namespace, "stanza");
                }
            }
            else
            {
                xmlWriter.writeStartElement("stanza");
            }

            if (this.stanza == null)
            {
                throw new ADBException("stanza cannot be null!!");
            }
            else
            {
                xmlWriter.writeCharacters(this.stanza);
            }
            xmlWriter.writeEndElement();
        }
        
        if (this.descriptionTracker)
        {
            namespace = "";
            if (!namespace.equals(""))
            {
                prefix = xmlWriter.getPrefix(namespace);
                if (prefix == null)
                {
                    prefix = ConfigPropertyType.generatePrefix(namespace);
                    xmlWriter.writeStartElement(prefix, "description", namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }
                else
                {
                    xmlWriter.writeStartElement(namespace, "description");
                }
            }
            else
            {
                xmlWriter.writeStartElement("description");
            }

            if (this.description == null)
            {
                throw new ADBException("description cannot be null!!");
            }
            else
            {
                xmlWriter.writeCharacters(this.description);
            }
            xmlWriter.writeEndElement();
        }
        
        if (this.typeTracker)
        {
            if (this.type == null)
            {
                throw new ADBException("type cannot be null!!");
            }
            this.type.serialize(new QName("", "type"), factory, xmlWriter);
        }
        
        if (this.formatTracker)
        {
            namespace = "";
            if (!namespace.equals(""))
            {
                prefix = xmlWriter.getPrefix(namespace);
                if (prefix == null)
                {
                    prefix = ConfigPropertyType.generatePrefix(namespace);
                    xmlWriter.writeStartElement(prefix, "format", namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }
                else
                {
                    xmlWriter.writeStartElement(namespace, "format");
                }
            }
            else
            {
                xmlWriter.writeStartElement("format");
            }

            if (this.format == null)
            {
                throw new ADBException("format cannot be null!!");
            }
            else
            {
                xmlWriter.writeCharacters(this.format);
            }
            xmlWriter.writeEndElement();
        }
        
        if (this._defaultTracker)
        {
            namespace = "";
            if (!namespace.equals(""))
            {
                prefix = xmlWriter.getPrefix(namespace);
                if (prefix == null)
                {
                    prefix = ConfigPropertyType.generatePrefix(namespace);
                    xmlWriter.writeStartElement(prefix, "default", namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }
                else
                {
                    xmlWriter.writeStartElement(namespace, "default");
                }
            }
            else
            {
                xmlWriter.writeStartElement("default");
            }

            if (this._default == null)
            {
                throw new ADBException("default cannot be null!!");
            }
            else
            {
                xmlWriter.writeCharacters(this._default);
            }
            xmlWriter.writeEndElement();
        }
        
        if (this.localRestartTracker)
        {
            namespace = "";
            if (!namespace.equals(""))
            {
                prefix = xmlWriter.getPrefix(namespace);
                if (prefix == null)
                {
                    prefix = ConfigPropertyType.generatePrefix(namespace);
                    xmlWriter.writeStartElement(prefix, "restart", namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }
                else
                {
                    xmlWriter.writeStartElement(namespace, "restart");
                }
            }
            else
            {
                xmlWriter.writeStartElement("restart");
            }

            xmlWriter.writeCharacters(ConverterUtil.convertToString(this.restart));
            xmlWriter.writeEndElement();
        }
        
        if (this.exampleTracker)
        {
            namespace = "";
            if (!namespace.equals(""))
            {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null)
                {
                    prefix = ConfigPropertyType.generatePrefix(namespace);

                    xmlWriter.writeStartElement(prefix, "example", namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }
                else
                {
                    xmlWriter.writeStartElement(namespace, "example");
                }
            }
            else
            {
                xmlWriter.writeStartElement("example");
            }

            if (this.example == null)
            {
                throw new ADBException("example cannot be null!!");
            }
            else
            {
                xmlWriter.writeCharacters(this.example);
            }
            xmlWriter.writeEndElement();
        }
        
        xmlWriter.writeEndElement();
    }


    public void setDefault(final String param)
    {
        if (param != null)
        {
            this._defaultTracker = true;
        }
        else
        {
            this._defaultTracker = false;
        }

        this._default = param;
    }

    public void setDescription(final String param)
    {
        if (param != null)
        {
            this.descriptionTracker = true;
        }
        else
        {
            this.descriptionTracker = false;
        }

        this.description = param;
    }

    public void setExample(final String param)
    {
        if (param != null)
        {
            this.exampleTracker = true;
        }
        else
        {
            this.exampleTracker = false;
        }

        this.example = param;
    }

    public void setFormat(final String param)
    {
        if (param != null)
        {
            this.formatTracker = true;
        }
        else
        {
            this.formatTracker = false;
        }

        this.format = param;
    }

    public void setKey(final String param)
    {
        this.key = param;
    }

    public void setRestart(final boolean param)
    {
        this.localRestartTracker = true;
        this.restart = param;
    }

    public void setStanza(final String param)
    {
        if (param != null)
        {
            this.stanzaTracker = true;
        }
        else
        {
            this.stanzaTracker = false;
        }

        this.stanza = param;
    }

    public void setType(final ConfigPropertyTypeEnum param)
    {
        if (param != null)
        {
            this.typeTracker = true;
        }
        else
        {
            this.typeTracker = false;
        }
        this.type = param;
    }

    public void setValue(final String param)
    {
        this.value = param;
    }

    private void writeAttribute(final String prefix, final String namespace, final String attName,
            final String attValue, final XMLStreamWriter xmlWriter) throws XMLStreamException
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
        public static ConfigPropertyType parse(final XMLStreamReader reader) throws Exception
        {
            final ConfigPropertyType object = new ConfigPropertyType();
            try
            {
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null)
                {
                    final String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "type");
                    if (fullTypeName != null)
                    {
                        String nsPrefix = null;
                        if (fullTypeName.indexOf(":") > -1)
                        {
                            nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
                        }
                        nsPrefix = nsPrefix == null ? "" : nsPrefix;

                        final String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
                        if (!"ConfigPropertyType".equals(type))
                        {
                            final String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                            return (ConfigPropertyType) ExtensionMapper.getTypeObject(nsUri, type, reader);
                        }
                    }
                }
                
                reader.next();
                while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                if (reader.isStartElement() && new QName("", "key").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setKey(ConverterUtil.convertToString(content));
                    reader.next();
                }
                else
                {                 
                    throw new ADBException("Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() && new QName("", "value").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setValue(ConverterUtil.convertToString(content));
                    reader.next();
                }
                else
                {                 
                    throw new ADBException("Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() && new QName("", "stanza").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setStanza(ConverterUtil.convertToString(content));
                    reader.next();
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();
                if (reader.isStartElement() && new QName("", "description").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setDescription(ConverterUtil.convertToString(content));
                    reader.next();
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();
                if (reader.isStartElement() && new QName("", "type").equals(reader.getName()))
                {
                    object.setType(ConfigPropertyTypeEnum.Factory.parse(reader));
                    reader.next();
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();
                if (reader.isStartElement() && new QName("", "format").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setFormat(ConverterUtil.convertToString(content));
                    reader.next();
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();
                if (reader.isStartElement() && new QName("", "default").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setDefault(ConverterUtil.convertToString(content));
                    reader.next();
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();
                if (reader.isStartElement() && new QName("", "restart").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setRestart(ConverterUtil.convertToBoolean(content));
                    reader.next();
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() && new QName("", "example").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setExample(ConverterUtil.convertToString(content));
                    reader.next();
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();
                if (reader.isStartElement()) throw new ADBException("Unexpected subelement " + reader.getLocalName());
            }
            catch (final XMLStreamException e)
            {
                throw new Exception(e);
            }

            return object;
        }
    }
}
