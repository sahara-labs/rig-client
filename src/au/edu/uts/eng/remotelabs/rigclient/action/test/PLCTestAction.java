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
 * @date 20th January 2010
 *
 * Changelog:
 * - 20/01/2010 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.action.test;


import au.edu.uts.remotelabs.eip.*;

import java.io.IOException;
import java.lang.Math;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.Vector;

/**
 * Test action which determines if a PLC is available by connecting to it via
 * Ethernet/IP, setting outputs, and checking the corresponding inputs and
 * outputs.
 * <p />
 * The behavior of ping test is:
 * <ol>
 * <li>Test run interval - the default is 120 seconds but may be configured by
 * setting the property 'PLC_Test_Interval' to a value in seconds.</li>
 * <li>Periodicity - is random.</li>
 * <li>Set interval - honoured.</li>
 * <li>Light-dark scheduling - disabled.</li>
 * </ol>
 * The configuration properties for PingTestAction are:
 * <ul>
 * <li><tt>PLC_Test_Host_&lt;n&gt;</tt> - The host names for the PLC test to
 * verify for response, where <tt>'n'</tt> is from 1 to the nth host to ping
 * test, in order. The <tt>'PLC_Test_Host_1'</tt> property is mandatory and 
 * each subsequent property is optional.</li>
 * <li><tt>PLC_Test_Port_&lt;n&gt;</tt> - The ports on the corresponding hosts
 * to connect to, where <tt>'n'</tt> is from 1 to the nth host to test, in
 * order. The <tt>'PLC_Test_Port_&lt;n&gt;'</tt> property is optional for all 
 * values of n</li>
 * <li><tt>PLC_Test_Interval</tt> - The time between PLC tests in seconds.</li>
 * <li><tt>PLC_Test_Write_Tag_&lt;n&gt;</tt> - The tags to write to for each
 * host, in order. The <tt>PLC_Test_Write_Tag_&lt;n&gt;</tt> property is
 * mandatory for all <tt>PLC_Test_Host_&lt;n&gt;</tt></li>
 * <li><tt>PLC_Test_Write_Bits_&lt;n&gt;</tt> - The value of the bits to be set
 * during the write operation of the test. These should be in binary, have MSB
 * first, and will be truncated to 16 bits.</li>
 * <li><tt>PLC_Test_Read_Tag_&lt;n&gt;</tt> - The tags to read from for each
 * host, in order. The <tt>PLC_Test_Read_Tag_&lt;n&gt;</tt> property is
 * mandatory for all <tt>PLC_Test_Host_&lt;n&gt;</tt></li>
 * <li><tt>PLC_Test_Read_Bits_&lt;n&gt;</tt> - The value of the bits to be
 * compared with the response in the read operation of the test. These should be
 * in binary, have MSB first, and will be truncated to 16 bits.</li>
 * </ul>
 */


public class PLCTestAction extends AbstractTestAction 
{

	/** The list of PLC URLs and their failures. */
    private List<Plc> plcConnections;
    
    /** The number of bytes to read or write in one transaction. */
    private int numberOfBytes;
    
    /** The number of milliseconds to pause between transactions to/from the device. */
    public int testPause;

    /** The length of timeout for connecting to the device. */
    public int timeout;
    
    public static final int EXPECTED_RESPONSE = 1;
	public static final int INVALID_CONFIGURATION = -1;
	public static final int UNKNOWN_HOST_EXCEPTION = -2;
	public static final int IO_EXCEPTION = -3;
	public static final int EIP_EXCEPTION = -4;
	public static final int UNEXPECTED_RESPONSE = -5;
	
	private Random randomNumberGenerator = new Random();
	

	public PLCTestAction() 
	{
		super();
		this.runInterval = 300;
		this.isPeriodic = false;
		this.isSetIntervalHonoured = true;
		this.doLightDarkSchedule = true;

		this.plcConnections = new Vector<Plc>();
	}

	@Override
	public void setUp() 
	{
		String tmp;
		int tmpPort;
		int c;
		
		/* Get Properties from config file: */
		/* Test Interval */
    	try
        {
            this.runInterval = Integer.parseInt(this.config.getProperty("PLC_Test_Interval", "300"));
            this.logger.info("The PLC test interval is " + this.runInterval + " seconds.");
        }
        catch (NumberFormatException ex)
        {
            this.logger.warn("Invalid PLC test interval configuration. It should be either undefined or a valid " +
            		" number to specify the test interval in seconds. Using 300 seconds as the default.");
            this.runInterval = 300;
        }
        
        /* Connection Timeout */
    	try
        {
            this.timeout = Integer.parseInt(this.config.getProperty("PLC_Test_Timeout", "300"));
            this.logger.info("The PLC test timeout is " + this.timeout + " milliseconds.");
        }
        catch (NumberFormatException ex)
        {
            this.logger.warn("Invalid PLC test timeout configuration. It should be either undefined or a valid " +
            		" number to specify the test timeout in milliseconds. Using 300 milliseconds as the default.");
            this.timeout = 300;
        }
        
        /* Delay between transactions */
        try
        {
            this.testPause = Integer.parseInt(this.config.getProperty("PLC_Test_Pause", "1500"));
            this.logger.info("The test pause length is " + this.testPause + " milliseconds.");
        }
        catch (NumberFormatException ex)
        {
            this.logger.warn("Invalid test pause length configuration. It should be either undefined or a valid " +
            		" number to specify the test pause length in milliseconds. Using 1500 seconds as the default.");
            this.runInterval = 1500;
        }
        
        /* Size of data transaction */
    	try
        {
            this.numberOfBytes = Integer.parseInt(this.config.getProperty("PLC_Test_Data_Size", "2"));
            this.logger.info("The PLC test data packet size is " + this.numberOfBytes + " bytes.");
        }
        catch (NumberFormatException ex)
        {
            this.logger.warn("Invalid PLC test data packet size configuration. It should be either undefined or a valid " +
            		" number to specify the size of a data packet in bytes. Using 2 bytes as the default.");
            this.numberOfBytes = 2;
        }
        
    	/* Host 1 */
        while ((tmp = this.config.getProperty("PLC_Test_Host_1")) == null)
        {
            this.logger.error("When using PLC test, atleast one host must be configured using the property" +
            		" 'PLC_Test_Host_1.");
            return;
        }
        
        try
    	{
        	tmpPort = Integer.parseInt(this.config.getProperty("PLC_Test_Port_1", "44818"));
    	}
    	catch (NumberFormatException ex)
    	{
    		this.logger.warn("Invalid PLC test port configuration. It should be either undefined or a valid " +
    				" number to specify the port number. Using 44818 as the default.");
    		tmpPort = 44818;
    	}
        this.plcConnections.add(new Plc(tmp, tmpPort));
        this.logger.info("Going to test host " + tmp + ":" + tmpPort + " with PLC test.");
        
        
        /* Remaining hosts */
        c = 2;
        while ((tmp = this.config.getProperty("PLC_Test_Host_" + c)) != null)
        {
        	try
        	{
            	tmpPort = Integer.parseInt(this.config.getProperty("PLC_Test_Port_" + c, "44818"));
        	}
        	catch (NumberFormatException ex)
        	{
        		this.logger.warn("Invalid PLC test port configuration. It should be either undefined or a valid " +
        				" number to specify the port number. Using 44818 as the default.");
        		tmpPort = 44818;
        	}
            this.plcConnections.add(new Plc(tmp, tmpPort));
            this.logger.info("Going to test host " + tmp + ":" + tmpPort + " with PLC test.");
            c++;
        }
        
        /* Get remaining properties. */
        
        for (int index = 0; index < plcConnections.size(); index++)
        {    
        	/* Safe Values */
        	if ((tmp = this.config.getProperty("PLC_Test_Safe_Value_" + (index+1))) != null)
        	{
        		this.plcConnections.get(index).valueSafe = Integer.parseInt(tmp);
        	}
        	else
        	{
	            this.logger.error("Invalid safe value (" + tmp + "). Marking host " + this.plcConnections.get(index).host
	            		+ " as invalid");
	            this.plcConnections.get(index).result = INVALID_CONFIGURATION;
	        }
        	
        	/* Load tags - write */
	        if ((tmp = this.config.getProperty("PLC_Test_Write_Tag_" + (index+1))) != null 
	        		&& Pattern.matches(".\\d+:\\d+", tmp))
	        {
	        	this.logger.info("Tag " + tmp + " added to host " + this.plcConnections.get(index).host + 
	        			" for writing.");
	        	this.plcConnections.get(index).tagWrite = tmp;
	        }
	        else
	        {
	        	this.logger.warn("Invalid tag (" + tmp + "). Marking host " + this.plcConnections.get(index).host 
	        			+ " as invalid");
	        	this.plcConnections.get(index).result = INVALID_CONFIGURATION;
	        }

	        
	        /* Load tags - read */
	        if ((tmp = this.config.getProperty("PLC_Test_Read_Tag_" + (index+1))) != null 
	        		&& Pattern.matches(".\\d+:\\d+", tmp))
	        {
	        	this.logger.info("Tag " + tmp + " added to host " + this.plcConnections.get(index).host + 
	        			" for reading.");
	        	this.plcConnections.get(index).tagRead = tmp;
	        }
	        else
	        {
	        	this.logger.warn("Invalid tag (" + tmp + "). Marking host " + this.plcConnections.get(index).host + " as invalid");
	        	this.plcConnections.get(index).result = INVALID_CONFIGURATION;
	        }
	        
	        c = 0;
	        /* Load values - write */
	        while ((tmp = this.config.getProperty("PLC_Test_Write_Values_" + (index+1) + "_" + (c+1) )) != null)
	        {
	        	int i = 0;
	        	String[] tmp2 = tmp.split("/");
	        	try
	        	{
	        		this.plcConnections.get(index).valuesWrite.add(new Vector<Integer>());
	        		
	        		for (i = 0; i < tmp2.length; i++)
	        		{
		        		this.plcConnections.get(index).valuesWrite.get(c).add(Math.abs(Integer.parseInt(tmp2[i])));
		        		/* Convert number of bits to number of bytes */	
	        		}
	        		
	        		this.logger.info("Sequence " + tmp + " added to host " + this.plcConnections.get(index).host 
	        				+ " for writing.");
	        	}
	        	catch(NumberFormatException nfex)
	        	{
	        		this.logger.warn("Invalid write value (" + tmp2[i] + "). It should be a valid integer. Marking host " 
	        				+ this.plcConnections.get(index).host + " as invalid");
	        		this.plcConnections.get(index).result = INVALID_CONFIGURATION;
	        	}
	        	c++;
	        }
	        
	        
	        c = 0;
	        /* Load values - read */
	        while ((tmp = this.config.getProperty("PLC_Test_Read_Values_" + (index+1) + "_" + (c+1) )) != null)
	        {
	        	int i = 0;
	        	String[] tmp2 = tmp.split("/");
	        	try
	        	{
	        		this.plcConnections.get(index).valuesRead.add(new Vector<Integer>());
	        		
	        		for (i = 0; i < tmp2.length; i++)
	        		{
		        		this.plcConnections.get(index).valuesRead.get(c).add(Math.abs(Integer.parseInt(tmp2[i])));
		        		/* Convert number of bits to number of bytes */	
	        		}
	        		
	        		this.logger.info("Sequence " + tmp + " added to host " + this.plcConnections.get(index).host 
	        				+ " for reading.");
	        	}
	        	catch(NumberFormatException nfex)
	        	{
	        		this.logger.warn("Invalid read value (" + tmp2[i] + "). It should be a valid integer. Marking host " 
	        				+ this.plcConnections.get(index).host + " as invalid");
	        		this.plcConnections.get(index).result = INVALID_CONFIGURATION;
	        	}
	        	c++;
	        }
        }
        	
        
        for (int index = 0; index < plcConnections.size(); index++)
        {
        	if (this.plcConnections.get(index).result != INVALID_CONFIGURATION)
        	{
        		try
				{
        			this.plcConnections.get(index).eip = new Eip(this.plcConnections.get(index).host, 
        					this.plcConnections.get(index).port, this.timeout);
				}
				catch (UnknownHostException uhex)
				{
					this.logger.error("Unknown Host Exception connecting to host " + 
							this.plcConnections.get(index).host);
					this.plcConnections.get(index).result = UNKNOWN_HOST_EXCEPTION;
				}
				catch (IOException ioex)
				{
					this.logger.error("IO Exception connecting to host " + this.plcConnections.get(index).host);
					this.plcConnections.get(index).result = IO_EXCEPTION;
				}
        	}
        }
		
	}

	@Override
	public void doTest() 
	{
		for (int plcIndex = 0; plcIndex < plcConnections.size(); plcIndex++)
        {
			if (!this.runTest) return;
			
			/* Don't test unconfigured devices */
        	if (this.plcConnections.get(plcIndex).result != INVALID_CONFIGURATION)
        	{
        		/* Try to reconnect to disconnected devices */
        		if (this.plcConnections.get(plcIndex).result == UNKNOWN_HOST_EXCEPTION 
        				|| this.plcConnections.get(plcIndex).result == IO_EXCEPTION)
        		{
        			try
    				{
            			this.plcConnections.get(plcIndex).eip = new Eip(this.plcConnections.get(plcIndex).host, 
            					this.plcConnections.get(plcIndex).port, this.timeout);
            			this.plcConnections.get(plcIndex).result = EXPECTED_RESPONSE;
    				}
    				catch (UnknownHostException uhex)
    				{
    					this.logger.error("Unknown Host Exception connecting to host " + 
    							this.plcConnections.get(plcIndex).host);
    					this.plcConnections.get(plcIndex).result = UNKNOWN_HOST_EXCEPTION;
    				}
    				catch (IOException ioex)
    				{
    					this.logger.error("IO Exception connecting to host " + this.plcConnections.get(plcIndex).host);
    					this.plcConnections.get(plcIndex).result = IO_EXCEPTION;
    				}

        		}
        		else
        		{
	        		try 
					{        			
	        			/* Randomly select a series of values to write. */
	        			int series = randomNumberGenerator.nextInt(this.plcConnections.get(plcIndex).valuesWrite.size());
	        			
	        			/* Loop through the selected set of values. */
	        			for (int i = 0; i < this.plcConnections.get(plcIndex).valuesWrite.get(series).size(); i++)
	        			{        				
	        				/* Write test bits to device */
	        				this.plcConnections.get(plcIndex).eip.writeTag(this.plcConnections.get(plcIndex).tagWrite, 
	        						this.plcConnections.get(plcIndex).valuesWrite.get(series).get(i), this.numberOfBytes);
	        				
	        				/* Delay so attached hardware can respond. */
	        				Thread.sleep(this.testPause);
	        				
	        				/* Check that test is still running */
	        				if (!this.runTest) return;
	        				
	        				/* Read test bits from device */
	        				int response = this.plcConnections.get(plcIndex).eip.readTag(
	        						this.plcConnections.get(plcIndex).tagRead, this.numberOfBytes);
	        				
	        				/* Compare expected response with actual response */
		        			if (response == this.plcConnections.get(plcIndex).valuesRead.get(series).get(i))
		        			{
		        				this.plcConnections.get(plcIndex).result = EXPECTED_RESPONSE;
		        			}
		        			else
		        			{
		        				this.plcConnections.get(plcIndex).result = UNEXPECTED_RESPONSE;
		        				this.logger.error("Unexpected response from host " + this.plcConnections.get(plcIndex).host + 
		        						" - Expected: " + this.plcConnections.get(plcIndex).valuesRead.get(series).get(i) + 
		        						"; Actual: " + response);
		        			}			
	        			}
	        			
	        			/* Return to 'safe' state */
        				this.plcConnections.get(plcIndex).eip.writeTag(this.plcConnections.get(plcIndex).tagWrite, 
        						this.plcConnections.get(plcIndex).valueSafe, this.numberOfBytes);
        				
					} 
					catch (EipException eipex) 
					{
						this.logger.error("EIPException while testing host " + this.plcConnections.get(plcIndex).host);
						this.plcConnections.get(plcIndex).result = EIP_EXCEPTION;
					}
					catch (IOException ioex)
					{
						this.logger.error("IOException while testing host " + this.plcConnections.get(plcIndex).host);
						this.plcConnections.get(plcIndex).result = IO_EXCEPTION;
					} 
					catch (InterruptedException e) 
					{
						Thread.currentThread().interrupt();
		                return;
					}

        		}
        	}
        }
	}

	@Override
	public void tearDown() 
	{
		for (int index = 0; index < this.plcConnections.size(); index++) 
		{
			try 
			{
				this.plcConnections.get(index).eip.close();
			} 
			catch (SocketException e) 
			{
				// TODO Auto-generated catch block
				//Not sure how to handle this
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				//Not sure how to handle this
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getReason() 
	{
		StringBuilder buf = new StringBuilder();
		
		for (int index = 0; index < this.plcConnections.size(); index++) 
		{
			switch (this.plcConnections.get(index).result)
			{
			case EXPECTED_RESPONSE:
				break;
				
			case INVALID_CONFIGURATION:
				buf.append("Configuration for device at " + this.plcConnections.get(index).host + " was invalid or " +
						"incomplete - please correct the Rig Client properties file; ");
				break;
				
			case UNKNOWN_HOST_EXCEPTION:
				buf.append("Could not connect to " + this.plcConnections.get(index).host + " - Unknown Host " +
						"Exception thrown; ");
				break;
				
			case IO_EXCEPTION:
				buf.append("IO exception thrown communicating with " + this.plcConnections.get(index).host + "; ");
				break;
				
			case EIP_EXCEPTION:
				buf.append("Error code received from device " + this.plcConnections.get(index).host + "; ");
				break;
				
			case UNEXPECTED_RESPONSE:
				buf.append("Unexpected response received from device " + this.plcConnections.get(index).host + "; ");
				break;
				
			default:
				buf.append("Unknown error for device " + this.plcConnections.get(index).host + "; ");
				break;
			}
		}
		
		if (buf.length() > 0)
        {
            return buf.toString();
        }
        
        return null;
	}
	
	@Override
	public boolean getStatus() 
	{
		for (int index = 0; index < this.plcConnections.size(); index++) 
		{
			if (this.plcConnections.get(index).result != EXPECTED_RESPONSE)
			{
				return false;
			}
		}
		
		return true;
	}

	@Override
	public String getActionType() 
	{
		return "PLC Test";
	}
	
	public final class Plc
	{
		public Eip eip;
		
		public String host;
		public int port;
		public String tagWrite;
		public String tagRead;
		public Vector<Vector<Integer>> valuesWrite;
		public Vector<Vector<Integer>> valuesRead;
		public int valueSafe;
		public int result;
		
		public Plc(String inputHost, int inputPort)
		{
			this.host = inputHost;
			this.port = inputPort;
			
			this.result = EXPECTED_RESPONSE; 
			
			this.valuesRead = new Vector<Vector<Integer>>();
			this.valuesWrite = new Vector<Vector<Integer>>();
		}
		
	}
	
}


