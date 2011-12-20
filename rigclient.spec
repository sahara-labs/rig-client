#
# Sahara Rig Client
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
# @author Michael Diponio (mdiponio), Tania Machet (tmachet)
# @date 23rd April 2010
#

# Spec file to generate the Rig Client RPM

Name: RigClient
Summary: Software abstraction of of a physical rig.
Version: 3.2
Release: 0
License: BSD
Group: Sahara

URL: http://sourceforge.net/projects/labshare-sahara/
Distribution: Redhat, SuSE
Vendor: University of Technology, Sydney
Packager: Tania Machet <tmachet@eng.uts.edu.au>, Michael Diponio <mdiponio@eng.uts.edu.au>
Requires: jre >= 1.6.0

%define installdir /usr/lib/rigclient

%description
Software abstraction of physical rig to provide rig session control
and rig device control. Automatically tests rig hardware and reports
the rig status to ensure rig goodness.

%install

# Executables
mkdir -p $RPM_BUILD_ROOT/%{installdir}
cp $RPM_BUILD_DIR/../../dist/rigclient.jar $RPM_BUILD_ROOT/%{installdir}/rigclient.jar
install -m 700 $RPM_BUILD_DIR/../../servicewrapper/rigclientservice $RPM_BUILD_ROOT/%{installdir}/rigclientservice

# Library directory to drop in other Jars
mkdir -p $RPM_BUILD_ROOT/%{installdir}/lib

# Configuration files
mkdir -p $RPM_BUILD_ROOT/%{installdir}/conf
mkdir -p $RPM_BUILD_ROOT/%{installdir}/conf/conf.d
mkdir -p $RPM_BUILD_ROOT/%{installdir}/conf/conf.example
cp $RPM_BUILD_DIR/../../conf/rigclient.properties $RPM_BUILD_ROOT/%{installdir}/conf/rigclient.properties
cp $RPM_BUILD_DIR/../../conf/conf.d/*.properties $RPM_BUILD_ROOT/%{installdir}/conf/conf.d/
cp $RPM_BUILD_DIR/../../conf/conf.example/*.properties $RPM_BUILD_ROOT/%{installdir}/conf/conf.example/
cp $RPM_BUILD_DIR/../../servicewrapper/rigclient_service.ini $RPM_BUILD_ROOT/%{installdir}/rigclient_service.ini

# Init script, install
mkdir -p $RPM_BUILD_ROOT/etc/init.d
install -m 755 $RPM_BUILD_DIR/../../servicewrapper/rigclient_init  $RPM_BUILD_ROOT/etc/init.d/rigclient

mkdir -p $RPM_BUILD_ROOT/etc/Sahara

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
        ln -sf /etc/init.d/rigclient /etc/rc.d/rc${i}.d/K10rigclient
   done
fi

# Add a symlink to the Rig Client configuration into /etc
ln -sf %{installdir}/conf /etc/Sahara/RigClient

# Perform clean up from SRL2
if [ -e %{installdir}/config/conf ] ; then
    rm -f %{installdir}/config/conf
fi

if [ -e %{installdir}/interface ] ; then
    rm -rf %{installdir}/interface
fi

%preun

# Let the operating system know the service has been removed
if [ $1 = 0 ]; then
  /etc/init.d/rigclient stop  > /dev/null 2>&1
  if [ -x /usr/lib/lsb/remove_initd ]; then
    /usr/lib/lsb/remove_initd /etc/init.d/rigclient
  elif [ -x /sbin/chkconfig ]; then
    /sbin/chkconfig --del rigclient
  else
    rm -f /etc/rc.d/rc?.d/???rigclient
  fi
fi

# Remove configuration symlink
rm -f  /etc/Sahara/RigClient

%postun 

# Clean up any remaining directories
if [ -e %{installdir}/conf ] ; then
    rm -rf %{installdir}/conf &> /dev/null
fi

%files
%{installdir}/conf/rigclient.properties
%{installdir}/conf/conf.d/logging.properties
%{installdir}/conf/conf.d/rigattributes.properties
%{installdir}/conf/conf.example/batch.properties
%{installdir}/conf/conf.example/configuredrig.properties
%{installdir}/conf/conf.example/primitive.properties
%{installdir}/rigclient_service.ini
%{installdir}/rigclient.jar
%{installdir}/rigclientservice
%{installdir}/lib
/etc/init.d/rigclient
/etc/Sahara
