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
 * BatchStatusResponseType.java This file was auto-generated from WSDL by the
 * Apache Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
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
 * BatchStatusResponseType bean class.
 */
public class BatchStatusResponseType implements ADBBean
{
    /*
     * This type was generated from the piece of schema that had name =
     * BatchStatusResponseType Namespace URI =
     * http://remotelabs.eng.uts.edu.au/rigclient/protocol Namespace Prefix =
     * ns1
     */

    private static final long serialVersionUID = -3154318364201993566L;
    
    protected BatchState state;
    
    protected String progress;
    
    protected boolean resultFilePathTracker = false;
    protected String[] resultFilePath;

    private static String generatePrefix(String namespace)
    {
        if (namespace.equals("http://remotelabs.eng.uts.edu.au/rigclient/protocol")) return "ns1";
        return BeanUtil.getUniquePrefix();
    }

    public OMElement getOMElement(final QName parentQName, final OMFactory factory) throws ADBException
    {
        OMDataSource dataSource = new ADBDataSource(this, parentQName)
        {
            @Override
            public void serialize(MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException
            {
                BatchStatusResponseType.this.serialize(this.parentQName, factory, xmlWriter);
            }
        };
        return new OMSourcedElementImpl(parentQName, factory, dataSource);
    }

    public XMLStreamReader getPullParser(QName qName) throws ADBException
    {
        ArrayList<Serializable> elementList = new ArrayList<Serializable>();
        ArrayList<QName> attribList = new ArrayList<QName>();

        elementList.add(new QName("", "state"));
        if (this.state == null)
        {
            throw new ADBException("state cannot be null!!");
        }
        elementList.add(this.state);

        elementList.add(new QName("", "progress"));
        if (this.progress != null)
        {
            elementList.add(ConverterUtil.convertToString(this.progress));
        }
        else
        {
            throw new ADBException("progress cannot be null!!");
        }
        
        if (this.resultFilePathTracker)
        {
            if (this.resultFilePath != null)
            {
                for (String element : this.resultFilePath)
                {
                    if (element != null)
                    {
                        elementList.add(new QName("", "resultFilePath"));
                        elementList.add(ConverterUtil.convertToString(element));
                    }
                }
            }
            else
            {
                throw new ADBException("resultFilePath cannot be null!!");
            }
        }

        return new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }

    private String registerPrefix(XMLStreamWriter xmlWriter, String namespace) throws XMLStreamException
    {
        String prefix = xmlWriter.getPrefix(namespace);
        if (prefix == null)
        {
            prefix = BatchStatusResponseType.generatePrefix(namespace);
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
        String prefix = parentQName.getPrefix();
        String namespace = parentQName.getNamespaceURI();

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
                    prefix = BatchStatusResponseType.generatePrefix(namespace);
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

            String namespacePrefix = this.registerPrefix(xmlWriter, "http://remotelabs.eng.uts.edu.au/rigclient/protocol");
            if (namespacePrefix != null && namespacePrefix.trim().length() > 0)
            {
                this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", namespacePrefix
                        + ":BatchStatusResponseType", xmlWriter);
            }
            else
            {
                this.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
                        "BatchStatusResponseType", xmlWriter);
            }

        }

        if (this.state == null)
        {
            throw new ADBException("state cannot be null!!");
        }
        this.state.serialize(new QName("", "state"), factory, xmlWriter);

        namespace = "";
        if (!namespace.equals(""))
        {
            prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null)
            {
                prefix = BatchStatusResponseType.generatePrefix(namespace);
                xmlWriter.writeStartElement(prefix, "progress", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            else
            {
                xmlWriter.writeStartElement(namespace, "progress");
            }
        }
        else
        {
            xmlWriter.writeStartElement("progress");
        }

        if (this.progress == null)
        {
            throw new ADBException("progress cannot be null!!");
        }
        else
        {
            xmlWriter.writeCharacters(this.progress);
        }
        xmlWriter.writeEndElement();
        
        if (this.resultFilePathTracker)
        {
            if (this.resultFilePath != null)
            {
                namespace = "";
                boolean emptyNamespace = namespace == null || namespace.length() == 0;
                prefix = emptyNamespace ? null : xmlWriter.getPrefix(namespace);
                for (String element : this.resultFilePath)
                {
                    if (element != null)
                    {
                        if (!emptyNamespace)
                        {
                            if (prefix == null)
                            {
                                String prefix2 = BatchStatusResponseType.generatePrefix(namespace);
                                xmlWriter.writeStartElement(prefix2, "resultFilePath", namespace);
                                xmlWriter.writeNamespace(prefix2, namespace);
                                xmlWriter.setPrefix(prefix2, namespace);
                            }
                            else
                            {
                                xmlWriter.writeStartElement(namespace, "resultFilePath");
                            }
                        }
                        else
                        {
                            xmlWriter.writeStartElement("resultFilePath");
                        }

                        xmlWriter.writeCharacters(ConverterUtil.convertToString(element));
                        xmlWriter.writeEndElement();
                    }
                }
            }
            else
            {
                throw new ADBException("resultFilePath cannot be null!!");
            }
        }
        xmlWriter.writeEndElement();
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
    
    public String getProgress()
    {
        return this.progress;
    }
    
    public String[] getResultFilePath()
    {
        return this.resultFilePath;
    }
    
    public BatchState getState()
    {
        return this.state;
    }
    
    @SuppressWarnings("unchecked")
    public void addResultFilePath(String param)
    {
        if (this.resultFilePath == null)
        {
            this.resultFilePath = new String[]{};
        }
        this.resultFilePathTracker = true;
        
        List<String> list = (List<String>)ConverterUtil.toList(this.resultFilePath);
        list.add(param);
        this.resultFilePath = list.toArray(new String[list.size()]);
    }

    public void setProgress(String param)
    {
        this.progress = param;
    }

    public void setResultFilePath(String[] param)
    {
        if (param != null)
        {
            this.resultFilePathTracker = true;
        }
        else
        {
            this.resultFilePathTracker = false;
        }
        this.resultFilePath = param;
    }

    public void setState(BatchState param)
    {
        this.state = param;
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
        public static BatchStatusResponseType parse(XMLStreamReader reader) throws Exception
        {
            BatchStatusResponseType object = new BatchStatusResponseType();
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
                        if (!"BatchStatusResponseType".equals(type))
                        {
                            // find namespace for the prefix
                            String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                            return (BatchStatusResponseType) ExtensionMapper.getTypeObject(nsUri, type, reader);
                        }
                    }
                }
                reader.next();

                ArrayList<String> files = new ArrayList<String>();
                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                if (reader.isStartElement() && new QName("", "state").equals(reader.getName()))
                {
                    object.setState(BatchState.Factory.parse(reader));
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

                if (reader.isStartElement() && new QName("", "progress").equals(reader.getName()))
                {
                    String content = reader.getElementText();
                    object.setProgress(ConverterUtil.convertToString(content));
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

                if (reader.isStartElement() && new QName("", "resultFilePath").equals(reader.getName()))
                {

                    files.add(reader.getElementText());
                    boolean parentElement = false;
                    while (!parentElement)
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
                            // two continuous end elements means we are exiting
                            // the xml structure
                            parentElement = true;
                        }
                        else
                        {
                            if (new QName("", "resultFilePath").equals(reader.getName()))
                            {
                                files.add(reader.getElementText());
                            }
                            else
                            {
                                parentElement = true;
                            }
                        }
                    }

                    object.setResultFilePath(files.toArray(new String[files.size()]));
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
