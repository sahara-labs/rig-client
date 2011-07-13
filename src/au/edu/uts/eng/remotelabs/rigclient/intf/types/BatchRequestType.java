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
 */

package au.edu.uts.eng.remotelabs.rigclient.intf.types;

import java.util.ArrayList;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.axiom.om.OMConstants;
import org.apache.axiom.om.OMDataSource;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.impl.MTOMConstants;
import org.apache.axiom.om.impl.llom.OMSourcedElementImpl;
import org.apache.axiom.om.impl.llom.OMStAXWrapper;
import org.apache.axiom.om.util.ElementHelper;
import org.apache.axiom.soap.impl.builder.MTOMStAXSOAPModelBuilder;
import org.apache.axis2.databinding.ADBBean;
import org.apache.axis2.databinding.ADBDataSource;
import org.apache.axis2.databinding.ADBException;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl;
import org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter;

/**
 * BatchRequestType bean class.
 */
public class BatchRequestType extends AuthRequiredRequestType implements ADBBean
{
    /*
     * This type was generated from the piece of schema that had name =
     * BatchRequestType Namespace URI =
     * http://remotelabs.eng.uts.edu.au/rigclient/protocol Namespace Prefix =
     * ns1
     */

    private static final long serialVersionUID = 5682929355367496267L;
    
    protected DataHandler batchFile;
    
    protected String fileName;

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

    @Override
    public OMElement getOMElement(final QName parentQName, final OMFactory factory) throws ADBException
    {
        final OMDataSource dataSource = new ADBDataSource(this, parentQName)
        {
            @Override
            public void serialize(final MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException
            {
                BatchRequestType.this.serialize(this.parentQName, factory, xmlWriter);
            }
        };
        return new OMSourcedElementImpl(parentQName, factory, dataSource);
    }

    @Override
    public XMLStreamReader getPullParser(final QName qName) throws ADBException
    {

        final ArrayList<Object> elementList = new ArrayList<Object>();
        final ArrayList<QName> attribList = new ArrayList<QName>();

        attribList.add(new QName("http://www.w3.org/2001/XMLSchema-instance", "type"));
        attribList.add(new QName("http://remotelabs.eng.uts.edu.au/rigclient/protocol", "BatchRequestType"));
        
        if (this.identityTokenTracker)
        {
            elementList.add(new QName("", "identityToken"));
            if (this.identityToken != null)
            {
                elementList.add(ConverterUtil.convertToString(this.identityToken));
            }
            else
            {
                throw new org.apache.axis2.databinding.ADBException("identityToken cannot be null!!");
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
                throw new ADBException("requestor cannot be null!!");
            }
        }
        
        if (this.asyncTracker)
        {
            elementList.add(new QName("", "async"));
            elementList.add(ConverterUtil.convertToString(this.async));
        }
        
        elementList.add(new QName("", "batchFile"));
        elementList.add(this.batchFile);
        
        elementList.add(new QName("", "fileName"));
        if (this.fileName != null)
        {
            elementList.add(ConverterUtil.convertToString(this.fileName));
        }
        else
        {
            throw new ADBException("fileName cannot be null!!");
        }
        
        return new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }

    private String registerPrefix(final XMLStreamWriter xmlWriter, final String namespace) throws XMLStreamException
    {
        String prefix = xmlWriter.getPrefix(namespace);
        if (prefix == null)
        {
            prefix = BatchRequestType.generatePrefix(namespace);
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
    public void serialize(final QName parentQName, final OMFactory factory, final MTOMAwareXMLStreamWriter xmlWriter, final boolean serializeType)
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
                    prefix = BatchRequestType.generatePrefix(namespace);
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
                    + ":BatchRequestType", xmlWriter);
        }
        else
        {
            this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "BatchRequestType",
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
                    prefix = BatchRequestType.generatePrefix(namespace);
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
                throw new ADBException("identityToken cannot be null!!");
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
                    prefix = BatchRequestType.generatePrefix(namespace);
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
                throw new ADBException("requestor cannot be null!!");
            }
            else
            {
                xmlWriter.writeCharacters(this.requestor);
            }

            xmlWriter.writeEndElement();
        }
        
        if (this.asyncTracker)
        {
            namespace = "";
            if (!namespace.equals(""))
            {
                prefix = xmlWriter.getPrefix(namespace);
                if (prefix == null)
                {
                    prefix = BatchRequestType.generatePrefix(namespace);
                    xmlWriter.writeStartElement(prefix, "async", namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }
                else
                {
                    xmlWriter.writeStartElement(namespace, "async");
                }
            }
            else
            {
                xmlWriter.writeStartElement("async");
            }
            xmlWriter.writeCharacters(ConverterUtil.convertToString(this.async));
            xmlWriter.writeEndElement();
        }
        
        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null)
            {
                prefix = BatchRequestType.generatePrefix(namespace);
                xmlWriter.writeStartElement(prefix, "batchFile", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            else
            {
                xmlWriter.writeStartElement(namespace, "batchFile");
            }

        }
        else
        {
            xmlWriter.writeStartElement("batchFile");
        }

        if (this.batchFile != null)
        {
            xmlWriter.writeDataHandler(this.batchFile);
        }
        xmlWriter.writeEndElement();
        
        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null)
            {
                prefix = generatePrefix(namespace);
                xmlWriter.writeStartElement(prefix, "fileName", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            else
            {
                xmlWriter.writeStartElement(namespace, "fileName");
            }
        }
        else
        {
            xmlWriter.writeStartElement("fileName");
        }

        if (this.fileName == null)
        {
            throw new org.apache.axis2.databinding.ADBException("fileName cannot be null!!");
        }
        else
        {
            xmlWriter.writeCharacters(this.fileName);
        }
        xmlWriter.writeEndElement();
        
        xmlWriter.writeEndElement();
    }
    
    public DataHandler getBatchFile()
    {
        return this.batchFile;
    }

    public void setBatchFile(final DataHandler param)
    {
        this.batchFile = param;
    }
    
    public  String getFileName()
    {
        return this.fileName;
    }

    public void setFileName( String param)
    {
        this.fileName = param;
    }

    private void writeAttribute(final String prefix, final String namespace, final String attName, final String attValue, final XMLStreamWriter xmlWriter)
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
        @SuppressWarnings("deprecation")
        public static BatchRequestType parse(final XMLStreamReader reader) throws Exception
        {
            final BatchRequestType object = new BatchRequestType();
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

                        if (!"BatchRequestType".equals(type))
                        {
                            final String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                            return (BatchRequestType) ExtensionMapper.getTypeObject(nsUri, type, reader);
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
                
                if (reader.isStartElement() && new QName("", "async").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setAsync(ConverterUtil.convertToBoolean(content));
                    reader.next();
                }
                
                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }
                if (reader.isStartElement() && new QName("", "batchFile").equals(reader.getName()))
                {
                    reader.next();
                    if (BatchRequestType.isReaderMTOMAware(reader) && 
                            Boolean.TRUE.equals(reader.getProperty(OMConstants.IS_BINARY)))
                    {
                        /* MTOM aware reader - get the data handler directly and put it in the object. */
                        object.setBatchFile((DataHandler) reader.getProperty(OMConstants.DATA_HANDLER));
                    }
                    else
                    {
                        if (reader.getEventType() == XMLStreamConstants.START_ELEMENT && reader.getName().equals(
                                        new QName(MTOMConstants.XOP_NAMESPACE_URI, MTOMConstants.XOP_INCLUDE)))
                        {
                            final String id = ElementHelper.getContentID(reader);
                            object.setBatchFile(((MTOMStAXSOAPModelBuilder) ((OMStAXWrapper) reader).getBuilder()).getDataHandler(id));
                            reader.next();
                            reader.next();

                        }
                        else if (reader.hasText())
                        {
                            // Do the usual conversion
                            final String content = reader.getText();
                            object.setBatchFile(ConverterUtil.convertToBase64Binary(content));
                            reader.next();
                        }
                    }
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
                if (reader.isStartElement() && new QName("", "fileName").equals(reader.getName()))
                {
                    String content = reader.getElementText();
                    object.setFileName(ConverterUtil.convertToString(content));
                    reader.next();
                }
                else
                {
                    // A start element we are not expecting indicates an invalid
                    // parameter was passed
                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement "
                            + reader.getLocalName());
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
