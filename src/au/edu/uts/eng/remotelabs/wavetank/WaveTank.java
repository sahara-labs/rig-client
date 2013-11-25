/**
 * UTS Remote Labs Wave Tank Rig Client. 
 *
 * @author Michael Diponio (mdiponio)
 * @date 9th Janurary 2012
 * 
 * - 15/5/2013 (DA) - Altered to store the values sent to the CRIO
 * and added getters to retrieve them. 
 */

package au.edu.uts.eng.remotelabs.wavetank;

import java.io.IOException;
import java.util.Arrays;

import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Implements the TCP interface provided by the cRIO.
 */
public class WaveTank
{
    /** Number of analogue data channels. */
    public static final int NUM_AIN_CHANS = 17;
    
    /** Number of bytes in a 'rig:data?' packet. */
    public static final int PACKET_SIZE = NUM_AIN_CHANS + 2;
    
    /** Number of output channels for digital. */
    private static final int DIGITAL_OUTPUT_CHANS = 8;
    
    /** Number of output channels for analog. */
    private static final int ANALOG_OUTPUT_CHANS = 8;
    
    /** Analogue channel data. */
    private volatile double ain[];
    
    /** Digital channel data. */
    //private byte din;
    private volatile boolean din[];
    
    /** Analogue channel output **/
    private volatile double[] aout;
    
    /** Digital channel output **/
    private volatile boolean[] dout;
    
    /** Pump state **/
    private volatile boolean pump;
    
    /** Inverter state **/
    private volatile boolean inverter;
    
    /** Paddle speed **/
    private volatile double speed;
    
    /** Rig state. */
    private byte rigState;
    
    /** Logger. */
    private final ILogger logger;
    
    public WaveTank()
    {
        this.logger = LoggerFactory.getLoggerInstance();
                
        this.ain = new double[NUM_AIN_CHANS];
        this.din = new boolean[DIGITAL_OUTPUT_CHANS];
        this.aout = new double[ANALOG_OUTPUT_CHANS];
        this.dout = new boolean[DIGITAL_OUTPUT_CHANS];
    }
    
    /**
     * Enable the pump.
     * 
     * @param enabled true to enable the pump
     * @throws IOException error communicating with cRIO
     */
    public synchronized void enablePump(boolean enabled) throws IOException
    {
        this.pump = enabled;
    }
    
    /**
     * Enable the inverter.
     * 
     * @param enabled true to enable the inverter
     * @throws IOException error communicating with cRIO
     */
    public synchronized void enableInverter(boolean enabled) throws IOException
    {
        this.inverter = enabled;
    }
    
    /**
     * Sets paddle speed.
     * 
     * @param speed speed to set
     * @throws IOException error communicating with cRIO
     */
    public synchronized void setSpeed(double speed) throws IOException
    {
        this.speed = speed;
    }
    
    /**
     * Sets analogue output.
     * 
     * @param channel channel that is being set
     * @param value value to write
     * @throws IOException error communicating with cRIO
     */
    public synchronized void setAnalogOutput(int channel, double value) throws IOException
    {
        this.aout[channel] = value;
    	this.ain[channel] = value;
    	
    }
    
    /**
     * Set digital output.
     * 
     * @param channel channel that is being set
     * @param value value to write
     * @throws IOException error communicating with cRIO
     */
    public synchronized void setDigitalOutput(int channel, boolean value) throws IOException
    {
        this.dout[channel] = value;
    	this.din[channel] = value;

    }
    
    /**
     * Gets whether a digital input channel is on or off.
     * 
     * @param chan channel to get
     * @return true if channel on, false if off
     */
    public boolean getDigitalInput(int chan)
    {
        //return (this.din & (int)Math.pow(2, chan)) != 0;
    	return this.din[chan];
    }
    
    /**
     * Gets all digital input channels.
     * 
     * @return all digital inputs.
     */
    public boolean[] getDigitalInputs()
    {
        /*boolean d[] = new boolean[8];
        d[0] = (this.din & 0x1) != 0;
        d[1] = (this.din & 0x2) != 0;
        d[2] = (this.din & 0x4) != 0;
        d[3] = (this.din & 0x8) != 0;
        d[4] = (this.din & 0x16) != 0;
        d[5] = (this.din & 0x32) != 0;
        d[6] = (this.din & 0x64) != 0;
        d[7] = (this.din & 0x128) != 0;
        return d;*/
    	return Arrays.copyOf(this.din, DIGITAL_OUTPUT_CHANS);
    }

    /**
     * Gets the value of an analog input.
     * 
     * @param chan channel to get
     * @return value of channel
     */
    public double getAnalogInput(int chan)
    {
        return this.ain[chan];
    }
    
    /**
     * Gets all the analog channel input values.
     * 
     * @return value of analog inputs
     */
    public double[] getAnalogInputs()
    {   
        return Arrays.copyOf(this.ain, NUM_AIN_CHANS);
    }
    
    /**
     * Gets the digital output values.
     * @return array of digital channel outputs
     */
    
    public boolean[] getDigitalOutputs()
    {
    	return Arrays.copyOf(this.dout, DIGITAL_OUTPUT_CHANS);
    }
   
    public boolean getDigitalOutput(int chan)
    {
    	return this.dout[chan];
    }
    /**
     * Gets the analog output values.
     * @return array of analog channel outputs
     */
    public double[] getAnalogOutputs()
    {
    	return Arrays.copyOf(this.aout, ANALOG_OUTPUT_CHANS);
    }
    
    public double getAnalogOutput(int chan)
    {
    	return this.aout[chan];
    }
    
    /**
     * Gets the current paddle speed.
     * @return paddle speed
     */
    public double getSpeed()
    {
    	return this.speed;
    }
    
    /**
     * Gets the pump status
     * @return pump status
     */
    
    public boolean getPump()
    {
    	return this.pump;
    }
    
    /**
     * Gets the inverter status
     * @return inverter status
     */
    public boolean getInverter()
    {
    	return this.inverter;
    }

    /**
     * Returns whether the rig is at paddle limit 1.
     * 
     * @return true if at paddle limit 1
     */
    public boolean getPaddleLimit1()
    {
        return (this.rigState & 0x02) != 0;
    }
    
    /**
     * Returns whether the rig is at paddle limit 2.
     * 
     * 
     * @return true if at paddle limit 2
     */
    public boolean getPaddleLimit2()
    {
        return (this.rigState & 0x04) != 0;
    }
    
    /**
     * Returns whether the rig is at sump limit.
     * 
     * @return true if at sump limit
     */
    public boolean getSumpLimit()
    {
        return (this.rigState & 0x08) != 0;
    }
    
    /** I have no idea what a wave tank actually does so */
    public synchronized void bufferData() throws IOException
    {
    	boolean[] dtmp = Arrays.copyOf(this.din, ANALOG_OUTPUT_CHANS);
    	for (int i=0; i<NUM_AIN_CHANS; i++)
    		this.ain[i] = this.aout[i%ANALOG_OUTPUT_CHANS];
    	
    	if (pump){
    		if (inverter){
		    	for (int i=0; i<8;i++){
		    		if (this.din[i] == true){
		    			if (i==0) dtmp[7] = true;
		    			else dtmp[(i-1)] = true;
		    			dtmp[(i)%DIGITAL_OUTPUT_CHANS] = false;
		    		}
		    	}
	    	}
    		else{
    	    	for (int i=0; i<8;i++){
		    		if (this.din[i] == true){
		    			dtmp[(i+1)%DIGITAL_OUTPUT_CHANS] = true;
		    			dtmp[(i)%DIGITAL_OUTPUT_CHANS] = false;
		    		}
    	    	}
    		}
    	}
    	
    	din = dtmp;
    }
}