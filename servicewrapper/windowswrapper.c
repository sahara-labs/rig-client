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


#include "windowswrapper.h"

int main(int argc, char* argv[])
{
	char *arg;

    if (!initService())
	{
		logMessage("Failed to initialise service.\n");
		return 1;
	}
    
    if (!loadConfig())
    {
		logMessage("Unable to load configuration.\n");
		return 2;
    }

	if (argc == 1)
	{
		/* Start service. */
	}

	arg = argv[1];
	while (*arg != '\0' && *arg == '-') arg++;
	if (strcmp("install", arg) == 0)
	{
		/* Install the service. */
	}
	else if (strcmp("uninstall", arg)  == 0)
	{
		/* Remove the service. */
	}
	else if (stricmp("help", arg) == 0 || *arg == 'h')
	{		
		/* Print help. */
		printf("Usage: %s [install]|[uninstall]\n\n", argv[0]);
		printf("\t- install   - Installs the %s service. For installation to\n", SERVICE_NAME);
		printf("\t              succeed the service must not be currently install.\n");
		printf("\t- uninstall - Removes the service %s.\n\n", SERVICE_NAME);
		printf("Once the service is installed the following Windows commands allow\n");
		printf("the %s service to started and stopped\n\n", SERVICE_NAME);
		printf("\t- Start %s: net start \"%s\"\n", SERVICE_NAME, SERVICE_NAME);
		printf("\t- Stop %s:  net stop \"%s\"\n\n", SERVICE_NAME, SERVICE_NAME);
		printf("Alternatively, the %s service may be started or stopped using the\n", SERVICE_NAME);
		printf("Windows Services Administrative Tool, located in:\n\n");
		printf("\tControl Panel -> Administrative Tools -> Services\n\n");
	}
}

/**
 * Initialises the service, by setting the working directory of the service from
 * 'C:/Windows/system32' to the directory te executable is in.
 */
int initService()
{
	char currentDir[FILENAME_MAX + 1], *lastSl;
	memset(currentDir, 0, FILENAME_MAX + 1);

	GetModuleFileName(NULL, currentDir, FILENAME_MAX);
	
	lastSl = strrchr(currentDir, '\\');
	if (lastSl == NULL)
	{
		logMessage("Unable to determine current directory.\n");
		return 0;
	}

	*lastSl = '\\';
	lastSl++;
	*lastSl = '\0';

	logMessage("Setting current working directory to %s.\n", currentDir);
	SetCurrentDirectory(currentDir);
	return 1;
}

/**
 * Function called be the OS when the service is started.
 */
void WINAPI ServiceMain(DWORD dwArgc, LPTSTR *lpszArgv) 
{
}


void installService()
{
}

void uninstallService()
{
}
