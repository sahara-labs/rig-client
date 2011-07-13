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
 * TypeSlaveUser.java This file was auto-generated from WSDL by the Apache Axis2
 * version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
 */

package au.edu.uts.eng.remotelabs.rigclient.intf.types;

import java.util.HashMap;

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
import org.apache.axis2.databinding.utils.reader.ADBXMLStreamReader;
import org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl;
import org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter;

/**
 * TypeSlaveUser bean class.
 */
public class TypeSlaveUser implements ADBBean
{
    private static final long serialVersionUID = -6001751344270049315L;

    public static final QName MY_QNAME = new QName("http://remotelabs.eng.uts.edu.au/rigclient/protocol", "type_type1",
            "ns1");

    protected String slaveUserType;

    private static HashMap<String, TypeSlaveUser> _table_ = new HashMap<String, TypeSlaveUser>();

    public static final String _Active = ConverterUtil.convertToString("Active");
    public static final String _Passive = ConverterUtil.convertToString("Passive");

    public static final TypeSlaveUser Active = new TypeSlaveUser(TypeSlaveUser._Active, true);
    public static final TypeSlaveUser Passive = new TypeSlaveUser(TypeSlaveUser._Passive, true);

    protected TypeSlaveUser(final String value, final boolean isRegisterValue)
    {
        this.slaveUserType = value;
        if (isRegisterValue)
        {
            TypeSlaveUser._table_.put(this.slaveUserType, this);
        }
    }

    public TypeSlaveUser(final String value)
    {
        this(value, false);
    }

    private static String generatePrefix(final String namespace)
    {
        if (namespace.equals("http://remotelabs.eng.uts.edu.au/rigclient/protocol")) return "ns1";
        return BeanUtil.getUniquePrefix();
    }

    @SuppressWarnings("deprecation")
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

    public OMElement getOMElement(final QName parentQName, final OMFactory factory) throws ADBException
    {
        final OMDataSource dataSource = new ADBDataSource(this, TypeSlaveUser.MY_QNAME)
        {
            @Override
            public void serialize(final MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException
            {
                TypeSlaveUser.this.serialize(TypeSlaveUser.MY_QNAME, factory, xmlWriter);
            }
        };
        return new OMSourcedElementImpl(TypeSlaveUser.MY_QNAME, factory, dataSource);
    }

    @Override
    public XMLStreamReader getPullParser(final QName qName) throws ADBException
    {

        return new ADBXMLStreamReaderImpl(TypeSlaveUser.MY_QNAME, new Object[] { ADBXMLStreamReader.ELEMENT_TEXT,
                ConverterUtil.convertToString(this.slaveUserType) }, null);
    }

    private String registerPrefix(final XMLStreamWriter xmlWriter, final String namespace) throws XMLStreamException
    {
        String prefix = xmlWriter.getPrefix(namespace);
        if (prefix == null)
        {
            prefix = TypeSlaveUser.generatePrefix(namespace);
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
        final String namespace = parentQName.getNamespaceURI();
        final String localName = parentQName.getLocalPart();

        if (!namespace.equals(""))
        {
            String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null)
            {
                prefix = TypeSlaveUser.generatePrefix(namespace);
                xmlWriter.writeStartElement(prefix, localName, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            else
            {
                xmlWriter.writeStartElement(namespace, localName);
            }
        }
        else
        {
            xmlWriter.writeStartElement(localName);
        }

        if (serializeType)
        {
            final String namespacePrefix = this.registerPrefix(xmlWriter,
                    "http://remotelabs.eng.uts.edu.au/rigclient/protocol");
            if (namespacePrefix != null && namespacePrefix.trim().length() > 0)
            {
                this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix
                        + ":type_type1", xmlWriter);
            }
            else
            {
                this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "type_type1",
                                xmlWriter);
            }
        }

        if (this.slaveUserType == null)
        {
            throw new ADBException("Value cannot be null !!");
        }
        else
        {
            xmlWriter.writeCharacters(this.slaveUserType);
        }
        xmlWriter.writeEndElement();
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

    public String getValue()
    {
        return this.slaveUserType;
    }

    @Override
    public boolean equals(final Object obj)
    {
        return obj == this;
    }

    @Override
    public int hashCode()
    {
        return this.toString().hashCode();
    }

    @Override
    public String toString()
    {
        return this.slaveUserType.toString();
    }

    public static class Factory
    {
        public static TypeSlaveUser fromString(final String value, final String namespaceURI) throws IllegalArgumentException
        {
            try
            {
                return Factory.fromValue(ConverterUtil.convertToString(value));
            }
            catch (final Exception e)
            {
                throw new IllegalArgumentException();
            }
        }

        public static TypeSlaveUser fromString(final XMLStreamReader xmlStreamReader, final String content)
        {
            if (content.indexOf(":") > -1)
            {
                final String prefix = content.substring(0, content.indexOf(":"));
                final String namespaceUri = xmlStreamReader.getNamespaceContext().getNamespaceURI(prefix);
                return TypeSlaveUser.Factory.fromString(content, namespaceUri);
            }
            else
            {
                return TypeSlaveUser.Factory.fromString(content, "");
            }
        }

        public static TypeSlaveUser fromValue(final String value) throws IllegalArgumentException
        {
            final TypeSlaveUser enumeration = TypeSlaveUser._table_.get(value);

            if (enumeration == null) throw new IllegalArgumentException();
            return enumeration;
        }

        public static TypeSlaveUser parse(final XMLStreamReader reader) throws Exception
        {
            TypeSlaveUser object = null;

            String prefix = "";
            String namespaceuri = "";
            try
            {
                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }
                while (!reader.isEndElement())
                {
                    if (reader.isStartElement() || reader.hasText())
                    {
                        final String content = reader.getElementText();
                        if (content.indexOf(":") > 0)
                        {
                            prefix = content.substring(0, content.indexOf(":"));
                            namespaceuri = reader.getNamespaceURI(prefix);
                            object = TypeSlaveUser.Factory.fromString(content, namespaceuri);
                        }
                        else
                        {
                            object = TypeSlaveUser.Factory.fromString(content, "");
                        }
                    }
                    else
                    {
                        reader.next();
                    }
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
