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

package au.edu.uts.eng.remotelabs.rigclient.collaboration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import au.edu.uts.eng.remotelabs.rigclient.collaboration.types.Message;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

public class CommunicationDirectory
{

    private final ILogger logger;
    private final ArrayList<Message> messages;
    private final Map<String, Integer> lastReceived;

    public CommunicationDirectory()
    {
        this.logger = LoggerFactory.getLoggerInstance();
        this.messages = new ArrayList<Message>();
        this.lastReceived = new TreeMap<String, Integer>();

        this.logger.debug("Communication Directory re-initialized.");
    }

    public synchronized boolean addMessage(final String user, final String message)
    {
        this.messages.add(new Message(user, message));
        return true;
    }

    public Message getMessage(final int id)
    {
        try
        {
            return this.messages.get(id);
        }
        catch (final IndexOutOfBoundsException e)
        {
            this.logger.warn("Invalid message index in Communication Directory.");
            return null;
        }
    }

    public boolean isUpToDate(final String user)
    {
        if (!this.lastReceived.containsKey(user))
        {
            this.lastReceived.put(user, -1);
        }
        return (this.lastReceived.get(user).intValue() == (this.messages.size() - 1));
    }

    public List<Message> getMessages(final int startId)
    {
        return this.messages.subList(startId, this.messages.size());
    }

    public List<Message> getRemainingMessages(final String user)
    {
        if (!this.lastReceived.containsKey(user))
        {
            this.lastReceived.put(user, -1);
        }
        final int s = this.messages.size();
        final List<Message> ret = this.messages.subList(this.lastReceived.get(user) + 1, s);
        this.lastReceived.put(user, s - 1);
        return ret;

    }

}
