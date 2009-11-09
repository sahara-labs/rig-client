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
    public boolean performBatch(String fileName)
    {
        /* Make sure no batch control is currently running. */
        // TODO
        
        this.runner = this.instantiateBatchRunner(fileName, "");
        this.logger.debug("Performing batch control using a batch runner of type " + this.runner.getClass().getName() + 
                "with uploaded instruction file "+ fileName);
        
        /* Start the batch runner. */
        Thread thr = new Thread(this.runner);
        thr.start();
        
        /* Wait until it has been started. */
        int timeCount = 0;
        int timeOut = Integer.parseInt(this.configuration.getProperty("Batch_Timeout", "60"));
        while (!(this.runner.isStarted() || this.runner.isFailed()))
        {
            try
            {
                Thread.sleep(1000);
                if (++timeCount >= timeOut)
                {
                    // TODO
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
        // TODO Auto-generated method stub
        return false;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl#abortBatch()
     */
    @Override
    public boolean abortBatch()
    {
        // TODO Auto-generated method stub
        return false;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl#getBatchProgress()
     */
    @Override
    public int getBatchProgress()
    {
        // TODO Auto-generated method stub
        return 0;
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
