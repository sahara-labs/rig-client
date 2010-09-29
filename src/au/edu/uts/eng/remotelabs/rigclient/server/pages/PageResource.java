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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Downloads resources from the META-INF folder.
 */
public class PageResource
{
    /** Servlet state time. */
    private Long startTime;
    
    /** Logger. */
    private ILogger logger;
    
    public PageResource()
    {
        this.logger = LoggerFactory.getLoggerInstance();
        
        this.startTime = System.currentTimeMillis();
    }
    /**
     * Downloads a resource.
     * 
     * @param req request
     * @param resp response
     * @throws IOException 
     */
    public void download(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        String path = "/META-INF/web" + req.getRequestURI();
        this.logger.debug("Resource request path is '" + path + "'.");
        
//        FIXME Re-enable 302 browser caching
//        if (req.getDateHeader("If-Modified-Since") / 60000 == this.startTime / 60000)
//        {
//            resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
//            return;
//        }
        
        InputStream is = PageResource.class.getResourceAsStream(path);
        if (is == null)
        {
            this.logger.warn("Web resource '" + path + "' not found.");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("Not found.");
            return;
        }
        
        /* Set the header at the time of start the rig client (the files are 
         * packaged in the jar so they won't change. */
        resp.setDateHeader("Last-Modified", this.startTime);
       
        
        BufferedInputStream stream = new BufferedInputStream(is);
        BufferedOutputStream out = new BufferedOutputStream(resp.getOutputStream());
        
        /* Content type. */
        if      (path.endsWith("js"))   resp.setContentType("text/javascript");
        else if (path.endsWith("css"))  resp.setContentType("text/css");
        else if (path.endsWith("png"))  resp.setContentType("image/png");
        else if (path.endsWith("jpg") 
              || path.endsWith("jpeg")) resp.setContentType("image/jpeg");
        else if (path.endsWith("gif"))  resp.setContentType("image/gif");
        else                            resp.setContentType("application/octet-stream");
        
        byte buf[] = new byte[1024];
        int len = 0;
        while ((len = stream.read(buf)) > 0)
        {
            out.write(buf, 0, len);
        }
        
        out.flush();
        stream.close();
    }
}
