kobieta(maria).
kobieta(wanda).
kobieta(anna).
kobieta(ewa).
kobieta(zofia).
kobieta(katarzyna).

mezczyzna(franciszek).
mezczyzna(jan).
mezczyzna(krzysztof).
mezczyzna(wojciech).
mezczyzna(robert).
mezczyzna(bogdan).

malzenstwo(jan, maria).
malzenstwo(bogdan, anna).
malzenstwo(wojciech, zofia).

rodzic(franciszek, maria).
rodzic(jan, krzysztof).
rodzic(maria, krzysztof).
rodzic(jan, wojciech).
rodzic(maria, wojciech).
rodzic(wanda, bogdan).
rodzic(bogdan, ewa).
rodzic(anna, ewa).
rodzic(bogdan, zofia).
rodzic(anna, zofia).

rodzic(wojciech, katarzyna).
rodzic(wojciech, robert).
rodzic(zofia, katarzyna).
rodzic(zofia, robert).

syn(X,Y) :- rodzic(Y,X), mezczyzna(X).
corka(X,Y) :- rodzic(Y,X), kobieta(X).

ojciec(X,Y) :- rodzic(X,Y), mezczyzna(X).
matka(X,Y) :- rodzic(X,Y), kobieta(X).

brat(X,Y) :- rodzic(Z,X),rodzic(Z,Y), mezczyzna(X), X\=Y.
siostra(X,Y) :- rodzic(Z,X),rodzic(Z,Y), kobieta(X), X\=Y.

przodek(X,Y) :- rodzic(X,Z),rodzic(Z,Y), mezczyzna(X).
potomek(X,Y) :- rodzic(X,Z),rodzic(Z,Y), kobieta(X).

przodek(X,Y) :-	rodzic(X,Y).

przodek(X,Z) :- rodzic(X,Y), przodek(Y,Z).

potomek(X,Y) :- rodzic(Y,X).

potomek(Z,X) :- rodzic(Y,Z), potomek(Y,X).

ciocia(X,Y) :- rodzic(Z,Y), siostra(X,Z).
wujek(X,Y) :- rodzic(Z,Y), brat(X,Z).

ma_dziecko(X) :- rodzic(X,_).
	
dziadek(X,Y) :- dziadkowie(X,Y), mezczyzna(X).

babcia(X,Y) :- dziadkowie(X,Y), kobieta(X).

dziadkowie(X,Y) :- rodzic(X,Z), rodzic(Z,Y).
	
	
