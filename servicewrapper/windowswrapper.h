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

#ifndef WindowsWrapper_H
#define WindowsWrapper_H

#pragma comment(lib, "advapi32.lib")
#define _CRT_SECURE_NO_WARNINGS

#include <windows.h>
#include <winbase.h>
#include <winsvc.h>

#include "jvmrunner.h"

/*******************************************************************************
 ** Constants                                                                 **
 ******************************************************************************/

#define SERVICE_NAME "RigClient"

#define SERVICE_DESC "Software abstraction of physical rig to provide rig session \
control and rig device control. Automatically tests rig hardware \
and reports the rig status to ensure rig goodness."

/*******************************************************************************
 ** Functions                                                                 **
 ******************************************************************************/

/**
 * Installs the windows service into the service control manager.
 *
 * @return 0 on success, error code for error.
 */
int installService();

/**
 * Removes the service from the service control manager.
 *
 * @return 0 on success, error code for error
 */
int uninstallService();

/**
 * Initialises the service, by setting the working directory of the service from
 * 'C:/Windows/system32' to the directory te executable is in.
 */
int initService();

/**
 * Function called be the OS when the service is started.
 */
void WINAPI ServiceMain(DWORD dwArgc, LPTSTR *lpszArgv);

/**
 * Receives control requests for the service.
 */
void WINAPI ServiceHandler(DWORD control);

DWORD WINAPI threadMain(LPVOID lpParam);

/*******************************************************************************
 ** Globals                                                                   **
 ******************************************************************************/

/** Event to notify the ServiceMain function to terminate. */
HANDLE stopEvent;

/** Handle for the status structure. */
SERVICE_STATUS_HANDLE serviceHandle; 

/** Structure used to set the services status. */
SERVICE_STATUS serviceStatus;

/** JVM thread handle. */
HANDLE threadHandle;

/** JVM thread ID. */
DWORD threadID;

#endif
