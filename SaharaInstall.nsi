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
Name "Sahara"

!define REGKEY "SOFTWARE\$(^Name)"

; Sahara Rig Client Version
!define Version "1.0"

!define JREVersion "1.6"

; The file to write
OutFile "SaharaRigClient.exe"

; The default installation directory
InstallDir "C:\Program Files\"

BrandingText "$(^Name)"
WindowIcon off
XPStyle on
Var skipSection
;--------------------------------
;Interface Settings
 
!define MUI_HEADERIMAGE
!define MUI_HEADERIMAGE_BITMAP "labshare.bmp"
!define MUI_ICON "labshare.ico"
!define MUI_ABORTWARNING
  
!define MUI_UNICON win-install.ico

!define Sahara_Windows_Service "RigClient"

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
Var SaharaAlreadyInstalled ;0=Not installed, 1=Installed but different version, 2=installed and same version 

Function DirectoryPagePre
	; If there is already an installation of Sahara, use the same folder for this installation. Else let the user select the installation folder
 	${If} $SaharaAlreadyInstalled S== "0"
		StrCpy $DirHeaderText "Choose Install Location"
		StrCpy $DirHeaderSubText "Choose the folder in which to install $(^Name)"
	${ElseIf} $SaharaAlreadyInstalled S== "2" 
		StrCpy $DirHeaderText "Using existing Sahara installation forlder"
		StrCpy $DirHeaderSubText "One or more components of $(^Name) are already installed on this machine. Installer will use same destination folder"
		StrCpy $INSTDIR $R0
	${EndIf}

FunctionEnd

Function CheckSaharaVersion
	ReadRegStr $R0 HKLM "${REGKEY}" "CurrentVersion"
	${If} $R0 S== ""
		StrCpy $SaharaAlreadyInstalled "0"
	${ElseIf} $R0 S!=  ${Version} 
		StrCpy $SaharaAlreadyInstalled "1"
	${Else}
		StrCpy $SaharaAlreadyInstalled "2"
	${EndIf}
FunctionEnd


Function DirectoryPageShow

	; If there is already an installation of Sahara, disable the destination folder selection and use the same folder for this installation. 
	; Else let the user select the installation folder
	ReadRegStr $R0 HKLM "${REGKEY}" "CurrentVersion"
	${If} $R0 S!= ""
		StrCpy $INSTDIR $R0
		; Disable the page
		FindWindow $R0 "#32770" "" $HWNDPARENT
		GetDlgItem $R1 $R0 1019
		SendMessage $R1 ${EM_SETREADONLY} 1 0
		EnableWindow $R1 0
		GetDlgItem $R1 $R0 1001
		EnableWindow $R1 0
	${EndIf}
FunctionEnd

Function SetInstallDir
	StrCpy $INSTDIR "$INSTDIR\Sahara"
	MessageBox MB_OK "Inst dir is $INSTDIR"
FunctionEnd

Function .onInit
	; Splash screen 
	advsplash::show 1000 1000 1000 -1 labshare
 	StrCpy $skipSection "false"
 	StrCpy $SaharaAlreadyInstalled "-1"
	call CheckSaharaVersion
	${If} $SaharaAlreadyInstalled S== "1"
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
	
	nsExec::ExecToStack /OEM '"sc" query ${Sahara_Windows_Service}'
	Pop $0	; $0 contains return value/error/timeout
	Pop $1	; $1 contains printed text, up to ${NSIS_MAX_STRLEN}

	ClearErrors
	${WordReplace} '$1' 'FAILED' 'FAILED' 'E+1' $R0
	IfErrors 0 Found
	StrCmp $R0 '1' 0 Error
	MessageBox MB_OK "RigClient service is already installed.  please stop the RigClient service if it is running (Windows Control Panel->Administrative Tools->Services) $\n$\nUse rigclientservice.exe to uninstall the previous version (rigclientservice.exe uninstall)"
	Abort
	Error:
	MessageBox MB_OK "Error is detecting if RigClient service is installed"
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
			MessageBox MB_OK|MB_ICONSTOP  "Version of Rig Client installed on this machine is different that the current version of ${thisSection}.$\nSkipping ${thisSection} installation. $\n$\nPlease select Rig Client in the installation or install Rig Client version ${Version} separately and retry ${thisSection} installation"
			;Abort
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
	MessageBox MB_OK "Rig Client ${operation} requires administrative privileges"
	Abort
	AdminUser:
!macroend

; Installer group. It contains following sub sections -
; 1. Admin check
; 2. Rig Client
; 3. Rig Client CLI
SectionGroup /e "Sahara Rig Client" RigClientGrp

Section 
	!insertmacro checkAdminUser "installation"
SectionEnd

;--------------------------------
; Install Rig Client

Section "Sahara Rig Client" RigClient

	call checkJREVersion	
	call checkIfServiceInstalled 
	
	; Set output path to the installation directory
	SetOutPath $INSTDIR\RigClient
  
	; Copy the component files/directories
	File Built\rigclient.jar
	File /r Built\config
	File /r Built\interface
	File Built\rigclientservice.exe

	; Add the RigClient service to the windows services
	ExecWait '"$INSTDIR\RigClient\rigclientservice" install'
	ifErrors 0 WinServiceNoError
	MessageBox MB_OK "Error in executing rigclientservice.exe"
	; TODO  Revert back the installed RC in case of error?
	Abort
	WinServiceNoError:
	WriteRegStr HKLM "${REGKEY}\RigClient" Path $INSTDIR\RigClient
	WriteRegStr HKLM "${REGKEY}\RigClient" CurrentVersion  ${Version}
SectionEnd 

;--------------------------------
; Install Rig Client CLI
Section "Sahara Rig Client CLI" RCCLI

	!insertmacro checkIfRCInstalledOrSelected "Rig Client CLI"
	${IfNot} $skipSection S== "true"
		; Set output path to the installation directory.
		SetOutPath $INSTDIR\RigClient
  		
		; Copy the component files/directories
		File Built\rigclientcli.jar
		WriteRegStr HKLM "${REGKEY}\RigClientCLI" Path $INSTDIR\RigClient
		WriteRegStr HKLM "${REGKEY}\RigClientCLI" CurrentVersion  ${Version}
	${Else}
		StrCpy $skipSection "false"
	${EndIf}
SectionEnd 

SectionGroupEnd

;--------------------------------
; Install Rig Client source code
Section "Sahara Rig Client source code" RCSource

	; Set output path to the installation directory.
	SetOutPath $INSTDIR\RigClientSource
  
	; Copy the component files/directories
	File /r RigClient\*.*
	WriteRegStr HKLM "${REGKEY}\RigClientSource" Path $INSTDIR\RigClientSource
	WriteRegStr HKLM "${REGKEY}\RigClientSource" CurrentVersion  ${Version}
  
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
	${If} ${SectionIsSelected} ${RigClient}
		MessageBox MB_OK "Postinstall actions: $\n$\nUpdate the configuration file $INSTDIR\RigClient\config\rigclient.properties $\n$\nGo to the Windows Control Panel->Administrative Tools->Services and start the RigClient service"
	${EndIf}
installEnd:
FunctionEnd

;--------------------------------
; Create uninstaller
Section -createUninstaller
	ClearErrors
	EnumRegKey $1 HKLM  "${REGKEY}" 0
	ifErrors 0 createUninstaller
	MessageBox MB_OK|MB_ICONSTOP  "No component selected for installation. Aborting the installation"
	ABort
	createUninstaller:
	WriteRegStr HKLM "${REGKEY}" Path $INSTDIR
	WriteRegStr HKLM "${REGKEY}" CurrentVersion  ${Version}
	SetOutPath $INSTDIR
	WriteUninstaller $INSTDIR\uninstallSaharaRigClient.exe
SectionEnd


;--------------------------------
; Uninstaller group. It contains following sub sections -
; 1. Admin check
; 2. Rig Client
; 3. Rig Client CLI
SectionGroup /e "un.Sahara Rig Client" un.RigClientGrp 

; Uninstall Rig Client CLI
Section "un.Sahara Rig Client CLI" un.RCCLI
	; Delete the component files/directories
	ReadRegStr $2 HKLM "${REGKEY}\RigClientCLI" "Path"
	Delete $2\rigclientcli.jar
	DeleteRegKey /IfEmpty HKLM "${REGKEY}\RigClientCLI"
SectionEnd 

Section "un.Sahara Rig Client" un.RigClient 
	!insertmacro checkAdminUser "uninstallation"
	; Remove the RigClient service from the windows services
	ReadRegStr $2 HKLM "${REGKEY}\RigClient" "Path"
	ClearErrors
	ExecWait '"$2\rigclientservice" uninstall'
	ifErrors 0 WinServiceNoError
	MessageBox MB_ABORTRETRYIGNORE "Error in uninstalling RigClient service.  $\n$\nIf the service is installed, manually uninstall the service from command prompt using: '$2\rigclientservice uninstall' as admin and press 'Retry'. $\nIf the service is already uninstalled, press 'Ignore'. $\nPress 'Abort' to end the uninstallation" IDABORT AbortUninstall IDIGNORE WinServiceNoError 
	;TryAgain
	ExecWait '$2\rigclientservice uninstall'
	ifErrors 0 WinServiceNoError
	MessageBox MB_OK "Error in uninstalling RigClient service again. Aborting the uninstallation"
	AbortUninstall:
	Abort
	WinServiceNoError:
	;Delete the component files/directories
	Delete $2\rigclient.jar
	RMDir /r $2\config
	RMDir /r $2\interface
	Delete $2\rigclientservice*

	ClearErrors
	RMDir $2
	ifErrors 0 RCDeleted
	MessageBox MB_ABORTRETRYIGNORE "Error in deleting the directory $2." IDRETRY RetryDelete IDIGNORE RCDeleted
	Abort
	RetryDelete:
	RMDir $2
	ifErrors 0 RCDeleted
	MessageBox MB_OK "Error in deleting the directory $2 again. Please delete the directory manually"
	RCDeleted:
	DeleteRegKey /IfEmpty HKLM "${REGKEY}\RigClient"
SectionEnd ; end the section

SectionGroupEnd


;--------------------------------
; Uninstall Rig Client source code

Section "un.Sahara Rig Client source code" un.RCSource
	ReadRegStr $2 HKLM "${REGKEY}\RigClientSource" "Path"
	RMDir /r $2\*.*
	DeleteRegKey /IfEmpty HKLM "${REGKEY}\RigClientSource"
SectionEnd 

;--------------------------------
; Uninstaller functions
Function un.onInit

	; Get the Rig Client path
	ReadRegStr $INSTDIR HKLM "${REGKEY}" "Path"

	; Check if the components are installed and enable only the installed components for uninstallation
	Push $R0
	ReadRegStr $R0 HKLM "${REGKEY}\RigClient" "Path"
	${If} $R0 S== ""
	      	!insertmacro disableSection ${un.RigClient}
	${EndIf}

	ReadRegStr $R0 HKLM "${REGKEY}\RigClientCLI" "Path"
	${If} $R0 S== ""
	      	!insertmacro disableSection ${un.RCCLI}
	${EndIf}
	
	ReadRegStr $R0 HKLM "${REGKEY}\RigClientSource" "Path"
	${If} $R0 S== ""
	      	!insertmacro disableSection ${un.RCSource}
	${EndIf}
	StrCpy $DisplayText ""
	StrCpy $NoSectionSelectedUninstall "true"

FunctionEnd

Function un.CheckSlectedComponents
	Var /GLOBAL SelectRCCLI
	StrCpy $SelectRCCLI "false"

	; Check if Rig Client is selected for uninstallation. If it is, Rig Client should also be selected
	Push $0
	${If} ${SectionIsSelected} ${un.RCSource}
		StrCpy $DisplayText "$\nRig Client Source"
		StrCpy $NoSectionSelectedUninstall "false"
	${EndIf}
	${If} ${SectionIsSelected} ${un.RigClient} 
		; Rig Client is selected
		StrCpy $DisplayText "$DisplayText$\nRig Client"
		${IfNot} ${SectionIsSelected} ${un.RCCLI}
			ReadRegStr $R0 HKLM "${REGKEY}\RigClientCLI" "Path"
			${If} $R0 S!= ""
			StrCpy $DisplayText "$DisplayText$\nRig Client CLI (Rig Client CLI will also be selected for uninstallation as it depends on Rig Client)"
			STrCpy $SelectRCCLI "true"
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
		MessageBox MB_YESNO "$DisplayText" IDYES checkRCCLI
		Abort
		checkRCCLI:
		${If} $SelectRCCLI S== "true"
			SectionGetFlags ${un.RCCLI} $0
			IntOp $0 $0 | ${SF_SELECTED}
			SectionSetFlags ${un.RCCLI} $0
		${EndIf}
	${Else}
		MessageBox MB_OK "No compoennts selected"
	${EndIf}
FunctionEnd

Section -un.postactions 
	
	; Delete the main registry entry for Sahara if all the subkeys (RigClient, RigClientCLI and RigClientSource are deleted)
	DeleteRegKey /IfEmpty HKLM "${REGKEY}"



	; Check if all the components are deleted. If they are, delete the main installation directory and uninstaller
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
LangString DESC_SecRCSource ${LANG_ENGLISH} "Sahara Rig Client source code"


; Assign language strings to sections
!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
!insertmacro MUI_DESCRIPTION_TEXT ${RigClient} $(DESC_SecRC)
!insertmacro MUI_DESCRIPTION_TEXT ${RCCLI} $(DESC_SecRCCLI)
!insertmacro MUI_DESCRIPTION_TEXT ${RCSource} $(DESC_SecRCSource)
!insertmacro MUI_FUNCTION_DESCRIPTION_END

!insertmacro MUI_UNFUNCTION_DESCRIPTION_BEGIN
!insertmacro MUI_DESCRIPTION_TEXT ${un.RigClient} $(DESC_SecRC)
!insertmacro MUI_DESCRIPTION_TEXT ${un.RCCLI} $(DESC_SecRCCLI)
!insertmacro MUI_DESCRIPTION_TEXT ${un.RCSource} $(DESC_SecRCSource)
!insertmacro MUI_UNFUNCTION_DESCRIPTION_END

;--------------------------------

