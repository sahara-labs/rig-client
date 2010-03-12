; SaharaInstall.nsi
;
; This script installs Sahara components on the machine. 
; It performas following actions:
; 1. Display Welcome page
; 2. Display License page
; 3. Display the page containing a list of components and allow user to select the components
; 4. Display the page for user to select the installation directory
; 5. Check if the user has admin rights
; 6. Install the selected components
;    a. Check the JRE version to be equal to or higher than 1.6 (Rig Client installation only)
;    b. Copy the component files/directories to installation directory
;    c. Add the RigClient service to the windows services  (Rig Client installation only)
;    d. Start the RigClient service

;--------------------------------
!include MUI2.nsh
!include TextFunc.nsh
!include nsDialogs.nsh
!include LogicLib.nsh



; The name of the installer
Name "Sahara Rig Client"

; The file to write
OutFile "SaharaRigClient.exe"

; The default installation directory
InstallDir "C:\Program Files\Sahara"
;InstallDir "D:\Sahara_RigClient"

; Request application privileges for Windows Vista
;RequestExecutionLevel admin

;AddBrandingImage left 100

BrandingText "Sahara Rig Client"
WindowIcon off

; Sahara Rig Client Version
VIProductVersion "1.0"

;--------------------------------
;Interface Settings
 
!define MUI_HEADERIMAGE
!define MUI_HEADERIMAGE_BITMAP "labshare.bmp"
!define MUI_ICON "labshare.ico"
!define MUI_ABORTWARNING
  
!define JRE_VERSION "1.6"


;--------------------------------

; Pages
;
!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_LICENSE "License"
!insertmacro MUI_PAGE_COMPONENTS
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH

!insertmacro ConfigWrite

;--------------------------------
;Languages
 
!insertmacro MUI_LANGUAGE "English"

;--------------------------------

Function .onInstSuccess
	MessageBox MB_OK "Postinstall actions: $\n$\nUpdate the configuration file $INSTDIR\RigClient\config\rigclient.properties $\n$\nGo to the Windows Control Panel->Administrative Tools->Services and start the RigClient service"
FunctionEnd


Function checkJREVersion

	ReadRegStr $0 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" CurrentVersion 
	StrCpy $1 $0 "" -1
	IntCmp $1 6 VersionOK VersionNotOK VersionOK 
	VersionNotOK:
	MessageBox MB_OK "Rig Client needs JRE version 1.6 or higher. It is currently $0. Aborting the installation."
	Abort ; causes installer to quit.
	VersionOK:
FunctionEnd

; check if the user has admin rights
Section

	userInfo::getAccountType
	pop $0
	strCmp $0 "Admin" AdminUser
	MessageBox MB_OK "Rig Client installation requires administrative privileges"
	Abort
	AdminUser:
SectionEnd

; Install Rig Client
Section "Sahara Rig Client Installation" RigClient 

	call checkJREVersion	; check the JRE version
  	
	MessageBox MB_OK "If Sahara Rig Client is already installed on the machine, please stop the RigClient service if it is running (Windows Control Panel->Administrative Tools->Services) $\n$\nUse rigclientservice.exe to uninstall the previous version (rigclientservice.exe uninstall)"
	; Set output path to the installation directory.
	SetOutPath $INSTDIR\RigClient
  
	; Copy the component files/directories
	File ..\product\Built\rigclient.jar
	File /r ..\product\Built\config
	File /r ..\product\Built\interface
	File ..\product\Built\rigclientservice.exe

	; Add the RigClient service to the windows services
	ExecWait '$INSTDIR\RigClient\rigclientservice.exe install'
	ifErrors 0 WinServiceNoError
	MessageBox MB_OK "Error in executing rigclientservice.exe"
	Abort
	WinServiceNoError:
SectionEnd ; end the section

;--------------------------------
; Install Rig Client CLI
Section "Sahara Rig Client CLI Installation" RCCLI

	; Set output path to the installation directory.
	SetOutPath $INSTDIR\RigClient
  
	; Copy the component files/directories
	File ..\product\Built\rigclientcli.jar
  
SectionEnd ; end the section


;--------------------------------

; Install Rig Client source code
Section "Sahara Rig Client source code" RCSource

	; Set output path to the installation directory.
	SetOutPath $INSTDIR\RigClientSource
  
	; Copy the component files/directories
	File /r ..\product\RigClient\*.*
  
SectionEnd ; end the section

;--------------------------------

;Descriptions

; Language strings
LangString DESC_SecRC ${LANG_ENGLISH} "Sahara Rig Client"
LangString DESC_SecRCCLI ${LANG_ENGLISH} "Sahara Rig Client Command Line Interface"
LangString DESC_SecRCSource ${LANG_ENGLISH} "Sahara Rig Client source code"


; Assign language strings to sections
!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
!insertmacro MUI_DESCRIPTION_TEXT ${RigClient} $(DESC_SecRC)
!insertmacro MUI_DESCRIPTION_TEXT ${RCCLI} $(DESC_SecRCCLI)
!insertmacro MUI_DESCRIPTION_TEXT ${RCSOurce} $(DESC_SecRCSource)
!insertmacro MUI_FUNCTION_DESCRIPTION_END


