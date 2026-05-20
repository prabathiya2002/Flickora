$env:JAVA_HOME="C:\Program Files\Java\jdk-21"
$env:Path="$env:JAVA_HOME\bin;$env:Path"

$env:CATALINA_HOME="D:\Research\Flickora\tools\apache-tomcat-9.0.118"
$env:CATALINA_BASE="D:\Research\Flickora\tools\apache-tomcat-9.0.118"

& "$env:CATALINA_HOME\bin\catalina.bat" run