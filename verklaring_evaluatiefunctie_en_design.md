# Verklaring

## Epochs
Ik gebruik 100 epochs vanwege de aantal verschillende kandidaten in de verschillende bestanden, waarbij het derde bestand de meeste bestemmingen bevat
## Kandidaten
Ik besloot ervoor om een standaarde batch grootte van 50 te gebruiken waardoor ik 5000 verschillende kandidaten gebruik. Dit kan bij het eerste bestand als een overkill beschouwd worden, maar mijn idee is om de kandidaten te laten stoppen na het bereiken van een specifiek percentage waarmee ik tevreden van de nauwkeurigheid.

## ontwerp : EvalutatieFunctie

### design
startsituatie &rarr; randomkandidaat zonder score &rarr; evalueerEpoch(randomkandidaat) &rarr; randomkandidaat krijgt een score &rarr; evaluatieEpoch() &rarr; randomkandidaat vergelijken met andere randomkandidaten van dezelfde epoch.

### Uitleg
Er zijn een aantal vaste kandidaten binnen een epoch die random worden gegenereerd door de randomkandidaat functie, de kandidaten worden vervolgens geëvalueerd op basis van de random routes. Uiteindelijk worden alle kandidaten binnen de epoch geëvalueerd.

