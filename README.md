<h1>Openbaton Client</h1>

<h2>Overview</h2>
Openbaton Client provides a command-line client, which enables you to access the project API through easy-to-use commands. 
For example, Openbaton Client provides the creation of a new Vim instance.
<br>
You can run the commands from the command line with the relative json that describe the Vim instance. 
Openbaton APIs are RESTful APIs, and use the HTTP protocol. 
<br>
They include methods, URIs, media types, and response codes.
Openbaton APIs are open-source Java clients, and can run on Linux or Mac OS X systems. 

<h2>Install the Openbaton Client</h2>

<h2>Set environment variables using the Openbaton Client properties</h2>
To set the required environment variables for the Openbaton Client, you must create an environment file cli.properties
<br>
In default mode Openbaton Client searches the environment file in /etc/Openbaton or you can specifie the path when you call a command with the prefix -c
<br>
<B>$ openbaton.sh  -c /path/  command-name</B>
<br>
If the system doesn't found the environment file ask to enter the properties

<h3>Create and source the evenvironment file</h3>
In a text editor, create a file named cli.properties file and add the following authentication information:
<br>
<UL>
<LI>nfvo-usr = username
<LI>nfvo-pwd = password
<LI>nfvo-ip = ip-address
<LI>nfvo-port = port-number
<LI>nfvo-version = version
</UL>

<h2>Openbaton Client usage</h2>
    
    openbaton.sh [-c <path of properties file>] command-name [arg-1] [arg-2] [arg-3]
    <br>
    openbaton.sh [-d #debug mode] command-name [arg-1] [arg-2] [arg-3]
    <br>
    openbaton.sh command-name [help]
    <br>
    openbaton.sh help



