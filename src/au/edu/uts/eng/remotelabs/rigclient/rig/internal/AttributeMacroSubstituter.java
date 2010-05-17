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

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Map.Entry;

import au.edu.uts.eng.remotelabs.rigclient.rig.IRig;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession.Session;
import au.edu.uts.eng.remotelabs.rigclient.type.RigFactory;
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
public class AttributeMacroSubstituter
{   
    /** Configured IP address to use as IP and discover hostname from. */
    private String ip;
    
    /** Configured network interface name to discover IP and hostname from. */
    private String nic;
    
    /** Logger. */
    private ILogger logger;
    
    public AttributeMacroSubstituter()
    {
        this.logger = LoggerFactory.getLoggerInstance();
        
        final IConfig conf = ConfigFactory.getInstance();
        if ((this.ip = conf.getProperty("Rig_Client_IP_Address")) != null && this.ip.length() > 0)
        {
            this.logger.info("Providing network information based on the configured IP: " + this.ip + ".");
            this.nic = null;
        }
        else if ((this.nic = conf.getProperty("Listening_Network_Interface")) != null && this.nic.length() > 0)
        {
            this.logger.info("Providing network information based on the configured network interface name " + 
                    this.nic + ".");
            this.ip = null;
        }
        else
        {
            this.ip = null;
            this.nic = null;
        }
    }
    
    /**
     * Substitutes the marcos with their run-time determined values.
     * 
     * @param str macro string
     * @return string with macros replaced
     * @throws Exception error detected network information
     */
    public String substitueMacros(String str) throws Exception
    {
        StringBuilder buf = new StringBuilder();
        IRig rig = RigFactory.getRigInstance();
        
        for (String tok : str.split("__"))
        {
            if ("IP".equalsIgnoreCase(tok))
            {
                buf.append(this.findNetworkMacroValue(false));
            }
            else if ("HOSTNAME".equalsIgnoreCase(tok))
            {
                buf.append(this.findNetworkMacroValue(true));
            }
            else if ("USER".equalsIgnoreCase(tok))
            {
                for (Entry<String, Session> user : rig.getSessionUsers().entrySet())
                {
                    if (user.getValue() == Session.MASTER)
                    {
                        buf.append(user.getKey());
                    }
                }
            }
            else if ("RIG_NAME".equalsIgnoreCase(tok))
            {
                buf.append(rig.getName());
            }
            else if ("RIG_TYPE".equalsIgnoreCase(tok))
            {
                buf.append(rig.getType());
            }
            else
            {
                buf.append(tok);
            }
        }
        
        return buf.toString();
    }
    
    /**
     * Finds and returns either the host name or IP of a network interface.
     * The network interface used is either:
     * <ol>
     *  <li>The network interface with the configured IP address, if the IP 
     *  address is set.</li>
     *  <li>The network interface with the configured device name, if the
     *  device name is configured and the IP address is not configured.</li>
     *  <li>The first iterated non-loopback network interface if neither 
     *  the network interface IP address or network device name is 
     *  configured.</li>
     * </ol>
     * 
     * @param returnHostname true if the host name is to be returned, \
     *        otherwise the IP address is returned 
     * @return host name or IP network host name or IP
     * @throws Exception error finding network information or no viable \
     *         network interface 
     */
    private String findNetworkMacroValue(boolean returnHostname) throws Exception
    {
        NetworkInterface inf = null;
        
        /* If IP configured, use the configured value. */
        if (this.ip != null)
        {
            if (returnHostname)
            {
                inf = NetworkInterface.getByInetAddress(InetAddress.getByName(this.ip));
            }
            else
            {
                return this.ip;
            }
        }
        /* If NIC configured, determined the (first)IP of the NIC. */ 
        else if (this.nic != null)
        {
            inf = NetworkInterface.getByName(this.nic);
            
        }
        /* Otherwise, use first non-loopback device. */
        else
        {
            Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
            while (nics.hasMoreElements())
            {
                inf = nics.nextElement();
                if (inf.isLoopback())
                {
                    inf = null;
                }
                else
                {
                    break;
                }
            }
        }
        
        if (inf == null)
        {
            this.logger.warn("Unable to find any viable network interfaces for network information macro substitution.");
            throw new Exception(" no network interface found");
        }
     
        Enumeration<InetAddress> addrs = inf.getInetAddresses();
        while (addrs.hasMoreElements())
        {
            InetAddress addr = addrs.nextElement();
            if (!(addr instanceof Inet4Address)) // Only interested in IPv4 currently
            {
                continue;
            }

            if (returnHostname)
            {
                return addr.getHostName();
            }
            else
            {
                return addr.getHostAddress();
            }
        }


        this.logger.warn("Unable to find an IP address for network interface " + inf.getDisplayName() + " as it" +
                " appears to have none.");
        throw new Exception(" no addresses found for interface " + inf.getDisplayName()); 
    }
}
