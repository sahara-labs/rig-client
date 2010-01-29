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
 * @date 12th December 2010
 *
 * Changelog:
 * - 28/01/2010 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.action.test;

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
 *  <li>File owner user name - The name of the user who should have ownership
 *  of the file.</li>
 *  <li>File group name of owner - The name of the group who should have 
 *  ownership of the file.</li>
 *  <li>Device node numbers - The device node major and/or minor numbers.</li>
 *  <li>Device driver name - the name of the device driver as listed in
 *  <tt>/proc/devices</tt>. This determines from the device name the major
 *  number of the device, then ensures the device node has the same major.</li>
 * <ul>
 * 
 */
public class LinuxDeviceNodeTestAction extends AbstractTestAction
{
    public LinuxDeviceNodeTestAction()
    {
        this.isPeriodic = true;
        this.isSetIntervalHonoured = false;
        this.doLightDarkSchedule = false;
        this.runInterval = 60;
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
    
    class DeviceNodeTest
    {
        public DeviceNodeTest()
        {
            
        }
    }

}
