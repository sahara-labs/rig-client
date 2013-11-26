/**
 * SAHARA Rig Client
 * 
 * Software abstraction of physical rig to provide rig session control
 * and rig device control. Automatically tests rig hardware and reports
 * the rig status to ensure rig goodness.
 *
 * @license See LICENSE in the top level directory for complete license terms.
 *
 * Copyright (c) 2013, Barath Kannan
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
 * @author Barath Kannan
 * @date 26th November 2013
 */

package au.edu.uts.eng.remotelabs.rigclient.collaboration.primitive;

import java.io.IOException;
import java.util.List;

import au.edu.uts.eng.remotelabs.rigclient.collaboration.CollaborationEngine;
import au.edu.uts.eng.remotelabs.rigclient.collaboration.CommunicationDirectory;
import au.edu.uts.eng.remotelabs.rigclient.collaboration.types.Message;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveResponse;
import au.edu.uts.eng.remotelabs.rigclient.rig.primitive.IPrimitiveController;

/**
 * Controller for a Primitive Collaborative Rig
 */
public class CollaborationController implements IPrimitiveController
{
    @Override
    public boolean initController()
    {
        return true;
    }

    @Override
    public boolean preRoute()
    {
        return true;
    }

    /**
     * Updates response with User data
     * 
     * @param request
     * @return response
     */
    public PrimitiveResponse dataAction(final PrimitiveRequest request)
    {
        final PrimitiveResponse response = new PrimitiveResponse();
        response.setSuccessful(true);

        response.addResult("master", CollaborationEngine.getMasterUser());
        response.addResult("users", CollaborationEngine.getUserList());
        response.addResult("requestor", request.getRequestor());
        response.addResult("hascontrol", String.valueOf(CollaborationEngine.hasControl(request.getRequestor())));
        response.addResult("mode", CollaborationEngine.getMode());
        final CommunicationDirectory dir = CollaborationEngine.getDirectory();
        if (!dir.isUpToDate(request.getRequestor()))
        {
            response.addResult("messages", this.htmlStringMessages(dir.getRemainingMessages(request.getRequestor())));
        }
        return response;
    }

    /**
     * Toggles experiment control for the passed in user
     * 
     * @param request
     * @return response
     */
    public PrimitiveResponse toggleControlAction(final PrimitiveRequest request) throws IOException
    {
        if (request.getRequestor().equals(CollaborationEngine.getMasterUser()))
        {
            final String user = request.getParameters().get("user");
            if (!CollaborationEngine.hasControl(user))
            {
                CollaborationEngine.assignControlToUser(user);
            }
            else
            {
                CollaborationEngine.removeControlFromUser(user);
            }
        }
        else if (CollaborationEngine.getMode().equals("Baton Pass")
                && CollaborationEngine.hasControl(request.getRequestor()))
        {
            CollaborationEngine.assignControlToUser(request.getParameters().get("user"));
        }
        return this.dataAction(request);
    }

    public PrimitiveResponse setModeAction(final PrimitiveRequest request) throws IOException
    {
        if (!request.getRequestor().equals(CollaborationEngine.getMasterUser()))
        {
            return this.dataAction(request);
        }

        final String mode = request.getParameters().get("mode");
        if (mode.equals("Free For All"))
        {
            CollaborationEngine.setModeFFA();
        }
        else if (mode.equals("Master/Slave"))
        {
            CollaborationEngine.setModeMS();
        }
        else if (mode.equals("Baton Pass"))
        {
            CollaborationEngine.setModeBP();
        }
        return this.dataAction(request);
    }

    /**
     * Pushes a message to the Communication Directory
     * 
     * @param request
     * @return response
     */
    public PrimitiveResponse pushMessageAction(final PrimitiveRequest request) throws IOException
    {
        CollaborationEngine.getDirectory().addMessage(request.getRequestor(), request.getParameters().get("message"));
        return this.dataAction(request);
    }

    public boolean hasControl(final PrimitiveRequest request)
    {
        return CollaborationEngine.hasControl(request.getRequestor());
    }

    @Override
    public boolean postRoute()
    {
        return true;
    }

    @Override
    public void cleanup()
    {
        /* Does nothing. */
    }

    /**
     * Converts a list of messages to a HTML string to display in a chat box interface
     * 
     * @param List
     *            of messages
     * @return String of messages
     */
    private String htmlStringMessages(final List<Message> messages)
    {
        String ret = "";
        for (final Message m : messages)
        {
            ret = ret + m.toString() + "<br/>";
        }

        return ret;
    }

}