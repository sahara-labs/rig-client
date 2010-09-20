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
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface for the a embedded server page.
 */
public abstract class AbstractPage
{
    /**
     * Services the request.
     * 
     * @param req request
     * @param resq resposne
     * @throws IOException 
     */
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        resp.setStatus(HttpServletResponse.SC_OK);
        
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        this.addHead(out);
        out.println("<body onload='initPage()' onresize='resizeFooter()'>");
        out.println("<div id='wrapper'>");
        this.addHeader(out);
        this.addNavbar(out, "Index");
        
        /* Actual page contents. */
        this.contents(req, resp);
        
        this.addFooter(out);
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
    
    /**
     * Adds the page specific contents.
     * 
     * @param req request 
     * @param resp response
     * @throws IOException 
     */
    protected abstract void contents(HttpServletRequest req, HttpServletResponse resp) throws IOException;
    
    /**
     * Gets the page type.
     * 
     * @return page type
     */
    protected abstract String getPageType();
    
    /**
     * Adds the head section to the page.
     * 
     * @param out output writer
     */
    protected void addHead(PrintWriter out)
    {
        out.println("<head>");
        out.println("   <meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
        out.println("   <link href='/res/rigclient.css' media='screen' rel='stylesheet' type='text/css' >");
        out.println("   <script type='text/javascript' src='/res/rigclient.js'> </script> ");
        out.println("   <title>Rig Client</title>");
        out.println("</head>");
    }
   
    /**
     * Adds the header to the page.
     * 
     * @param out output writer
     */
    protected void addHeader(PrintWriter out)
    {
        out.println("<div id='header'>");
        out.println("    <div class='headerimg' >");
        out.println("        <a href='http://sourceforge.net/projects/labshare-sahara/'>" +
        		"<img src='/img/logo.png' alt='Sourceforge Project' /></a>");
        out.println("   </div>");
        out.println("   <div class='headerimg' >");
        out.println("        <img src='/img/sahara.png' alt='Sahara Labs' />");
        out.println("    </div>");
        out.println("    <div id='labshareimg'>");
        out.println("        <a href='http://www.labshare.edu.au/'><img src='/img/labshare.png' alt='LabShare' /></a>");
        out.println("    </div>");
        out.println("</div>");   
    }
    
    /**
     * Adds the header to the page.
     * 
     * @param out output writer
     */
    protected void addNavbar(PrintWriter out, String page)
    {
        out.println("<div class='menu ui-corner-bottom'>");
        out.println("   <ol id='menu'>");
        this.innerNavBar(out, "Main", "/");
        this.innerNavBar(out, "Configuration", "/config");
        this.innerNavBar(out, "Documentation", "/doc");
        this.innerNavBar(out, "Logs", "/logs");
        this.innerNavBar(out, "Runtime Information", "/info");
        out.println("   </ol>");
        out.println("   <div id='slide'> </div>");
        out.println("</div>");
    }
    
    /**
     * Adds a nav bar item to the navigation bar.
     * 
     * @param out output writer
     * @param name name to display
     * @param path path to page
     */
    private void innerNavBar(PrintWriter out, String name, String path)
    {
        if (this.getPageType().equals(name))
        {
            out.println("    <li value='1'>");
        }
        else
        {
            out.println("    <li>");
        }
        out.println("            <a class='plaina apad' href='" + path + "'>" + name + "</a>");
        out.println("        </li>");
    }
    
    /**
     * Adds the footer to the page.
     * 
     * @param out output writer
     */
    protected void addFooter(PrintWriter out)
    {
        out.println("<div id='footer' class='ui-corner-top'>");
        out.println("   <a class='plaina alrpad' href='http://www.labshare.edu.au'>Labshare Australia</a>");
        out.println("   | <a class='plaina alrpad' href='http://www.uts.edu.au'>&copy; University of Technology, Sydney 2009 - 2010</a>");
        out.println("</div>");
    }
}
