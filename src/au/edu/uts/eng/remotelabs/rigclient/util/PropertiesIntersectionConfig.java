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
 * @date 20th August 2010
 */
package au.edu.uts.eng.remotelabs.rigclient.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Configuration class which uses an intersection of multiple configuration
 * classes to load configuration properties. The intersection consists of a
 * canonical properties file and an extension directory from which zero of  
 * more properties files may be iterated. If there are duplicate properties in 
 * the any of the loaded files, the canonical file takes precedence over the 
 * extension directory properties. Within the extension directory the first 
 * loaded value of duplicate properties is used. The loading order of the 
 * extension directory properties files are in the natural ordering of the 
 * file names, so prepending an appropriate number to be beginning of file 
 * names allows the loading order to be controlled. The default location of the
 * canonical file and extension directory are:
 * <ul>
 *  <li>Canonical properties file - config/rigclient.properties</li>
 *  <li>Extension directory - config/config.d</li>
 * </ul>
 * The following system properties can be used to override the locations:
 * <ul>
 *  <li><tt>prop.file</tt> - The location of the canonical properties 
 *  file.</li>
 *  <li><tt>prop.extension.dir</tt> - The location of the properties extension
 *  library.</li>
 * </ul>
 * The following extensions may be used for the properties files in the
 * extension directory (note case):
 * <ul>
 *  <li>properties</ul>
 *  <li>props</li>
 *  <li>conf</li>
 *  <li>config</li>
 *  <li>rc</li>
 * </ul>
 */
public class PropertiesIntersectionConfig implements IConfig
{
    /** The map containing the intersection of properties that are loaded from
     *  the various properties files. Direct lookup of properties is from this
     *  map and not proxied to the properties file. */
    private Map<String, String> props;
    
    /** The canonical properties file. */
    private Properties canonicalProps;
    
    /** The location of the canonical properties file. */
    private String canonicalLocation;
    
    /** The properites files loaded from the extension directory in order of 
     *  their precedence. */
    private List<Properties> extensionProps;

    /** The location of the extension directory. */
    private String extensionLocation;
    
    public PropertiesIntersectionConfig()
    {
        this.props = new HashMap<String, String>();
        this.extensionProps = new ArrayList<Properties>();
        
        this.reload();
    }

    @Override
    public String getProperty(String key)
    {
        return this.props.get(key);
    }

    @Override
    public String getProperty(final String key, final String defaultValue)
    {
        final String val = this.props.get(key);
        if (val == null)
        {
            return defaultValue;
        }
        else
        {
            return val;
        }
    }

    @Override
    public Map<String, String> getAllProperties()
    {
        Map<String, String> clone = new HashMap<String, String>();
        
        for (Entry<String, String> p : this.props.entrySet())
        {
            clone.put(p.getKey(), p.getValue());
        }
        
        return clone;
    }

    @Override
    public void setProperty(String key, String value)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeProperty(String key)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void reload()
    {
        try
        {
            /* The canonical properties file has the highest precedence. */
            this.canonicalProps = new Properties();
            FileInputStream in = new FileInputStream(new File(this.canonicalLocation));
            this.canonicalProps.load(in);
            
            for (String k : this.canonicalProps.stringPropertyNames())
            {
                this.props.put(k, this.canonicalProps.getProperty(k));
            }
            
            in.close();
            
            File ext = new File(this.extensionLocation);
            
            /* The extension directory need not exist. */
            if (this.extensionLocation == null || !ext.isDirectory()) return;
            
            /* The extension directory configuration files are loaded in
             * the natural order of their filenames. */
            String files[] = ext.list();
        }
        catch (IOException ex)
        {
            
        }
        
        

    }

    @Override
    public void serialise()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public String dumpConfiguration()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getConfigurationInfomation()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    class FilenameExtFiler implements FileFilter
    {
        /** The list of allowable extensions. */
        private List<String> extensions;
        
        

        @Override
        public boolean accept(File f)
        {
            String name = f.getName();            
            return this.extensions.contains(name.substring(name.lastIndexOf('.')));
        }
        
    }
    
}
