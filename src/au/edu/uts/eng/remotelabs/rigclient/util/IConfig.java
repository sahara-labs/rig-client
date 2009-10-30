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
 * - 21/10/2009 - mdiponio - Added <code>removeProperty</code> method.
 */
package au.edu.uts.eng.remotelabs.rigclient.util;

import java.util.Map;

/**
 * Interface for configuration class.
 */
public interface IConfig
{
    /**
     * Returns the configuration value of the property key.
     * Null is returned if the key cannot be found in configuration.
     *
     * @param key name of the configuration item
     * @return configuration value set in properties if found, null otherwise
     */
    public String getProperty(String key);
    
    /**
     * Returns the configuration value of the property key.
     * If the key value cannot be found in configuration, the 
     * <code>defaultValue</code> parameter is returned.
     *
     * @param key name of the configuration item
     * @param defaultValue the value to return if the key value is not found
     * @return configuration value set in properties if found, null otherwise
     */
    public String getProperty(String key, String defaultValue);
    
    /**
     * Gets a map of all the configuration properties in key => value pairs.
     * 
     * @return all configuration properties
     */
    public Map<String, String> getAllProperties();
    
    /**
     * Sets a property that will be persistently stored to configuration on 
     * the next configuration serialisation.
     * 
     * @param key name of configuration item
     * @param value value of configuration item
     */
    public void setProperty(String key, String value);
    
    /**
     * Removes a property from configuration.
     * 
     * @param key property to remove
     */
    public void removeProperty(String key);
    
    /**
     * Reload configuration from a persistent store. If the in memory
     * configuration store is dirty, any changes will be flushed.
     */
    public void reload();
    
    /**
     * Write back configuration to a persistent store.
     */
    public void serialise();
    
    /**
     * Return a configuration dump containing all the configuration
     * key values.
     * 
     * @return dump of configuration
     */
    public String dumpConfiguration();
    
    /**
     * Gets implementation specific configuration information. The 
     * information returned should be helpful to print diagnostics 
     * for configuration errors.
     * 
     * @return configuration information
     */
    public String getConfigurationInfomation();
}
