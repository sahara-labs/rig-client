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
 * @date 22nd October 2009
 *
 * Changelog:
 * - 22/10/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig;

import au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner;

/**
 * Abstract rig type class for rigs which provide direct control of the 
 * rig using the rig client.
 */
public abstract class AbstractControlledRig extends AbstractRig implements IRigControl
{
    /** Batch runner. */
    private AbstractBatchRunner runner;
    
    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl#performPrimitive(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest)
     */
    @Override
    public PrimitiveResponse performPrimitive(PrimitiveRequest req)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl#expungePrimitiveControllerCache()
     */
    @Override
    public void expungePrimitiveControllerCache()
    {
        // TODO Auto-generated method stub
        
    }
    
    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl#performBatch(java.lang.String)
     */
    @Override
    public boolean performBatch(String fileName, String userName)
    {
        /* Make sure no batch control is currently running. */
        if (this.isBatchRunning())
        {
            this.logger.warn("Attempting to start a new batch control invocation, cannot start a new invocation.");
            this.logger.debug("Tania, congraulations! You have found a debugging message to delete.");
            return false;
        }
        
        this.runner = this.instantiateBatchRunner(fileName, userName);
        this.logger.debug("Performing batch control using a batch runner of type " + this.runner.getClass().getName() + 
                "with uploaded instruction file "+ fileName);
        
        /* Start the batch runner. */
        Thread thr = new Thread(this.runner);
        thr.start();
        
        /* Wait until it has been started. */
        int timeCount = 0;
        int timeOut = Integer.parseInt(this.configuration.getProperty("Batch_Timeout", "60"));
        this.logger.debug("Loaded batch start up timeout as " + timeOut + " seconds.");
        while (!(this.runner.isStarted() || this.runner.isFailed()))
        {
            try
            {
                Thread.sleep(1000);
                if (++timeCount >= timeOut)
                {
                    this.logger.warn("Batch process has not started in " + timeCount + " seconds, aborting " +
                    		"batch control.");
                    this.runner.terminate();
                    return false;
                }
            }
            catch (InterruptedException e)
            {
                /* This could only happen when the Rig Client is about to 
                 * shutdown so return false to notify that the batch
                 * process (probably) won't be started and run. */ 
                return false;
            }
        }
        return true;
    }
    
    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl#isBatchRunning()
     */
    @Override
    public boolean isBatchRunning()
    {
        if (this.runner == null) return false;
        
        return this.runner.isRunning();
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl#abortBatch()
     */
    @Override
    public boolean abortBatch()
    {
        if (!this.isBatchRunning())
        {
            this.logger.debug("Unable to abort batch since it is not running.");
            return true;
        }
        
        this.runner.terminate();
        
        int termTimeOut = Integer.parseInt(this.configuration.getProperty("Batch_Termination_TimeOut", "10"));
        int termTimeCount = 0;
        while (this.runner.isRunning() && (termTimeCount < termTimeOut))
        {
            ++termTimeCount;
        }
             
        return this.runner.isRunning();
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl#getBatchProgress()
     */
    @Override
    public int getBatchProgress()
    {
        /* No batch invocation has been started. */
        if (this.runner == null) return 0;
        if (!this.runner.isStarted()) return 0;
        
        /* Batch invocation failed or finished. */
        if (!this.isBatchRunning()) return 100;
        
        /* Batch invocation is running, so try and give a progression number. */
        /* DODGY This makes the assuption that the batch process prints an 
         * integer to standard out specifying the percentage complete and if 
         * this integer is updated, it should print a number on a new line. So
         * the standard output should look like:
         * 
         *     <progress number>[space]<optional junk>[new line]
         * 
         * For example:
         * mdiponio@eng047151~$ runprocess.sh
         * 1 <optional junk>\n
         * 2 <optional junk>\n
         * ...
         * 100 <optional junk>\n
         * mdiponio@eng047151~$ 
         */
        String stdOutRead = this.runner.getBatchStandardOut();
        String lines[] = stdOutRead.split("line.separator");
        String words[] = lines[lines.length - 1].split(" ", 2);
        try
        {
            return Integer.parseInt(words[0]);
        }
        catch (NumberFormatException nfe)
        {
            /* DODGY Oh, well, the assumption we made is wrong. */
            return -1;
        }
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl#getBatchResults()
     */
    @Override
    public BatchResults getBatchResults()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl#getBatchState()
     */
    @Override
    public BatchState getBatchState()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * /**
     * Creates an instance of <code>AbstractBatchRunner</code>.
     * 
     * @param fileName
     * @param userName
     * @return
     */
    protected abstract AbstractBatchRunner instantiateBatchRunner(final String fileName, final String userName);
}
