/**
 * UTS Remote Labs Wave Tank Rig Client. 
 *
 * @author Michael Diponio (mdiponio)
 * @date 9th Janurary 2012
 */

package au.edu.uts.eng.remotelabs.wavetank.primitive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveResponse;
import au.edu.uts.eng.remotelabs.rigclient.rig.primitive.IPrimitiveController;

import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;
/*
import au.edu.uts.eng.remotelabs.wavetank.CRIOHandler;
import au.edu.uts.eng.remotelabs.wavetank.CRIOTcp;
*/
import au.edu.uts.eng.remotelabs.wavetank.WaveTank;
import au.edu.uts.eng.remotelabs.wavetank.WaveTankInterface;


/**
 * Controller for the Wave Tank controller.
 */
public class WaveTankController implements IPrimitiveController
{
    /** Number of output channels for digital. */
    private static final int DIGITAL_OUTPUT_CHANS = 8;
    
    /** Number of output channels for analog. */
    private static final int ANALOG_OUTPUT_CHANS = 8;
    
    /** Interface to Tank. */
    private WaveTank tank;

    /** Logger. */
    private ILogger logger;

    /**
     * Obtains data about the wave tank. No parameters are required.
     * 
     * @param request 
     * @return response
     */
	public PrimitiveResponse dataAction(PrimitiveRequest request) {
    	PrimitiveResponse response = new PrimitiveResponse();
    	response.setSuccessful(true);
    	
        response.addResult("pump", String.valueOf(this.tank.getPump()));
        response.addResult("inverter", String.valueOf(this.tank.getInverter()));
        response.addResult("paddle", String.valueOf(this.tank.getSpeed()));

        /* Inputs. */
        response.addResult("din", Arrays.toString(this.tank.getDigitalInputs()));
        response.addResult("ain", Arrays.toString(this.tank.getAnalogInputs()));

        /* Outputs. */
        response.addResult("dout", Arrays.toString(this.tank.getDigitalOutputs()));
        response.addResult("aout", Arrays.toString(this.tank.getAnalogOutputs()));
        
        return response;
	}
    
    /**
     * Sets the pump on or off. The parameter 'on' is required and must have
     * a value of 'true' or 'false'.
     * 
     * @param request
     * @return response
     * @throws IOException 
     */
    public PrimitiveResponse setPumpAction(PrimitiveRequest request) throws IOException
    {
    	this.tank.enablePump("true".equals(request.getParameters().get("on")));
    	return this.dataAction(request);
    }
    
    /**
     * Sets the inverter on or off. The parameter 'on' is required and must have
     * a value of 'true' or 'false'.
     * 
     * @param request
     * @return response
     * @throws IOException
     */
    public PrimitiveResponse setInverterAction(PrimitiveRequest request) throws IOException
    {
    	this.tank.enableInverter("true".equals(request.getParameters().get("on")));
    	return this.dataAction(request);
    }
    
    /**
     * Sets the paddle speed in Hertz. The parameter 'speed' is required and 
     * must have a double value.
     * 
     * @param request
     * @return response
     * @throws IOException
     */
    public PrimitiveResponse setPaddleAction(PrimitiveRequest request) throws IOException
    {
    	this.tank.setSpeed(Double.parseDouble(request.getParameters().get("speed")));
    	return this.dataAction(request);
    }
    
    /**
     * Sets a digital output channel. The parameters 'chan' specifying channel  
     * number and 'val' specifying 'true' or 'false'.
     * 
     * @param request
     * @return response
     * @throws IOException
     */
    public PrimitiveResponse setDigitalOutputAction(PrimitiveRequest request) throws IOException
    {
    	int chan = Integer.parseInt(request.getParameters().get("chan"));
    	boolean on = "true".equals(request.getParameters().get("val"));
    	
    	if (chan < DIGITAL_OUTPUT_CHANS)
    	{
    		this.tank.setDigitalOutput(chan, on);
    	}
    	return this.dataAction(request);
    }
    
    /**
     * Sets an analogue output channel. The parameters 'chan' specifying channel 
     * number and 'val' specifying the analogue value.
     * 
     * @param request
     * @return response
     * @throws IOException
     */
    public PrimitiveResponse setAnalogOutputAction(PrimitiveRequest request) throws IOException
    {
    	int chan = Integer.parseInt(request.getParameters().get("chan"));
    	double val = Double.parseDouble(request.getParameters().get("val"));
    	
    	if (chan < ANALOG_OUTPUT_CHANS)
    	{
    		this.tank.setAnalogOutput(chan, val);
    	}
    	return this.dataAction(request);
    }

	@Override
	public boolean initController() {
        this.logger = LoggerFactory.getLoggerInstance();
    	/* Call getInstance */
    	this.tank = WaveTankInterface.acquire();
    	if(this.tank == null)
    	{
    		this.logger.warn("Could not retrieve CRIOTcp instance. Failing Wave Tank controller initialisation.");
    		return false;
    	}
    	return true;
	}

	@Override
	public boolean preRoute() {
		return true;
	}

	@Override
	public boolean postRoute() {
		return true;
	}

	@Override
	public void cleanup() {
		WaveTankInterface.lease();
		
	}
    
}