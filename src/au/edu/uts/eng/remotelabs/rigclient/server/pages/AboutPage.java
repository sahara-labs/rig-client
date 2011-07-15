/**
 * SAHARA Rig Client
 * 
 * Software abstraction of physical rig to provide rig session control
 * and rig device control. Automatically tests rig hardware and reports
 * the rig status to ensure rig goodness.
 *
 * @license See LICENSE in the top level directory for complete license terms.
 *
 * Copyright (c) 2011, University of Technology, Sydney
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
 * @date 13th July 2011
 */
package au.edu.uts.eng.remotelabs.rigclient.server.pages;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * About page.
 */
public class AboutPage extends AbstractPage
{
    /** The names of the tabs. */
    private final Map<String, String> tabNames;
    
    /** Methods that provide specific tab information. */
    private final Map<String, Method> tabMethods;
    
    /** The icons for the tabs. */
    private final Map<String, String> tabIcons;
    
    /** The tool tips for the tabs */
    private final Map<String, String> tabTooltips;
    
    public AboutPage()
    {
        super();
        
        this.tabNames = new TreeMap<String, String>();
        this.tabNames.put("about", "About");
        this.tabNames.put("developers", "Developers");
        this.tabNames.put("license", "License");
        this.tabNames.put("libraries", "Libraries");
        
        this.tabMethods = new HashMap<String, Method>();
        
        try
        {
            this.tabMethods.put("about", AboutPage.class.getDeclaredMethod("adTab"));
            this.tabMethods.put("developers", AboutPage.class.getDeclaredMethod("dvTab"));
            this.tabMethods.put("license", AboutPage.class.getDeclaredMethod("lcTab"));
            this.tabMethods.put("libraries", AboutPage.class.getDeclaredMethod("ldTab"));
        }
        catch (SecurityException e)
        {
            this.logger.error("Security exception access method of about page class, message: " + e.getMessage() + ". " +
            		"This is a bug so please report it.");
        }
        catch (NoSuchMethodException e)
        {
            this.logger.error("No such method exception access method of about page class, message: " + e.getMessage() +
                    ". " + "This is a bug so please report it.");
        }
        
        this.tabIcons = new HashMap<String, String>();
        this.tabIcons.put("about", "about");
        this.tabIcons.put("developers", "person");
        this.tabIcons.put("license", "license");
        this.tabIcons.put("libraries", "lib");
        
        this.tabTooltips = new HashMap<String, String>();
        this.tabTooltips.put("about", "Short description about the Rig Client.");
        this.tabTooltips.put("developers", "Who the developers are.");
        this.tabTooltips.put("license", "The license of SAHARA Labs which specifies your rights and responsiblities " +
                "with respect to the software.");
        this.tabTooltips.put("libraries", "The open source libraries used in the development of SAHARA Labs.");
    }
  
    @Override
    public void contents(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        /* Developers section. */
        /* Tabs. */
        this.println("<div id='lefttabbar'>");
        this.println("  <ul id='lefttablist'>");
        
        int i = 0;
        for (Entry<String, String> t : this.tabNames.entrySet())
        {
            String name = t.getKey();
            String classes = "notselectedtab";
            if (i == 0) classes = "ui-corner-tl selectedtab";
            else if (i == this.tabNames.size() - 1) classes += " ui-corner-bl";

            this.println("<li><a id='" + name + "tab' class='" + classes + "' onclick='loadInfoTab(\"" + name + "\")'>");
            this.println("  <div class='linkbutcont'>");
            this.println("    <div class='linkbutconticon'>");
            this.println("      <img src='/img/" + this.tabIcons.get(name) + "_small.png' alt='" + name + "' />");
            this.println("    </div>");
            this.println("    <div class='linkbutcontlabel'>" + t.getValue() + "</div>");
            this.println("    <div id='" + name + "hover' class='tooltiphov ui-corner-all'>");
            this.println("      <div class='tooltipimg'><img src='/img/" + this.tabIcons.get(name) + ".png' alt='"+ name + "' /></div>");
            this.println("      <div class='tooltipdesc'>" + this.tabTooltips.get(name) + "</div>");
            this.println("    </div>");
            this.println("  </div>");
            this.println("</a></li>");
            
            i++;
        }
        
        this.println("  </ul>");
        this.println("</div>");
        
        /* Content pane. */
        this.println("<div id='contentspane' class='ui-corner-tr ui-corner-bottom'>");
        boolean firstSet = false;
        for (String name : this.tabNames.keySet())
        {
            if (firstSet) this.println("<div id='" + name + "contents' class='notdisplayed'>");
            else
            {
                this.println("<div id='" + name + "contents' class='displayed'>");
                firstSet = true;
            }
            
            try
            {
                this.tabMethods.get(name).invoke(this);
            }
            catch (Exception e)
            {
                this.logger.error("Error invoking about page method. This is a bug so please report it.");
            }
        }
        this.println("</div>");

        /* Tool tip events. */
        this.println("<script type='text/javascript'>");
        this.println("var ttStates = new Object();");
        this.println("var selectedTab = 'runtime';");

        this.println( 
                "function loadInfoToolTip(name)\n" + 
                "{\n" + 
                "    if (ttStates[name])\n" + 
                "    {\n" + 
                "        $('#' + name + 'hover').fadeIn();\n" + 
                "        $('#' + name + 'link').css('font-weight', 'bold');\n" + 
                "    }\n" + 
        "}\n");

        this.println("$(document).ready(function() {");
        for (String name : this.tabTooltips.keySet())
        {
            this.println("    ttStates['" + name + "'] = false;");
            this.println("    $('#" + name + "tab').hover(");
            this.println("        function() {");
            this.println("            ttStates['" + name + "'] = true;");
            this.println("            setTimeout('loadInfoToolTip(\"" + name + "\")', 1200);");
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
		
		/* Contents pane height. */
		this.println("  $('#contentspane').css('height', $(window).height() - 230);");
		this.println(
		        "  $(window).resize(function() { " +
				"    $('#contentspane').css('height', $(window).height() - 230);\n" +
				"  });");

        
        this.println("})");
        this.println("</script>");
    }
    
    protected void adTab()
    {
        this.println("about");
    }
    
    protected void dvTab()
    {
        this.println("developers");
    }
    
    protected void lcTab()
    {
        this.println("license");
    }
    
    protected void ldTab()
    {
        this.println("libraries");
    }

    @Override
    protected String getPageType()
    {
        return "About";
    }
}
