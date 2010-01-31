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
 *  <li><tt>LinuxDeviceNode_Test_Path_&lt;n&gt;</tt> - The path to the device
 *  node (usually in '/dev'.</li>
 *  <li><tt>LinuxDeviceNode_Test_Type_&lt;n&gt;</tt> - The device node file
 *  type, either '-', 'd', 'c', 'b', 'l', 's' or 'p'.</li>
 *  <li><tt>LinuxDeviceNode_Test_Permission_&lt;n&gt;</tt> - The permission
 *  string as shown be 'ls'.</li>
 *  <li><tt>LinuxDeviceNode_Test_Octal_Permission_&lt;n&gt;</tt> - The device
 *  node permission as an octal number.</li>
 *  <li><tt>LinuxDeviceNode_Test_User_&lt;n&gt;</tt> - The name of the owning 
 *  user.</li>
 *  <li><tt>LinuxDeviceNode_Test_UID_&lt;n&gt;</tt> - The uid of the owning
 *  user.</li>
 *  <li><tt>LinuxDeviceNode_Test_Group_&lt;n&gt;</tt> - The name of the owning
 *  group.</li>
 *  <li><tt>LinuxDeviceNode_Test_GID_&lt;n&gt;</tt> - The gid of the owning
 *  group.</li>
 *  <li><tt>LinuxDeviceNode_Test_Major_Number_&lt;n&gt;</tt> - The major
 *  number of the device node. This is only useful for character and
 *  block devices ('c' or 'b' must be set as the file type).</li>
 *  <li><tt>LinuxDeviceNode_Test_Minor_Number_&lt;n&gt;</tt> - The minor 
 *  number of the device node. This is only useful for character and
 *  block devices ('c' or 'b' must be set as the file type).</li>
 *  <li><tt>LinuxDeviceNode_Test_Driver_&lt;n&gt;</tt> - The driver name of
 *  the device as shown in <strong>/proc/devices</strong>. This is only useful
 *  for character and block devices ('c' or 'b' must be set as the file type).</li>
 * </ul>
 */
public class LinuxDeviceNodeTestAction extends AbstractTestAction
{
    /** The list of device nodes to test. */
    private List<DeviceNode> deviceNodes;
    
    /** The process builder to spawn 'ls'. */
    private ProcessBuilder ls;
    
    /** The process builder to spawn stat. */
    private ProcessBuilder stat;
    
    /** The number of times a node test can fail before this test action 
     *  returns a failure. */
    private int failThreshold;
    
    /**
     * Constructor, sets up the default test behaviour.
     * 
     * @throws IllegalStateException thrown when not running on Linux
     */
    public LinuxDeviceNodeTestAction()
    {
        super();
        
        /* This will only work on Linux. */
        if (!System.getProperty("os.name").startsWith("Linux"))
        {
            this.logger.error("Can only run the Linux device node test action on a Linux operation system. " +
            		"Detected operating system " + System.getProperty("os.name") + '.');
            throw new IllegalStateException("Wrong operating system.");
        }
        
        /* Test behaviour setup. */
        this.isPeriodic = true;
        this.isSetIntervalHonoured = false;
        this.doLightDarkSchedule = false;
        this.runInterval = 60;
        
        this.deviceNodes = new ArrayList<DeviceNode>();
        this.ls = new ProcessBuilder("/bin/ls", "-l");
        this.stat = new ProcessBuilder("/usr/bin/stat", "-c");
        this.failThreshold = 3;
    }
    
    @Override
    public void setUp()
    {
        String tmp;
        
        /* Common configuration.*/
        // TODO common configuration
        
        /* Load each node. */
        int i = 1;
        while ((tmp = this.config.getProperty("LinuxDeviceNode_Test_Path_" + i)) != null)
        {
            this.logger.info("Going to test device node with path " + tmp + '.');
            this.deviceNodes.add(new DeviceNode(tmp, i));
            i++;
        }
    }
    
    @Override
    public void doTest()
    {
        for (DeviceNode node : this.deviceNodes)
        {
            node.test();
        }
    }
    
    @Override
    public void tearDown()
    {
        /* Does nothing. */
    }

    @Override
    public String getReason()
    {
        StringBuilder buf = new StringBuilder();
        for (DeviceNode node : this.deviceNodes)
        {
            if (node.getFails() > this.failThreshold)
            {
                if (buf.length() > 0)
                {
                    buf.append(", ");
                }
                buf.append("Device node " + node.getPath() + " failure: " + node.getReason());
            }
        }
        
        return buf.length() > 0 ? buf.toString() : null;
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
    
    /**
     * The test for a specific device node.
     */
    public class DeviceNode
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
        private int gid = -1;
        
        /** The device node major number. If this is set to '-1' the major 
         *  number test is disabled. */
        private int majorNumber = -1;
        
        /** The device node minor number. If this is set to '-1' the minor
         *  number test is disabled. */
        private int minorNumber = -1;
        
        /** The name of the device driver name as shown in '/proc/devices'.
         *  If this is set to <code>null</code> the test is disabled. */
        private String driverName = null;
        
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
            
            if ((tmp = config.getProperty("LinuxDeviceNode_Test_Octal_Permission_" + confNum)) != null)
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
            
            if ((tmp = config.getProperty("LinuxDeviceNode_Test_User_" + confNum)) != null)
            {
                this.owner = tmp;
                logger.info("Going to test device node " + this.nodePath + " for owning user " + this.owner + ".");
            }
            
            if ((tmp = config.getProperty("LinuxDeviceNode_Test_UID_" + confNum)) != null)
            {
                try
                {
                    this.uid = Integer.parseInt(tmp);
                    logger.info("Going to test device node " + this.nodePath + " for owning uid " + this.uid + '.');
                }
                catch (NumberFormatException ex)
                {
                    this.uid = -1;
                    logger.warn("Invalid uid number " + tmp + ", not going to test owning user of " + 
                            this.nodePath + '.'); 
                }
            }
            
            if ((tmp = config.getProperty("LinuxDeviceNode_Test_Group_" + confNum)) != null)
            {
                this.group = tmp;
                logger.info("Going to test device node " + this.nodePath + " for owning group " + this.group + ".");
            }
            
            if ((tmp = config.getProperty("LinuxDeviceNode_Test_GID_" + confNum)) != null)
            {
                try
                {
                    this.gid = Integer.parseInt(tmp);
                    logger.info("Going to test device node " + this.nodePath + " for owning gid " + this.gid + '.');
                }
                catch (NumberFormatException ex)
                {
                    this.gid = -1;
                    logger.warn("Invalid gid number " + tmp + ", not going to test owning group of " + 
                            this.nodePath + '.'); 
                }
            }
            
            if ((tmp = config.getProperty("LinuxDeviceNode_Test_Major_Number_" + confNum)) != null &&
                    (this.fileType == 'c' || this.fileType == 'b'))
            {
                try
                {
                    this.majorNumber = Integer.parseInt(tmp);
                    logger.info("Going to test device node " + this.nodePath + " for major number " + 
                            this.majorNumber + '.');
                }
                catch (NumberFormatException ex)
                {
                    this.majorNumber = -1;
                    logger.warn("Invalid major number " + tmp + ", not going to test the major number of " + 
                            this.nodePath + '.'); 
                }
            }

            if ((tmp = config.getProperty("LinuxDeviceNode_Test_Minor_Number_" + confNum)) != null &&
                    (this.fileType == 'c' || this.fileType == 'b'))
            {
                try
                {
                    this.minorNumber = Integer.parseInt(tmp);
                    logger.info("Going to test device node " + this.nodePath + " for minor number " + 
                            this.minorNumber + '.');
                }
                catch (NumberFormatException ex)
                {
                    this.minorNumber = -1;
                    logger.warn("Invalid minor number " + tmp + ", not going to test the minor number of " + 
                            this.nodePath + '.'); 
                }
            }
           
           if ((tmp = config.getProperty("LinuxDeviceNode_Test_Driver_" + confNum)) != null &&
                   (this.fileType == 'c' || this.fileType == 'b'))
           {
               this.driverName = tmp;
               logger.info("Going to test if device node " + this.nodePath + " has the same major number as device " +
               		"driver " + this.driverName + '.');
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
                    this.failureReason = "Does not exist.";
                    this.fails++;
                    return;
                }
                
                /* Get the output of `ls`. */
                String lsParts[] = null;
                LinuxDeviceNodeTestAction.this.ls.command().add(this.nodePath);
                Process lsProc = LinuxDeviceNodeTestAction.this.ls.start();
                LinuxDeviceNodeTestAction.this.ls.command().remove(this.nodePath);
                if (lsProc.waitFor() != 0)
                {
                    /* ls doesn't detect this file... Bizarre, Java detects is... */
                    logger.debug("Failing device node test for " + this.nodePath + " becase the file does not exist " +
                    		"('ls' returned a non-zero (" + lsProc.exitValue() + ") return code.");
                    this.fails++;
                    this.failureReason = "Does not exist.";
                    return;
                }
                
                BufferedReader lsReader = new BufferedReader(new InputStreamReader(lsProc.getInputStream()));
                lsParts = lsReader.readLine().split("\\s");
                lsReader.close();
                
                /* 2) Test the file type. */
                if (this.fileType != '\0' && this.fileType != lsParts[0].charAt(0))
                {
                    logger.debug("Failed device node test for " + this.nodePath + " becase the file type (" 
                            + lsParts[0].charAt(0) + ") is not " + this.fileType + '.');
                    this.fails++;
                    this.failureReason = "Not of type '" + this.fileType + "'.";
                    return;
                }
                
                /* 3) Test the device file permission (can include or exclude file type prefix). */
                if (this.permissionStr != null && 
                        !(this.permissionStr.equals(lsParts[0]) || this.permissionStr.equals(lsParts[0].substring(1))))
                {
                    logger.debug("Failed device node test for " + this.nodePath + " because the file permissions are " +
                    		"incorrect (" + lsParts[0].substring(1) + " is not " + this.permissionStr + ").");
                    this.fails++;
                    this.failureReason = "Incorrect permission.";
                    return;
                }
                
                /* 4) Test the device file permission using octal number permission. */
                if (this.octalPermissions >= 0)
                {
                    List<String> command = LinuxDeviceNodeTestAction.this.stat.command();
                    command.add("%a"); // From man 1 stat %a is access permissions in octal
                    command.add(this.nodePath);
                    Process statProc = LinuxDeviceNodeTestAction.this.stat.start();
                    command.remove("%a");
                    command.remove(this.nodePath);
                    if (statProc.waitFor() != 0)
                    {
                        logger.debug("Failed device node test for " + this.nodePath + " because stat returned " +
                        		"a non-zero exit code (" + statProc.exitValue() + ").");
                        this.fails++;
                        this.failureReason = "Stat returned a non-zero exit code.";
                        return;
                    }
                    
                    BufferedReader reader = new BufferedReader(new InputStreamReader(statProc.getInputStream()));
                    String statPerm = reader.readLine();
                    if (Integer.parseInt(statPerm) != this.octalPermissions)
                    {
                        logger.debug("Failed device node test for " + this.nodePath + " because the octal number " +
                        		"permission (" + statPerm + ") is not " + this.octalPermissions + '.');
                        this.fails++;
                        this.failureReason = "Incorrect permission (octal).";
                        return;
                    }
                }
                
                /* 5) Device node owner. */
                if (this.owner != null && !this.owner.equalsIgnoreCase(lsParts[2]))
                {
                    logger.debug("Failed device node test for " + this.nodePath + " because the owning user (" + 
                            lsParts[2] + ") is not " + this.owner + '.');
                    this.fails++;
                    this.failureReason = "Incorrect owner.";
                    return;
                }
                
                /* 6) Device node uid owner. */
                if (this.uid >= 0)
                {
                    List<String> command = LinuxDeviceNodeTestAction.this.stat.command();
                    command.add("%u"); // From man 1 stat, %u is user id of owner
                    command.add(this.nodePath);
                    Process statProc = LinuxDeviceNodeTestAction.this.stat.start();
                    command.remove("%u");
                    command.remove(this.nodePath);
                    if (statProc.waitFor() != 0)
                    {
                        logger.debug("Failed device node test for " + this.nodePath + " because stat returned " +
                                "a non-zero exit code (" + statProc.exitValue() + ").");
                        this.fails++;
                        this.failureReason = "Stat returned a non-zero exit code.";
                        return;
                    }
                    
                    BufferedReader reader = new BufferedReader(new InputStreamReader(statProc.getInputStream()));
                    String statRet = reader.readLine();
                    if (Integer.parseInt(statRet) != this.uid)
                    {
                        logger.debug("Failed device node test for " + this.nodePath + " because the owner uid " +
                                "(" + statRet + ") is not " + this.uid + '.');
                        this.fails++;
                        this.failureReason = "Incorrect owner (uid).";
                        return;
                    }
                }
                
                /* 7) Device node group. */
                if (this.owner != null && !this.group.equalsIgnoreCase(lsParts[3]))
                {
                    logger.debug("Failed device node test for " + this.nodePath + " because the owning group (" + 
                            lsParts[3] + ") is not " + this.group + '.');
                    this.fails++;
                    this.failureReason = "Incorrect group.";
                    return;
                }
                
                /* 8) Device node gid owner. */
                if (this.gid >= 0)
                {
                    List<String> command = LinuxDeviceNodeTestAction.this.stat.command();
                    command.add("%g"); // From man 1 stat, %g is group id of owner
                    command.add(this.nodePath);
                    Process statProc = LinuxDeviceNodeTestAction.this.stat.start();
                    command.remove("%g");
                    command.remove(this.nodePath);
                    if (statProc.waitFor() != 0)
                    {
                        logger.debug("Failed device node test for " + this.nodePath + " because stat returned " +
                                "a non-zero exit code (" + statProc.exitValue() + ").");
                        this.fails++;
                        this.failureReason = "Stat returned a non-zero exit code.";
                        return;
                    }
                    
                    BufferedReader reader = new BufferedReader(new InputStreamReader(statProc.getInputStream()));
                    String statRet = reader.readLine();
                    if (Integer.parseInt(statRet) != this.gid)
                    {
                        logger.debug("Failed device node test for " + this.nodePath + " because the owner gid " +
                                "(" + statRet + ") is not " + this.gid + '.');
                        this.fails++;
                        this.failureReason = "Incorrect owner (gid).";
                        return;
                    }
                }
                
                /* 9) Major number test. */
                if (this.majorNumber >= 0 && this.majorNumber != 
                        Integer.parseInt(lsParts[4].substring(0, lsParts[4].length() - 1)))
                {
                    logger.debug("Failed device node test for " + this.nodePath + " because the major number is " +
                    		"incorrect. Detected " + lsParts[4].substring(0, lsParts[4].length() - 1) + " which is " +
                    		"not " + this.majorNumber + '.');
                    this.fails++;
                    this.failureReason = "Incorrect major number.";
                    return;
                }
                
                /* 10) Minor number test. */
                if (this.minorNumber >= 0 && this.minorNumber != Integer.parseInt(lsParts[5]))
                {
                    logger.debug("Failed device node test for " + this.nodePath + " because the minor number is " +
                    		"incorrect. Detected " + lsParts[5] + " which is not " + this.minorNumber + '.');
                    this.fails++;
                    this.failureReason = "Incorrect minor number.";
                    return;
                }
                
                /* 11) Device driver name. */
                
                /* All good -> clear fails. */
                this.fails = 0;
            }
            catch (IOException ex)
            {
                // TODO io exception
                ex.printStackTrace();
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
        
        public String getPath()
        {
            return this.nodePath;
        }
        
        public String getReason()
        {
            return this.failureReason;
        }
    }

}
