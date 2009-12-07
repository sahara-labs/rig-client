/**
 * ExtensionMapper.java This file was auto-generated from WSDL by the Apache
 * Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
 */

package au.edu.uts.eng.remotelabs.rigclient.protocol.types;

/**
 * ExtensionMapper class
 */

public class ExtensionMapper
{

    public static java.lang.Object getTypeObject(java.lang.String namespaceURI, java.lang.String typeName,
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception
    {

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI)
                && "state_type1".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.State_type1.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI)
                && "BatchStatusResponseType".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.BatchStatusResponseType.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI)
                && "TestIntervalRequestType".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.TestIntervalRequestType.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI)
                && "StatusResponseType".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.StatusResponseType.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI)
                && "PrimitiveControlResponseType".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.PrimitiveControlResponseType.Factory
                    .parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI)
                && "SlaveUserType".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveUserType.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI)
                && "AttributeRequestType".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.AttributeRequestType.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI)
                && "NotificationRequestType".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.NotificationRequestType.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI)
                && "BatchRequestType".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.BatchRequestType.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI)
                && "AttributeResponseType".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.AttributeResponseType.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI)
                && "MaintenanceRequestType".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.MaintenanceRequestType.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI)
                && "state_type1".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.State_type1.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) && "UserType".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.UserType.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) && "ErrorType".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.ErrorType.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI)
                && "OperationResponseType".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.OperationResponseType.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI)
                && "PrimitiveControlRequestType".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.PrimitiveControlRequestType.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) && "type_type1".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.TypeSlaveUser.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) && "NullType".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.NullType.Factory.parse(reader);

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) && "ParamType".equals(typeName))
            return au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType.Factory.parse(reader);

        throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
    }

}
