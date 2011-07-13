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
 * @date 12th November 2009
 *
 * Changelog:
 * - 12/11/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.primitive;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveResponse;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Front controller for the primitive control. Routes a primitive control 
 * requests to the specified controller (a <code>IPrimitiveController</code>
 * instance) and action.
 */
public class PrimitiveFront
{
    /** Controller cache. */
    private final PrimitiveCache cache;
    
    /** Logger object. */
    private final ILogger logger;
    
    /**
     * Constructor.
     */
    public PrimitiveFront()
    {
        this.logger = LoggerFactory.getLoggerInstance();
        this.logger.debug("Creating a new primitive front controller.");
        
        this.cache = new PrimitiveCache();
    }
    
    /**
     * Routes a request to the specified 'controller' and 'action'.  The
     * action must have the same signature as the described in 
     * {@link IPrimitiveController}.
     * 
     * @param request request request parameters
     * @return response results of operation
     */
    public PrimitiveResponse routeRequest(final PrimitiveRequest request)
    {
        PrimitiveResponse response = new PrimitiveResponse();
        
        /* --------------------------------------------------------------------
         * ---- 1. Get the requested controller instance. ---------------------
         * ------------------------------------------------------------------*/
        final String controllerName = request.getController();
        this.logger.debug("Requested controller is " + controllerName + ".");
        if (controllerName == null)
        {
            this.logger.info("The primitive control requested controller is null. Failing primitive control " +
            		"with signature: " + request.toString() + ".");
            response.setErrorCode(-1);
            response.setErrorReason("The requested controller argument is null.");
            response.setSuccessful(false);
            return response;
        }
        final IPrimitiveController controller = this.cache.getInstance(controllerName);
        if (controller == null)
        {
            response.setErrorCode(-2);
            response.setErrorReason("The requested controller " + controllerName + " not found.");
            response.setSuccessful(false);
            return response;
        }
        
        /* --------------------------------------------------------------------
         * ---- 2. Run the ACL filter hook if the controller supports it. -----
         * ------------------------------------------------------------------*/
        if (controller instanceof IPrimitiveAcl)
        {
            IPrimitiveAcl acl = (IPrimitiveAcl)controller;
            if (!acl.allowRole(request.getRole(), request.getAction()))
            {
                this.logger.info("Failing primitive control because the role " + request.getRole() + " does not " +
                		"have permission to run " + request.getAction() + " on " + request.getController() + '.');
                response.setErrorCode(-9);
                response.setErrorReason("ACL check does not allow the role the perform the action.");
                response.setSuccessful(false);
                return response;
            }
        }
        
        /* --------------------------------------------------------------------
         * ---- 3. Get the requested controller action. -----------------------
         * ------------------------------------------------------------------*/
        if (request.getAction() == null)
        {
            this.logger.warn("The primitive control requested action is null. Failing primitive control " +
                    "with signature: " + request.toString() + ".");
            response.setErrorCode(-1);
            response.setErrorReason("The requested action argument is null.");
            response.setSuccessful(false);
            return response;
        }
        final String actionName = request.getAction() + "Action";
        this.logger.debug("Requested action is " + actionName + ".");
        
        /* --------------------------------------------------------------------
         * ---- 4. Invoke the preRoute method. --------------------------------
         * ------------------------------------------------------------------*/
        if (!controller.preRoute())
        {
            this.logger.warn("Calling the pre-routing (preRoute) method on controller " + controllerName + 
                    " method failed. It's cache instance will be removed.");
            this.cache.removeCachedInstance(controllerName);
            response.setErrorCode(-8);
            response.setErrorReason("preRoute failed.");
            response.setSuccessful(false);
            return response;
        }
        
        try
        {
            final Method meth = controller.getClass().getMethod(actionName, PrimitiveRequest.class);
            this.logger.debug("Found action method has the signature: " + meth.toGenericString());
            
            /* ----------------------------------------------------------------
             * ---- 5. Invoke the action method. ------------------------------
             * --------------------------------------------------------------*/
            /* Works for both static and instance methods. If it is a static 
             * method the instance parameter is ignored. */
            final Object obj = meth.invoke(controller, request);
            if (obj instanceof PrimitiveResponse)
            {
                /* Success response! */
                this.logger.debug("Successfully routed and invoked " + actionName + " on " + controllerName + ".");
                response = (PrimitiveResponse)obj;
            }
            else
            {
                this.logger.warn("The action " + actionName + " on " + controllerName + " did not provide a " +
                		"PrimitiveResponse instance as the invocation return.");
                response.setErrorCode(-6);
                response.setErrorReason(actionName + " on " + controllerName + " has an invalid signature (must return" +
                		"a PrimitiveResponse instance).");
                response.setSuccessful(false);
            }
        }
        catch (SecurityException e) // Either method not public or no access to the package with which the class resides
        {
            this.logger.warn("Security exception accessing action " + actionName + " on " + controllerName + ".");   
            response.setErrorCode(-4);
            response.setErrorReason("Security execption attempting to access " + actionName + " on " + controllerName + ".");
            response.setSuccessful(false);
        }
        catch (NoSuchMethodException e) // Method not found
        {
            this.logger.warn("The action " + actionName + " on " + controllerName + " does not exist.");
            response.setErrorCode(-3);
            response.setErrorReason("Action method " + actionName + " not found on " + controllerName + ".");
            response.setSuccessful(false);
        }
        catch (IllegalArgumentException e) // Method does not take a PrimitiveRequest parameter
        {                                  // Unlikely because resolution should fail
            this.logger.warn("The action " + actionName + " on " + controllerName + " has an invalid signature." +
            		" Must only take a PrimitiveRequest instance as a parameter.");
            response.setErrorCode(-6);
            response.setErrorReason(actionName + " on " + controllerName + " has an invalid signature (must only take" +
            		"a PrimitiveRequest parameter).");
            response.setSuccessful(false);
         }
        catch (IllegalAccessException e) // Method not public
        {
            this.logger.warn("Illegally accesing " + actionName + " on " + controllerName + " (" + actionName +
            		" not public).");
            response.setErrorCode(-5);
            response.setErrorReason("Illegally accesing " + actionName + " on " + controllerName + " (" + actionName +
            		" not public).");
            response.setSuccessful(false);
        }
        catch (InvocationTargetException e) // Method threw an exception
        {
            final Throwable cause = e.getCause();
            this.logger.warn("Action " + actionName + " on " + controllerName + " threw an exception of type: " + 
                    cause.getClass().getName() + " with message: " + cause.getMessage());
            response.setErrorCode(-7);
            response.setErrorReason("Action " + actionName + " on " + controllerName + " threw an exception of type " + 
                    cause.getClass().getName() + " with message: " + cause.getMessage());
            response.setSuccessful(false);
        }
        
        /* --------------------------------------------------------------------
         * ---- 6. Invoke postRoute on the controller. ------------------------
         * ------------------------------------------------------------------*/
         if (!controller.postRoute())
         {
             this.logger.warn("Calling the post-routing (postRoute) method on controller " + controllerName + 
                     " method failed. It's cache instance will be removed.");
             this.cache.removeCachedInstance(controllerName);
         }
            
        return response;
    }
    
    /**
     * Gets the cache.
     */
    public PrimitiveCache getCache()
    {
        return this.cache;
    }
    
    /**
     * Expunge the cache forcing cleanup of all controllers.
     */
    public void expungeCache()
    {
        this.cache.expungeCache();
    }
}
