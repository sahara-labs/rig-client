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
 * @date 30th November 2009
 *
 * Changelog:
 * - 30/11/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests;

import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveResponse;
import au.edu.uts.eng.remotelabs.rigclient.rig.primitive.IPrimitiveController;

/**
 * Mock controller.
 */
public class MockController implements IPrimitiveController
{
    /** If the initController method was called. */
    private boolean isInitialised;
    
    /** If the cleanup method was called. */
    private boolean isCleanedUp;
    
    /** If the count method was called. */
    private int count;

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.primitive.IPrimitiveController#cleanup()
     */
    @Override
    public void cleanup()
    {
       this.isCleanedUp = true;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.primitive.IPrimitiveController#initController()
     */
    @Override
    public boolean initController()
    {
        this.isInitialised = true;
        return true;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.primitive.IPrimitiveController#postRoute()
     */
    @Override
    public boolean postRoute()
    {
        return true;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.primitive.IPrimitiveController#preRoute()
     */
    @Override
    public boolean preRoute()
    {
        return true;
    }
    
    /**
     * Test action.
     * 
     * @param req request
     * @return response
     */
    public PrimitiveResponse testAction(PrimitiveRequest req)
    {
        PrimitiveResponse response = new PrimitiveResponse();
        response.setErrorCode(0);
        response.setSuccessful(true);
        response.setResults(req.getParameters()); 
        return response;
    }
    
    /**
     * Test static action.
     * 
     * @param req
     * @return
     */
    public static PrimitiveResponse testStaticAction(PrimitiveRequest req)
    {
        PrimitiveResponse resp = new PrimitiveResponse();
        resp.setErrorCode(0);
        resp.setSuccessful(true);
        resp.setResults(req.getParameters());
        return resp;
    }
    
    /**
     * Throws exception 
     * 
     * @return
     * @throws Exception
     */
    public PrimitiveResponse exceptionAction(PrimitiveRequest req) throws Exception
    {
        throw new Exception("An exception.");
    }
    
    /**
     * Wrong sig action
     * 
     * @param req request
     * @return response
     */
    public PrimitiveRequest wrongSigAction(PrimitiveRequest req)
    {
        return req;
    }

    
    /**
     * Test action with wrong signature - wrong parameter.
     * 
     * @param wrong *wrong*!
     * @return wrong...
     */
    public PrimitiveResponse wrongParamSigAction(PrimitiveResponse wrong)
    {
        return wrong;
    }
    
    /**
     * Count to ensure the instance is the same instance.
     * @return
     */
    public int callCount()
    {
        return ++this.count;
    }
    
    public PrimitiveResponse callCountAction(PrimitiveRequest req)
    {
        PrimitiveResponse resp = new PrimitiveResponse();
        resp.setErrorCode(0);
        resp.setSuccessful(true);
        resp.addResult("count", String.valueOf(++this.count));
        return resp;
    }

    /**
     * @return the isInitialised
     */
    public boolean isInitialised()
    {
        return this.isInitialised;
    }

    /**
     * @return the isCleanedUp
     */
    public boolean isCleanedUp()
    {
        return this.isCleanedUp;
    }

}
