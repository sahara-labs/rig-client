/**
 * SAHARA Rig Client
 * 
 * Software abstraction of physical rig to provide rig session control
 * and rig device control. Automatically tests rig hardware and reports
 * the rig status to ensure rig goodness.
 *
 * @license See LICENSE in the top level directory for complete license terms.
 *
 * Copyright (c) 2009, University of Technology, Sydney
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
 * @date 26th Febrary 2013
 */

package au.edu.uts.eng.remotelabs.rigclient.rig.internal.tests;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.reset;
import static org.easymock.classextension.EasyMock.verify;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.rig.IRig;
import au.edu.uts.eng.remotelabs.rigclient.rig.internal.DataTransferWatcher;
import au.edu.uts.eng.remotelabs.rigclient.rig.internal.DataTransferWatcher.TransferMethod;
import au.edu.uts.eng.remotelabs.rigclient.status.SchedulingServerProviderStub;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;

/**
 * Tests the {@link DataTransferWatcher} class.
 */
public class DataTransferWatcherTester extends TestCase
{
    /** Object of class under test. */
    private DataTransferWatcher watcher;
    
    /** Mock rig. */
    private IRig mockRig;
    
    /** Mock communication stub. */
    private SchedulingServerProviderStub mockStub;
    
    /** Mock configuration. */
    private IConfig mockConfig;
    
    /**
     * @throws java.lang.Exception
     */
    @Override
    @Before
    public void setUp() throws Exception
    {
        this.mockConfig = createMock(IConfig.class);
        this.mockRig = createMock(IRig.class);
        this.mockStub = createMock(SchedulingServerProviderStub.class);
        
        /* Watcher configuration. */
        expect(this.mockConfig.getProperty("Data_Transfer_Method", "WEBDAV")).andReturn("WEBDAV");
        expect(this.mockConfig.getProperty("Data_Transfer_Local_Directory", "")).andReturn("");
        expect(this.mockConfig.getProperty("Data_Transfer_Restore_File", DataTransferWatcher.DEFAULT_RESTORE_FILE))
                .andReturn(DataTransferWatcher.DEFAULT_RESTORE_FILE);
        
        /* Endpoint configuration. */
        expect(this.mockConfig.getProperty("Scheduling_Server_Address")).andReturn("localhost");
        expect(this.mockConfig.getProperty("Scheduling_Server_Port", "8080")).andReturn("8080");
        
        /* Logging configuration. */
        expect(this.mockConfig.getProperty("Logger_Type")).andReturn("SystemErr");
        expect(this.mockConfig.getProperty("Log_Level")).andReturn("DEBUG");
        expect(this.mockConfig.getProperty("Default_Log_Format", "[__LEVEL__] - [__ISO8601__] - __MESSAGE__"))
            .andReturn("[__LEVEL__] - [__ISO8601__] - __MESSAGE__");
        expect(this.mockConfig.getProperty("FATAL_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("PRIORITY_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("ERROR_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("WARN_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("INFO_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("DEBUG_Log_Format")).andReturn(null);
        
        replay(this.mockConfig);
        
        Field field = ConfigFactory.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, this.mockConfig);
        
        this.watcher = new DataTransferWatcher(this.mockRig);
        
        field = DataTransferWatcher.class.getDeclaredField("stub");
        field.setAccessible(true);
        field.set(this.watcher, this.mockStub);
    }
    
    @Test
    public void testDefaults() throws Exception
    {
        Field f = DataTransferWatcher.class.getDeclaredField("method");
        f.setAccessible(true);
        TransferMethod tm = (TransferMethod)f.get(this.watcher);
        assertNotNull(tm);
        assertEquals(TransferMethod.WEBDAV, tm);
        
        f = DataTransferWatcher.class.getDeclaredField("localDirectory");
        f.setAccessible(true);
        String ld = (String)f.get(this.watcher);
        assertNotNull(ld);
        assertEquals("", ld);
        
        f = DataTransferWatcher.class.getDeclaredField("restoreFile");
        f.setAccessible(true);
        String rf = (String)f.get(this.watcher);
        assertNotNull(rf);
        assertEquals(DataTransferWatcher.DEFAULT_RESTORE_FILE, rf);
    }
    
    @Test
    public void testConfiguration() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Data_Transfer_Method", "WEBDAV")).andReturn("ATTACHMENT");
        expect(this.mockConfig.getProperty("Data_Transfer_Local_Directory", "")).andReturn("/tmp");
        expect(this.mockConfig.getProperty("Data_Transfer_Restore_File", "./dfrestore")).andReturn("./someotherdir");
        expect(this.mockConfig.getProperty("Scheduling_Server_Address")).andReturn("localhost");
        expect(this.mockConfig.getProperty("Scheduling_Server_Port", "8080")).andReturn("8080");
        replay(this.mockConfig);
        
        DataTransferWatcher dw = new DataTransferWatcher(this.mockRig);
        
        Field f = DataTransferWatcher.class.getDeclaredField("method");
        f.setAccessible(true);
        TransferMethod tm = (TransferMethod)f.get(dw);
        assertNotNull(tm);
        assertEquals(TransferMethod.ATTACHMENT, tm);
        
        f = DataTransferWatcher.class.getDeclaredField("localDirectory");
        f.setAccessible(true);
        String ld = (String)f.get(dw);
        assertNotNull(ld);
        assertEquals("/tmp", ld);
        
        f = DataTransferWatcher.class.getDeclaredField("restoreFile");
        f.setAccessible(true);
        String rf = (String)f.get(dw);
        assertNotNull(rf);
        assertEquals("./someotherdir", rf);
                
        verify(this.mockConfig);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testRestore() throws Exception
    {
        File restoreFile = new File("./test/resources/DataTransfer/dfrestoretmp");
        
        /* Restore file will be deleted a copy of a template is created. */
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("./test/resources/DataTransfer/dfrestore")));
        PrintWriter out = new PrintWriter(restoreFile);
        
        String line;
        while ((line = in.readLine()) != null) out.println(line);
        out.close();
        in.close();
        
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Data_Transfer_Method", "WEBDAV")).andReturn("WEBDAV");
        expect(this.mockConfig.getProperty("Data_Transfer_Local_Directory", "")).andReturn("/tmp");
        expect(this.mockConfig.getProperty("Data_Transfer_Restore_File", "./dfrestore")).andReturn(restoreFile.getPath());
        expect(this.mockConfig.getProperty("Scheduling_Server_Address")).andReturn("localhost");
        expect(this.mockConfig.getProperty("Scheduling_Server_Port", "8080")).andReturn("8080");        
        replay(this.mockConfig);
        
        
        
        DataTransferWatcher df = new DataTransferWatcher(this.mockRig);
        
        Method method = DataTransferWatcher.class.getDeclaredMethod("loadRestoreFile");
        method.setAccessible(true);
        method.invoke(df);
        
        Field f = DataTransferWatcher.class.getDeclaredField("sessionFiles");
        f.setAccessible(true);
        
        Map<String, Set<File>> files = (Map<String, Set<File>>)f.get(df);
        assertEquals(3, files.size());
    }
   
    @Test
    public void testLoad()
    {

    }
}
