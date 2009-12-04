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
 * @date 1st December 2009
 *
 * Changelog:
 * - 01/12/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.type;

import au.edu.uts.eng.remotelabs.rigclient.rig.IRig;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Factory class for the rig type class whose type is loaded from
 * configuration. Only one instance of the rig type class is 
 * ever loaded.
 */
public class RigFactory
{
    /** Rig type class. */
    private static IRig rig;
    
    static 
    {
        RigFactory.rig = RigFactory.loadInstance();
    }
    
    /**
     * Returns an instance of the rig type class.
     * 
     * @return rig type class
     */
    public static IRig getRigInstance()
    {
        return RigFactory.rig;
    }
    
    /**
     * Loads the rig type class instance using the configured rig type class
     * name loaded from configuration. If the class cannot be resolved or
     * instantiated <code>null</code> is returned.
     * 
     * @return rig type class instance or null if not found
     */
    private static IRig loadInstance()
    {
        ILogger logger = LoggerFactory.getLoggerInstance();
        logger.debug("Attempting to load the rig type class.");
        IConfig config = ConfigFactory.getInstance();
        
        final String rigClassStr = config.getProperty("Rig_Class");
        if (rigClassStr == null || rigClassStr == "")
        {
            logger.fatal("Unable to find configuration value of the rig type class. Check the property 'Rig_Class'" +
            		" exists and has a value set.");
            return null;
        }
        logger.debug("Rig type class configuration is " + rigClassStr + ".");

        try
        {
            final Class<?> rigClass = Class.forName(rigClassStr);
            final Object rigObj = rigClass.newInstance();
            if (rigObj instanceof IRig)
            {
                logger.info("Loaded rig type class as " + rigClass.getCanonicalName() + ".");
                return (IRig)rigObj;
            }
            else
            {
                logger.fatal("Loaded rig type class " + rigClass.getCanonicalName() + " does not implement the" +
                		" IRig interface.");
            }
        }
        catch (ClassNotFoundException e)
        {
            logger.fatal("Unable to find rig type class " + rigClassStr + ". Ensure the configured class " +
            		"(Rig_Type) exists.");
        }
        catch (InstantiationException e)
        {
            Throwable cause = e.getCause();
            logger.fatal("Unable to instantiate the rig type class " + rigClassStr + " because of its constructor" +
            		" threw an exception of type " + cause.getClass().getCanonicalName() + " with message " +
            		cause.getMessage() + ".");
        }
        catch (IllegalAccessException e)
        {
            logger.fatal("Unable to access rig type class " + rigClassStr + " with exception message " + 
                    e.getMessage());
        }
        
        logger.fatal("Failed loading rig type class. This is ominous and unrecoverable.");
        return null;
    }
}
