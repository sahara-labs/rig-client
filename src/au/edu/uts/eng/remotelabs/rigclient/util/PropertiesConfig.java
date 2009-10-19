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
 * @author Michael Diponio mdiponio
 * @date 5th October 2009
 *
 * Changelog:
 * - 05/10/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.util;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Java properties configuration. By default uses
 * <code>config/rigclient.properties</code>.
 */
public class PropertiesConfig implements IConfig
{
    /** Location of default properties file. */
    private static final String DEFAULT_CONFIG_FILE = "config/rigclient.properties";

    /** Java Properties class. */
    private Properties prop = null;
    
    /** Loaded configuration file. */
    private String configFile;

    /**
     * Loads configuration from the <code>config/rigclient.properties</code>
     * file.
     */
    public PropertiesConfig()
    {
        this(PropertiesConfig.DEFAULT_CONFIG_FILE);
    }
    
    /**
     * Loads configuration from the <code>filename</code> parameter.
     *  
     * @param filename properties file
     */
    public PropertiesConfig(final String filename)
    {
        this.configFile = filename;
        this.prop = new Properties();
        try
        {
            this.prop.load(new FileInputStream(filename));
        }
        catch (Exception ex)
        {
            System.err.println("Failed to load configuration file.");
        }
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.util.IConfig#getProperty(String key)
     */
    @Override
    public String getProperty(final String key)
    {
        return this.prop.getProperty(key);
    }
    
    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.util.IConfig#getAllProperties()
     */
    @Override
    public Map<String, String> getAllProperties()
    {
        final Map<String, String> all = new HashMap<String, String>();
        
        for (Object k : this.prop.keySet())
        {
            final Object v = this.prop.get(k);
            if (k instanceof String && v instanceof String)
            {
                all.put((String)k, (String)v);
            }
        }
        
        return all;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.util.IConfig#setProperty(String key, String value)
     */
    @Override
    public void setProperty(final String key, final String value)
    {
        this.prop.setProperty(key, value);
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.util.IConfig#reload()
     */
    @Override
    public synchronized void reload()
    {
        // TODO PropertiesConfig->reload implement properties configuration reloading
        throw new UnsupportedOperationException();        
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.util.IConfig#serialise()
     */
    @Override
    public synchronized void serialise()
    {
        // TODO ProperitesConfig->serialise implement properties configuration serialisation
        throw new UnsupportedOperationException();
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.util.IConfig#getConfigurationInfomation()
     */
    @Override
    public String getConfigurationInfomation()
    {
        return "properties configuration file: " + this.configFile;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.util.IConfig#dumpConfiguration()
     */
    @Override
    public String dumpConfiguration()
    {
        final StringBuffer buf = new StringBuffer();
        for (Object o : this.prop.keySet())
        {
            buf.append(o);
            buf.append(" ");
            buf.append(this.prop.get(o));
            buf.append('\n');
        }
        return buf.toString();
    }
}
