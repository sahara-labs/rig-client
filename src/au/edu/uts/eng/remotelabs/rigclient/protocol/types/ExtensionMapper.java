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
 * ExtensionMapper.java This file was auto-generated from WSDL by the Apache
 * Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:44 LKT)
 */

package au.edu.uts.eng.remotelabs.rigclient.protocol.types;

import javax.xml.stream.XMLStreamReader;

import org.apache.axis2.databinding.ADBException;

/**
 * ExtensionMapper class, maps a type class to its bean class implementation.
 */
public class ExtensionMapper
{

    public static Object getTypeObject(String namespaceURI, String typeName, XMLStreamReader reader) throws Exception
    {

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) && 
                "state_type1".equals(typeName))
        {
            return BatchState.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) && 
                "BatchStatusResponseType".equals(typeName))
        {
            return BatchStatusResponseType.Factory.parse(reader);
        }
        
        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) &&
                "TestIntervalRequestType".equals(typeName))
        {
            return TestIntervalRequestType.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) &&
                "StatusResponseType".equals(typeName))
        {
            return StatusResponseType.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) &&
                "PrimitiveControlResponseType".equals(typeName))
        {
            return PrimitiveControlResponseType.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) &&
                "AuthRequiredRequestType".equals(typeName))
        {
            return AuthRequiredRequestType.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) &&
                "SlaveUserType".equals(typeName))
        {
            return SlaveUserType.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) &&
                "AttributeRequestType".equals(typeName))
        {
            return AttributeRequestType.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) &&
                "NotificationRequestType".equals(typeName))
        {
            return NotificationRequestType.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) &&
                "BatchRequestType".equals(typeName))
        {
            return BatchRequestType.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) &&
                "AttributeResponseType".equals(typeName))
        {
            return AttributeResponseType.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) &&
                "state_type1".equals(typeName))
        {
            return BatchState.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) &&
                "MaintenanceRequestType".equals(typeName))
        {
            return MaintenanceRequestType.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) && 
                "UserType".equals(typeName))
        {
            return UserType.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) && 
                "ErrorType".equals(typeName))
        {
            return ErrorType.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) &&
                "OperationResponseType".equals(typeName))
        {
            return OperationResponseType.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) &&
                "PrimitiveControlRequestType".equals(typeName))
        {
            return PrimitiveControlRequestType.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) && 
                "type_type1".equals(typeName))
        {
            return TypeSlaveUser.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) && 
                "NullType".equals(typeName))
        {
            return NullType.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) &&
                "ActivityDetectableType".equals(typeName))
        {
            return ActivityDetectableType.Factory.parse(reader);
        }

        if ("http://remotelabs.eng.uts.edu.au/rigclient/protocol".equals(namespaceURI) && 
                "ParamType".equals(typeName))
        {
            return ParamType.Factory.parse(reader);
        }

        throw new ADBException("Unsupported type " + namespaceURI + " " + typeName);
    }

}
