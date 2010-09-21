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
 * @date 20th September 2010
 */
package au.edu.uts.eng.remotelabs.rigclient.server.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession.Session;
import au.edu.uts.eng.remotelabs.rigclient.status.StatusUpdater;

/**
 * Rig status page.
 */
public class StatusPage extends AbstractPage
{
    @Override
    public void contents(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        /* Main status. */
        this.println("<div id='bigstatus'>");
        if (!StatusUpdater.isRegistered())
        {
            this.println("  <img src='/img/blue.gif' alt='Not registered' />");
            this.println("  <h3>Not registered</h3>");
        }
        else if (this.rig.isSessionActive())
        {
            this.println("  <img src='/img/yellow.gif' alt='In use' />");
            this.println("  <h3>In Use</h3>");
        }
        else if (this.rig.isMonitorStatusGood())
        {
            this.println("  <img src='/img/green.gif' alt='In use' />");
            this.println("  <h3>Online</h3>");
        }
        else
        {
            this.println("  <img src='/img/red_anime.gif' alt='In use' />");
            this.println("  <h3>Offline</h3>");
        }
        this.println("</div>");
        
        /* Push div. */
        this.println("<div style='height:40px'> </div>");
        
        /* Exerciser details. */
        this.println("<div id='exerciserdetails' class='ui-corner-all detailspanel'>");
        this.println("  <div class='detailspaneltitle'>");
        this.println("  <p><span class='ui-icon ui-icon-wrench detailspanelicon'></span>Exerciser details</p>");
        this.println("  </div>");
        this.println("  <div class='detailpanelcontents'>");
        
        this.println("  </div>");
        this.println("</div>");
        
        
        /* Session details. */
        this.println("<div id='sessiondetails' class='ui-corner-all detailspanel'>");
        this.println("  <div class='detailspaneltitle'>");
        this.println("  <p><span class='ui-icon ui-icon-person detailspanelicon'></span>Session details</p>");
        this.println("  </div>");
        this.println("  <div class='detailpanelcontents'>");
        if (this.rig.isSessionActive())
        {
            /* Activity detection. */
            this.println("<div id='activitydect'>");
            this.println("  Activity:");
            if (this.rig.isActivityDetected())
            {
               this.println("<img src='/img/green_small.gif' />");
            }
            else
            {
                this.println("<img src='/img/red_small.gif' />");
            }
            this.println("</div>");
            
            /* User list. */
            String master = "";
            List<String> activeSlaves = new ArrayList<String>();
            List<String> passiveSlaves = new ArrayList<String>();
            
            for (Entry<String, Session> e : this.rig.getSessionUsers().entrySet())
            {
                switch (e.getValue())
                {
                    case MASTER:
                        master = e.getKey();
                        break;
                    case SLAVE_ACTIVE:
                        activeSlaves.add(e.getKey());
                        break;
                    case SLAVE_PASSIVE:
                        passiveSlaves.add(e.getKey());
                        break;
                }
            }
            
            this.println("Master user: <strong>" + master + "</strong><br />");
            this.println("<strong>" + activeSlaves.size() + "</strong> active slave users.<br />");
            if (activeSlaves.size() > 0)
            {
                this.println("Active slave list:");
                this.println("<ul>");
                for (String u : activeSlaves)
                {
                    this.println("<li>" + u + "</li>");
                }
                this.println("</ul>");
            }
            
            this.println("<strong>" + passiveSlaves.size() + "</strong> passive slave users.<br />");
            if (passiveSlaves.size() > 0)
            {
                this.println("Passive slave list:");
                this.println("<ul>");
                for (String u : passiveSlaves)
                {
                    this.println("<li>" + u + "</li>");
                }
                this.println("</ul>");
            }
            
            
        }
        else
        {
            this.println("No session is active.");
        }
        
        this.println("  </div>");
        this.println("</div>");
    }

    @Override
    protected String getPageType()
    {
        return "Status";
    }
    
    @Override
    protected String getPageHeader()
    {
        return this.stringTransform(this.config.getProperty("Rig_Name")) + " Status";
    }

}
