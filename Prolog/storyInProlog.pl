pilkarz(marek).
pilkarz(jan).
plywak(jan).
plywak(adam).

jest(marek, pilkarz).
jest(jan, pilkarz).
jest(jan, plywak).
jest(adam, plywak).

biega(X) :- pilkarz(X).
sportowiec(X) :- plywak(X).
sportowiec(X) :- biega(X).

ma_dobra_kondycje(X) :- pilkarz(X).

bierze_udzial_w_zawodach(X) :- sportowiec(X), ma_dobra_kondycje(X).




