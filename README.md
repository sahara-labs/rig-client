Sahara Labs: Rig Client
======================

Remote laboratory framework (as deployed at https://remotelabs.eng.uts.edu.au)

What is Sahara?
----------------------
Sahara Labs is a software suite developed by UTS Remote Labs that helps to enable remote access to computer controlled laboratories. It is designed to be a scalable, stable platform that enables the use and sharing of a variety of types of remote laboratories and maximises remote lab usage by implementing queuing and booking (or reservations) for users over a group of identical laboratories.


Rig Client
----------------------
The Rig Client is one component of the Sahara Remote Labs framework (along with the Scheduling Server and Web Interface). It provides a software abstraction of a rig and interfaces with the physical apparatus or it’s controller. The Rig Client was developed in Java and will require further development to adapt the client to a specific rig type to be integrated into Sahara. Rig Clients can have various levels of control over the physical rig, either having direct control (previously called primitive control), batch instruction control or peripheral control (where the Rig Client provides access to the rig’s controller).

More information and installation files can be found at: http://sourceforge.net/projects/labshare-sahara/