# Online Movie Ticket Reservation System

SE1020 Object-Oriented Programming web application built with Java, JSP, Servlets, Bootstrap, and text-file persistence only.

## Features

- User management with login and registration
- Movie management
- Showtime management
- Ticket reservation and cancellation
- Seat management
- Review and rating management

## Architecture

- Model: POJO classes in `src/model`
- Controller: Servlets in `src/servlet`
- Service: business logic in `src/service`
- Utility: text-file handling in `src/util`
- View: JSP pages in `web/jsp`

## Data Files

Sample `.txt` files are included in both `src/data` and `web/WEB-INF/data` for submission and deployment compatibility.

## Build and Run

1. Install JDK 8+ and Apache Maven.
2. Build the WAR:

   ```bash
   mvn clean package
   ```

3. Deploy `target/OnlineMovieTicketSystem.war` to Apache Tomcat 9.
4. Open the app in a browser and start at `index.jsp` or `jsp/dashboard.jsp`.

### One-command setup on Windows

Run the PowerShell bootstrap script to check/install prerequisites, build the WAR, download Tomcat 9 if needed, and copy the app into Tomcat:

```powershell
Set-ExecutionPolicy -Scope Process Bypass
.\setup.ps1
```

If Java is already installed in a custom location, you can pass it in explicitly:

```powershell
Set-ExecutionPolicy -Scope Process Bypass
.\setup.ps1 -JavaHome 'C:\Program Files\Java\jdk-17'
```

## Default Demo Accounts

- Admin: `admin / admin123`
- Customer: `customer1 / pass123`

## Notes

- The project uses `javax.servlet` APIs, so Tomcat 9 is the safest target.
- File handling uses `FileReader`, `FileWriter`, `BufferedReader`, and `PrintWriter` only.