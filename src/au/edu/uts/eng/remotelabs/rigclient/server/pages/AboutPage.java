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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.uts.eng.remotelabs.rigclient.main.RigClientDefines;

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
        this.tabTooltips.put("about", "Short description about this software.");
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

            this.println("<li><a id='" + name + "tab' class='" + classes + "' onclick='loadAboutTab(\"" + name + "\")'>");
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
            this.println("</div>");
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
        this.println("<div id='slbanner'>");
        this.println("    <span>Rig Client r" + RigClientDefines.RC_VERSION + " (" + RigClientDefines.RC_RELEASE
                + ")</span><br />");
        this.println("    <img src='/img/logo_large.png' alt='SAHARA Labs' />");
        this.println("    Software abstraction of physical rig to provide rig session control " + 
        		"and rig device control. Automatically tests rig hardware and reports " + 
        		"the rig status to ensure rig goodness.");
        this.println("</div>");
        
        this.println("<div id='slcopyright'>");
        this.println("   Copyright &copy; 2009 - 2013, University of Technology, Sydney");
        this.println("</div>");
    }
    
    protected void dvTab()
    {
        this.println("<div id='devimage'>");
        this.println("   <img src='/img/team.jpg' alt='Developers' />");
        this.println("</div>");
        
        this.println("<div id='devdesc'>");
        this.println("From left:");
        this.println("   <ul>");
        this.println("      <li>Michael Diponio - designed and wrote the system.</li>");
        this.println("      <li>Tania Machet - managed the process.</li>");
        this.println("      <li>Michel de la Villefromoy - the <em>boss</em>.</li>");
        this.println("      <li>Tejaswini Despandi - tested the system.</li>");
        this.println("   </ul>");
        this.println("</div>");
    }
    
    protected void lcTab()
    {
        this.println("<div id='saharalicense'>");
        this.println("  <div class='licensebrief'>SAHARA Labs is released under the terms:</div>");
        this.println("  <div class='licensetext'>");
        this.printLicense("/META-INF/web/lic/SAHARA_Labs");
        this.println("  </div>");
        this.println("</div>");
    }
    
    /**
     * The license tab which prints out information about the 
     */
    protected void ldTab()
    {
        this.println("<div id='saharalibs'>");
        this.println("  <div id='saharalibsbrief'>");
        this.println("      SAHARA Labs is built on the libraries:");
        this.println("  </div>");
        
        /* -- Jetty ---------------------------------------------------------- */
        this.println("<div class='libblock'>");
        this.println("  <div class='libname'>");
        this.println("      <img src='/img/libs/jetty.gif' alt='Jetty' />");
        this.println("  </div>");
        this.println("  <div class='liblicense'>");
        this.println("      <div class='liblicenseheader'>Jetty</div>"); 
        this.printLicense("/META-INF/web/lic/Jetty");
        this.println("  </div>");
        this.println("</div>");
        
        /* -- Axis2 ---------------------------------------------------------- */
        this.println("<div class='libblock'>");
        this.println("  <div class='libname'>");
        this.println("      <img src='/img/libs/axis.jpg' alt='Axis2' />");
        this.println("  </div>");
        this.println("  <div class='liblicense'>");
        this.println("      <div class='liblicenseheader'>Apache Axis2</div>"); 
        this.printLicense("/META-INF/web/lic/Axis2");
        this.println("  </div>");
        this.println("</div>");

        /* -- Equinox -------------------------------------------------------- */
        this.println("<div class='libblock'>");
        this.println("  <div class='libname'>");
        this.println("      <img src='/img/libs/equinox.png' alt='Equinox' />");
        this.println("  </div>");
        this.println("  <div class='liblicense'>");
        this.println("      <div class='liblicenseheader'>Equinox (OSGi)</div>"); 
        this.printLicense("/META-INF/web/lic/Equinox");
        this.println("  </div>");
        this.println("</div>");
        
        /* -- Hibernate ------------------------------------------------------ */
        this.println("<div class='libblock'>");
        this.println("  <div class='libname'>");
        this.println("      <img src='/img/libs/hibernate.gif' alt='Hibernate' />");
        this.println("  </div>");
        this.println("  <div class='liblicense'>");
        this.println("      <div class='liblicenseheader'>Hibernate</div>"); 
        this.printLicense("/META-INF/web/lic/Hibernate");
        this.println("  </div>");
        this.println("</div>");
        
        /* -- MySQL connector ------------------------------------------------ */
        this.println("<div class='libblock'>");
        this.println("  <div class='libname'>");
        this.println("      <img src='/img/libs/mysql.png' alt='MySQL' />");
        this.println("  </div>");
        this.println("  <div class='liblicense'>");
        this.println("      <div class='liblicenseheader'>MySQL Connector/J</div>"); 
        this.printLicense("/META-INF/web/lic/MySQLConnector");
        this.println("  </div>");
        this.println("</div>");
        
        /* -- PostgreSQL connector ------------------------------------------- */
        this.println("<div class='libblock'>");
        this.println("  <div class='libname'>");
        this.println("      <img src='/img/libs/postgresql.jpeg' alt='PostgreSQL' />");
        this.println("  </div>");
        this.println("  <div class='liblicense'>");
        this.println("      <div class='liblicenseheader'>PostgreSQL JDBC Connector</div>"); 
        this.printLicense("/META-INF/web/lic/PostgreSQLConnector");
        this.println("  </div>");
        this.println("</div>");
        
        /* -- jTDS SQLServer connector --------------------------------------- */
        this.println("<div class='libblock'>");
        this.println("  <div class='libname'>");
        this.println("      <img src='/img/libs/jTDS.gif' alt='jTDS' />");
        this.println("  </div>");
        this.println("  <div class='liblicense'>");
        this.println("      <div class='liblicenseheader'>jTDS SQLServer JDBC driver</div>"); 
        this.printLicense("/META-INF/web/lic/jTDS");
        this.println("  </div>");
        this.println("</div>");
        
        
        /* -- c3p0 connector ------------------------------------------------- */
        this.println("<div class='libblock'>");
        this.println("  <div class='libname'>");
        this.println("      c3p0");
        this.println("  </div>");
        this.println("  <div class='liblicense'>");
        this.println("      <div class='liblicenseheader'>c3p0 (connection pooling)</div>"); 
        this.printLicense("/META-INF/web/lic/c3p0");
        this.println("  </div>");
        this.println("</div>");
        
        /* -- log4j ---------------------------------------------------------- */
        this.println("<div class='libblock'>");
        this.println("  <div class='libname'>");
        this.println("      <img src='/img/libs/log4j.jpg' alt='log4j' />");
        this.println("  </div>");
        this.println("  <div class='liblicense'>");
        this.println("      <div class='liblicenseheader'>Apache log4j</div>"); 
        this.printLicense("/META-INF/web/lic/log4j");
        this.println("  </div>");
        this.println("</div>");
        
        /* -- jQuery --------------------------------------------------------- */
        this.println("<div class='libblock'>");
        this.println("  <div class='libname'>");
        this.println("      <img src='/img/libs/jquery.png' alt='jQuery' />");
        this.println("  </div>");
        this.println("  <div class='liblicense'>");
        this.println("      <div class='liblicenseheader'>jQuery</div>"); 
        this.printLicense("/META-INF/web/lic/jQuery");
        this.println("  </div>");
        this.println("</div>");
        
        this.println("</div>");
        
        this.println("<script type='text/javascript'>");
        this.println("$(document).ready(function() {\n" + 
        		"    $('#saharalibs .libname').click(function() {\n" + 
        		"        var lic = $(this).next();\n" + 
        		"        lic.css('display', lic.css('display') == 'block' ? 'none' : 'block');\n" + 
        		"    });\n" + 
        		"});");
        this.println("</script>");
    }

    @Override
    protected String getPageType()
    {
        return "About";
    }
    
    /** 
     * Prints out a license given by the name.
     * 
     * @param path file path
     */
    private void printLicense(String path)
    {
        BufferedReader reader = null;
        try
        {
            InputStream is = PageResource.class.getResourceAsStream(path);
            if (is == null)
            {
                this.logger.error("Unable to find license file '" + path + "', this is a bug so please report.");
                return;
            }
            
            reader = new BufferedReader(new InputStreamReader(is));

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                line = line.replace(" ", "&nbsp;");
                if ("".equals(line)) line = "&nbsp;";

                this.println("<div class='licenseline'>" + line + "</div>");
            }
        }
        catch (IOException ex)
        {
            this.logger.error("Exception reading license file '" + path + "', this is a bug so please report.");
        }
        finally 
        {
            if (reader != null)
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    this.logger.error("Exception closing license file '" + path + "', this is a bug so please report.");
                }
        }
    }
}
