package wybory;

import java.util.Random;

/* Głosuje na kandydata będącego na z góry ustalonym numerze listy
   z góry wybranej partii.
*/
class ŻelaznyElektoratKandydata extends Wyborca{
  int partia;
  int nr;

  ŻelaznyElektoratKandydata(String imię, String nazwisko, int partia, int nr){
    super(imię, nazwisko);
    this.partia = partia;
    this.nr = nr;
  }
  Kandydat zagłosuj(Lista[] listy){
    return listy[partia - 1].kandydaci[nr - 1];
  }
}

/* Głosuje na kandydata będącego na losowo wybranym numerze listy
   z góry wybranej partii.
*/
class ŻelaznyElektoratPartyjny extends Wyborca{
  int partia;

  ŻelaznyElektoratPartyjny(String imię, String nazwisko, int partia){
    super(imię, nazwisko);
    this.partia = partia;
  }
  Kandydat zagłosuj(Lista[] listy){
    Random generator = new Random();
    int nr = generator.nextInt(listy[partia - 1].liczbaKandydatów);
    return listy[partia - 1].kandydaci[nr];
  }
}
