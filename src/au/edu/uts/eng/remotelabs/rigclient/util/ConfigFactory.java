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
 * @author <First name> <Last name> mdiponio
 * @date <day> <month> 2009
 *
 * Changelog:
 * - 05/10/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.util;

import java.io.File;

/**
 * Configuration implementation factory.
 */
public class ConfigFactory
{
    /** The system property which the implementation can be specified. */
    public static String IMPL_SYSTEM_PROP = "conf.impl";
    
    /** Single properties file (2.0 configuration implementation). */
    public static String R2_BEHAVIOUR = "single";
    
    /** Multiple properties file (conf.d). */
    public static String R3_BEHAVIOUR = "intersection";
    
    /** Configuration instance. */
    private static IConfig instance;
    
    static
    {
        ConfigFactory.instance = ConfigFactory.getInternalInstance();
    }
    
    /**
     * Singleton class.
     */
    private ConfigFactory()
    { 
        /* Singleton constructor. */
    }
    
    /**
     * Gets a IConfig instance.
     *
     * @return IConfig instance
     */
    public static IConfig getInstance()
    {
        return ConfigFactory.instance;
    }
    
    /**
     * Returns an instance of <code>IConfig</code> configuration.
     * 
     * @return instance of <code>IConfig</code>
     */
    private static IConfig getInternalInstance()
    {
        /* It would be ideal to use the properties conf.d implementation but
         * for existing installations, existing behavior should be preserved
         * to make upgrades least intrusive. */
        
        /* Test 1) Allow the user to specify the implementation via a system 
         * property. */
        String explicit = System.getProperty(ConfigFactory.IMPL_SYSTEM_PROP);
        if (ConfigFactory.R3_BEHAVIOUR.equalsIgnoreCase(explicit))
        {
            return new PropertiesIntersectionConfig();
        }
        else if (ConfigFactory.R2_BEHAVIOUR.equalsIgnoreCase(explicit))
        {
            return new PropertiesConfig();
        }
        
        /* Test 2) Detect from expected directories:
         *   - config - PropertiesConfig
         *   - conf   - PropertiesIntersectionConfig 
         */
       if (new File("conf").isDirectory())
       {
           return new PropertiesIntersectionConfig();
       }
       else if (new File("config").isDirectory())
       {
           return new PropertiesConfig();
       }
        
        /* Nothing determined or detected so use the new implementation. */
        return new PropertiesIntersectionConfig();
    }
}
