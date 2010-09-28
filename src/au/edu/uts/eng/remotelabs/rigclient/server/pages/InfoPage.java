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
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Runtime information page.
 */
public class InfoPage extends AbstractPage
{
    /** The names of the tabs. */
    private final Map<String, String> tabNames;
    
    /** Methods that provide specific tab information. */
    private final Map<String, Method> tabMethods;
    
    /** The icons for the tabs. */
    private Map<String, String> tabIcons;
    
    /** The tool tips for the tabs */
    private Map<String, String> tabTooltips;
    
    public InfoPage()
    {
        super();
        
        this.tabNames = new LinkedHashMap<String, String>(4);
        this.tabNames.put("runtime", "Runtime Info");
        this.tabNames.put("resources", "System");
        this.tabNames.put("vm", "Java");
        this.tabNames.put("os", "OS");
        
        this.tabMethods = new HashMap<String, Method>(4);
        try
        {
            this.tabMethods.put("runtime", InfoPage.class.getMethod("runtimeTab"));
            this.tabMethods.put("resources", InfoPage.class.getMethod("resTab"));
            this.tabMethods.put("vm", InfoPage.class.getMethod("vmTab"));
            this.tabMethods.put("os", InfoPage.class.getMethod("osTab"));
        }
        catch (SecurityException e)
        {
            this.logger.error("Security exception access method of info page class, message: " + e.getMessage() + ". " +
            		"This is a bug so please report it.");
        }
        catch (NoSuchMethodException e)
        { 
            this.logger.error("No such method in info page class, message: " + e.getMessage() + ". " +
                    "This is a bug so please report it.");
        }
        
        this.tabIcons = new HashMap<String, String>(4);
        this.tabIcons.put("runtime", "runtime");
        this.tabIcons.put("resources", "res");
        this.tabIcons.put("vm", "javavm");
        this.tabIcons.put("os", "opsys");
        
        this.tabTooltips = new HashMap<String, String>(4);
        this.tabTooltips.put("runtime", "Displays runtime information like classpath and system properties...");
        this.tabTooltips.put("resources", "Displays resources being used by the rig client.");
        this.tabTooltips.put("vm", "Displays information about the in use Java virtual machine.");
        this.tabTooltips.put("os", "Displays operating system information.");
    }
    
    @Override
    public void preService(HttpServletRequest req)
    {
        if (this.tabMethods.containsKey(req.getRequestURI().substring(req.getRequestURI().lastIndexOf('/') + 1)))
        {
            this.framing = false;
        }
    }
    
    @Override
    public void contents(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        String suf = req.getRequestURI().substring(req.getRequestURI().lastIndexOf('/') + 1);
        if (this.tabMethods.containsKey(suf))
        {
            try
            {
                this.tabMethods.get(suf).invoke(this);
            }
            catch (Exception e)
            {
                this.logger.error("Exception invoking info page method, message: " + e.getMessage() + ". This is a " +
                		"bug so please report it.");
            }
        }
        else
        {
            this.indexPage();
        }
    }
    
    public void indexPage()
    {
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
            this.println("      <img src='/img/info/" + this.tabIcons.get(name) + "_small.png' alt='" + name + "' />");
            this.println("    </div>");
            this.println("    <div class='linkbutcontlabel'>" + t.getValue() + "</div>");
            this.println("    <div id='" + name + "hover' class='tooltiphov ui-corner-all'>");
            this.println("      <div class='tooltipimg'><img src='/img/info/" + this.tabIcons.get(name) + ".png' alt='"+ name + "' /></div>");
            this.println("      <div class='tooltipdesc'>" + this.tabTooltips.get(name) + "</div>");
            this.println("    </div>");
            this.println("  </div>");
            this.println("</a></li>");
            
            i++;
        }
        
        this.println("  </ul>");
        this.println("</div>");

        /* Tool tip events. */
        this.println("<script type='text/javascript'>");
        this.println("var ttStates = new Object();");

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
        this.println("})");
        this.println("</script>");
        
        /* Content pane. */
        this.println("<div id='contentspane' class='ui-corner-tr ui-corner-bottom'>");
        this.println("  <table id='contentstable'>");
        this.println("    <tr>");
        this.println("      <th class='propcol'>Property</th>");
        this.println("      <th class='valcol'>Value</th>");
        this.println("    </tr>");
        this.runtimeTab();
        this.println("  </table>");
        this.println("</div>");
    }
    
    /**
     * Provides runtime information from the <tt>RuntimeMXBean</tt>. 
     */
    public void runtimeTab()
    {
        // TODO runtime tab
        this.addRow("run", "time");
    }
    
    public void resTab()
    {
        // TODO resources tab
        this.addRow("Resources", "tab");
    }
    
    public void vmTab()
    {
        // TODO virutal machine tab
        this.addRow("virtual", "machine");
    }
    
    public void osTab()
    {
        // TODO Operating systems tab
        this.addRow("op", "sys");
    }
    
    /**
     * Adds a row to the output.
     * 
     * @param prop property
     * @param val value
     */
    private void addRow(String prop, String val)
    {
        this.println("<tr>");
        this.println("  <td col='propcol'>" + prop + "</td>");
        this.println("  <td col='valcol>" + val + "</td>");
        this.println("</tr>");
    }

    @Override
    protected String getPageType()
    {
        return "Diagnostics";
    }
}
