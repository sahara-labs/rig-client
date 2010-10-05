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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Documentation about the rig client.
 */
public class DocPage extends AbstractPage
{
    /** Documentation PDF links. */
    private final Map<String, String> links;
    
    /** Icons. */
    private final Map<String, String> icons;
    
    /** Tooltips. */
    private final Map<String, String> toolTips;
    
    public DocPage()
    {
        super();
        
        this.links = new LinkedHashMap<String, String>(2);
        this.links.put("Installation", "/pdf/Installation.pdf");
        this.links.put("Development", "/pdf/Handbook.pdf");

        this.icons = new HashMap<String, String>(2);
        this.icons.put("Installation", "install");
        this.icons.put("Development", "handbook");
        
        this.toolTips = new HashMap<String, String>(2);
        this.toolTips.put("Installation", "How to install Sahara.");
        this.toolTips.put("Development", "How to develop rigs for Sahara.");
    }
    
    @Override
    public void contents(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        this.println("<div id='doclist'>");
        this.println("  <ul class='ullinklist'>");

        int i = 0;
        for (Entry<String, String> e : this.links.entrySet())
        {
            String name = e.getKey();
            String classes = "linkbut indexlinkbut plaina";
            if (i == 0) classes += " ui-corner-top";
            else if (i == this.links.size() - 1) classes += " ui-corner-bottom";

            this.println("       <li><a id='" + name + "link' class='" + classes + "' href='" + e.getValue() + "'>");
            this.println("           <div class='linkbutcont'>");
            this.println("               <div class='linkbutconticon'>");
            this.println("                   <img src='/img/" + this.icons.get(name) + "_small.png' alt='" + name + "' />");
            this.println("               </div>");
            this.println("               <div class='linkbutcontlabel'>" + this.stringTransform(name) + "</div>");
            this.println("               <div id='" + name + "hover' class='tooltiphov ui-corner-all'>");
            this.println("                  <div class='tooltipimg'><img src='/img/" + this.icons.get(name) + ".png' alt='"+ name + "' /></div>");
            this.println("                  <div class='tooltipdesc'>" + this.toolTips.get(name) + "</div>");
            this.println("               </div>");
            this.println("           </div>");
            this.println("      </a></li>");

            i++;
        }

        this.println("   </ul>"); // ullinklist
        this.println("</div>"); // doclist

        /* Tooltip hover events. */
        this.println("<script type='text/javascript'>");

        this.println("var ttStates = new Object();");

        this.println( 
                "function loadIndexToolTip(name)\n" + 
                "{\n" + 
                "    if (ttStates[name])\n" + 
                "    {\n" + 
                "        $('#' + name + 'hover').fadeIn();\n" + 
                "        $('#' + name + 'link').css('font-weight', 'bold');\n" + 
                "    }\n" + 
        "}\n");

        this.println("$(document).ready(function() {");
        for (String name : this.toolTips.keySet())
        {
            this.println("    ttStates['" + name + "'] = false;");
            this.println("    $('#" + name + "link').hover(");
            this.println("        function() {");
            this.println("            ttStates['" + name + "'] = true;");
            this.println("            setTimeout('loadIndexToolTip(\"" + name + "\")', 1200);");
            this.println("        },");
            this.println("        function() {");
            this.println("            if (ttStates['" + name + "'])");
            this.println("            {");
            this.println("                $('#" + name + "hover').fadeOut();");
            this.println("                $('#" + name + "link').css('font-weight', 'normal');");
            this.println("                ttStates['" + name + "'] = false;");
            this.println("            }");
            this.println("        }");
            this.println("     )");

        }
        this.println("})");
        this.println("</script>");
    }

    @Override
    protected String getPageType()
    {
        return "Documentation";
    }

}
