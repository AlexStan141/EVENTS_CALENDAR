# EVENTS_CALENDAR

Pentru a rula proiectul.
1.Instalati MySQLWorkbench, XAMPP Control Panel, IntelliJIdeaComunity si VSCode. 
2.In MySQL creati o conexiune cu port 3306 si in ea creati schema(baza de date) events_calendar
3.Deschideti XAMP Control Panel si dati start la serviciul MySQL. O sa afiseze port 3306
4.Deschideti folderul BACKEND cu IntelliJ si dati run.
5.Deschideti frontend-ul cu Visual Studio code.
6.In Visual Studio deschideti un terminal si tastati "npm start"
7.Url-ul este localhost:3000. Scrieti in url localhost:3000/event/view/{data pe care voi o alegeti in formatul yyyy-MM-dd}
  Se va afisa saptamana care are data tastata in mijloc.Puteti sa va miscati in trecut sau viitor cu ajutorul sagetilor.
8.Daca vreti sa inserati un eveniment dati pe butonul create event
9.Dati click pe o celula cu numele unui eveniment in ea ca sa ajungeti la fereastra in care puteti sa il actualizati sau sa-l stergeti.
10.Planificati evenimentele dorite.
11.Dupa inchiderea programului in BACKEND in resources in application.properties setati spring.jpa.hibernate.ddl-auto la update pentru a nu va pierde evenimentele.
