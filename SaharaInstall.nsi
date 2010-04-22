/**
 * SAHARA Installer
 * 
 * Sahara installer/uninstallaer script for Windows.  
 
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
!define RCCLIName "Rig Client CLI"
!define RCName "Rig Client"

!define REGKEY "SOFTWARE\$(^Name)"

; Sahara Rig Client Version
!define Version "1.0"

!define JREVersion "1.6"

; The file to write
OutFile "SaharaRigClient.exe"

; The default installation directory
InstallDir "C:\Program Files\Sahara"

BrandingText "$(^Name)"
WindowIcon off
XPStyle on
Var skipSection
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

;--------------------------------
;Languages
 
!insertmacro MUI_LANGUAGE "English"

;--------------------------------

Var DisplayText
Var NoSectionSelectedUninstall 
Var RCAlreadyInstalled ;0=Not installed, 1=Installed but different version, 2=installed and same version 


Function DirectoryPagePre
	; If there is already an installation of Sahara Rig Client, use the same folder for this installation. Else let the user select the installation folder
 	${If} $RCAlreadyInstalled S== "0"
		StrCpy $DirHeaderText "Choose Install Location"
		StrCpy $DirHeaderSubText "Choose the folder in which to install $(^Name)"
	${ElseIf} $RCAlreadyInstalled S== "2" 
		StrCpy $DirHeaderText "Using existing $(^Name) installation folder"
		StrCpy $DirHeaderSubText "$(^Name) is already installed on this machine. Installer will overwrite the existing installation"
		ReadRegStr $R0 HKLM "${REGKEY}" "Path"
		StrCpy $INSTDIR $R0
	${EndIf}

FunctionEnd

Function CheckRCVersion
    Push $R0
	ReadRegStr $R0 HKLM "${REGKEY}" "CurrentVersion"
	${If} $R0 S== ""
		StrCpy $RCAlreadyInstalled "0"
	${ElseIf} $R0 S!=  ${Version} 
		StrCpy $RCAlreadyInstalled "1"
	${Else}
		StrCpy $RCAlreadyInstalled "2"
	${EndIf}
    Pop $R0
FunctionEnd


Function DirectoryPageShow

	; If there is already an installation of Rig Client, disable the destination folder selection and use the same folder for this installation. 
	; Else let the user select the installation folder
	
	${If} $RCAlreadyInstalled S== "2"
		FindWindow $R0 "#32770" "" $HWNDPARENT
		GetDlgItem $R1 $R0 1019
		SendMessage $R1 ${EM_SETREADONLY} 1 0
		EnableWindow $R1 0
		GetDlgItem $R1 $R0 1001
		EnableWindow $R1 0
	${EndIf}
FunctionEnd

Function SetInstallDir
	${If} $RCAlreadyInstalled S== "0"
		StrCpy $INSTDIR "$INSTDIR\RigClient"
	${EndIf}
FunctionEnd

Function .onInit
	; Splash screen 
	advsplash::show 1000 1000 1000 -1 labshare
 	StrCpy $skipSection "false"
 	StrCpy $RCAlreadyInstalled "-1"
	call CheckRCVersion
	${If} $RCAlreadyInstalled S== "1"
		MessageBox MB_OK|MB_ICONSTOP "A different version of Sahara is already installed on this machine. $\nPlease uninstall the existing Sahara software before continuing the installation"
		Abort 
	${EndIf}

FunctionEnd

Function checkJREVersion
	; Check the JRE version to be 1.6 or higher
	ReadRegStr $0 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" CurrentVersion 
	${If} $0 S< ${JREVersion} 
		MessageBox MB_OK "Rig Client needs JRE version ${JREVersion} or higher. It is currently $0. Aborting the installation."
		Abort ; causes installer to quit.
	${EndIf}
FunctionEnd

; Check if RigClient service is running
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
	MessageBox MB_OK "'${Sahara_RCWindows_Service}' service is already installed.  please stop the '${Sahara_RCWindows_Service}' service if it is running (Windows Control Panel->Administrative Tools->Services) $\n$\nUse rigclientservice.exe to uninstall the previous version (rigclientservice.exe uninstall)"
	Abort
	Error:
	MessageBox MB_OK "Error is detecting if '${Sahara_RCWindows_Service}' service is installed"
	Abort
	Found:
FunctionEnd

; Check if the installation should continue if Rig Client is not selected and/or not installed already
!macro checkIfRCInstalledOrSelected thisSection
        ${IfNot} ${SectionIsSelected} ${RigClient}
        ClearErrors
        ; Compare the registry version of RigClient with the current version
        ReadRegStr $1 HKLM "${REGKEY}\RigClient" CurrentVersion
        ifErrors RCNotInstalled 0
        ${If} $1 S== ${Version} 
            ; Section RigClient is not selected in this installation but same version of RigClient is already installed
            Goto EndMacro
        ${Else}
            MessageBox MB_OK|MB_ICONSTOP  "Version of Rig Client installed on this machine is different than the current version of ${thisSection}.$\nSkipping ${thisSection} installation. $\n$\nPlease select Rig Client in the installation or install Rig Client version ${Version} separately and retry ${thisSection} installation"
            StrCpy $skipSection "true"
            Goto EndMacro
        ${EndIf}
        RCNotInstalled:
        MessageBox MB_OK|MB_ICONSTOP  "RigClient is not installed.$\nSkipping ${thisSection} installation. $\n$\nPlease select the RigClient in the installation or install Rig Client version ${Version} separately and retry ${thisSection} installation" 
        StrCpy $skipSection "true"
    ${EndIf}
EndMacro:
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
	MessageBox MB_OK "$(^Name) ${operation} requires administrative privileges"
	Abort
	AdminUser:
!macroend

; Installer group. It contains following sub sections -
; 1. Rig Client
; 2. Rig Client CLI

;--------------------------------
; Install Rig Client

Section "Sahara Rig Client" RigClient

    !insertmacro checkAdminUser "installation"
	call checkJREVersion	
	call checkIfServiceInstalled 
	
	; Set output path to the installation directory
    SetOutPath $INSTDIR
  
	; Copy the component files/directories
	File dist\rigclient.jar
	File /r /x *.svn  config
	File /r /x *.svn interface
	File servicewrapper\WindowsServiceWrapper\Release\rigclientservice.exe
    
    ;File ..\RigClientCLI\builds\rigclientcli.jar

	; Add the RigClient service to the windows services
	ExecWait '"$INSTDIR\rigclientservice" install'
	ifErrors 0 WinServiceNoError
	MessageBox MB_OK "Error in executing rigclientservice.exe"
	; TODO  Revert back the installed RC in case of error?
	Abort
	WinServiceNoError:
    WriteRegStr HKLM "${REGKEY}\${RCName}" Path $INSTDIR
    WriteRegStr HKLM "${REGKEY}\${RCName}" CurrentVersion  ${Version}

SectionEnd 

;--------------------------------
; Install Rig Client CLI
Section "Sahara Rig Client CLI" RCCLI

    !insertmacro checkIfRCInstalledOrSelected "Rig Client CLI"
    ${IfNot} $skipSection S== "true"
        ; Set output path to the installation directory.
        SetOutPath $INSTDIR
        
        ; Copy the component files/directories
        File ..\RigClientCLI\builds\rigclientcli.jar
        
        WriteRegStr HKLM "${REGKEY}\${RCCLIName}" Path $INSTDIR
        WriteRegStr HKLM "${REGKEY}\${RCCLIName}" CurrentVersion  ${Version}
    ${Else}
        StrCpy $skipSection "false"
    ${EndIf}
SectionEnd 

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
	WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" "Comments" "Uninstaller can be run directly by executing $\"$INSTDIR\uninstallSaharaRigClient.exe$\""
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
; Uninstall Rig Client. It contains following sub sections -

; Uninstall Rig Client CLI
Section "un.Sahara Rig Client CLI" un.RCCLI
    ; Delete the component files/directories
    ReadRegStr $2 HKLM "${REGKEY}\${RCCLIName}" "Path"
    Delete $2\rigclientcli.jar
    DeleteRegKey /IfEmpty HKLM "${REGKEY}\${RCCLIName}"
SectionEnd 

Section "un.Sahara Rig Client" un.RigClient 
    Push $R1
	!insertmacro checkAdminUser "uninstallation"
	; Remove the RigClient service from the windows services
	ReadRegStr $R1 HKLM "${REGKEY}" "Path"
	ClearErrors
	ExecWait '"$R1\rigclientservice" uninstall'
	ifErrors 0 WinServiceNoError
	MessageBox MB_ABORTRETRYIGNORE "Error in uninstalling RigClient service.  $\n$\nIf the service is installed, manually uninstall the service from command prompt using: '$R1\rigclientservice uninstall' as admin and press 'Retry'. $\nIf the service is already uninstalled, press 'Ignore'. $\nPress 'Abort' to end the uninstallation" IDABORT AbortUninstall IDIGNORE WinServiceNoError 
	;TryAgain
	ExecWait '$R1\rigclientservice uninstall'
	ifErrors 0 WinServiceNoError
	MessageBox MB_OK "Error in uninstalling RigClient service again. Aborting the uninstallation"
	AbortUninstall:
	Abort
	WinServiceNoError:
	;Delete the component files/directories
	Delete $R1\rigclient.jar
	RMDir /r $R1\config
	RMDir /r $R1\interface
	Delete $R1\rigclientservice*
    ;Delete $R1\rigclientcli.jar
	DeleteRegKey /IfEmpty HKLM "${REGKEY}\${RCName}"
    Pop $R1
SectionEnd ; end the section



;--------------------------------
; Uninstaller functions
Function un.onInit

	; Get the Rig Client path
	ReadRegStr $INSTDIR HKLM "${REGKEY}" "Path"

	; Check if the components are installed and enable only the installed components for uninstallation
	Push $R0
	ReadRegStr $R0 HKLM "${REGKEY}\${RCName}" "Path"
	${If} $R0 S== ""
	      	!insertmacro disableSection ${un.RigClient}
	${EndIf}
    
    ReadRegStr $R0 HKLM "${REGKEY}\${RCCLIName}" "Path"
    ${If} $R0 S== ""
            !insertmacro disableSection ${un.RCCLI}
    ${EndIf}
    
	StrCpy $DisplayText ""
	StrCpy $NoSectionSelectedUninstall "true"

	Pop $R0

FunctionEnd

Function un.CheckSlectedComponents
    Var /GLOBAL SelectRCCLI
    StrCpy $SelectRCCLI "false"
    StrCpy $DisplayText ""
    ; Check if Rig Client is selected for uninstallation. If it is, Rig Client should also be selected
    Push $0
    ${If} ${SectionIsSelected} ${un.RigClient} 
        ; Rig Client is selected
        StrCpy $DisplayText "$DisplayText$\nRig Client"
        ${IfNot} ${SectionIsSelected} ${un.RCCLI}
            ReadRegStr $R0 HKLM "${REGKEY}\RigClientCLI" "Path"
            ${If} $R0 S!= ""
            StrCpy $DisplayText "$DisplayText$\n(Rig Client CLI will also be selected for uninstallation as it depends on Rig Client)"
            StrCpy $SelectRCCLI "true"
            SectionGetFlags ${un.RCCLI} $0
            IntOp $0 $0 | ${SF_SELECTED}
            SectionSetFlags ${un.RCCLI} $0
            ${EndIf}
        ${EndIf}
        StrCpy $NoSectionSelectedUninstall "false"
    ${EndIf}

    ${If} ${SectionIsSelected} ${un.RCCLI}
        StrCpy $DisplayText "$DisplayText$\nRig Client CLI"
        StrCpy $NoSectionSelectedUninstall "false"
    ${EndIf}

    ${If} $NoSectionSelectedUninstall S== "false"
        StrCpy $DisplayText "Following sections are selected for uninstallation. Do you want to continue?$DisplayText" 
        MessageBox MB_YESNO "$DisplayText" IDYES selectionEnd
        Abort
    ${Else}
        MessageBox MB_OK "No compoennts selected"
    ${EndIf}
selectionEnd:
FunctionEnd

Section -un.postactions 
	
    ; Delete the main registry entry for Rig Client 
    ClearErrors
    DeleteRegKey /IfEmpty HKLM "${REGKEY}"

    ; Check if all the components are deleted. If they are, delete the main installation directory and uninstaller
    ClearErrors
    ReadRegStr $R0 HKLM "${REGKEY}" "Path"
    ${If} $R0 S== ""
        DeleteRegKey /IfEmpty HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)"
        Delete $INSTDIR\uninstallSaharaRigClient.exe
        ClearErrors
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
 
SectionEnd

;--------------------------------


;Descriptions

; Language strings
LangString DESC_SecRC ${LANG_ENGLISH} "Sahara Rig Client"
LangString DESC_SecRCCLI ${LANG_ENGLISH} "Sahara Rig Client Command Line Interface"



; Assign language strings to sections
!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
!insertmacro MUI_DESCRIPTION_TEXT ${RigClient} $(DESC_SecRC)
!insertmacro MUI_DESCRIPTION_TEXT ${RCCLI} $(DESC_SecRCCLI)
!insertmacro MUI_FUNCTION_DESCRIPTION_END

!insertmacro MUI_UNFUNCTION_DESCRIPTION_BEGIN
!insertmacro MUI_DESCRIPTION_TEXT ${un.RigClient} $(DESC_SecRC)
!insertmacro MUI_DESCRIPTION_TEXT ${un.RCCLI} $(DESC_SecRCCLI)
!insertmacro MUI_UNFUNCTION_DESCRIPTION_END

;--------------------------------

