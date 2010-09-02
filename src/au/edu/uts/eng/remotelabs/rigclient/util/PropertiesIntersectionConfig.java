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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.bcel.generic.FCMPG;

/**
 * Configuration class which uses an intersection of multiple configuration
 * classes to load configuration properties. The intersection consists of a
 * canonical properties file and an extension directory from which zero of  
 * more properties files may be iterated. If there are duplicate properties in 
 * the any of the loaded files, the canonical file takes precedence over the 
 * extension directory properties. Within the extension directory the first 
 * loaded value of duplicate properties is used. The loading order of the 
 * extension direcMtory properties files are in the natural ordering of the 
 * file names, so prepending an appropriate number to be beginning of file 
 * names allows the loading order to be controlled. The default location of the
 * canonical file and extension directory are:
 * <ul>
 *  <li>Canonical properties file - conf/rigclient.properties</li>
 *  <li>Extension directory - conf/conf.d</li>
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
    /** The default location of the canonical properties file. */
    public static final String CANONICAL_FILE_LOC = "conf";
    
    /** The default location of the extension directory. */
    public static final String EXTENSION_DIR_LOC = "conf/conf.d";
    
    /** The map containing the intersection of properties that are loaded from
     *  the various properties files. Direct lookup of properties is from this
     *  map and not proxied to the properties file. */
    private Map<String, String> props;
    
    /** The canonical properties file. */
    private Properties canonicalProps;
    
    /** The location of the canonical properties file. */
    private String canonicalLocation;
    
    /** The properties files loaded from the extension directory in order of 
     *  their precedence. */
    private Map<String, Properties> extensionProps;

    /** The location of the extension directory. */
    private String extensionLocation;
    
    public PropertiesIntersectionConfig()
    {
        this.props = new HashMap<String, String>();
        this.extensionProps = new HashMap<String, Properties>();
        
        /* Find the location of the properties files. */
        this.canonicalLocation = System.getProperty("prop.file", PropertiesIntersectionConfig.CANONICAL_FILE_LOC);
        System.out.println("The location of the canonical properties file is: " + this.canonicalLocation);
        
        File f = new File(this.canonicalLocation);
        if (!(f.isFile() || f.canRead() || f.canWrite()))
        {
            
        }
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
    public synchronized Map<String, String> getAllProperties()
    {
        Map<String, String> clone = new HashMap<String, String>();
        
        for (Entry<String, String> p : this.props.entrySet())
        {
            clone.put(p.getKey(), p.getValue());
        }
        
        return clone;
    }

    @Override
    public synchronized void setProperty(String key, String value)
    {
        /* Update the memory properties store. */
        this.props.put(key, value);
        
        /* If the property is from the canonical store, update the existing
         * property with the new value. */
        if (this.canonicalProps.containsKey(key))
        {
            this.canonicalProps.put(key, value);
            return;
        }
        
        /* Otherwise update the extension location properties store. */
        for (Properties p : this.extensionProps.values())
        {
            if (p.containsKey(key))
            {
                p.put(key, value);
                return;
            }
        }

        /* It is a new property, so add it to the canonical location. */
        this.canonicalProps.put(key, value);
    }

    @Override
    public synchronized void removeProperty(String key)
    {
        if (!this.props.containsKey(key)) return;
        
        /* Remove from the memory properties store. */
        this.props.remove(key);
        
        
        /* If the property is from the canonical store, update the existing
         * property with the new value. */
        if (this.canonicalProps.containsKey(key))
        {
            this.canonicalProps.remove(key);
            return;
        }
        
        /* Otherwise update the extension location properties store. */
        String mKey = null;
        for (Entry<String, Properties> e : this.extensionProps.entrySet())
        {
            if (e.getValue().containsKey(key))
            {
                mKey = e.getKey();
            }
        }
        if (mKey != null) this.extensionProps.remove(mKey);
    }

    @Override
    public synchronized void reload()
    {
        this.props.clear();
        
        try
        {
            /* Load the canonical file. */
            FileInputStream fs = new FileInputStream(new File(this.canonicalLocation));
            this.canonicalProps = new Properties();
            this.canonicalProps.load(fs);
            
            /* All canonical properties are loaded to be used. */
            for (Entry<Object, Object> e : this.canonicalProps.entrySet())
            {
                /* This is probably a unneeded check (properties are string key value
                 * pairs), but unguarded casts are rarely a good idea. */
                if (!(e.getKey() instanceof String || e.getValue() instanceof String)) continue;
                
               this.props.put((String)e.getKey(), (String)e.getValue());
            }
            fs.close();
            
            /* Load the extension properties. The are pre-sorted in order of precedence. */
            Iterator<Entry<String, Properties>> it = this.extensionProps.entrySet().iterator();
            while (it.hasNext())
            {
                Entry<String, Properties> e = it.next();
                try
                {
                    fs = new FileInputStream(new File(e.getKey()));
                    Properties p = new Properties();
                    p.load(fs);
                    e.setValue(p);
                    
                    for (Entry<Object, Object> pe : this.canonicalProps.entrySet())
                    {
                        if (!(pe.getKey() instanceof String || pe.getValue() instanceof String)) continue;
                        
                        if (!this.props.containsKey(pe.getKey())) 
                        {
                            this.props.put((String)pe.getKey(), (String)pe.getValue());
                        }
                    }
                    
                    fs.close();
                }
                catch (FileNotFoundException ex)
                {
                    System.out.println("Extension configuration file '" + e.getKey() + "' no longer exists. Removing " +
                    		"it from the properties set.");
                    it.remove();
                }
                catch (IOException ex)
                {
                    System.out.println("Error reading extension configuration file '" + e.getKey() + "'. Removing it " +
                            "from the properties set.");
                    it.remove();
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Canonical properties file not found, not reloading configuration properties.");
        }
        catch (IOException e)
        {
            System.out.println("Error reading canonical properties file, not reloading configuration properties.");
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
    
    /**
     * File name filter which filters base the file extension (trailing
     * characters after the last '.' character).
     */
    public class FilenameExtFiler implements FileFilter
    {
        /** The list of allowable extensions. */
        private List<String> extensions;
        
        public FilenameExtFiler(String[] extensions)
        {
            this.extensions = Arrays.asList(extensions);
        }

        @Override
        public boolean accept(File f)
        {
            String name = f.getName();            
            return this.extensions.contains(name.substring(name.lastIndexOf('.')));
        }
    }
    
}
