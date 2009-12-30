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
 */
package au.edu.uts.eng.remotelabs.rigclient.util;

import java.util.Map;

/**
 * Abstract logger, fills in some boilerplate code.
 */
abstract class AbstractLogger implements ILogger
{
    /** Level to log to. */
    protected short logLevel;
    
    /** Log formatter. */
    private final LogFormatter formatter;
    
    /** Format strings for each log type. */
    private final Map<Integer, String> formatStrings;
    
    /**
     * Constructor - loads the logging level.
     */
    public AbstractLogger()
    {
        this.logLevel = LoggerFactory.getLoggingLevel();
        this.formatStrings = LoggerFactory.getFormatStrings();
        this.formatter = new LogFormatter();
     }

    @Override
    public void debug(final String message)
    {
        if (ILogger.DEBUG <= this.logLevel)
        {
            this.log(ILogger.DEBUG, 
                    this.formatter.formatLog(this.formatStrings.get(ILogger.DEBUG), message, "DEBUG", 3));
        }
    }

    @Override
    public void error(final String message)
    {
        this.log(ILogger.ERROR, 
                this.formatter.formatLog(this.formatStrings.get(ILogger.ERROR), message, "ERROR", 3));
    }

    @Override
    public void fatal(final String message)
    {
        this.log(ILogger.FATAL, 
                this.formatter.formatLog(this.formatStrings.get(ILogger.FATAL), message, "FATAL", 3));        
    }

    @Override
    public void info(final String message)
    {
        if (ILogger.INFO <= this.logLevel)
        {
            this.log(ILogger.INFO, 
                    this.formatter.formatLog(this.formatStrings.get(ILogger.INFO), message, "INFO", 3));
        }
    }

    @Override
    public void priority(final String message)
    {
        this.log(ILogger.PRIORITY, 
                this.formatter.formatLog(this.formatStrings.get(ILogger.PRIORITY), message, "PRIORITY", 3));        
    }

    @Override
    public void warn(final String message)
    {
        if (ILogger.WARN <= this.logLevel)
        {
            this.log(ILogger.WARN, 
                    this.formatter.formatLog(this.formatStrings.get(ILogger.WARN), message, "WARN", 3));
        }
    }
}
