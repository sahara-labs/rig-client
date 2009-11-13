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

/**
 * Interface for primitive controller classes. Primitive control classes must 
 * extend this class and provide public methods which may be requested as 
 * 'actions'. The declaration of these methods must have the following 
 * signature:
 * <br />
 *  &nbsp;&nbsp;&nbsp;&nbsp;<strong>public</strong> <em>PrimitiveResponse</em> 
 *  <span style="color:red;font-weight:bolder">name</span>Action(<em>PrimitiveRequest</em>)
 * <br />
 * Where 'name' is what is requested as the action parameter of a primitive 
 * control request. If the actions do not follow this signature, then routing
 * to them will fail. Action methods may be either instance or static methods.
 * <br />
 * The following methods should be implemented in controller class:
 * <ul>
 *     <li><strong><code>initController</code></strong> - Method that is 
 *     called during instantiation of the controller.</li>
 *     <li><strong><code>preRoute</code></strong> - Method that is called 
 *     before routing to an action of this class.</li>
 *     <li><strong><code>postRoute</code></strong> - Method that is called
 *     after the completion of the routed action.</li>
 *     <li><strong><code>cleanup</code></strong> - Method that is called
 *     when the controller is cleanup up (generally at the termination
 *     of sessions).</li>
 * </ul>
 * 
 * Controller classes have the following life-cycle:
 * <ol>
 *     <li>On first request of a controller instance action method, the 
 *     controller is instantiated and added to the controller cache.</li>
 *     <li>On the next and subsequent requests of a controller instance 
 *     action method, the controller instance is recalled and reused to
 *     run action.</li>
 *     <li>On termination of the masters rig session, clean up is called
 *     and the controller instance is discarded.</li>
 * </ol> 
 * It is safe to have resources (open files, handles...) as instance fields
 * provided they are cleaned in the <code>cleanup</code>.
 */
public interface IPrimitiveController
{
    /**
     * Initialise the controller. 
     * 
     * @return
     */
    public boolean initController();
    
    public boolean preRoute();
    
    public boolean postRoute();
   
    public boolean cleanup();
}
