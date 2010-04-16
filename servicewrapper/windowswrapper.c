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

	if (argc == 1)
	{
		/* Service table entry to start service. */
		SERVICE_TABLE_ENTRY lpServiceTable[] = 
		{
			{SERVICE_NAME, (LPSERVICE_MAIN_FUNCTION)ServiceMain},
			{0, 0}
		};

		/* Start the service. */
		if (!StartServiceCtrlDispatcher(lpServiceTable))
		{
			DWORD err = GetLastError();
			if (err == ERROR_FAILED_SERVICE_CONTROLLER_CONNECT)
			{
				logMessage("Can't run service as a command line program, it must be installed as a service and the '%s' service started.\n", SERVICE_NAME);
				printf("Can't run service as a command line program, it must be installed as a service and the '%s' service started.\n\n", SERVICE_NAME);
			}
			else if (err == ERROR_INVALID_DATA)
			{
				logMessage("BUG: Invalid service dispatch table information.\n");
			}
			else if (err == ERROR_SERVICE_ALREADY_RUNNING)
			{
				logMessage("Failed to start service %s because it is already running.\n", SERVICE_NAME);
			}
			else
			{
				logMessage("Failed to start service %s because it is already running.\n", SERVICE_NAME);
			}
		}
	}
	else
	{
		arg = argv[1];
		while (*arg != '\0' && *arg == '-') arg++;
		if (_stricmp("install", arg) == 0)
		{
			/* Install the service. */
			return installService();
		}
		else if (_stricmp("uninstall", arg)  == 0)
		{
			/* Remove the service. */
			return uninstallService();
		}
		else if (_stricmp("help", arg) == 0 || *arg == 'h')
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

	return 0;
}

/**
 * Entry point of the service.
 */
void WINAPI ServiceMain(DWORD dwArgc, LPTSTR *lpszArgv) 
{
	if ((serviceHandle = RegisterServiceCtrlHandler(SERVICE_NAME, ServiceHandler)) == NULL)
	{
		logMessage("Failed to add service handle with error code %i.\n", GetLastError());
		return;
	}

	/* Initialise the service status structure. */
	serviceStatus.dwServiceSpecificExitCode = 0;
	serviceStatus.dwServiceType = SERVICE_WIN32_OWN_PROCESS;
	serviceStatus.dwWin32ExitCode = NOERROR;
	serviceStatus.dwWaitHint = 30000;

	/* Set the service start to start pending. */
	serviceStatus.dwCurrentState = SERVICE_START_PENDING;
	serviceStatus.dwControlsAccepted = 0;
	SetServiceStatus(serviceHandle, &serviceStatus);

	/* Start the JVM in it's own thread. */
	threadHandle = CreateThread(
		NULL,       /* Default security attributes. */
		0,          /* Default stack size. */
		threadMain, /* Thread function. */
		NULL,       /* No arguments for function. */
		0,          /* Thread runs immediately. */
		&threadID); 
	if (threadHandle == NULL)
	{
		logMessage("Failed to start the JVM thread, with exit code %i.\n", GetLastError());
		serviceStatus.dwCurrentState = SERVICE_STOPPED;
		SetServiceStatus(serviceHandle, &serviceStatus);
		return;
	}
	

	/* Set the service start to running. */
	serviceStatus.dwCurrentState = SERVICE_RUNNING;
	serviceStatus.dwControlsAccepted = SERVICE_ACCEPT_STOP;
	serviceStatus.dwWaitHint = 0;
	SetServiceStatus(serviceHandle, &serviceStatus);

	/* Wait forver for the rig client thread to terminate. */
	WaitForSingleObject(threadHandle, INFINITE);

	logMessage("Rig client service has successfully shutdown...\n\n");

	/* Shutdown service. */	
	serviceStatus.dwCurrentState = SERVICE_STOPPED;
	SetServiceStatus(serviceHandle, &serviceStatus);

	return;
}

/**
 * Receives control requests for the service.
 */
void WINAPI ServiceHandler(DWORD control)
{
	switch (control)
	{
		/* Shutdown the service. */
		case SERVICE_CONTROL_STOP:
		case SERVICE_CONTROL_SHUTDOWN:
			logMessage("Handling a stop/shutdown control request, shutting down...\n");

			/* Set the status to stop pending and shutdown JVM. */
			serviceStatus.dwCurrentState = SERVICE_STOP_PENDING;
			serviceStatus.dwControlsAccepted = 0;
			serviceStatus.dwWaitHint = 60000;
			SetServiceStatus(serviceHandle, &serviceStatus);

			/* Notify the Rig Client to shutdown. */
			shutDownJVM();
			break;
		default:
			break;
	}

	return;
}

DWORD WINAPI threadMain(LPVOID lpParam)
{
	if (!loadConfig())
    {
		logMessage("Unable to load configuration.\n");
		return 0;
    }

	if (!generateClassPath())
	{
		logMessage("Unable to generate classpath.");
		return 0;
	}
	
	startJVM();
	return 1;
}

/**
 * Initialises the service, by setting the working directory of the service from
 * 'C:/Windows/system32' to the directory te executable is in.
 */
int initService()
{
	char currentDir[FILENAME_MAX + 1], *lastSl;
	memset(currentDir, 0, FILENAME_MAX + 1);

	if (!GetModuleFileName(NULL, currentDir, FILENAME_MAX))
	{
		logMessage("Getting the programs file name with error code %i.\n", GetLastError());
	}

	lastSl = strrchr(currentDir, '\\');
	if (lastSl == NULL)
	{
		logMessage("Unable to determine current directory.\n");
		return 0;
	}

	*lastSl = '\\';
	lastSl++;
	*lastSl = '\0';

	SetCurrentDirectory(currentDir);
	logMessage("Setting current working directory to %s.\n", currentDir);
	return 1;
}

/**
 * Installs the windows service into the service control manager.
 *
 * @return 0 on success, error code for error.
 */
int installService()
{
	char exeFile[FILENAME_MAX + 1];
	SC_HANDLE scManager, service;
	SERVICE_DESCRIPTION desc;

	/* Get the file name of the executable, so it can be installed as the 
	 * service executable. */
	memset(exeFile, 0, FILENAME_MAX + 1);
	GetModuleFileName(NULL, exeFile, FILENAME_MAX);
	
	/* Local computer with create active services database. */
	scManager = OpenSCManager(NULL, SERVICES_ACTIVE_DATABASE, SC_MANAGER_CREATE_SERVICE);
	if (scManager == NULL)
	{
		DWORD err = GetLastError();
		if (err == ERROR_ACCESS_DENIED)
		{
			logMessage("Access denied opening service control manager. Please run this as a user with ");
			logMessage("administrative privileges.\n");
			printf("Access denied opening service control manager. Please run this as a user with\n");
			printf("administrative privileges.\n");
		}
		else if (err == ERROR_DATABASE_DOES_NOT_EXIST)
		{
			logMessage("BUG: Services database does not exist. The requested database is the currently active services ");
			logMessage("database which should exist.\n");
			printf("BUG: Services database does not exist. The requested database is the currently active\n");
			printf("services database which should exist.\n");
		}
		else
		{
			logMessage("Unknown error opening service control manager with code %i.\n", err);
			printf("Unknown error opening service control manager with code %i.\n", err);
		}
		
		CloseServiceHandle(scManager);
		return err;
	}

	service = CreateService(
		scManager,
		SERVICE_NAME,              /* Service name. */
		SERVICE_NAME,              /* Service display name. */
		SERVICE_ALL_ACCESS,        /* Desired access, needed to add the description. */
		SERVICE_WIN32_OWN_PROCESS, /* Service that runs in its own process. */
		SERVICE_AUTO_START,        /* Automatically start by the SC Manager during system start. */
		SERVICE_ERROR_NORMAL,      /* Log error by continue startup. */
		exeFile,                   /* Executable. */
		NULL,                      /* No load order group membership. */
		NULL,                      /* Not needed because no load order. */
		NULL,                      /* No dependencies. */
		NULL,                      /* Run as LocalSystem account. */
		NULL);                     /* No password with local system account. */
	if (service == NULL)
	{
		DWORD err = GetLastError();
		if (err == ERROR_ACCESS_DENIED)
		{
			logMessage("Access denied to install service.\n");
			printf("Access denied to install service.\n");
		}
		else if (err == ERROR_CIRCULAR_DEPENDENCY)
		{
			logMessage("BUG: Circular dependency detected (?), this shouldn't happen as no dependencies are specified.\n");
			printf("BUG: Circular dependency detected (?), this shouldn't happen as no dependencies are specified.\n");
		}
		else if (err == ERROR_DUPLICATE_SERVICE_NAME)
		{
			logMessage("Duplicate service name.\n");
			printf("Duplicate service name.\n");
		}
		else if (err == ERROR_INVALID_HANDLE)
		{
			logMessage("BUG: Invalid handle to service control manager.\n");
			printf("BUG: Invalid handle to service control manager.\n");
		}
		else if (err == ERROR_INVALID_NAME)
		{
			logMessage("BUG: Invalid service name. This service name is %s and hard coded!\n", SERVICE_NAME);
			printf("BUG: Invalid service name. This service name is %s and hard coded!\n", SERVICE_NAME);
		}
		else if (err == ERROR_INVALID_PARAMETER)
		{
			logMessage("BUG: Invalid parameter when creating the service %s.\n", SERVICE_NAME);
			printf("BUG: Invalid parameter when creating the service %s.\n", SERVICE_NAME);
		}
		else if (err == ERROR_INVALID_SERVICE_ACCOUNT)
		{
			logMessage("BUG: Invalid service account, this should not occur as it should be using the defaul LocalSystem account.\n");
			printf("BUG: Invalid service account, this should not occur as it should be using the defaul LocalSystem account.\n");
		}
		else if (err == ERROR_SERVICE_EXISTS)
		{
			logMessage("Service %s already exists. Please uninstall the previous instance before reinstalling it.\n", SERVICE_NAME);
			printf("Service %s already exists. Please uninstall the previous instance before reinstalling it.\n", SERVICE_NAME);
		}
		else
		{
			logMessage("Unknown error installing service with code %i.\n", err);
			printf("Unknown error installing service with code %i.\n", err);
		}

		CloseServiceHandle(scManager);
		CloseServiceHandle(service);
		return err;
	}

	/* Set a friendly description for the service. */
	desc.lpDescription = SERVICE_DESC;
	ChangeServiceConfig2(service, SERVICE_CONFIG_DESCRIPTION, (void *)&desc);
	
	CloseServiceHandle(scManager);
	CloseServiceHandle(service);
	printf("Successfully installed the %s service.\n", SERVICE_NAME);
	return 0;
}

/**
 * Removes the service from the service control manager.
 *
 * @return 0 on success, error code for error
 */
int uninstallService()
{
	SC_HANDLE scManager, service;

	/* Local computer with create active services database. */
	scManager = OpenSCManager(NULL, SERVICES_ACTIVE_DATABASE, SC_MANAGER_ALL_ACCESS);
	if (scManager == NULL)
	{
		DWORD err = GetLastError();
		if (err == ERROR_ACCESS_DENIED)
		{
			logMessage("Access denied opening service control manager. Please run this as a user with ");
			logMessage("administrative privileges.\n");
			printf("Access denied opening service control manager. Please run this as a user with\n");
			printf("administrative privileges.\n");
		}
		else if (err == ERROR_DATABASE_DOES_NOT_EXIST)
		{
			logMessage("BUG: Services database does not exist. The requested database is the currently active services ");
			logMessage("database which should exist.\n");
			printf("BUG: Services database does not exist. The requested database is the currently active\n");
			printf("services database which should exist.\n");
		}
		else
		{
			logMessage("Unknown error opening service control manager with code %i.\n", err);
			printf("Unknown error opening service control manager with code %i.\n", err);
		}
		
		CloseServiceHandle(scManager);
		return err;
	}

	/* Open a handle to the service. */
	service = OpenService(scManager, SERVICE_NAME, SERVICE_ALL_ACCESS);
	if (service == NULL)
	{
		DWORD err = GetLastError();
		if (err == ERROR_ACCESS_DENIED)
		{
			logMessage("Access denied opening service. Please run this as a user with ");
			logMessage("administrative privileges.\n");
			printf("Access denied opening service. Please run this as a user with\n");
			printf("administrative privileges.\n");
		}
		else if (err == ERROR_INVALID_HANDLE)
		{
			logMessage("BUG: Invalid handle to service control manager.\n");
			printf("BUG: Invalid handle to service control manager.\n");
		}
		else if (err == ERROR_INVALID_NAME)
		{
			logMessage("BUG: Invalid service name. This service name is %s and hard coded!\n", SERVICE_NAME);
			printf("BUG: Invalid service name. This service name is %s and hard coded!\n", SERVICE_NAME);
		}
		else if (err == ERROR_SERVICE_DOES_NOT_EXIST)
		{
			logMessage("The %s service is not currently installed.\n");
			printf("The %s service is not currently installed.\n");
		}
		else
		{
			logMessage("Unknown error opening service with code %i.\n", err);
			printf("Unknown error opening service with code %i.\n", err);
		}

		CloseServiceHandle(scManager);
		return err;
	}

	/* Delete the service. */
	if (!DeleteService(service))
	{
		DWORD err = GetLastError();
		if (err == ERROR_ACCESS_DENIED)
		{
			logMessage("Access denied deleting service. Please run this as a user with ");
			logMessage("administrative privileges.\n");
			printf("Access denied deleting service. Please run this as a user with\n");
			printf("administrative privileges.\n");
		}
		else if (err == ERROR_INVALID_HANDLE)
		{
			logMessage("BUG: Invalid handle to service.\n");
			printf("BUG: Invalid handle to service.\n");
		}
		else if (err == ERROR_SERVICE_MARKED_FOR_DELETE)
		{
			logMessage("Service %s already marked for deletion. Please reboot the computer.\n");
			printf("Service %s already marked for deletion. Please reboot the computer.\n");
		}
		else
		{
			logMessage("Unknown error deleting service with code %i.\n", err);
			printf("Unknown error deleting service with code %i.\n", err);
		}
		CloseServiceHandle(scManager);
		return err;
	}

	CloseServiceHandle(scManager);
	printf("Successfully deleted service %s.\n", SERVICE_NAME);
	return 0;
}

