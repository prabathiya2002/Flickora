param(
    [string]$JavaHome,
    [string]$TomcatVersion = '9.0.118'
)

$ErrorActionPreference = 'Stop'

function Write-Section {
    param([string]$Text)
    Write-Host "`n=== $Text ===" -ForegroundColor Cyan
}

function Ensure-Command {
    param(
        [string]$Name,
        [string[]]$WingetIds
    )

    if (Get-Command $Name -ErrorAction SilentlyContinue) {
        return
    }

    if (-not (Get-Command winget -ErrorAction SilentlyContinue)) {
        throw "$Name is not installed and winget is unavailable. Install it manually, then rerun this script."
    }

    foreach ($id in $WingetIds) {
        Write-Host "Installing $Name using winget package $id..."
        winget install --id $id -e --accept-package-agreements --accept-source-agreements
        if (Get-Command $Name -ErrorAction SilentlyContinue) {
            return
        }
    }

    throw "Unable to install $Name automatically. Install it manually and rerun this script."
}

Write-Section 'Checking prerequisites'

Ensure-Command -Name 'java' -WingetIds @('EclipseAdoptium.Temurin.17.JDK', 'Microsoft.OpenJDK.17')
Ensure-Command -Name 'mvn' -WingetIds @('Apache.Maven')

if (-not $JavaHome) {
    $javaPath = (Get-Command java).Source
    if ($javaPath) {
        $javaHome = Split-Path (Split-Path $javaPath -Parent) -Parent
        $JavaHome = $javaHome
    }
}

if ($JavaHome) {
    $env:JAVA_HOME = $JavaHome
    Write-Host "JAVA_HOME set to $JavaHome"
}

Write-Section 'Building project'
mvn clean package -DskipTests

Write-Section 'Downloading Tomcat if needed'
$tomcatRoot = Join-Path $PSScriptRoot 'tools'
$tomcatDir = Join-Path $tomcatRoot "apache-tomcat-$TomcatVersion"
$tomcatZip = Join-Path $tomcatRoot "apache-tomcat-$TomcatVersion-windows-x64.zip"

if (-not (Test-Path $tomcatDir)) {
    New-Item -ItemType Directory -Force -Path $tomcatRoot | Out-Null
    $tomcatUrl = "https://dlcdn.apache.org/tomcat/tomcat-9/v$TomcatVersion/bin/apache-tomcat-$TomcatVersion-windows-x64.zip"
    Invoke-WebRequest -Uri $tomcatUrl -OutFile $tomcatZip
    Expand-Archive -Path $tomcatZip -DestinationPath $tomcatRoot -Force
}

Write-Section 'Deploying WAR'
$warPath = Join-Path $PSScriptRoot 'target\OnlineMovieTicketSystem.war'
if (-not (Test-Path $warPath)) {
    throw "WAR not found: $warPath"
}

Copy-Item -Force $warPath (Join-Path $tomcatDir 'webapps\OnlineMovieTicketSystem.war')

Write-Section 'Done'
Write-Host "Tomcat location: $tomcatDir"
Write-Host "Start the app with:"
Write-Host "& '$tomcatDir\bin\catalina.bat' run"
Write-Host "Then open: http://localhost:8080/OnlineMovieTicketSystem/"
