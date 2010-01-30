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

import java.util.ArrayList;
import java.util.List;

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
 * <ul>
 * The behaviour of Linux device node test is:
 * <ul>
 *  <li>Periodicity - is periodic.</li>
 *  <li>Set interval - ignored, not honoured.</li>
 *  <li>Light-dark scheduling - disabled.</li>
 * </ul>
 * The configuration properties for this test is:
 * <ul>
 *  <li></li>
 * </ul>
 */
public class LinuxDeviceNodeTestAction extends AbstractTestAction
{
    private List<DeviceNode> deviceNode;
    
    public LinuxDeviceNodeTestAction()
    {
        this.isPeriodic = true;
        this.isSetIntervalHonoured = false;
        this.doLightDarkSchedule = false;
        this.runInterval = 60;
        
        this.deviceNode = new ArrayList<DeviceNode>();
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
        // TODO Auto-generated method stub
        return false;
    }

    
    @Override
    public String getActionType()
    {
        // TODO Auto-generated method stub
        return null;
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
        private String permissionStr;
        
        /** The name of the owning user. If this is set to <code>null</code>
         *  The owner name test is disabled. */
        private String owner;
        
        /** The user identifier of the owning user. If this is set to '-1' 
         *  the owner uid tests is disabled. */
        private int uid = -1;
        
        /** The name of the owning group. If this is set to <code>null</code>,
         *  this test is disabled. */
        private String group;
        
        /** The group identifier of the owning group. If this is set to '-1'
         *  the group identifier test is disabled. */
        private int guid = -1;
        
        /** The device node major number. If this is set to '-1' the major 
         *  number test is disabled. */
        private int majorNumber = -1;
        
        /** The device node minor number. If this is set to '-1' the minor
         *  number test is disabled. */
        private int minorNumber = -1;
        
        /** The name of the device driver name as shown in '/proc/devices'.
         *  If this is set to <code>null</code> the test is disabled. */
        private String driverName;
        
        private DeviceNode(String path, int confNum)
        {
            this.nodePath = path;
        }
        
        
    }

}
