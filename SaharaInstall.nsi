/**
 * SAHARA Installer
 * 
 * Sahara installer/uninstaller script for Windows.  
 
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
 * @author Tejaswini Deshpande (tdeshpan)
 * @date 12th March 2010
 */
 

; SaharaInstall.nsi
;
;--------------------------------
!include MUI2.nsh
!include TextFunc.nsh
!include nsDialogs.nsh
!include LogicLib.nsh
!include WordFunc.nsh
!include Sections.nsh

!define SF_UNSELECTED  0


; The name of the installer
Name "Sahara Rig Client"

!define REGKEY "SOFTWARE\$(^Name)"

; Sahara Rig Client Version
!define Version "3.0-0"

!define JREVersion "1.6"

; The file to write
OutFile "package\RigClient-${Version}.exe"

; The default installation directory
InstallDir "C:\Program Files\Sahara"

BrandingText "$(^Name)"
WindowIcon off
XPStyle on
Var commonsDisabled
;--------------------------------
;Interface Settings
 
!define MUI_HEADERIMAGE
!define MUI_HEADERIMAGE_BITMAP "installerFiles\labshare.bmp"
!define MUI_ICON "installerFiles\labshare.ico"
!define MUI_ABORTWARNING
  
!define MUI_UNICON "installerFiles\win-install.ico"

!define Sahara_RCWindows_Service "RigClient"

;--------------------------------

; Pages
;
!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_LICENSE "License"
Page custom getUpgradeChoice selectionDone
!define MUI_PAGE_CUSTOMFUNCTION_LEAVE CheckSlectedComponents
Var CompHeaderSubText
!define MUI_PAGE_HEADER_SUBTEXT "$CompHeaderSubText"
!define MUI_PAGE_CUSTOMFUNCTION_Pre ComponentPagePre
!insertmacro MUI_PAGE_COMPONENTS

Var DirHeaderText
Var DirHeaderSubText
!define MUI_PAGE_HEADER_TEXT    "$DirHeaderText"
!define MUI_PAGE_HEADER_SUBTEXT "$DirHeaderSubText"

!define MUI_PAGE_CUSTOMFUNCTION_Pre DirectoryPagePre
!define MUI_PAGE_CUSTOMFUNCTION_SHOW DirectoryPageShow
!insertmacro MUI_PAGE_DIRECTORY
!define MUI_PAGE_CUSTOMFUNCTION_Pre SetInstallDir
!insertmacro MUI_PAGE_INSTFILES
!define MUI_FINISHPAGE_TEXT "$(^Name) is installed at $INSTDIR"
!insertmacro MUI_PAGE_FINISH 

!insertmacro MUI_UNPAGE_CONFIRM
!define MUI_PAGE_CUSTOMFUNCTION_LEAVE un.CheckSlectedComponents
!insertmacro MUI_UNPAGE_COMPONENTS
!insertmacro MUI_UNPAGE_INSTFILES

Var Dialog
Var UpgradeSelected
Var CleanInstallSelected
Var Checkbox_State
;--------------------------------
;Languages
 
!insertmacro MUI_LANGUAGE "English"

;--------------------------------

Var DisplayText
Var NoSectionSelectedUninstall 
Var RCAlreadyInstalled ;NI=Not installed, V2=2.x version installed, SAME=installed and same version, 
                       ;V3=Major version 3 is installed but with different minor version/build,
                       ;OTHER=some other version than thsi version or 2.x is installed


Function DirectoryPagePre
	; If there is already an installation of Sahara Rig Client, use the same folder for this installation. Else let the user select the installation folder
 	${If} $RCAlreadyInstalled S== "NI"
		StrCpy $DirHeaderText "Choose Install Location"
		StrCpy $DirHeaderSubText "Choose the folder in which to install $(^Name)"
	${Else}
		StrCpy $DirHeaderText "Using existing $(^Name) installation folder"
		StrCpy $DirHeaderSubText "$(^Name) is already installed on this machine. Installer will upgarde the existing installation"
		ReadRegStr $R0 HKLM "${REGKEY}" "Path"
		StrCpy $INSTDIR $R0
	${EndIf}

FunctionEnd

Function CheckRCVersion
    Var /GLOBAL RCVersion
    Push $R0
	ReadRegStr $RCVersion HKLM "${REGKEY}" "CurrentVersion"
    StrCpy $R0 $RCVersion 1
	${If} $R0 S== ""
        ; RC not installed. Clean install
		StrCpy $RCAlreadyInstalled "NI"
	${ElseIf} $R0 S== "2"
        ; RC version 2.x is installed. Can be upgraded
        StrCpy $RCAlreadyInstalled "V2"
    ${ElseIf} $RCVersion S== ${Version}
        ; Same RC version is installed. No action
		StrCpy $RCAlreadyInstalled "SAME"
	${ElseIf} $R0 S== "3"
        ; RC version 3.x is installed (with different minor version or build). Can be upgraded
        StrCpy $RCAlreadyInstalled "V3"
    ${Else}
        ; Some other version is installed
		StrCpy $RCAlreadyInstalled "OTHER"
	${EndIf}
    Pop $R0
FunctionEnd


Function DirectoryPageShow

	; If there is already an installation of Rig Client, disable the destination folder selection and use the same folder for this installation. 
	; Else let the user select the installation folder
	Push $R0
    Push $R1
	${If} $RCAlreadyInstalled S== "V2" 
    ${OrIf} $RCAlreadyInstalled S== "V3"
 		FindWindow $R0 "#32770" "" $HWNDPARENT
		GetDlgItem $R1 $R0 1019
		SendMessage $R1 ${EM_SETREADONLY} 1 0
		EnableWindow $R1 0
		GetDlgItem $R1 $R0 1001
		EnableWindow $R1 0
	${EndIf}
    Pop $R0
    Pop $R1
FunctionEnd

Function SetInstallDir
	${If} $RCAlreadyInstalled S== "NI"
		StrCpy $INSTDIR "$INSTDIR\RigClient"
	${EndIf}
FunctionEnd

Function .onInit
	; Splash screen 
	advsplash::show 1000 1000 1000 -1 ..\InstallerFiles\labshare
 	StrCpy $RCAlreadyInstalled "-1"
	call CheckRCVersion
	${If} $RCAlreadyInstalled S== "OTHER"
		MessageBox MB_OK|MB_ICONSTOP "$(^Name) version ${Version} can not be installed on current version $RCVersion.\
        $\nPlease uninstall the existing $(^Name) software and re-run the installer"
		Abort
    ${ElseIf} $RCAlreadyInstalled S== "SAME"
        MessageBox MB_OK|MB_ICONSTOP "$(^Name) version ${Version} is already installed on this machine.$\nExiting the installation."
        Abort
	${EndIf}

FunctionEnd

Function getUpgradeChoice
    ${If} $RCAlreadyInstalled S== "V2" 
    ${OrIf} $RCAlreadyInstalled S== "V3"
        ; Can be upgraded or overwritten. Get the user choice
        !insertmacro MUI_HEADER_TEXT "Select the installation option" ""
        nsDialogs::Create 1018
        Pop $Dialog
        ${If} $Dialog == error
            Abort
        ${EndIf}
        ${NSD_CreateLabel} 0 0 100% 15% "Please select one of the following install options:"
        ${NSD_CreateRadioButton} 0 20% 100% 15% "Upgrade the $(^Name) from the existing version $RCVersion to version ${Version}"
        Pop $UpgradeSelected
        ${NSD_CreateRadioButton} 0 35% 100% 15% "Clean install $(^Name) ${Version}"
        Pop $CleanInstallSelected
        ; By default, select the upgrade option
        ${NSD_SetState} $UpgradeSelected ${BST_CHECKED} 
        nsDialogs::Show
    ${EndIf}     
FunctionEnd

Function selectionDone
    ; Check which option is selected
    ${NSD_GetState} $UpgradeSelected $Checkbox_State
    ${If} $Checkbox_State == ${BST_UNCHECKED} ; Upgrade option not selected
        ${NSD_GetState} $CleanInstallSelected $Checkbox_State
        ${If} $Checkbox_State == ${BST_CHECKED}
            ; If 'Clean Install' option is selected, abort the installer and display message
            MessageBox MB_ICONINFORMATION|MB_OKCANCEL "To install version ${Version}, please uninstall the existing version of $(^Name) \
            and re-run the installer" IDCANCEL ShowSamePage
            Quit
        ShowSamePage:
            Abort
        ${Else}
            MessageBox MB_OK "Please select one option"
            Abort
        ${EndIf}
    ${EndIf}
FunctionEnd

Function checkJREVersion
	; Check the JRE version to be 1.6 or higher
	ReadRegStr $0 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" CurrentVersion 
	${If} $0 S< ${JREVersion} 
		MessageBox MB_OK|MB_ICONSTOP "Rig Client needs JRE version ${JREVersion} or higher. It is currently $0. Aborting the installation."
		Abort ; causes installer to quit.
	${EndIf}
FunctionEnd

; Check if RigClient service is installed
Function checkIfServiceInstalled
	;ReadEnvStr $R0 COMSPEC

	; Check if the RigClient service is already installed. (probably need a better way to do this)
	; If the service is installed, the output of 'sc query RigClient' will be like:
	;		SERVICE_NAME: RigClient
        ;		TYPE               : 10  WIN32_OWN_PROCESS
        ;		STATE              : 1  STOPPED
        ;              		          (NOT_STOPPABLE,NOT_PAUSABLE,IGNORES_SHUTDOWN)
        ;		WIN32_EXIT_CODE    : 1077       (0x435)
        ;		SERVICE_EXIT_CODE  : 0  (0x0)
        ;		CHECKPOINT         : 0x0
        ;		WAIT_HINT          : 0x0
	; If the service is not installed, the output will be like:
	;		[SC] EnumQueryServicesStatus:OpenService FAILED 1060:
	;		The specified service does not exist as an installed service.
	; Checking for the word 'FAILED' in the output of the above command
	;
	; - If function "WordReplace" is successful (the word "FAILED" is found in the result), the service is not installed
	; - If the function "WordReplace" is not successful and the errorlevel (value of $R0) is 1 (the word "FAILED" not found), the service is installed
	; - If the function "WordReplace" is not successful and the errorlevel (value of $R0) is anything other than 1 then some error has occured in
	;   executing function "WordReplace"
	
	nsExec::ExecToStack /OEM '"sc" query ${Sahara_RCWindows_Service}'
	Pop $0	; $0 contains return value/error/timeout
	Pop $1	; $1 contains printed text, up to ${NSIS_MAX_STRLEN}

	ClearErrors
	${WordReplace} '$1' 'FAILED' 'FAILED' 'E+1' $R0
	IfErrors 0 Found
	StrCmp $R0 '1' 0 Error
	MessageBox MB_OK|MB_ICONSTOP "'${Sahara_RCWindows_Service}' service is already installed.  Please stop the '${Sahara_RCWindows_Service}' service if it is running (Windows Control Panel->Administrative Tools->Services) $\n$\nUse rigclientservice.exe to uninstall the previous version (rigclientservice.exe uninstall)"
	Abort
	Error:
	MessageBox MB_OK|MB_ICONSTOP "Error is detecting if '${Sahara_RCWindows_Service}' service is installed"
	Abort
	Found:
FunctionEnd

; Check if RigClient service is running
!macro checkIfServiceRunning
	; Check if the RigClient service is running. (probably need a better way to do this)
	; If the service is running, the output of 'sc query RigClient' will be like:
	;	SERVICE_NAME: RigClient
	;	        TYPE               : 10  WIN32_OWN_PROCESS
	;	        STATE              : 4  RUNNING
        ;       		                (STOPPABLE,NOT_PAUSABLE,ACCEPTS_SHUTDOWN)
	;	        WIN32_EXIT_CODE    : 0  (0x0)
	;	        SERVICE_EXIT_CODE  : 0  (0x0)
	;	        CHECKPOINT         : 0x0
	;	        WAIT_HINT          : 0x0
	;
	; If the service is installed but not running, the STATE would be 
	;		SERVICE_NAME: RigClient
        ;		TYPE               : 10  WIN32_OWN_PROCESS
        ;		STATE              : 1  STOPPED
        ;              		          (NOT_STOPPABLE,NOT_PAUSABLE,IGNORES_SHUTDOWN)
        ;		WIN32_EXIT_CODE    : 1077       (0x435)
        ;		SERVICE_EXIT_CODE  : 0  (0x0)
        ;		CHECKPOINT         : 0x0
        ;		WAIT_HINT          : 0x0
        ;
        ; If the service is not installed, the STATE would be 
	;		[SC] EnumQueryServicesStatus:OpenService FAILED 1060:
	;		The specified service does not exist as an installed service.
	;
	; Check for the word 'RUNNING' in the output of the above command. If not found, 
	;
	; - If function "WordReplace" is successful (the word "RUNNING" is found in the result), the service is running
	; - If the function "WordReplace" is not successful and the errorlevel (value of $R0) is 1 (the word "RUNNING" not found), the service is stopped or not installed
	; - If the function "WordReplace" is not successful and the errorlevel (value of $R0) is anything other than 1 then some error has occured in
	;   executing function "WordReplace"
	
	nsExec::ExecToStack /OEM '"sc" query ${Sahara_RCWindows_Service}'
	Pop $0	; $0 contains return value/error/timeout
	Pop $1	; $1 contains printed text, up to ${NSIS_MAX_STRLEN}

	ClearErrors
	${WordReplace} '$1' 'RUNNING' 'RUNNING' 'E+1' $R0
	IfErrors Errors 0
	MessageBox MB_OK|MB_ICONSTOP "Please stop the '${Sahara_RCWindows_Service}' service before continuing"
	Abort
	Errors:
	StrCmp $R0 '1' NotRunning 0
	MessageBox MB_OK|MB_ICONSTOP "Error is detecting if '${Sahara_RCWindows_Service}' service is installed"
	Abort
	NotRunning:
!macroend

!macro uninstallWindowsService operation
    !insertmacro checkIfServiceRunning
    Push $R1
    ReadRegStr $R1 HKLM "${REGKEY}" "Path"
    ClearErrors
    ExecWait '"$R1\rigclientservice" uninstall'
    ifErrors 0 WinServiceNoError
    MessageBox MB_ABORTRETRYIGNORE "Error in uninstalling RigClient service.$\n$\nIf the service is installed, manually \
    uninstall the service from command prompt using: '$R1\rigclientservice uninstall' as admin and pClick 'Retry'. $\nIf the service \
    is already uninstalled, pClick 'Ignore'. $\nClick 'Abort' to end the uninstallation" IDABORT AbortUninstall IDIGNORE WinServiceNoError 
    ;TryAgain
    ExecWait '$R1\rigclientservice uninstall'
    ifErrors 0 WinServiceNoError
    MessageBox MB_OK|MB_ICONSTOP "Error in uninstalling RigClient service again. Aborting the ${operation}"
    AbortUninstall:
    Abort
    WinServiceNoError:
    Pop $R1
!macroend 


; Disable the section so that user will not be able to select it
; This macro is used mainly for uninstallation. 
; The uninstaller will enable only those sections which are installed. Other sections will be disabled for selection.
!macro disableSection sectionId
	Push $R0
	IntOp $R0 ${SF_UNSELECTED} | ${SF_RO}
	SectionSetFlags ${SectionId} $R0
	Pop $R0
!macroend

; Check if the user has admin rights
!macro checkAdminUser operation
	userInfo::getAccountType
	pop $0
	strCmp $0 "Admin" AdminUser
	MessageBox MB_OK|MB_ICONSTOP "$(^Name) ${operation} requires administrative privileges"
	Abort
	AdminUser:
!macroend

Function ComponentPagePre
    ${If} $RCAlreadyInstalled S== "NI"
        StrCpy $CompHeaderSubText "Select the $(^Name) components for installation "
    ${Else}
        StrCpy $CompHeaderSubText "Select the $(^Name) components for upgrade "
    ${EndIf}
FunctionEnd

; Installer group. It contains following sub sections -
; 1. Rig Client
; 2. Rig Client CLI

;--------------------------------
; Install Rig Client
SectionGroup /e "Sahara Rig Client" RigClientGroup
Section "Rig Client" RigClient

	!insertmacro checkAdminUser "installation"
    ${If} $RCAlreadyInstalled S== "NI"
        ; Clean installation
        call checkJREVersion	
        call checkIfServiceInstalled
    ${Else}
        ; uninstall the existing service before upgrade
        !insertmacro uninstallWindowsService "upgrade"
        RMDir /r $INSTDIR\interface
        Delete $R1\config\rigclient_service.ini
    ${EndIf}
    ; Common steps for installation and upgrade
    SetOutPath $INSTDIR
	File dist\rigclient.jar
	File servicewrapper\WindowsServiceWrapper\Release\rigclientservice.exe
    File rigclient_service.ini

    ; Set output path to the configuration directory
    SetOutPath $INSTDIR\conf
    File conf\rigclient.properties 
    File /r /x *.svn conf\conf.d
    File /r /x *.svn conf\conf.example
            
    ; Bug #77 - create lib directory
    SetOutPath $INSTDIR\lib
    
	; Add the RigClient service to the windows services
	ExecWait '"$INSTDIR\rigclientservice" install'
	ifErrors 0 WinServiceInstNoError
	MessageBox MB_OK|MB_ICONSTOP "Error in executing rigclientservice.exe"
	; TODO  Revert back the installed RC in case of error?
	Abort
	WinServiceInstNoError:
    WriteRegStr HKLM "${REGKEY}" Path $INSTDIR
    WriteRegStr HKLM "${REGKEY}" CurrentVersion  ${Version}
SectionEnd

Section "Rig Client Commons" Commons
    SetOutPath $INSTDIR\lib
    File ..\RigClientCommons\trunk\dist\rigclient-commons.jar
    
    SetOutPath $INSTDIR\conf\conf.example
    File ..\RigClientCommons\trunk\conf\conf.example\CDUPower.properties
    File ..\RigClientCommons\trunk\conf\conf.example\DeleteFilesResetAction.properties
    File ..\RigClientCommons\trunk\conf\conf.example\DeviceOwnershipAccessAction.properties
    File ..\RigClientCommons\trunk\conf\conf.example\IPSPowerResetAction.properties
    File ..\RigClientCommons\trunk\conf\conf.example\JPEGCameraTest.properties
    File ..\RigClientCommons\trunk\conf\conf.example\LabjackResetAction.properties
    File ..\RigClientCommons\trunk\conf\conf.example\LDAP.properties
    File ..\RigClientCommons\trunk\conf\conf.example\LdapGroupAccessAction.properties
    File ..\RigClientCommons\trunk\conf\conf.example\LinuxDeviceNodeTestAction.properties
    File ..\RigClientCommons\trunk\conf\conf.example\PingTestAction.properties
    File ..\RigClientCommons\trunk\conf\conf.example\RemoteDesktopAccess.properties
    File ..\RigClientCommons\trunk\conf\conf.example\SocketCommandResetAction.properties
    File ..\RigClientCommons\trunk\conf\conf.example\SocketPassThroughController.properties
    File ..\RigClientCommons\trunk\conf\conf.example\WTSDetectorAction.properties
SectionEnd
SectionGroupEnd     
;--------------------------------

Function CheckSlectedComponents
    ${IfNot} ${SectionIsSelected} ${RigClientGroup}
        ${IfNot} ${SectionIsPartiallySelected} ${RigClientGroup} 
            MessageBox MB_ICONEXCLAMATION|MB_OK "Please select a component for installation"
            Abort
        ${EndIf}
    ${EndIf}
FunctionEnd

Function .onSelChange
    ${IfNot} ${SectionIsSelected} ${RigClient}
        ; If Rig Client is not selected, check Commons
        call CheckRCVersion
        ${If} $RCAlreadyInstalled S!= "2"
            ; Rig Client is not installed. Disable Commons
            !insertmacro disableSection ${Commons}
        ${EndIf}
    ${Else}
        !insertmacro ClearSectionFlag ${Commons} ${SF_RO}
    ${EndIf}    
FunctionEnd

;--------------------------------

; Post install actions
Function .onInstSuccess
	ClearErrors
	EnumRegKey $2 HKLM  "${REGKEY}" 0
	ifErrors installEnd createRegKey 
	createRegKey:
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" "DisplayName"  "$(^Name)"
	WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" "DisplayVersion" "${VERSION}"
	WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" "InstallLocation" "$INSTDIR"
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" "UninstallString" "$\"$INSTDIR\uninstallSaharaRigClient.exe$\""
	WriteRegDWORD HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" "NoModify" 1
	WriteRegDWORD HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" "NoRepair" 1
	;${If} ${SectionIsSelected} ${RigClient}
	;	MessageBox MB_OK "Postinstall actions: $\n$\nUpdate the configuration file $INSTDIR\RigClient\config\rigclient.properties $\n$\nGo to the Windows Control Panel->Administrative Tools->Services and start the RigClient service"
	;${EndIf}
installEnd:
FunctionEnd

;--------------------------------
; Create uninstaller
Section -createUninstaller
	ClearErrors
	EnumRegKey $1 HKLM  "${REGKEY}" 0
	ifErrors 0 createUninstaller
	MessageBox MB_OK|MB_ICONSTOP  "No component selected for installation. Aborting the installation"
	Abort
	createUninstaller:
    WriteRegStr HKLM "${REGKEY}" Path $INSTDIR
	SetOutPath $INSTDIR
	WriteUninstaller $INSTDIR\uninstallSaharaRigClient.exe
SectionEnd


;--------------------------------
; Uninstall Rig Client. 
SectionGroup /e "un.Sahara Rig Client" un.RigClientGroup
Section "un.Rig Client" un.RigClient 
    Push $R1
	!insertmacro checkAdminUser "uninstallation"
	!insertmacro uninstallWindowsService "uninstallation"
    ReadRegStr $R1 HKLM "${REGKEY}" "Path"
	;Delete the component files/directories
	Delete $R1\rigclient.jar
    Delete $R1\rigclient_service.ini
    
    ; Delete individual config files and conf.d directory
    Delete $R1\conf\conf.d\logging.properties
    Delete $R1\conf\conf.d\rigattributes.properties
    RMDir $R1\conf\conf.d
    Delete $R1\conf\conf.example\batch.properties
    Delete $R1\conf\conf.example\configuredrig.properties
    Delete $R1\conf\conf.example\primitive.properties
    Delete $R1\conf\rigclient.properties
    Delete $R1\conf\batch.properties

    ; If RC was upgraded from 2.x, the config directory will be present
    Delete $R1\config\batch.properties
    Delete $R1\config\rigclient.properties
    RMDir $R1\config
   
	Delete $R1\rigclientservice*
    DeleteRegKey /IfEmpty HKLM "${REGKEY}"
    Pop $R1
SectionEnd ; end the section
Section "un.Rig Client Commons" un.Commons
    Delete $INSTDIR\lib\rigclient-commons.jar
    
    
    ; Delete individual config files
    Delete $INSTDIR\conf\conf.example\CDUPower.properties
    Delete $INSTDIR\conf\conf.example\DeleteFilesResetAction.properties
    Delete $INSTDIR\conf\conf.example\DeviceOwnershipAccessAction.properties
    Delete $INSTDIR\conf\conf.example\IPSPowerResetAction.properties
    Delete $INSTDIR\conf\conf.example\JPEGCameraTest.properties
    Delete $INSTDIR\conf\conf.example\LabjackResetAction.properties
    Delete $INSTDIR\conf\conf.example\LDAP.properties
    Delete $INSTDIR\conf\conf.example\LdapGroupAccessAction.properties
    Delete $INSTDIR\conf\conf.example\LinuxDeviceNodeTestAction.properties
    Delete $INSTDIR\conf\conf.example\PingTestAction.properties
    Delete $INSTDIR\conf\conf.example\RemoteDesktopAccess.properties
    Delete $INSTDIR\conf\conf.example\SocketCommandResetAction.properties
    Delete $INSTDIR\conf\conf.example\SocketPassThroughController.properties
    Delete $INSTDIR\conf\conf.example\WTSDetectorAction.properties
    
SectionEnd
SectionGroupEnd


;--------------------------------
; Uninstaller functions
Function un.onInit
    StrCpy $commonsDisabled "false"
	; Get the Rig Client path
	ReadRegStr $INSTDIR HKLM "${REGKEY}" "Path"
    ; Check if the components are installed and enable only the installed components for uninstallation
    ${If} $INSTDIR S== ""
            !insertmacro disableSection ${un.RigClient}
    ${EndIf}
    ; As the Commons component does not have an entry in the registry, the jar file
    ; is checked to see if the component is installed. 
    ${IfNot} ${FileExists} "$INSTDIR\lib\rigclient-commons.jar"
            !insertmacro disableSection ${un.Commons}
            StrCpy $commonsDisabled "true"
    ${EndIf}
	StrCpy $DisplayText ""
	StrCpy $NoSectionSelectedUninstall "true"

FunctionEnd

Function un.CheckSlectedComponents
    StrCpy $DisplayText ""
    ; Check if Rig Client is selected for uninstallation. 
    ${If} ${SectionIsSelected} ${un.RigClient} 
        ; Rig Client is selected
        StrCpy $DisplayText "$DisplayText$\nRig Client"
        StrCpy $NoSectionSelectedUninstall "false"
    ${EndIf}
    ${If} ${SectionIsSelected} ${un.Commons} 
        ; Rig Client is selected
        StrCpy $DisplayText "$DisplayText$\nRig Client Commons"
        StrCpy $NoSectionSelectedUninstall "false"
    ${EndIf}


    ${If} $NoSectionSelectedUninstall S== "false"
        StrCpy $DisplayText "Following sections are selected for uninstallation. Do you want to continue?$DisplayText" 
        MessageBox MB_YESNO "$DisplayText" IDYES selectionEnd
        StrCpy $NoSectionSelectedUninstall "true"
        Abort
    ${Else}
        MessageBox MB_OK "No component selected"
        StrCpy $NoSectionSelectedUninstall "true"
        Abort
    ${EndIf}
selectionEnd:
FunctionEnd

Function un.onSelChange
${If} $commonsDisabled S== "false"
    ${If} ${SectionIsSelected} ${un.RigClient}
        Push $R0
        IntOp $R0 ${SF_SELECTED} | ${SF_RO}
        SectionSetFlags ${un.Commons} $R0
        Pop $R0
    ${Else}
        !insertmacro ClearSectionFlag ${un.Commons} ${SF_RO}
    ${EndIf}    
${EndIf}
FunctionEnd

Section -un.postactions 
    Push $R0
    ; Check if all the components are deleted. If they are, delete the main installation directory and uninstaller
    ClearErrors
    ReadRegStr $R0 HKLM "${REGKEY}" "Path"
    ${If} $R0 S== ""
        DeleteRegKey /IfEmpty HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)"
        Delete $INSTDIR\uninstallSaharaRigClient.exe
        ClearErrors
        ; These directories need to be deleted in post actions as some of the files
        ; in those directories are deleted in RigClient Commons which is uninstalled after
        ; the RigClient
        RMDir $INSTDIR\lib
        RMDir $INSTDIR\conf\conf.example
        RMDir $INSTDIR\conf
        RMDir $INSTDIR
        ifErrors 0 InstDirDeleted
        MessageBox MB_ABORTRETRYIGNORE "Error in deleting the directory $INSTDIR." IDRETRY RetryDelete IDIGNORE InstDirDeleted
        Abort
        RetryDelete:
        RMDir $INSTDIR
        ifErrors 0 InstDirDeleted
        MessageBox MB_OK "Error in deleting the directory $INSTDIR. Directory $INSTDIR will not be deleted."
        InstDirDeleted:
    ${EndIf}
    Pop $R0
SectionEnd

;--------------------------------


;Descriptions

; Language strings
LangString DESC_SecRC ${LANG_ENGLISH} "Sahara Rig Client."
LangString DESC_SecCommons ${LANG_ENGLISH} "Sahara Rig Client Commons. $\nThis component requires Rig Client component already installed or selected for installation."
LangString DESC_UnSecCommons ${LANG_ENGLISH} "Sahara Rig Client Commons. $\nIf this component is installed, it will be selected automatically for uninstallation when Rig Client is selected "


; Assign language strings to sections
!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
!insertmacro MUI_DESCRIPTION_TEXT ${RigClient} $(DESC_SecRC)
!insertmacro MUI_DESCRIPTION_TEXT ${Commons} $(DESC_SecCommons)
!insertmacro MUI_FUNCTION_DESCRIPTION_END

!insertmacro MUI_UNFUNCTION_DESCRIPTION_BEGIN
!insertmacro MUI_DESCRIPTION_TEXT ${un.RigClient} $(DESC_SecRC)
!insertmacro MUI_DESCRIPTION_TEXT ${un.Commons} $(DESC_UnSecCommons)
!insertmacro MUI_UNFUNCTION_DESCRIPTION_END

;--------------------------------

