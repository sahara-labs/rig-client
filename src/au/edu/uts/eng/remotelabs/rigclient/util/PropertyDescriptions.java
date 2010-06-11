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
 * @date 11th June 2010
 */
package au.edu.uts.eng.remotelabs.rigclient.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Loads descriptions of the rig client configuration properties for a resource
 * file '/META-INF/config/descriptions.xml'. The format of this file is given 
 * by the following DTD:<br />
 * <pre>
 * &lt?xml version="1.0" encoding="UTF-8"&gt;
 * &lt;DOCTYPE config[
 *  &lt!ELEMENT config(property*)&gt;
 *  &lt!ELEMENT property (#PCDATA)&gt;
 *  &lt!ATTLIST property name stanza mandatory type format default restart example #PCDATA #REQUIRED&gt;
 * ]&gt;
 * </pre>
 */
public class PropertyDescriptions
{
    public static PropertyDescriptions instance = new PropertyDescriptions();
    
    /** Property description information. */
    private List<Property> descs;
    
    /** Logger. */
    private ILogger logger;
    
    public PropertyDescriptions()
    {
        this.logger = LoggerFactory.getLoggerInstance();
        this.descs = new ArrayList<Property>();
        
        InputStream is = null;
        try
        {
            is = this.getClass().getClassLoader().getResourceAsStream("META-INF/config-descriptions.xml");
            if (is == null)
            {
                this.logger.error("Unable to find configuration description resource. It should be packaged in the" +
                		"'/META-INF/config/descriptions.xml' file in the rig client library. Please report this.");
                return;
            }
            
            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            fac.setIgnoringComments(true);
            fac.setIgnoringElementContentWhitespace(true);
            Document doc = fac.newDocumentBuilder().parse(is);
            
            /* Parse the all property elements. */
            Element root = doc.getDocumentElement();
            for (Node c = root.getFirstChild(); c != null; c = c.getNextSibling())
            {
                if (c.getNodeName().equals("property") && c.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element e = (Element)c;
                    
                    String tmp = e.getAttribute("mandatory");
                    boolean man = false;
                    if ("yes".equals(tmp) || "true".equals(tmp) || "on".equals(tmp)) man = true;
                    
                    tmp = e.getAttribute("restart");
                    boolean res = false;
                    if ("yes".equals(tmp) || "true".equals(tmp) || "on".equals(tmp)) res = true;
                    
                    String d = e.getTextContent();
                    if (d != null)
                    {
                        d = d.trim();
                        d = d.replace('\n', ' ');
                    }
                    
                    this.descs.add(new Property(
                            e.getAttribute("name"), 
                            e.getAttribute("stanza"), 
                            man,
                            e.getAttribute("type"),
                            e.getAttribute("format"),
                            e.getAttribute("default"),
                            res,
                            e.getAttribute("example"),
                            d
                    ));
                }
            }
        }
        catch (Exception ex)
        {
            this.logger.error("Failed loading property descriptions with exception '" + ex.getClass().getName() + 
                    "' and message '" + ex.getMessage() + "'.");
        }
        finally
        {

            try
            {
                if (is != null) is.close();
            }
            catch (IOException e)
            { /* Swallowing, because there isn't anything sensible to do here. */ }
        }
    }
    
    /** 
     * Gets all the property descriptions
     * 
     * @return list of property descriptions
     */
    public List<Property> getPropertyDescriptions()
    {
        List<Property> ret = new ArrayList<Property>(this.descs.size());
        for (Property p : this.descs)
        {
            ret.add(p);
        }
        return ret;
    }
    
    /**
     * Gets the property descriptions for the specified stanza.
     * 
     * @param stanza configuration stanza
     * @return list of property in that stanza
     */
    public List<Property> getPropertyDescriptions(final String stanza)
    {
        List<Property> ret = new ArrayList<Property>(this.descs.size());
        for (Property p : this.descs)
        {
            if (stanza.equals(p.stanza))ret.add(p);
        }
        return ret;
    }
    
    /**
     * Configuration property information.
     */
    public class Property
    {
        /** Name of property. */
        private final String name;

        /** Property groupings. */
        private final String stanza;
        
        /** Whether the property is mandatory. */
        private final boolean mandatory;
        
        /** The property value data type. */
        private final String type;
        
        /** The property value format. */
        private final String format;
        
        /** The default value if the property isn't configured. */
        private final String defaultValue;
        
        /** Whether the rig client will need a restart. */
        private final boolean restart;
        
        /** Example of a property value. */
        private final String example;
        
        /** Description. */
        private final String description;
        
        public Property(String name, String stanza, boolean mandatory, String type, 
                        String format, String defaultVal, boolean restart, String example, String description)
        {
            this.name = name;
            this.stanza = stanza;
            this.mandatory = mandatory;
            this.type = type;
            this.format = format;
            this.defaultValue = defaultVal;
            this.restart = restart;
            this.example = example;
            this.description = description;
        }

        /**
         * @return the name
         */
        public String getName()
        {
            return this.name;
        }

        /**
         * @return the stanza
         */
        public String getStanza()
        {
            return this.stanza;
        }

        /**
         * @return the mandatory
         */
        public boolean isMandatory()
        {
            return this.mandatory;
        }

        /**
         * @return the type
         */
        public String getType()
        {
            return this.type;
        }

        /**
         * @return the format
         */
        public String getFormat()
        {
            return this.format;
        }

        /**
         * @return the defaultValue
         */
        public String getDefaultValue()
        {
            return this.defaultValue;
        }

        /**
         * @return the restart
         */
        public boolean isRestart()
        {
            return this.restart;
        }

        /**
         * @return the example
         */
        public String getExample()
        {
            return this.example;
        }

        /**
         * @return the description
         */
        public String getDescription()
        {
            return this.description;
        }
        
        @Override
        public String toString()
        {
            final StringBuilder r = new StringBuilder();
            r.append("Name=");
            r.append(this.name);
            r.append(", description=");
            r.append(this.description);
            r.append(", stanza=");
            r.append(this.stanza);
            r.append(", mandatory=");
            r.append(this.mandatory);
            r.append(", type=");
            r.append(this.type);
            r.append(", format=");
            r.append(this.format);
            r.append(", default=");
            r.append(this.defaultValue);
            r.append(", restart=");
            r.append(this.restart);
            r.append(", exampe=");
            r.append(this.example);
            return r.toString();
        }
    }
    
    /**
     * Returns a cached instance of the property descriptions class. There isn't
     * must point in getting new instances of this class as it is immutable with
     * static data.
     * 
     * @return cached instance
     */
    public static PropertyDescriptions getInstance()
    {
        return PropertyDescriptions.instance;
    }
}
