/**
 * SAHARA Rig Client
 * 
 * Software abstraction of physical rig to provide rig session control
 * and rig device control. Automatically tests rig hardware and reports
 * the rig status to ensure rig goodness.
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
 * @date 14th December 2010
 *
 * Changelog:
 * - 14/01/2010 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.internal;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Macro substituter for rig client information discovery
 * (<code>getAttribute</code> call).
 * <p />
 * Substitutes macro variables into a string.  All variables must be prefixed
 * with '<code>--</code>' and end in '<code>--<code>'. The following is the 
 * list of macro variable name and macro value pairs.
 * <ul>
 *      <li><code>__HOSTNAME__</code> - </li>
 *      <li><code>__IP__</code> - </li>
 * </ul>
 *  The algorithm for determining the values is:
 * 
 *     1. If the property 'Rig_Client_IP_Address' is set, the __IP__ value is
 *        the configured value, and the __HOSTNAME__ value is determined from
 *        that IP.
 *     2. If the 'Listening_Network_Interface' property is set, the __IP__
 *        and __HOSTNAME__ values are determined from the configured network
 *        interface name.
 *     3. If neither are set, the __IP__ and __HOSTNAME__ are determined from
 *        the first iterated network interface (eth0 on Linux systems).
 */
public class AttributeMacroSubstituer
{ 
    
    /** Configured IP address. */
    private String ip;
    
    /** Configured network interface name. */
    private String nic;
    
    /** Logger. */
    private ILogger logger;
    
    public AttributeMacroSubstituer()
    {
        this.logger = LoggerFactory.getLoggerInstance();
        
        final IConfig conf = ConfigFactory.getInstance();
        if ((this.ip = conf.getProperty("")) != null)
        {
            this.logger.info("Providing network information based on the configured IP: " + this.ip + ".");
        }
        else if ((this.nic = conf.getProperty("")) != null)
        {
            this.logger.info("Providing network information based on the configured network interface name " + 
                    this.nic + ".");
        }
    }
    
    public String substitueMacros(String str)
    {
        StringBuilder buf = new StringBuilder();
        
        for (String tok : str.split("__"))
        {
            if (tok.equalsIgnoreCase("IP"))
            {
                NetworkInterface inf;
            }
        }
        
        
        return buf.toString();
    }
}
