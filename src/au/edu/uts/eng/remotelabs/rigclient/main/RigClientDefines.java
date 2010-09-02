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
 * @date 5th October 2009
 *
 * Changelog:
 * - 05/10/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.main;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Global definitions and convenient functions.
 */
public class RigClientDefines
{
    /** Bug reporting email. */
    public static final String RC_BUG_REPORTING_TO = "tmachet@eng.uts.edu.au (Software Engineer)\n" +
    		                                         "or mdiponio@eng.uts.edu.au (Junior Software Developer)";
    
    /** Rig Client version. */
    public static final String RC_VERSION = "0.2 Alpha";
    
    /** Rig client SOAP interface name space. */
    public static final String NAME_SPACE = "http://remotelabs.eng.uts.edu.au/rigclient/protocol";
    
    /**
     * Report a bug.
     * 
     * @param code error code
     * @param thr error exception (can be null)
     */
    public static void reportBug(final String code, final Throwable thr)
    {
        final Calendar cal = Calendar.getInstance();
        
        StringBuilder builder = new StringBuilder();
        builder.append('\n');
        builder.append("Oh, No! - Serious FATAL error.\n");
        builder.append('\n');
        builder.append("If this is not caused by using an action class that isn't valid on your platform,\n");
        builder.append("could you please file a bug report to " + RigClientDefines.RC_BUG_REPORTING_TO + ' ');
        builder.append("with the following attached:\n");
        builder.append('\n');
        builder.append("================================================================================\n");
        builder.append("== Rig Client v" + RigClientDefines.RC_VERSION + '\n');
        builder.append("== " + cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR)
                + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND) + '\n');
        builder.append("================================================================================\n");
        builder.append('\n');
        builder.append("Error code: " + code + '\n');
        builder.append('\n');
        
        /* Stack track stanza. */
        if (thr != null)
        {
            builder.append("--------------------------------------------------------------------------------\n");
            builder.append("Exception: " + thr.getClass().getCanonicalName() + '\n');
            if (thr.getMessage() != null)
            {
                builder.append("Message: " + thr.getMessage() + '\n');
            }
            builder.append("Stacktrace:\n");
            final Writer result = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(result);
            thr.printStackTrace(printWriter);
            builder.append(result.toString() + '\n');
        }
        
        builder.append('\n');
        builder.append("--------------------------------------------------------------------------------\n");
        builder.append('\n');
        
        /* Configuration stanza. */
        builder.append("################################################################################\n");
        final IConfig conf = ConfigFactory.getInstance();
        builder.append("## Configuration, " + conf.getConfigurationInfomation() + ":\n");
        builder.append('\n');
        builder.append(conf.dumpConfiguration() + '\n');
        builder.append('\n');
        builder.append("################################################################################\n");
        builder.append('\n');
        
        /* System properties stanza. */
        builder.append("################################################################################\n");
        builder.append("## System properties: \n");
        builder.append('\n');
        Properties sysProps = System.getProperties();
        for (Entry<Object, Object> prop : sysProps.entrySet())
        {
            builder.append(prop.getKey() + "=" + prop.getValue() + '\n');
        }
        builder.append('\n');
        builder.append("################################################################################\n");
        builder.append('\n');
        
        /* Environment variables stanza. */
        builder.append("################################################################################\n");
        builder.append("## Environment variables: \n");
        builder.append('\n');
        Map<String, String> env = System.getenv();
        for (Entry<String, String> e : env.entrySet())
        {
            builder.append(e.getKey() + "=" + e.getValue() + '\n');
        }
        builder.append('\n');
        builder.append("################################################################################\n");
        builder.append('\n');
        
        System.out.println(builder.toString());
        LoggerFactory.getLoggerInstance().fatal(builder.toString());
    }
    
    /**
     * Prepends a package to a class to form a valid fully qualified string. 
     *  
     * @param pack package 
     * @param clazz class 
     * @return valid qualified name
     */
    public static String prependPackage(String pack, String clazz)
    {
        StringBuilder name = new StringBuilder();
        pack = pack.trim();
        clazz = clazz.trim();
        
        name.append(pack);
        if (name.charAt(name.length() - 1) != '.')
        {
            name.append('.');
        }
        
        if (clazz.startsWith("."))
        {
            name.append(clazz.substring(1));
        }
        else
        {
            name.append(clazz);
        }
        
        return name.toString();
    }
}
