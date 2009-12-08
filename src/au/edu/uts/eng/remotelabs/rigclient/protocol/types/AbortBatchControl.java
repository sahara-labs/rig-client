/**
 * AbortBatchControl.java This file was auto-generated from WSDL by the Apache
 * Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
 */

package au.edu.uts.eng.remotelabs.rigclient.protocol.types;

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
import org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter;

/**
 * AbortBatchControl bean class.
 */
public class AbortBatchControl implements ADBBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 2577817300838449874L;

    public static final QName MY_QNAME = new QName("http://remotelabs.eng.uts.edu.au/rigclient/protocol",
            "abortBatchControl", "ns1");

    private static String generatePrefix(String namespace)
    {
        if (namespace.equals("http://remotelabs.eng.uts.edu.au/rigclient/protocol")) return "ns1";
        return BeanUtil.getUniquePrefix();
    }

    /**
     * isReaderMTOMAware
     * 
     * @return true if the reader supports MTOM
     */
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

    /**
     * field for AbortBatchControl
     */

    protected AuthRequiredRequestType localAbortBatchControl;

    /**
     * Auto generated getter method
     * 
     * @return AuthRequiredRequestType
     */
    public AuthRequiredRequestType getAbortBatchControl()
    {
        return this.localAbortBatchControl;
    }

    /**
     * @param parentQName
     * @param factory
     * @return OMElement
     */
    public OMElement getOMElement(final QName parentQName, final OMFactory factory) throws ADBException
    {

        OMDataSource dataSource = new ADBDataSource(this, AbortBatchControl.MY_QNAME)
        {

            @Override
            public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                    throws XMLStreamException
            {
                AbortBatchControl.this.serialize(AbortBatchControl.MY_QNAME, factory, xmlWriter);
            }
        };
        return new OMSourcedElementImpl(AbortBatchControl.MY_QNAME, factory, dataSource);

    }

    /**
     * databinding method to get an XML representation of this object
     */
    public XMLStreamReader getPullParser(QName qName) throws ADBException
    {

        // We can safely assume an element has only one type associated with it
        return this.localAbortBatchControl.getPullParser(AbortBatchControl.MY_QNAME);

    }

    /**
     * Register a namespace prefix
     */
    @SuppressWarnings("unused")
    private String registerPrefix(XMLStreamWriter xmlWriter, String namespace) throws XMLStreamException
    {
        String prefix = xmlWriter.getPrefix(namespace);

        if (prefix == null)
        {
            prefix = AbortBatchControl.generatePrefix(namespace);

            while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null)
            {
                prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
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

    public void serialize(final QName parentQName, final OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter,
            boolean serializeType) throws XMLStreamException, ADBException
    {
        if (this.localAbortBatchControl == null) throw new ADBException("Property cannot be null!");
        this.localAbortBatchControl.serialize(AbortBatchControl.MY_QNAME, factory, xmlWriter);

    }

    /**
     * Auto generated setter method
     * 
     * @param param AbortBatchControl
     */
    public void setAbortBatchControl(AuthRequiredRequestType param)
    {

        this.localAbortBatchControl = param;

    }
    
     /**
     * Factory class that contains the parse method.
     */
    public static class Factory
    {
        /**
         * static method to create the object Precondition: If this object is an
         * element, the current or next start element starts this object and any
         * intervening reader events are ignorable If this object is not an
         * element, it is a complex type and the reader is at the event just
         * after the outer start element Postcondition: If this object is an
         * element, the reader is positioned at its end element If this object
         * is a complex type, the reader is positioned at the end element of its
         * outer element
         */
        public static AbortBatchControl parse(XMLStreamReader reader) throws Exception
        {
            AbortBatchControl object = new AbortBatchControl();
            try
            {
                while (!reader.isStartElement() && !reader.isEndElement())
                {
                    reader.next();
                }

                while (!reader.isEndElement())
                {
                    if (reader.isStartElement())
                    {
                        if (reader.isStartElement()
                                && new QName("http://remotelabs.eng.uts.edu.au/rigclient/protocol", "abortBatchControl")
                                        .equals(reader.getName()))
                        {

                            object.setAbortBatchControl(AuthRequiredRequestType.Factory.parse(reader));

                        } // End of if for expected property start element
                        else
                        {
                            // A start element we are not expecting indicates an
                            // invalid parameter was passed
                            throw new ADBException("Unexpected subelement " + reader.getLocalName());
                        }

                    }
                    else
                    {
                        reader.next();
                    }
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
