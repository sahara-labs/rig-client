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
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Runtime information page.
 */
public class InfoPage extends AbstractPage
{
    @Override
    public void contents(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        this.println("<div id='allinfo'>");
        
        /* Runtime information. */
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        
        this.println("  <div id='runtimecontainer' class='detailspanel ui-corner-all'>");
        this.println("      <div class='detailspaneltitle'>");
        this.println("          <p>");
        this.println("              <span class='detailspanelicon ui-icon ui-icon-script'> </span>");
        this.println("              Runtime:");
        this.println("          </p>");
        this.println("      </div>");
        this.println("      <div class='detailspanelcontents'>");
        
        this.println("<table>");
        this.println("  <tr>");
        this.println("      <th>Item</th>");
        this.println("      <th>Value</th>");
        this.println("  </tr>");
        this.println("  <tr>");
        this.println("      <td>Start time</td>");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(runtime.getStartTime());
        
        
        this.println("      <td>"  + "</td>");
        this.println("  </tr>");
        this.println("</table>");
        
        this.println("      </div>"); // runtime panel contents
        this.println("  </div>");
        
        this.println("</div>"); // allinfo
    }

    @Override
    protected String getPageType()
    {
        return "Runtime Information";
    }
}
