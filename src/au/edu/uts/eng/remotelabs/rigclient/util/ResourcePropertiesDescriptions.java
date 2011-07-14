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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Loads descriptions of the rig client configuration properties. The format of
 * this file is given by the following DTD:<br />
 * <pre>
 * &lt?xml version="1.0" encoding="UTF-8"&gt;
 * &lt;DOCTYPE config[
 *  &lt!ELEMENT config(property*)&gt;
 *  &lt!ELEMENT property (#PCDATA)&gt;
 *  &lt!ATTLIST property name stanza mandatory type format default restart example #PCDATA #REQUIRED&gt;
 * ]&gt;
 * </pre>
 * <br />
 * The type attribute should have one of the following types:
 * <ul>
 *  <li>STRING</li>
 *  <li>INTEGER</li>
 *  <li>FLOAT</li>
 *  <li>BOOLEAN</li>
 *  <li>CHAR</li>
 * </ul>
 * The default location of the configuration descriptions file is 
 * 'META-INF/config-descriptions.xml' which is within the Rig Client
 * JAR file. This may be overridden with the system property
 * '<tt>prop.descriptions</tt>'.
 */
public class ResourcePropertiesDescriptions implements IConfigDescriptions
{
    /** The Property descriptions resource location. */
    public static final String RESOURCE_LOC = "META-INF/config-descriptions.xml";
    
    /** Configuration stanzas. */
    private final List<String> stanzas;
    
    /** Property description information. */
    private Map<String, Property> descriptions;
    
    /** Logger. */
    private ILogger logger;
    
    public ResourcePropertiesDescriptions()
    {
        this.logger = LoggerFactory.getLoggerInstance();
        List<Property> descs = new ArrayList<Property>();
        this.stanzas = new ArrayList<String>();
        
        List<InputStream> lis = new ArrayList<InputStream>();
        try
        {
            String desLoc = System.getProperty("prop.descriptions");
            if (desLoc == null)
            {
                Enumeration<URL> urls = this.getClass().getClassLoader().getResources(RESOURCE_LOC);
                while (urls.hasMoreElements())
                {
                    lis.add(urls.nextElement().openStream());
                }
            }
            else
            {
                lis.add(new BufferedInputStream(new FileInputStream(desLoc)));
            }
            
            if (lis.size() == 0)
            {
                this.logger.error("Unable to find configuration description resource. It should be packaged in the" +
                		"'/META-INF/config/descriptions.xml' file in the rig client library. Please report this.");
                this.descriptions = new LinkedHashMap<String, IConfigDescriptions.Property>(0);
                return;
            }
            
            for (InputStream is : lis)
            {
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
                        
                        String stanza = e.getAttribute("stanza");
                        if (!this.stanzas.contains(stanza)) this.stanzas.add(stanza);
                        
                        descs.add(new Property(
                                e.getAttribute("name"), 
                                stanza,
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
                for (InputStream is : lis) is.close();
            }
            catch (IOException e)
            { /* Swallowing, because there isn't anything sensible to do here. */ }
        }
        
        /* Key descriptions by name for fast lookups. */
        this.descriptions = new LinkedHashMap<String, Property>(descs.size());
        for (Property p : descs)
        {
            this.descriptions.put(p.getName(), p);
        }
    }
    
    @Override
    public List<String> getStanzas()
    {
        List<String> copy = new ArrayList<String>(this.stanzas.size());
        copy.addAll(this.stanzas);
        return copy;
    }
    
    @Override
    public List<Property> getPropertyDescriptions()
    {
        List<Property> ret = new ArrayList<Property>(this.descriptions.size());
        for (Property p : this.descriptions.values())
        {
            ret.add(p);
        }
        return ret;
    }
    
    @Override
    public List<Property> getPropertyDescriptions(final String stanza)
    {
        List<Property> ret = new ArrayList<Property>(this.descriptions.size());
        for (Property p : this.descriptions.values())
        {
            if (stanza.equals(p.getStanza())) ret.add(p);
        }
        return ret;
    }

    @Override
    public Property getPropertyDescription(final String name)
    {
        return this.descriptions.get(name);
    }
}
