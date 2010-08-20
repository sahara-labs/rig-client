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

import java.util.Map;

/**
 * Configuration class which uses an intersection of multiple configuration
 * classes to load configuration properties. The intersection consists of a
 * canonical properties file and an extension directory from which zero of  
 * more properties files (with the extension <tt>.properties</tt>) may be 
 * iterated. If there are duplicate properties in the any of the loaded files,
 * the canonical file takes precedence over the extension directory properties. 
 * Within the extension directory the first loaded value of duplicate properties 
 * is used. The loading order of the extension directory properties files are 
 * in the natural ordering of the file names, so prepending an appropriate 
 * number to be beginning of file names allows the loading order to be 
 * controlled. The default location of the canonical file and extension 
 * directory are:
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
 */
public class PropertiesIntersectionConfig implements IConfig
{

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.util.IConfig#getProperty(java.lang.String)
     */
    @Override
    public String getProperty(String key)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.util.IConfig#getProperty(java.lang.String, java.lang.String)
     */
    @Override
    public String getProperty(String key, String defaultValue)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.util.IConfig#getAllProperties()
     */
    @Override
    public Map<String, String> getAllProperties()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.util.IConfig#setProperty(java.lang.String, java.lang.String)
     */
    @Override
    public void setProperty(String key, String value)
    {
        // TODO Auto-generated method stub

    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.util.IConfig#removeProperty(java.lang.String)
     */
    @Override
    public void removeProperty(String key)
    {
        // TODO Auto-generated method stub

    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.util.IConfig#reload()
     */
    @Override
    public void reload()
    {
        // TODO Auto-generated method stub

    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.util.IConfig#serialise()
     */
    @Override
    public void serialise()
    {
        // TODO Auto-generated method stub

    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.util.IConfig#dumpConfiguration()
     */
    @Override
    public String dumpConfiguration()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.util.IConfig#getConfigurationInfomation()
     */
    @Override
    public String getConfigurationInfomation()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
