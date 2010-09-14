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
 * @date 14th September 2010
 */
package au.edu.uts.eng.remotelabs.rigclient.util;

import java.util.List;

/**
 * Interface for a class providing information about the configuration 
 * properties of the Rig Client.
 */
public interface IConfigDescriptions
{
    /**
     * The data type of a configuration value.
     */
    public enum ConfigDataType
    {
        /** String configuration value. */
        STRING, /** Integral number. */
        INTEGER, /** Decimal number. */
        FLOAT, /** Boolean value. */
        BOOLEAN, /** Single character. */
        CHAR
    }
    
    /** 
     * Gets all the property descriptions
     * 
     * @return list of property descriptions
     */
    public List<Property> getPropertyDescriptions();
    
    /**
     * Gets the property descriptions for the specified stanza.
     * 
     * @param stanza configuration stanza
     * @return list of property in that stanza
     */
    public List<Property> getPropertyDescriptions(final String stanza);
    
    
    /**
     * Gets the property description for a specific property.
     * 
     * @param name property nameXS
     * @return property description or null if not found.
     */
    public Property getPropertyDescription(final String name);
    
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
        private final ConfigDataType type;
        
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
            
            if ("INTEGER".equalsIgnoreCase(type)) this.type = ConfigDataType.INTEGER;
            else if ("FLOAT".equalsIgnoreCase(type)) this.type = ConfigDataType.FLOAT;
            else if ("BOOLEAN".equalsIgnoreCase(type)) this.type = ConfigDataType.BOOLEAN;
            else if ("CHAR".equalsIgnoreCase(type)) this.type = ConfigDataType.CHAR;
            else this.type = ConfigDataType.STRING; // String is the most generic
            
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
        public ConfigDataType getType()
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
        public boolean needsRestart()
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
}
