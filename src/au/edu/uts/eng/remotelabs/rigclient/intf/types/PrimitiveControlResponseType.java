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
 * PrimitiveControlResponseType.java This file was auto-generated from WSDL by
 * the Apache Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
 */

package au.edu.uts.eng.remotelabs.rigclient.intf.types;

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
 * PrimitiveControlResponseType bean class.
 */
public class PrimitiveControlResponseType extends OperationResponseType implements ADBBean
{
    /*
     * This type was generated from the piece of schema that had name =
     * PrimitiveControlResponseType Namespace URI =
     * http://remotelabs.eng.uts.edu.au/rigclient/protocol Namespace Prefix =
     * ns1
     */

    private static final long serialVersionUID = 3955492904065940469L;

    protected String wasSuccessful;

    protected boolean resultParamsTracker = false;
    protected ParamType[] resultParams;

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

    @SuppressWarnings("unchecked")
    public void addResult(final ParamType param)
    {
        if (this.resultParams == null)
        {
            this.resultParams = new ParamType[] {};
        }

        this.resultParamsTracker = true;
        final List<ParamType> list = ConverterUtil.toList(this.resultParams);
        list.add(param);
        this.resultParams = list.toArray(new ParamType[list.size()]);
    }

    @Override
    public ErrorType getError()
    {
        return this.error;
    }

    @Override
    public OMElement getOMElement(final QName parentQName, final OMFactory factory) throws ADBException
    {
        final OMDataSource dataSource = new ADBDataSource(this, parentQName)
        {
            @Override
            public void serialize(final MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException
            {
                PrimitiveControlResponseType.this.serialize(this.parentQName, factory, xmlWriter);
            }
        };
        return new OMSourcedElementImpl(parentQName, factory, dataSource);
    }

    @Override
    public XMLStreamReader getPullParser(final QName qName) throws ADBException
    {
        final ArrayList<Serializable> elementList = new ArrayList<Serializable>();
        final ArrayList<QName> attribList = new ArrayList<QName>();
        attribList.add(new QName("http://www.w3.org/2001/XMLSchema-instance", "type"));
        attribList.add(new QName("http://remotelabs.eng.uts.edu.au/rigclient/protocol",
                "PrimitiveControlResponseType"));

        elementList.add(new QName("", "success"));
        elementList.add(ConverterUtil.convertToString(this.success));
        if (this.errorTracker)
        {
            elementList.add(new QName("", "error"));

            if (this.error == null) throw new ADBException("error cannot be null!!");
            elementList.add(this.error);
        }
        
        elementList.add(new QName("", "wasSuccessful"));
        if (this.wasSuccessful != null)
        {
            elementList.add(ConverterUtil.convertToString(this.wasSuccessful));
        }
        else
        {
            throw new ADBException("wasSuccessful cannot be null!!");
        }

        if (this.resultParamsTracker)
        {
            if (this.resultParams != null)
            {
                for (final ParamType element : this.resultParams)
                {
                    if (element != null)
                    {
                        elementList.add(new QName("", "result"));
                        elementList.add(element);
                    }
                }
            }
            else
            {
                throw new ADBException("result cannot be null!!");
            }
        }

        return new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }

    public ParamType[] getResult()
    {
        return this.resultParams;
    }

    public String getWasSuccessful()
    {
        return this.wasSuccessful;
    }

    private String registerPrefix(final XMLStreamWriter xmlWriter, final String namespace) throws XMLStreamException
    {
        String prefix = xmlWriter.getPrefix(namespace);

        if (prefix == null)
        {
            prefix = PrimitiveControlResponseType.generatePrefix(namespace);
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
                    prefix = PrimitiveControlResponseType.generatePrefix(namespace);
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
                    + ":PrimitiveControlResponseType", xmlWriter);
        }
        else
        {
            this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "PrimitiveControlResponseType", xmlWriter);
        }

        /* Success element. */
        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = PrimitiveControlResponseType.generatePrefix(namespace);

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
        xmlWriter.writeCharacters(ConverterUtil.convertToString(this.success));
        xmlWriter.writeEndElement();

        /* Error element. */
        if (this.errorTracker)
        {
            if (this.error == null) throw new ADBException("error cannot be null!!");
            this.error.serialize(new QName("", "error"),
                    factory, xmlWriter);
        }
        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = PrimitiveControlResponseType.generatePrefix(namespace);

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

        /* Was successful string. */
        if (this.wasSuccessful == null)
        {
            throw new ADBException("wasSuccessful cannot be null!!");
        }
        else
        {
            xmlWriter.writeCharacters(this.wasSuccessful);
        }
        xmlWriter.writeEndElement();
        
        /* Results list. */
        if (this.resultParamsTracker)
        {
            if (this.resultParams != null)
            {
                for (final ParamType element : this.resultParams)
                {
                    if (element != null)
                    {
                        element.serialize(new QName("", "result"), factory, xmlWriter);
                    }
                }
            }
            else
            {
                throw new ADBException("result cannot be null!!");
            }
        }
        
        xmlWriter.writeEndElement();
    }

    @Override
    public void setError(final ErrorType param)
    {
        if (param != null)
        {
            this.errorTracker = true;
        }
        else
        {
            this.errorTracker = false;
        }
        this.error = param;
    }

    public void setResult(final ParamType[] param)
    {
        if (param != null)
        {
            this.resultParamsTracker = true;
        }
        else
        {
            this.resultParamsTracker = false;
        }
        this.resultParams = param;
    }

    public void setWasSuccessful(final String param)
    {
        this.wasSuccessful = param;
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
        public static PrimitiveControlResponseType parse(final XMLStreamReader reader) throws Exception
        {
            final PrimitiveControlResponseType object = new PrimitiveControlResponseType();
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
                        if (fullTypeName.indexOf(":") > -1)
                        {
                            nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
                        }
                        nsPrefix = nsPrefix == null ? "" : nsPrefix;
                        final String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
                        if (!"PrimitiveControlResponseType".equals(type))
                        {
                            final String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                            return (PrimitiveControlResponseType) ExtensionMapper.getTypeObject(nsUri, type, reader);
                        }
                    }
                }

                reader.next();
                final ArrayList<ParamType> resultList = new ArrayList<ParamType>();
                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement() && new QName("", "success").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setSuccess(ConverterUtil.convertToBoolean(content));
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
                if (reader.isStartElement()
                        && new QName("", "error").equals(reader.getName()))
                {
                    object.setError(ErrorType.Factory.parse(reader));
                    reader.next();
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }
                if (reader.isStartElement() && new QName("", "wasSuccessful").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setWasSuccessful(ConverterUtil.convertToString(content));
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

                if (reader.isStartElement() && new QName("", "result").equals(reader.getName()))
                {
                    resultList.add(ParamType.Factory.parse(reader));
                    boolean noMoreSiblings = false;
                    while (!noMoreSiblings)
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
                            noMoreSiblings = true;
                        }
                        else
                        {
                            if (new QName("", "result").equals(reader.getName()))
                            {
                                resultList.add(ParamType.Factory.parse(reader));
                            }
                            else
                            {
                                noMoreSiblings = true;
                            }
                        }
                    }
                    object.setResult((ParamType[]) ConverterUtil.convertToArray(ParamType.class, resultList));
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
