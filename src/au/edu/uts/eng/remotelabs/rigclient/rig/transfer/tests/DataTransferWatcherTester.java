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

package au.edu.uts.eng.remotelabs.rigclient.rig.transfer.tests;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.reset;
import static org.easymock.classextension.EasyMock.verify;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.rig.IRig;
import au.edu.uts.eng.remotelabs.rigclient.rig.transfer.DataTransferWatcher;
import au.edu.uts.eng.remotelabs.rigclient.rig.transfer.DataTransferWatcher.TransferMethod;
import au.edu.uts.eng.remotelabs.rigclient.status.SchedulingServerProviderStub;
import au.edu.uts.eng.remotelabs.rigclient.status.types.AddSessionFiles;
import au.edu.uts.eng.remotelabs.rigclient.status.types.AddSessionFilesResponse;
import au.edu.uts.eng.remotelabs.rigclient.status.types.ProviderResponse;
import au.edu.uts.eng.remotelabs.rigclient.status.types.SessionFile;
import au.edu.uts.eng.remotelabs.rigclient.status.types.SessionFileTransfer;
import au.edu.uts.eng.remotelabs.rigclient.status.types.SessionFiles;
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
        expect(this.mockConfig.getProperty("Delete_Data_Files_After_Transfer")).andReturn("false");
        
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
        Field f = DataTransferWatcher.class.getDeclaredField("transferMethod");
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
        expect(this.mockConfig.getProperty("Delete_Data_Files_After_Transfer")).andReturn("false");
        expect(this.mockConfig.getProperty("Scheduling_Server_Address")).andReturn("localhost");
        expect(this.mockConfig.getProperty("Scheduling_Server_Port", "8080")).andReturn("8080");
        replay(this.mockConfig);
        
        DataTransferWatcher dw = new DataTransferWatcher(this.mockRig);
        
        Field f = DataTransferWatcher.class.getDeclaredField("transferMethod");
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
        expect(this.mockConfig.getProperty("Delete_Data_Files_After_Transfer")).andReturn("false");
        expect(this.mockConfig.getProperty("Scheduling_Server_Address")).andReturn("localhost");
        expect(this.mockConfig.getProperty("Scheduling_Server_Port", "8080")).andReturn("8080");        
        replay(this.mockConfig);
        
        DataTransferWatcher df = new DataTransferWatcher(this.mockRig);
        
        Method method = DataTransferWatcher.class.getDeclaredMethod("loadRestoreFile");
        method.setAccessible(true);
        method.invoke(df);
        
        Field f = DataTransferWatcher.class.getDeclaredField("sessionFiles");
        f.setAccessible(true);
        
        Map<String, Set<File>> sessionFiles = (Map<String, Set<File>>)f.get(df);
        assertEquals(3, sessionFiles.size());   

        assertTrue(sessionFiles.containsKey("user1"));
        Set<File> files = sessionFiles.get("user1");
        assertEquals(3, files.size());
        assertTrue(files.contains(new File("./test/resources/DataTransfer/file1")));
        assertTrue(files.contains(new File("./test/resources/DataTransfer/file2")));
        assertTrue(files.contains(new File("./test/resources/DataTransfer/file3")));
        
        assertTrue(sessionFiles.containsKey("user2"));
        files = sessionFiles.get("user2");
        assertEquals(2, files.size());
        assertTrue(files.contains(new File("./test/resources/DataTransfer/file2")));
        assertTrue(files.contains(new File("./test/resources/DataTransfer/file4")));
        
        assertTrue(sessionFiles.containsKey("user3"));
        files = sessionFiles.get("user3");
        assertEquals(1, files.size());
        assertTrue(files.contains(new File("./test/resources/DataTransfer/file3")));
        
        assertFalse(sessionFiles.containsKey("user4"));
        
        f = DataTransferWatcher.class.getDeclaredField("transferredFiles");
        f.setAccessible(true);
        Map<String, List<File>> transferredFiles = (Map<String, List<File>>)f.get(df);

        assertEquals(3, transferredFiles.size());
        
        assertTrue(transferredFiles.containsKey("user1"));
        assertEquals(0, transferredFiles.get("user1").size());
        
        assertTrue(transferredFiles.containsKey("user2"));
        assertEquals(0, transferredFiles.get("user2").size());
        
        assertTrue(transferredFiles.containsKey("user3"));
        assertEquals(0, transferredFiles.get("user3").size());
        
        assertFalse(transferredFiles.containsKey("user4"));
        
        assertFalse(restoreFile.exists());
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testRestoreFileDoesNotExist() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Data_Transfer_Method", "WEBDAV")).andReturn("WEBDAV");
        expect(this.mockConfig.getProperty("Data_Transfer_Local_Directory", "")).andReturn("/tmp");
        expect(this.mockConfig.getProperty("Data_Transfer_Restore_File", "./dfrestore")).andReturn("./doesnotexist");
        expect(this.mockConfig.getProperty("Delete_Data_Files_After_Transfer")).andReturn("false");
        expect(this.mockConfig.getProperty("Scheduling_Server_Address")).andReturn("localhost");
        expect(this.mockConfig.getProperty("Scheduling_Server_Port", "8080")).andReturn("8080");        
        replay(this.mockConfig);
        
        DataTransferWatcher df = new DataTransferWatcher(this.mockRig);
        
        Method method = DataTransferWatcher.class.getDeclaredMethod("loadRestoreFile");
        method.setAccessible(true);
        method.invoke(df);
        
        Field f = DataTransferWatcher.class.getDeclaredField("sessionFiles");
        f.setAccessible(true);
        
        Map<String, Set<File>> sessionFiles = (Map<String, Set<File>>)f.get(df);
        assertEquals(0, sessionFiles.size());   

        f = DataTransferWatcher.class.getDeclaredField("transferredFiles");
        f.setAccessible(true);
        Map<String, List<File>> transferredFiles = (Map<String, List<File>>)f.get(df);
        
        assertEquals(0, transferredFiles.size());
    }
   
    @SuppressWarnings("unchecked")
    @Test
    public void testStore() throws Exception
    {
        File restoreFile = new File("./test/resources/DataTransfer/dfrestorestoretest");
        
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Data_Transfer_Method", "WEBDAV")).andReturn("WEBDAV");
        expect(this.mockConfig.getProperty("Data_Transfer_Local_Directory", "")).andReturn("/tmp");
        expect(this.mockConfig.getProperty("Data_Transfer_Restore_File", "./dfrestore")).andReturn(restoreFile.getPath());
        expect(this.mockConfig.getProperty("Delete_Data_Files_After_Transfer")).andReturn("false");
        expect(this.mockConfig.getProperty("Scheduling_Server_Address")).andReturn("localhost");
        expect(this.mockConfig.getProperty("Scheduling_Server_Port", "8080")).andReturn("8080");        
        replay(this.mockConfig);
        
        DataTransferWatcher df = new DataTransferWatcher(this.mockRig);

        Field f = DataTransferWatcher.class.getDeclaredField("sessionFiles");
        f.setAccessible(true);
        Map<String, Set<File>> sessionFiles = (Map<String, Set<File>>)f.get(df);

        f = DataTransferWatcher.class.getDeclaredField("transferredFiles");
        f.setAccessible(true);
        Map<String, List<File>> transferredFiles = (Map<String, List<File>>)f.get(df);
        
        Set<File> files = new HashSet<File>();
        files.add(new File("/store/file1"));
        files.add(new File("/store/file2"));
        sessionFiles.put("user1", files);
        transferredFiles.put("user1", new ArrayList<File>());
        
        files = new HashSet<File>();
        files.add(new File("/store/file3"));
        files.add(new File("/store/file4"));
        sessionFiles.put("user2", files);
        
        List<File> transFiles = new ArrayList<File>();
        transFiles.add(new File("/store/file4"));
        transferredFiles.put("user2", transFiles);
        
        files = new HashSet<File>();
        files.add(new File("/store/file5"));
        sessionFiles.put("user3", files);
        
        transFiles = new ArrayList<File>();
        transFiles.add(new File("/store/file5"));
        transferredFiles.put("user3", transFiles);
        
        Method method = DataTransferWatcher.class.getDeclaredMethod("storeRestoreFile");
        method.setAccessible(true);
        method.invoke(df);
        
        assertTrue(restoreFile.exists());
        
        BufferedReader reader = new BufferedReader(new FileReader(restoreFile));
        
        String line;
        int count = 0;
        boolean hasUser1 = false, hasUser2 = false;
        while ((line = reader.readLine()) != null)
        {
            if ("".equals(line)) continue;
            
            count++;
            if (line.startsWith("user1"))
            {
                assertEquals(5, line.indexOf(' '));
                assertEquals(18, line.indexOf(":#:"));
                assertTrue(line.contains("/store/file2"));
                assertTrue(line.contains("/store/file1"));
                hasUser1 = true;
            }
            else if (line.startsWith("user2"))
            {
                assertEquals("user2 /store/file3", line);
                hasUser2 = true;
            }
            else
            {
                fail("Wrong line: " + line);
            }
        }
        
        assertTrue(hasUser1);
        assertTrue(hasUser2);
        assertEquals(2, count);
        
        restoreFile.delete();
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testTransferNoFilesNoSession() throws Exception
    {
        replay(this.mockRig);
        replay(this.mockStub);
        
        Method meth = DataTransferWatcher.class.getDeclaredMethod("transferFiles");
        meth.setAccessible(true);
        meth.invoke(this.watcher);
        
        verify(this.mockRig);
        verify(this.mockStub);
        
        Field f = DataTransferWatcher.class.getDeclaredField("sessionFiles");
        f.setAccessible(true);
        Map<String, Set<File>> sessionFiles = (Map<String, Set<File>>)f.get(this.watcher);
        assertEquals(0, sessionFiles.size());

        f = DataTransferWatcher.class.getDeclaredField("transferredFiles");
        f.setAccessible(true);
        Map<String, List<File>> transferredFiles = (Map<String, List<File>>)f.get(this.watcher);
        assertEquals(0, transferredFiles.size());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testTransferNoFilesSession() throws Exception
    {
        expect(this.mockRig.detectSessionFiles()).andReturn(new HashSet<File>(0));
        replay(this.mockRig);
        replay(this.mockStub);
        
        this.watcher.sessionStarted("user1");
        
        Method meth = DataTransferWatcher.class.getDeclaredMethod("transferFiles");
        meth.setAccessible(true);
        meth.invoke(this.watcher);
        
        verify(this.mockRig);
        verify(this.mockStub);
        
        Field f = DataTransferWatcher.class.getDeclaredField("sessionFiles");
        f.setAccessible(true);
        Map<String, Set<File>> sessionFiles = (Map<String, Set<File>>)f.get(this.watcher);
        assertEquals(1, sessionFiles.size());
        assertEquals(0, sessionFiles.get("user1").size());

        f = DataTransferWatcher.class.getDeclaredField("transferredFiles");
        f.setAccessible(true);
        Map<String, List<File>> transferredFiles = (Map<String, List<File>>)f.get(this.watcher);
        assertEquals(1, transferredFiles.size());
        assertEquals(0, transferredFiles.get("user1").size());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testTransfer() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Data_Transfer_Method", "WEBDAV")).andReturn("ATTACHMENT");
        expect(this.mockConfig.getProperty("Data_Transfer_Local_Directory", "")).andReturn(new File(".").getAbsolutePath());
        expect(this.mockConfig.getProperty("Data_Transfer_Restore_File", "./dfrestore")).andReturn("./dfrestore");
        expect(this.mockConfig.getProperty("Delete_Data_Files_After_Transfer")).andReturn("false");
        expect(this.mockConfig.getProperty("Scheduling_Server_Address")).andReturn("localhost");
        expect(this.mockConfig.getProperty("Scheduling_Server_Port", "8080")).andReturn("8080");        
        replay(this.mockConfig);

        List<File> files = new ArrayList<File>();
        files.add(new File("./test/resources/DataTransfer/file1"));
        files.add(new File("./test/resources/DataTransfer/file2"));
        
        expect(this.mockRig.detectSessionFiles()).andReturn(new HashSet<File>(files));
        expect(this.mockRig.getName()).andReturn("Rig 1");
        replay(this.mockRig);
                
        AddSessionFilesResponse response = new AddSessionFilesResponse();
        ProviderResponse resp = new ProviderResponse();
        response.setAddSessionFilesResponse(resp);
        resp.setSuccessful(true);
        
        Capture<AddSessionFiles> cap = new Capture<AddSessionFiles>();
        
        expect(this.mockStub.addSessionFiles(capture(cap))).andReturn(response);
        replay(this.mockStub);
        
        DataTransferWatcher dtw = new DataTransferWatcher(this.mockRig);
        
        Field f = DataTransferWatcher.class.getDeclaredField("stub");
        f.setAccessible(true);
        f.set(dtw, this.mockStub);
        
        dtw.sessionStarted("user1");
        
        Method meth = DataTransferWatcher.class.getDeclaredMethod("transferFiles");
        meth.setAccessible(true);
        meth.invoke(dtw);
        
        verify(this.mockRig);
        verify(this.mockStub);
        
        f = DataTransferWatcher.class.getDeclaredField("sessionFiles");
        f.setAccessible(true);
        Map<String, Set<File>> sessionFiles = (Map<String, Set<File>>)f.get(dtw);
        assertEquals(1, sessionFiles.size());
        assertEquals(2, sessionFiles.get("user1").size());
        

        f = DataTransferWatcher.class.getDeclaredField("transferredFiles");
        f.setAccessible(true);
        Map<String, List<File>> transferredFiles = (Map<String, List<File>>)f.get(dtw);
        assertEquals(1, transferredFiles.size());
        assertEquals(2, transferredFiles.get("user1").size());
        
        AddSessionFiles request = cap.getValue();
        assertNotNull(request);
        
        SessionFiles sesFiles = request.getAddSessionFiles();
        assertNotNull(sesFiles);
        
        assertEquals("Rig 1", sesFiles.getName());
        assertEquals("user1", sesFiles.getUser());
        
        SessionFile sfs[] = sesFiles.getFiles();
        assertNotNull(sfs);
        assertEquals(2, sfs.length);

        boolean hasFile1 = false, hasFile2 = false;
        for (SessionFile sf : sfs)
        {
            assertEquals(SessionFileTransfer.ATTACHMENT, sf.getTransfer());
            assertNotNull(sf.getTimestamp());
            
            assertEquals("/test/resources/DataTransfer", sf.getPath());
            assertNotNull(sf.getFile());
            BufferedReader in = new BufferedReader(new InputStreamReader(sf.getFile().getInputStream()));
            if ("file1".equals(sf.getName()))
            {
                hasFile1 = true;
                assertEquals("File 1", in.readLine());
            }
            else if ("file2".equals(sf.getName()))
            {
                hasFile2 = true;
                assertEquals("File 2", in.readLine());
            }
            else
            {
                fail("Unknown file " + sf.getName());
            }
            
            in.close();
        }
        
        assertTrue(hasFile1);
        assertTrue(hasFile2);
        
        reset(this.mockRig);
        expect(this.mockRig.detectSessionFiles()).andReturn(new HashSet<File>(files));
        replay(this.mockRig);
        
        reset(this.mockStub);
        replay(this.mockStub);
        
        meth.invoke(dtw);
        verify(this.mockStub);
        verify(this.mockRig);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testTransferBadFile() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Data_Transfer_Method", "WEBDAV")).andReturn("FILESYSTEM");
        expect(this.mockConfig.getProperty("Data_Transfer_Local_Directory", "")).andReturn(new File(".").getAbsolutePath());
        expect(this.mockConfig.getProperty("Data_Transfer_Restore_File", "./dfrestore")).andReturn("./dfrestore");
        expect(this.mockConfig.getProperty("Delete_Data_Files_After_Transfer")).andReturn("false");
        expect(this.mockConfig.getProperty("Scheduling_Server_Address")).andReturn("localhost");
        expect(this.mockConfig.getProperty("Scheduling_Server_Port", "8080")).andReturn("8080");        
        replay(this.mockConfig);

        List<File> files = new ArrayList<File>();
        files.add(new File("./test/resources/DataTransfer/file1"));
        files.add(new File("./test/resources/DataTransfer/file2"));
        files.add(new File("./test/resources/DataTransfer/file3-notexist"));
        
        expect(this.mockRig.detectSessionFiles()).andReturn(new HashSet<File>(files));
        expect(this.mockRig.getName()).andReturn("Rig 1");
        replay(this.mockRig);
                
        AddSessionFilesResponse response = new AddSessionFilesResponse();
        ProviderResponse resp = new ProviderResponse();
        response.setAddSessionFilesResponse(resp);
        resp.setSuccessful(true);
        
        Capture<AddSessionFiles> cap = new Capture<AddSessionFiles>();
        
        expect(this.mockStub.addSessionFiles(capture(cap))).andReturn(response);
        replay(this.mockStub);
        
        DataTransferWatcher dtw = new DataTransferWatcher(this.mockRig);
        
        Field f = DataTransferWatcher.class.getDeclaredField("stub");
        f.setAccessible(true);
        f.set(dtw, this.mockStub);
        
        dtw.sessionStarted("user1");
        
        Method meth = DataTransferWatcher.class.getDeclaredMethod("transferFiles");
        meth.setAccessible(true);
        meth.invoke(dtw);
        
        verify(this.mockRig);
        verify(this.mockStub);
        
        f = DataTransferWatcher.class.getDeclaredField("sessionFiles");
        f.setAccessible(true);
        Map<String, Set<File>> sessionFiles = (Map<String, Set<File>>)f.get(dtw);
        assertEquals(1, sessionFiles.size());
        assertEquals(3, sessionFiles.get("user1").size());
        

        f = DataTransferWatcher.class.getDeclaredField("transferredFiles");
        f.setAccessible(true);
        Map<String, List<File>> transferredFiles = (Map<String, List<File>>)f.get(dtw);
        assertEquals(1, transferredFiles.size());
        assertEquals(3, transferredFiles.get("user1").size());
        
        AddSessionFiles request = cap.getValue();
        assertNotNull(request);
        
        SessionFiles sesFiles = request.getAddSessionFiles();
        assertNotNull(sesFiles);
        
        assertEquals("Rig 1", sesFiles.getName());
        assertEquals("user1", sesFiles.getUser());
        
        SessionFile sfs[] = sesFiles.getFiles();
        assertNotNull(sfs);
        assertEquals(2, sfs.length);

        boolean hasFile1 = false, hasFile2 = false;
        for (SessionFile sf : sfs)
        {
            assertEquals(SessionFileTransfer.FILESYSTEM, sf.getTransfer());
            assertNotNull(sf.getTimestamp());
            
            assertEquals("/test/resources/DataTransfer", sf.getPath());
            assertNull(sf.getFile());
            
            if ("file1".equals(sf.getName()))
            {
                hasFile1 = true;
            }
            else if ("file2".equals(sf.getName()))
            {
                hasFile2 = true;
            }
            else
            {
                fail("Unknown file " + sf.getName());
            }
        }
        
        assertTrue(hasFile1);
        assertTrue(hasFile2);   

        reset(this.mockRig);
        expect(this.mockRig.detectSessionFiles()).andReturn(new HashSet<File>(files));
        replay(this.mockRig);
        
        reset(this.mockStub);
        replay(this.mockStub);
        
        meth.invoke(dtw);
        verify(this.mockStub);
        verify(this.mockRig);   
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testTransferReRun() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Data_Transfer_Method", "WEBDAV")).andReturn("ATTACHMENT");
        expect(this.mockConfig.getProperty("Data_Transfer_Local_Directory", "")).andReturn(new File(".").getAbsolutePath());
        expect(this.mockConfig.getProperty("Data_Transfer_Restore_File", "./dfrestore")).andReturn("./dfrestore");
        expect(this.mockConfig.getProperty("Delete_Data_Files_After_Transfer")).andReturn("false");
        expect(this.mockConfig.getProperty("Scheduling_Server_Address")).andReturn("localhost");
        expect(this.mockConfig.getProperty("Scheduling_Server_Port", "8080")).andReturn("8080");        
        replay(this.mockConfig);

        List<File> files = new ArrayList<File>();
        files.add(new File("./test/resources/DataTransfer/file1"));
        files.add(new File("./test/resources/DataTransfer/file2"));
        files.add(new File("./test/resources/DataTransfer/file3-notexist"));
        
        expect(this.mockRig.detectSessionFiles()).andReturn(new HashSet<File>(files));
        expect(this.mockRig.getName()).andReturn("Rig 1");
        replay(this.mockRig);
                
        AddSessionFilesResponse response = new AddSessionFilesResponse();
        ProviderResponse resp = new ProviderResponse();
        response.setAddSessionFilesResponse(resp);
        resp.setSuccessful(true);
        
        Capture<AddSessionFiles> cap = new Capture<AddSessionFiles>();
        
        expect(this.mockStub.addSessionFiles(capture(cap))).andReturn(response);
        replay(this.mockStub);
        
        DataTransferWatcher dtw = new DataTransferWatcher(this.mockRig);
        
        Field f = DataTransferWatcher.class.getDeclaredField("stub");
        f.setAccessible(true);
        f.set(dtw, this.mockStub);
        
        dtw.sessionStarted("user1");
        
        Method meth = DataTransferWatcher.class.getDeclaredMethod("transferFiles");
        meth.setAccessible(true);
        meth.invoke(dtw);
        
        verify(this.mockRig);
        verify(this.mockStub);
        
        f = DataTransferWatcher.class.getDeclaredField("sessionFiles");
        f.setAccessible(true);
        Map<String, Set<File>> sessionFiles = (Map<String, Set<File>>)f.get(dtw);
        assertEquals(1, sessionFiles.size());
        assertEquals(3, sessionFiles.get("user1").size());
        

        f = DataTransferWatcher.class.getDeclaredField("transferredFiles");
        f.setAccessible(true);
        Map<String, List<File>> transferredFiles = (Map<String, List<File>>)f.get(dtw);
        assertEquals(1, transferredFiles.size());
        assertEquals(3, transferredFiles.get("user1").size());
        
        AddSessionFiles request = cap.getValue();
        assertNotNull(request);
        
        SessionFiles sesFiles = request.getAddSessionFiles();
        assertNotNull(sesFiles);
        
        assertEquals("Rig 1", sesFiles.getName());
        assertEquals("user1", sesFiles.getUser());
        
        SessionFile sfs[] = sesFiles.getFiles();
        assertNotNull(sfs);
        assertEquals(2, sfs.length);

        boolean hasFile1 = false, hasFile2 = false;
        for (SessionFile sf : sfs)
        {
            assertEquals(SessionFileTransfer.ATTACHMENT, sf.getTransfer());
            assertNotNull(sf.getTimestamp());
            
            assertEquals("/test/resources/DataTransfer", sf.getPath());
            assertNotNull(sf.getFile());
            
            BufferedReader in = new BufferedReader(new InputStreamReader(sf.getFile().getInputStream()));
            if ("file1".equals(sf.getName()))
            {
                hasFile1 = true;
                assertEquals("File 1", in.readLine());
            }
            else if ("file2".equals(sf.getName()))
            {
                hasFile2 = true;
                assertEquals("File 2", in.readLine());
            }
            else
            {
                fail("Unknown file " + sf.getName());
            }
            in.close();
        }
        
        assertTrue(hasFile1);
        assertTrue(hasFile2);   

        reset(this.mockRig);
        
        files.add(new File("./test/resources/DataTransfer/file3"));
        expect(this.mockRig.detectSessionFiles()).andReturn(new HashSet<File>(files));
        expect(this.mockRig.getName()).andReturn("Rig 1");
        replay(this.mockRig);
        
        reset(this.mockStub);
        expect(this.mockStub.addSessionFiles(capture(cap))).andReturn(response);
        replay(this.mockStub);
        
        meth.invoke(dtw);
        verify(this.mockStub);
        verify(this.mockRig);   
        
        request = cap.getValue();
        assertNotNull(request);

        sesFiles = request.getAddSessionFiles();
        assertNotNull(sesFiles);

        assertEquals("Rig 1", sesFiles.getName());
        assertEquals("user1", sesFiles.getUser());

        sfs = sesFiles.getFiles();
        assertNotNull(sfs);
        assertEquals(1, sfs.length);

        SessionFile sf = sfs[0];
        assertEquals(SessionFileTransfer.ATTACHMENT, sf.getTransfer());
        assertNotNull(sf.getTimestamp());

        assertEquals("/test/resources/DataTransfer", sf.getPath());
        assertNotNull(sf.getFile());

        BufferedReader in = new BufferedReader(new InputStreamReader(sf.getFile().getInputStream()));
        assertEquals("File 3", in.readLine());
        in.close();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testTransferRollback() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Data_Transfer_Method", "WEBDAV")).andReturn("ATTACHMENT");
        expect(this.mockConfig.getProperty("Data_Transfer_Local_Directory", "")).andReturn(".");
        expect(this.mockConfig.getProperty("Data_Transfer_Restore_File", "./dfrestore")).andReturn("./dfrestore");
        expect(this.mockConfig.getProperty("Delete_Data_Files_After_Transfer")).andReturn("false");
        expect(this.mockConfig.getProperty("Scheduling_Server_Address")).andReturn("localhost");
        expect(this.mockConfig.getProperty("Scheduling_Server_Port", "8080")).andReturn("8080");        
        replay(this.mockConfig);

        Set<File> files = new HashSet<File>();
        files.add(new File("./test/resources/DataTransfer/file1"));
        files.add(new File("./test/resources/DataTransfer/file2"));
        files.add(new File("./test/resources/DataTransfer/file3-notexist"));

        expect(this.mockRig.detectSessionFiles()).andReturn(files);
        expect(this.mockRig.getName()).andReturn("Rig 1");
        replay(this.mockRig);

        AddSessionFilesResponse response = new AddSessionFilesResponse();
        ProviderResponse resp = new ProviderResponse();
        response.setAddSessionFilesResponse(resp);
        resp.setSuccessful(false);

        Capture<AddSessionFiles> cap = new Capture<AddSessionFiles>();

        expect(this.mockStub.addSessionFiles(capture(cap))).andReturn(response);
        replay(this.mockStub);

        DataTransferWatcher dtw = new DataTransferWatcher(this.mockRig);

        Field f = DataTransferWatcher.class.getDeclaredField("stub");
        f.setAccessible(true);
        f.set(dtw, this.mockStub);

        dtw.sessionStarted("user1");

        Method meth = DataTransferWatcher.class.getDeclaredMethod("transferFiles");
        meth.setAccessible(true);
        meth.invoke(dtw);

        verify(this.mockRig);
        verify(this.mockStub);

        f = DataTransferWatcher.class.getDeclaredField("sessionFiles");
        f.setAccessible(true);
        Map<String, Set<File>> sessionFiles = (Map<String, Set<File>>)f.get(dtw);
        assertEquals(1, sessionFiles.size());
        assertEquals(3, sessionFiles.get("user1").size());


        f = DataTransferWatcher.class.getDeclaredField("transferredFiles");
        f.setAccessible(true);
        Map<String, List<File>> transferredFiles = (Map<String, List<File>>)f.get(dtw);
        assertEquals(1, transferredFiles.size());
        assertEquals(1, transferredFiles.get("user1").size());

        AddSessionFiles request = cap.getValue();
        assertNotNull(request);

        SessionFiles sesFiles = request.getAddSessionFiles();
        assertNotNull(sesFiles);


        reset(this.mockRig);
        files.add(new File("./test/resources/DataTransfer/file3"));
        expect(this.mockRig.detectSessionFiles()).andReturn(files);
        expect(this.mockRig.getName()).andReturn("Rig 1");
        replay(this.mockRig);

        reset(this.mockStub);
        expect(this.mockStub.addSessionFiles(capture(cap))).andReturn(response);
        replay(this.mockStub);

        resp.setSuccessful(true);
        meth.invoke(dtw);
        verify(this.mockStub);
        verify(this.mockRig);

        assertEquals(4, sessionFiles.get("user1").size());
        assertEquals(4, transferredFiles.get("user1").size());

        request = cap.getValue();
        assertNotNull(request);

        sesFiles = request.getAddSessionFiles();
        assertNotNull(sesFiles);

        assertEquals("Rig 1", sesFiles.getName());
        assertEquals("user1", sesFiles.getUser());

        SessionFile sfs[] = sesFiles.getFiles();
        assertNotNull(sfs);
        assertEquals(3, sfs.length);

        boolean hasFile1 = false, hasFile2 = false, hasFile3 = false;
        for (SessionFile sf : sfs)
        {
            assertEquals(SessionFileTransfer.ATTACHMENT, sf.getTransfer());
            assertNotNull(sf.getTimestamp());

            assertEquals("/test/resources/DataTransfer", sf.getPath());
            assertNotNull(sf.getFile());

            BufferedReader in = new BufferedReader(new InputStreamReader(sf.getFile().getInputStream()));
            if ("file1".equals(sf.getName()))
            {
                hasFile1 = true;
                assertEquals("File 1", in.readLine());
            }
            else if ("file2".equals(sf.getName()))
            {
                hasFile2 = true;
                assertEquals("File 2", in.readLine());
            }
            else if ("file3".equals(sf.getName()))
            {
                hasFile3 = true;
                assertEquals("File 3", in.readLine());
            }
            else
            {
                fail("Wrong name: " + sf.getName());
            }
            in.close();
        }

        assertTrue(hasFile1);
        assertTrue(hasFile2);
        assertTrue(hasFile3);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCleanup() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Data_Transfer_Method", "WEBDAV")).andReturn("ATTACHMENT");
        expect(this.mockConfig.getProperty("Data_Transfer_Local_Directory", "")).andReturn(new File(".").getAbsolutePath());
        expect(this.mockConfig.getProperty("Data_Transfer_Restore_File", "./dfrestore")).andReturn("./dfrestore");
        expect(this.mockConfig.getProperty("Delete_Data_Files_After_Transfer")).andReturn("true");
        expect(this.mockConfig.getProperty("Scheduling_Server_Address")).andReturn("localhost");
        expect(this.mockConfig.getProperty("Scheduling_Server_Port", "8080")).andReturn("8080");        
        replay(this.mockConfig);

        List<File> files = new ArrayList<File>();
        files.add(new File("./test/resources/DataTransfer/file1-todelete"));
        files.add(new File("./test/resources/DataTransfer/file2-todelete"));
        
        BufferedReader in = new BufferedReader(new FileReader("./test/resources/DataTransfer/file1"));
        PrintWriter out = new PrintWriter(files.get(0));
        out.println(in.readLine());
        in.close();
        out.close();
        
        in = new BufferedReader(new FileReader("./test/resources/DataTransfer/file2"));
        out = new PrintWriter(files.get(1));
        out.println(in.readLine());
        in.close();
        out.close();
        
        for (File file : files) assertTrue(file.exists());

        expect(this.mockRig.detectSessionFiles()).andReturn(new HashSet<File>(files));
        expect(this.mockRig.getName()).andReturn("Rig 1");
        replay(this.mockRig);

        AddSessionFilesResponse response = new AddSessionFilesResponse();
        ProviderResponse resp = new ProviderResponse();
        response.setAddSessionFilesResponse(resp);
        resp.setSuccessful(true);

        Capture<AddSessionFiles> cap = new Capture<AddSessionFiles>();

        expect(this.mockStub.addSessionFiles(capture(cap))).andReturn(response);
        replay(this.mockStub);

        DataTransferWatcher dtw = new DataTransferWatcher(this.mockRig);

        Field f = DataTransferWatcher.class.getDeclaredField("stub");
        f.setAccessible(true);
        f.set(dtw, this.mockStub);

        dtw.sessionStarted("user1");

        Method meth = DataTransferWatcher.class.getDeclaredMethod("transferFiles");
        meth.setAccessible(true);
        meth.invoke(dtw);

        verify(this.mockRig);
        verify(this.mockStub);

        f = DataTransferWatcher.class.getDeclaredField("sessionFiles");
        f.setAccessible(true);
        Map<String, Set<File>> sessionFiles = (Map<String, Set<File>>)f.get(dtw);
        assertEquals(1, sessionFiles.size());
        assertEquals(2, sessionFiles.get("user1").size());


        f = DataTransferWatcher.class.getDeclaredField("transferredFiles");
        f.setAccessible(true);
        Map<String, List<File>> transferredFiles = (Map<String, List<File>>)f.get(dtw);
        assertEquals(1, transferredFiles.size());
        assertEquals(2, transferredFiles.get("user1").size());

        AddSessionFiles request = cap.getValue();
        assertNotNull(request);

        SessionFiles sesFiles = request.getAddSessionFiles();
        assertNotNull(sesFiles);

        assertEquals("Rig 1", sesFiles.getName());
        assertEquals("user1", sesFiles.getUser());

        reset(this.mockRig);
        expect(this.mockRig.detectSessionFiles()).andReturn(new HashSet<File>(files));
        replay(this.mockRig);

        reset(this.mockStub);
        replay(this.mockStub);

        dtw.sessionComplete();
        
        verify(this.mockStub);
        verify(this.mockRig);
        
        assertEquals(0, sessionFiles.size());
        assertEquals(0, transferredFiles.size());
        
        for (File file : files) assertFalse(file.exists());
    }   
}
