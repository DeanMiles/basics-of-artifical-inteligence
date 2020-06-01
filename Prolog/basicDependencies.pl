kobieta(ala).
kobieta(maria).
kobieta(maja).
mezczyzna(jan).
mezczyzna(marek).
mezczyzna(tomek).
lubi(marek, ala).
lubi(ala, psy).
lubi(marek, koty).
lubi(maja, koty).
lubi(ala,X):-lubi(marek,X).
lubi(marek, papierosy).
lubi(maja, rower).
lubi(tomek, wino).
lubi(jan, wino).
lubi(tomek, X) :- lubi(X, wino).
