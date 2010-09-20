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
package au.edu.uts.eng.remotelabs.rigclient.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.uts.eng.remotelabs.rigclient.server.pages.AbstractPage;
import au.edu.uts.eng.remotelabs.rigclient.server.pages.ConfigPage;
import au.edu.uts.eng.remotelabs.rigclient.server.pages.DocPage;
import au.edu.uts.eng.remotelabs.rigclient.server.pages.IndexPage;
import au.edu.uts.eng.remotelabs.rigclient.server.pages.InfoPage;
import au.edu.uts.eng.remotelabs.rigclient.server.pages.LogsPage;
import au.edu.uts.eng.remotelabs.rigclient.server.pages.PageResource;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Root URL servlet which provides allows the following pages:
 * <ol>
 *  <li><tt>/</tt> - A jump-off page to either configuration or help 
 *  documentation.</li>
 *  <li><tt>/conf</tt> - Configuration page (authenticated).</li>
 *  <li><tt>/doc</tt> - Documentation page.</li>
 *  <li><tt>/restart</tt> - Restarts the rig client (authenticated).</li>
 *  <li><tt>/images/*</tt> - Images for the interface.</li>
 *  <li><tt>/css/*</tt> - Stylesheets for the interface.</li>
 * </ol>
 */
public class RootServlet extends HttpServlet
{
    /** Serializable class. */
    private static final long serialVersionUID = -454911787592576294L;
    
    /** Resource URL bases. */
    private final List<String> resources;
    
    /** Page URL bases. */
    private final Map<String, String> pages;
    
    /** Logger. */
    private ILogger logger;
    
    public RootServlet()
    {
        this.logger = LoggerFactory.getLoggerInstance();
        
        /* Resources list. */
        this.resources = new ArrayList<String>();
        this.resources.add("res");
        this.resources.add("img");
        
        /* Pages list. */
        this.pages = new HashMap<String, String>();
        this.pages.put("config", ConfigPage.class.getName());
        this.pages.put("doc", DocPage.class.getName());
        this.pages.put("info", InfoPage.class.getName());
        this.pages.put("logs", LogsPage.class.getName());
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void service(final HttpServletRequest req, final HttpServletResponse resp)
    {
        this.logger.debug("Received request: " + req.getRequestURI());
        String parts[] = req.getRequestURI().split("/");

        try
        {
            /* Dispatches the request either to a downloadable resource or 
             * page. */
            if (parts.length < 2)
            {
                /* Nothing has been requested, so just give the default index
                 * page. */
                new IndexPage().service(req, resp);
            }
            else if (this.resources.contains(parts[1]))
            {
                /* Downloadable resource, so provide the requested resource. */
                new PageResource().download(req, resp);
            }
            else if (this.pages.containsKey(parts[1]))
            {
                /* Load the page. */
                ClassLoader loader =  RootServlet.class.getClassLoader();
                Class<AbstractPage> clazz = (Class<AbstractPage>) loader.loadClass(this.pages.get(parts[1]));
                clazz.newInstance().service(req, resp);
            }
            else
            {
                // TODO error page
                
            }

        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
