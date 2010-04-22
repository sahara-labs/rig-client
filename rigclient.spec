#
# Sahara Rig Client - Service Wrapper
# 
# Software abstraction of physical rig to provide rig session control
# and rig device control. Automatically tests rig hardware and reports
# the rig status to ensure rig goodness.
#
# @license See LICENSE in the top level directory for complete license terms.
#
# Copyright (c) 2009, University of Technology, Sydney
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without 
# modification, are permitted provided that the following conditions are met:
#
#  * Redistributions of source code must retain the above copyright notice, 
#    this list of conditions and the following disclaimer.
#  * Redistributions in binary form must reproduce the above copyright 
#    notice, this list of conditions and the following disclaimer in the 
#    documentation and/or other materials provided with the distribution.
#  * Neither the name of the University of Technology, Sydney nor the names 
#    of its contributors may be used to endorse or promote products derived from 
#    this software without specific prior written permission.
# 
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
# IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
# DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE 
# FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
# DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
# SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
# CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, 
# OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
# OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#
# @author Michael Diponio (mdiponio)
# @date 8th March 2010
#

# Spec file to generate the Rig Client RPM

Name: RigClient
Summary: Software abstraction of of a physical rig.
Version: 2.0
Release: 1
License: BSD
Group: Sahara

URL: http://sourceforge.net/projects/labshare-sahara/
Distribution: RHEL
Vendor: University of Technology, Sydney
Packager: Tania Machet <tmachet@eng.uts.edu.au>, Michael Diponio <mdiponio@eng.uts.edu.au>

%define installdir /usr/lib/rigclient

%description
Software abstraction of physical rig to provide rig session control
and rig device control. Automatically tests rig hardware and reports
the rig status to ensure rig goodness.

%install

# Executables
mkdir -p  %{buildroot}/%{installdir}
cp %{buildroot}/../dist/rigclient.jar %{buildroot}/%{installdir}/rigclient.jar
cp %{buildroot}/../servicewrapper/rigclientservice %{buildroot}/%{installdir}/rigclientservice

# Configuration files
mkdir -p %{buildroot}/%{installdir}/config
cp %{buildroot}/../config/batch.properties %{buildroot}/%{installdir}/config/batch.properties
cp %{buildroot}/../config/rigclient.properties.release %{buildroot}/%{installdir}/config/rigclient.properties
cp %{buildroot}/../config/rigclient_service.ini %{buildroot}/%{installdir}/config/rigclient_service.ini

# Axis files
mkdir -p %{buildroot}/%{installdir}/interface/services/RigClient/META-INF/
cp %{buildroot}/../interface/services/RigClient/META-INF/RigClientService.wsdl %{buildroot}/%{installdir}/interface/services/RigClient/META-INF/RigClientService.wsdl
cp %{buildroot}/../interface/services/RigClient/META-INF/services.xml %{buildroot}/%{installdir}/interface/services/RigClient/META-INF/services.xml

# Init script, install
mkdir -p %{buildroot}/etc/init.d
cp %{buildroot}/../servicewrapper/rigclient_init  %{buildroot}/etc/init.d/rigclient

mkdir -p %{buildroot}/etc/Sahara

%post

# Let the operating system know the service has been added
if [ -x /usr/lib/lsb/install_initd ]; then
  /usr/lib/lsb/install_initd /etc/init.d/rigclient
elif [ -x /sbin/chkconfig ]; then
  /sbin/chkconfig --add rigclient
else
   for i in 3 4 5; do
        ln -sf /etc/init.d/rigclient /etc/rc.d/rc${i}.d/S90rigclient
   done
   for i in 1 6; do
        ln -sf /etc/init.d/novell-httpd /etc/rc.d/rc${i}.d/K10rigclient
   done
fi

# Add a symlink to the Rig Client configuration into /etc
ln -sf %{installdir}/config /etc/Sahara/RigClient

%preun

# Let the operating system know the service has been removed
if [ $1 = 0 ]; then
  /etc/init.d/rigclient stop  > /dev/null 2>&1
  if [ -x /usr/lib/lsb/remove_initd ]; then
    /usr/lib/lsb/install_initd /etc/init.d/rigclient
  elif [ -x /sbin/chkconfig ]; then
    /sbin/chkconfig --del rigclient
  else
    rm -f /etc/rc.d/rc?.d/???rigclient
  fi
fi

# Remove configuration symlink
rm -f  /etc/Sahara/RigClient

%files
%{installdir}/config/batch.properties
%{installdir}/config/rigclient.properties
%{installdir}/config/rigclient_service.ini
%{installdir}/interface/services/RigClient/META-INF/RigClientService.wsdl
%{installdir}/interface/services/RigClient/META-INF/services.xml
%{installdir}/rigclient.jar
%{installdir}/rigclientservice
/etc/init.d/rigclient
/etc/Sahara
