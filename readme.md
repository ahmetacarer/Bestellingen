# Opdracht: Bestellingen

## Algemene Beschrijving:
Studenten starten het ontwerp van een evolutionair algoritme om de optimale route van een bestelbusje te bepalen

## De situatie:
Een bestelbusje moet een aantal bestellingen afleveren. Hiervoor moet een optimale route bepaalt worden. Hieronder staat aangegeven welke aspecten bepalen hoe goed een route is.
In het project worden 3 situaties aangeleverd (1.txt, 2.txt en 3.txt). Deze files bevatten de volgende informatie:
-	Het aantal bestemmingen dat aangedaan moet worden
-	De nummers van de bestemmingen die aangedaan moeten worden
-	Het aantal pakketjes dat op die bestemming moet worden afgeleverd
-	Alle afstanden tussen de genummerde bestemmingen. De maximale afstand is 100.
Deze gegevens worden in het project al voor je ingelezen.

Voor het bepalen van de optimale route zijn 3 dingen van belang (met bijbehorende prioriteit)
-	Supersupersuper heel belangrijk: de route begint op het magazijn (nummer 1)
-	Belangrijk: de totale afstand die afgelegd is van begin tot eind is zo kort mogelijk
-	Een beetje belangrijk: de totale afstand die pakketjes hebben afgelegd (is ongeveer de totale wachttijd) is zo minimaal mogelijk. 

## Opdrachten
1.	Bekijk het klassendiagram in de bijlage en breidt het Javaproject uit met alle klasse-en methodedefinities. De methoden mogen nog leeg zijn
2.	Bekijk de JavaDoc van de interface Comparable en de bijbehorende method CompareTo. Schrijf nu de methode CompareTo van de klasse Kandidaatroute zodat deze sorteerbaar wordt. (Vraag jezelf waarop je moet sorteren)
3.	Kies het aantal kandidaatoplossingen en epochs en geef via de constructor de betreffende constanten deze waarde. Start een tekstdocument en benoem en onderbouw deze keuzes daarin.
4.	Vul je tekstdocument aan met een ontwerp van de evaluatiefunctie en bijbehorende onderbouwing. Programmeer deze functie in de methode evalueerKandidaat. Schrijf ook de methode evalueerEpoch die alle kandidaten in een epoch evalueert. Gebruik hierbij uiteraard de methode evalueerKandidaat.
5.	Maak de methode startSituatie waarin je volledig random kandidaatoplossingen genereert. Gebruik hierin de methode randomKandidaat die je ook zal moeten schrijven.
6.	Lever je tekstbestand in. Bewaar je code; bij de volgende opdracht ga je hiermee door.
 
Bijlage 1: Klassendiagram

## Toelichting
Main: Geen toelichting nodig

Kandidaatroute:
-	Score bevat de score voor deze kandidaatroute zoals berekent door de evaluatiefunctie
-	Route bevat de daadwerkelijke route volgens onderstaand schema
-	compareTo wordt gebruikt om deze klasse ‘sorteerbaar’ te maken

RouteCalc:
-	EPOCHS, KANDIDATEN: het in de constructor aangegeven aantal apochs en kandidaatoplossingen per epoch.
-	TOTALDEST: het aantal bestemmingen in de afstandsmatrix
-	destinations & packages: de te verwerken bestemmingen en pakketjes per bestemming. Zie onderstaand routeschema voor de samenhang
-	distances: de afstanden (in een matrix) tussen de verschillende bestemmingen
-	epochTeller: het huidige epochnummer
-	readSituation: leest de huidige bezorgopdracht in vanuit een file
-	bepaalRoute: bepaalt de optimale route en drukt deze af
-	evalueerKandidaat: bevat de evaluatiefunctie om een kandidaatroute van een score te voorzien
-	evalueerEpoch: evalueert alle kandidaatroutes in de huidige epoch
-	randomKandidaat: genereert een random volgorde van de gevraagde bestemmingen.
-	startSituatie: genereert een volledige set random kandidaten om het algoritme mee te starten.
-	muteer: past een mutatie toe op een kandidaatroute en geeft de gemuteerd kandidaatroute terug.
-	volgendeEpoch: bepaalt elitair de collectie kandidaatoplossingen voor de volgende epoch dmv mutatie en random toevoegingen en verhoogt het epochnummer

Samenhang route / destinations / packages / distances
De samenhang tussen deze arrays staat hieronder schematisch weergegeven.
(als je eigenwijs / slim bent mag je hier uiteraard vanaf wijken).  
