/**
 * Sahara Rig Client - Service Wrapper
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
 * @date 8th March 2010
 */

#include <pthread.h>
#include <signal.h>
#include "jvmrunner.h"

void *runRigClient();
void shutDownRigClient();

/**
 * Starts up the rig client by creating a JVM in a seperate thread.
 * Installs a signal handler so a SIGINT or SIGTERM causes the rig
 * client to shut down. */
int main(int argc, char *argv[])
{
    pthread_t thread;
    int code;
    
    struct sigaction shutAction;

    if (!loadConfig())
    {
	logMessage("Failed to load configuration, exiting...\n\n");
	printf("Failed to load configuration, exiting...\n\n");
	return 1;
    }
    
    if (!generateClassPath())
    {
    	logMessage("Failed to detected current directory...\n\n");
    	printf("Failed to detected current directory...\n\n");
	return 2;
    }

    /* Install signal handler for SIGINT & SIGTERM. */
    shutAction.sa_handler = shutDownRigClient;
    sigemptyset(&shutAction.sa_mask);
    shutAction.sa_flags = SA_RESTART;
    if (sigaction(SIGINT, &shutAction, NULL) == -1)
    {
	logMessage("Unable to install interrupt signal (SIGINT) handler.");
	perror("Failed installing SIGINT handler");
    }
    if (sigaction(SIGTERM, &shutAction, 0) == -1)
    {
	logMessage("Unable to install terminate signal (SIGTERM) handler.");
	perror("Failed installing SIGTERM handler");
    }

    /* Start thread. */
    if (code = pthread_create(&thread, NULL, runRigClient, NULL))
    {
	logMessage("Failed to create rig client thread with code %i.\n", code);
	printf("Failed to create rig client thread with code %i.\n", code);
	return 3;
    }

    pthread_join(thread, NULL);
    return 0;
}

void *runRigClient()
{
    logMessage("Starting up JVM...\n");
    if (!startJVM())
    {
    	printf("Failed to start JVM.\n\n");
    }
}

void shutDownRigClient()
{
    logMessage("Shutting down JVM...\n");
    shutDownJVM();
}
