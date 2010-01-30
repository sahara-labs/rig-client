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
 * @date 28th January 2010
 *
 * Changelog:
 * - 28/01/2010 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.action.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;

/**
 * Tests the existence of device nodes. At its most basic, the test ensures a
 * one or more files are present. Optionally it may test the following device
 * node parameters (actually file parameters, but device nodes are files):
 * <ul>
 *  <li>File type - if the device node is a character device ('<tt>c</tt>'),
 *  a block device ('<tt>b</tt>'), a directory ('<tt>d</tt>'), a regular file
 *  ('<tt>-</tt>'), a symbolic link ('<tt>l</tt>'), a socket ('<tt>s</tt>') or
 *  a named pipe ('<tt>p</tt>').</li>
 *  <li>File permission - Either the octal file permission number (e.g. '644')
 *  or a string specifying the permissions as shown by 'ls' (e.g. 'rw-rw----').
 *  </li>
 *  <li>File owner user name - The name or uid of the user who should have ownership
 *  of the file.</li>
 *  <li>File group name of owner - The name of the group who should have 
 *  ownership of the file.</li>
 *  <li>Device node numbers - The device node major and/or minor numbers.</li>
 *  <li>Device driver name - the name of the device driver as listed in
 *  <tt>/proc/devices</tt>. This determines from the device name the major
 *  number of the device, then ensures the device node has the same major.</li>
 * </ul>
 * The behaviour of Linux device node test is:
 * <ul>
 *  <li>Periodicity - is periodic.</li>
 *  <li>Set interval - ignored, not honoured.</li>
 *  <li>Light-dark scheduling - disabled.</li>
 * </ul>
 * This test may be configured to test multiple device nodes, with configuration 
 * property related to a specific device node suffixed with a number. The 
 * following are the configurable properties:
 * <ul>
 *  <li><tt>LinuxDeviceNode_Test_Path_&lt;n&gt;</tt> - The path to the device node
 *  (usually in '/dev'.</li>
 *  <li><tt>LinuxDeviceNode_Test_Type_&lt;n&gt;</tt> - The device node file type,
 *  either '-', 'd', 'c', 'b', 'l', 's' or 'p'.</li>
 *  <li><tt>LinuxDeviceNode_Test_Type_&lt;n&gt;</tt> - The permission string as
 *  shown be 'ls'.</li>
 * </ul>
 */
public class LinuxDeviceNodeTestAction extends AbstractTestAction
{
    private List<DeviceNode> deviceNodes;
    
    private ProcessBuilder ls;
    
    private ProcessBuilder stat;
    
    /** The number of times a node test can fail before this test action 
     *  returns a failure. */
    private int failThreshold;
    
    public LinuxDeviceNodeTestAction()
    {
        this.isPeriodic = true;
        this.isSetIntervalHonoured = false;
        this.doLightDarkSchedule = false;
        this.runInterval = 60;
        
        this.deviceNodes = new ArrayList<DeviceNode>();
        this.ls = new ProcessBuilder("/bin/ls", "-l");
        this.stat = new ProcessBuilder("/bin/stat", "-c");
    }

    
    @Override
    public void doTest()
    {
        // TODO Auto-generated method stub

    }

    
    @Override
    public void setUp()
    {
        // TODO Auto-generated method stub

    }

    
    @Override
    public void tearDown()
    {
        /* Does nothing. */
    }

    
    @Override
    public String getReason()
    {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
    public boolean getStatus()
    {
        for (DeviceNode node : this.deviceNodes)
        {
            if (node.getFails() > this.failThreshold)
            {
                return false;
            }
        }
        
        return true;
    }

    
    @Override
    public String getActionType()
    {
        return "Linux device node test";
    }
    
    
    class DeviceNode
    {
        /** The device node file path. */
        private final String nodePath;
        
        /** Device node file type. Options are -dcblsp. If the file type is
         *  set as \0, the file type test is disabled. */
        private char fileType = '\0';
        
        /** Device node octal permission number. If permission number set as
         *  '-1', the octal permission test is disabled. */
        private int octalPermissions = -1;
        
        /** Device node permission string (e.g 'rwxr--r--'). If this is set
         *  to <code>null</code>, the permission string test is disabled. */
        private String permissionStr = null;
        
        /** The name of the owning user. If this is set to <code>null</code>
         *  The owner name test is disabled. */
        private String owner = null;
        
        /** The user identifier of the owning user. If this is set to '-1' 
         *  the owner uid tests is disabled. */
        private int uid = -1;
        
        /** The name of the owning group. If this is set to <code>null</code>,
         *  this test is disabled. */
        private String group = null;
        
        /** The group identifier of the owning group. If this is set to '-1'
         *  the group identifier test is disabled. */
        private int guid = -1;
        
        /** The device node major number. If this is set to '-1' the major 
         *  number test is disabled. */
        private final int majorNumber = -1;
        
        /** The device node minor number. If this is set to '-1' the minor
         *  number test is disabled. */
        private final int minorNumber = -1;
        
        /** The name of the device driver name as shown in '/proc/devices'.
         *  If this is set to <code>null</code> the test is disabled. */
        private final String driverName = null;
        
        /** The number of times this device node has failed. */
        private int fails = 0;
        
        /** Failure reason. */
        private String failureReason;
        
        /**
         * Loads the device node test configuration for the specified path and
         * configuration suffix.
         * 
         * @param path the path of the node
         * @param confNum the device nodes configuration number
         */
        DeviceNode(String path, final int confNum)
        {
            IConfig config = LinuxDeviceNodeTestAction.this.config;
            ILogger logger = LinuxDeviceNodeTestAction.this.logger;
            
            this.nodePath = path;
            String tmp = config.getProperty("LinuxDeviceNode_Test_Type_" + confNum);
            if (tmp != null)
            {
                this.fileType = tmp.charAt(0);
                logger.info("Going to test device node " + this.nodePath + " for file type " + this.fileType + '.');
            }
            
            if ((tmp = config.getProperty("LinuxDeviceNode_Test_Permission_" + confNum)) != null)
            {
                this.permissionStr = tmp;
                logger.info("Going to test device node " + this.nodePath + " for permission string " + 
                        this.permissionStr + '.');
            }
            
            if ((tmp = config.getProperty("" + confNum)) != null)
            {
                try
                {
                    this.octalPermissions = Integer.parseInt(tmp);
                    logger.info("Going to test device node " + this.nodePath + " for octal permission number " +
                            this.octalPermissions + '.');
                }
                catch (NumberFormatException ex)
                {
                    this.octalPermissions = -1;
                    logger.warn("Invalid octal permission number " + tmp + ", no going to test octal file permission " +
                    		"of" + this.nodePath + '.');
                }
            }
            
        }
        
        /**
         * Test the device node.
         */
        public void test()
        {
            ILogger logger = LinuxDeviceNodeTestAction.this.logger;
            try
            {
                /* 1) Test the device node file exists. */
                File node = new File(this.nodePath);
                if (!node.exists())
                {
                    
                    logger.debug("Failing device node test for " + this.nodePath + " because the file does not exist.");
                    this.fails++;
                    return;
                }
                
                /* Get the output of `ls`. */
                String lsParts[] = null;
                LinuxDeviceNodeTestAction.this.ls.command().add(this.nodePath);
                Process proc = LinuxDeviceNodeTestAction.this.ls.start();
                LinuxDeviceNodeTestAction.this.ls.command().remove(this.nodePath);
                if (proc.waitFor() != 0)
                {
                    /* ls doesn't detect this file... Bizarre, Java detects is... */
                    logger.debug("Failing device node test for " + this.nodePath + " becase the file does not exist " +
                    		"('ls' returned a non-zero (" + proc.exitValue() + ") return code.");
                    this.fails++;
                    this.failureReason = "Does not exist.";
                    return;
                }
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                lsParts = reader.readLine().split("\\s");
                reader.close();
                
                /* 2) Test the file type. */
                if (this.fileType != '\0' && this.fileType != lsParts[0].charAt(0))
                {
                    logger.debug("Failed device node test for " + this.nodePath + " becase the file type (" 
                            + lsParts[0].charAt(0) + ") is not " + this.fileType + '.');
                    this.fails++;
                    this.failureReason = "Not of type " + this.fileType + '.';
                    return;
                }
                
                /* 3) Test the device file permission (can include on exclude file type prefix). */
                if (this.permissionStr != null && 
                        (this.permissionStr.equals(lsParts[0]) || this.permissionStr.equals(lsParts[0].substring(1))))
                {
                    logger.debug("Failed device node test for " + this.nodePath + " because the file permissions are " +
                    		"incorrect (" + lsParts[0].substring(1) + " is not " + this.permissionStr + ").");
                    this.fails++;
                    this.failureReason = "Incorrect permission.";
                    return;
                }
                
                
                
                /* All good!. */
                this.fails = 0;
            }
            catch (IOException ex)
            {
                // TODO io exception
            }
            catch (InterruptedException e)
            {
               Thread.currentThread().interrupt();
            }
            
            /* Last chance cleanup. */
            LinuxDeviceNodeTestAction.this.ls.command().remove(this.nodePath);
        }
        
        public int getFails()
        {
            return this.fails;
        }
    }

}
