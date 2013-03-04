/**
 * SAHARA Scheduling Server
 * Schedules and assigns local laboratory rigs.
 * 
 * @license See LICENSE in the top level directory for complete license terms.
 * 
 * Copyright (c) 2010, University of Technology, Sydney
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
 * @date 29th January 2013
 */

package au.edu.uts.eng.remotelabs.rigclient.status.types;

import java.util.ArrayList;
import java.util.Calendar;

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
 * SessionFile bean class.
 */
public class SessionFile implements ADBBean
{
    /*
     * This type was generated from the piece of schema that had
     * name = SessionFile
     * Namespace URI = http://remotelabs.eng.uts.edu.au/schedserver/localrigprovider
     * Namespace Prefix = ns1
     */

    private static final long serialVersionUID = -9039062519199403815L;

    private static String generatePrefix(final String namespace)
    {
        if (namespace.equals("http://remotelabs.eng.uts.edu.au/schedserver/localrigprovider"))
        {
            return "ns1";
        }
        return BeanUtil.getUniquePrefix();
    }

    protected String name;

    public String getName()
    {
        return this.name;
    }

    public void setName(final String param)
    {
        this.name = param;
    }

    protected String path;

    public String getPath()
    {
        return this.path;
    }

    public void setPath(final String param)
    {
        this.path = param;
    }

    protected SessionFileTransfer transfer;

    public SessionFileTransfer getTransfer()
    {
        return this.transfer;
    }

    public void setTransfer(final SessionFileTransfer param)
    {
        this.transfer = param;
    }

    protected Calendar timestamp;

    public Calendar getTimestamp()
    {
        return this.timestamp;
    }

    public void setTimestamp(final Calendar param)
    {
        this.timestamp = param;
    }

    protected DataHandler file;
    protected boolean fileTracker = false;

    public DataHandler getFile()
    {
        return this.file;
    }

    public void setFile(final DataHandler param)
    {
        this.file = param;
        this.fileTracker = param != null;
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
        final OMDataSource dataSource = new ADBDataSource(this, parentQName)
        {
            @Override
            public void serialize(final MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException
            {
                SessionFile.this.serialize(this.parentQName, factory, xmlWriter);
            }
        };
        return new OMSourcedElementImpl(parentQName, factory, dataSource);
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
                    prefix = SessionFile.generatePrefix(namespace);
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
                    "http://remotelabs.eng.uts.edu.au/schedserver/localrigprovider");
            if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0))
            {
                this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix
                        + ":SessionFile", xmlWriter);
            }
            else
            {
                this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "SessionFile",
                        xmlWriter);
            }

        }

        xmlWriter.writeStartElement("name");
        if (this.name == null)
        {
            throw new ADBException("name cannot be null!!");
        }
        else
        {
            xmlWriter.writeCharacters(this.name);
        }
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement("path");
        if (this.path == null)
        {
            throw new ADBException("path cannot be null!!");
        }
        else
        {
            xmlWriter.writeCharacters(this.path);
        }
        xmlWriter.writeEndElement();

        if (this.transfer == null)
        {
            throw new ADBException("transfer cannot be null!!");
        }
        this.transfer.serialize(new QName("", "transfer"), factory, xmlWriter);

        xmlWriter.writeStartElement("timestamp");
        if (this.timestamp == null)
        {
            throw new ADBException("timestamp cannot be null!!");
        }
        else
        {
            xmlWriter.writeCharacters(ConverterUtil.convertToString(this.timestamp));
        }
        xmlWriter.writeEndElement();
        
        if (this.fileTracker)
        {
            xmlWriter.writeStartElement("file");
            if (this.file != null)
            {
                xmlWriter.writeDataHandler(this.file);
            }
            xmlWriter.writeEndElement();
        }
        
        xmlWriter.writeEndElement();
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

    private String registerPrefix(final XMLStreamWriter xmlWriter, final String namespace) throws XMLStreamException
    {
        String prefix = xmlWriter.getPrefix(namespace);
        if (prefix == null)
        {
            prefix = SessionFile.generatePrefix(namespace);

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
    public XMLStreamReader getPullParser(final QName qName) throws ADBException
    {

        final ArrayList<Object> elementList = new ArrayList<Object>();

        elementList.add(new QName("", "name"));
        if (this.name != null)
        {
            elementList.add(ConverterUtil.convertToString(this.name));
        }
        else
        {
            throw new ADBException("name cannot be null!!");
        }

        elementList.add(new QName("", "path"));
        if (this.path != null)
        {
            elementList.add(ConverterUtil.convertToString(this.path));
        }
        else
        {
            throw new ADBException("path cannot be null!!");
        }

        elementList.add(new QName("", "transfer"));
        if (this.transfer == null)
        {
            throw new ADBException("transfer cannot be null!!");
        }
        elementList.add(this.transfer);

        elementList.add(new QName("", "timestamp"));
        if (this.timestamp != null)
        {
            elementList.add(ConverterUtil.convertToString(this.timestamp));
        }
        else
        {
            throw new ADBException("timestamp cannot be null!!");
        }
        
        if (this.fileTracker)
        {
            elementList.add(new QName("", "file"));
            elementList.add(this.file);
        }

        return new ADBXMLStreamReaderImpl(qName, elementList.toArray(), new Object[0]);
    }

    public static class Factory
    {
        @SuppressWarnings("deprecation")
        public static SessionFile parse(final XMLStreamReader reader) throws Exception
        {
            final SessionFile object = new SessionFile();

            try
            {
                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

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

                        if (!"SessionFile".equals(type))
                        {             
                            final String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                            return (SessionFile) ExtensionMapper.getTypeObject(nsUri, type, reader);
                        }
                    }
                }

                reader.next();
                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement() && new QName("", "name").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setName(ConverterUtil.convertToString(content));
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
                if (reader.isStartElement() && new QName("", "path").equals(reader.getName()))
                {

                    final String content = reader.getElementText();
                    object.setPath(ConverterUtil.convertToString(content));
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

                if (reader.isStartElement() && new QName("", "transfer").equals(reader.getName()))
                {
                    object.setTransfer(SessionFileTransfer.Factory.parse(reader));
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

                if (reader.isStartElement() && new QName("", "timestamp").equals(reader.getName()))
                {
                    final String content = reader.getElementText();
                    object.setTimestamp(ConverterUtil.convertToDateTime(content));
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

                if (reader.isStartElement() && new QName("", "file").equals(reader.getName()))
                {
                    reader.next();
                    if (SessionFile.isReaderMTOMAware(reader)
                            && Boolean.TRUE.equals(reader.getProperty(OMConstants.IS_BINARY)))
                    {
                        //MTOM aware reader - get the datahandler directly and put it in the object
                        object.setFile((DataHandler) reader.getProperty(OMConstants.DATA_HANDLER));
                    }
                    else
                    {
                        if (reader.getEventType() == XMLStreamConstants.START_ELEMENT
                                && reader.getName().equals(
                                        new QName(MTOMConstants.XOP_NAMESPACE_URI, MTOMConstants.XOP_INCLUDE)))
                        {
                            final String id = ElementHelper.getContentID(reader, "UTF-8");
                            object.setFile(((MTOMStAXSOAPModelBuilder) ((OMStAXWrapper) reader).getBuilder())
                                    .getDataHandler(id));
                            reader.next();
                            reader.next();

                        }
                        else if (reader.hasText())
                        {
                            final String content = reader.getText();
                            object.setFile(ConverterUtil.convertToBase64Binary(content));
                            reader.next();
                        }
                    }

                    reader.next();
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
