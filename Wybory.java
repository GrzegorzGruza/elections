package wybory;

/* Symuluje działanie wyborów w Bajtocji.
*/
class Wybory{
  int liczbaOkręgów; //liczba okręgów dodanych już do tablicy 'okręgi'
  Okręg[] okręgi;
  int liczbaPartii; //liczba partii dodanych już do tablicy 'partie'
  Partia[] partie; //partie[i-1] przetrzymuje partię o ID równym 'i'
  String[] nazwyPartii; //nazwaPartii[i] przetrzymuje nazwę partii o ID równym 'i'

  Wybory(int liczbaOkręgów, int liczbaPartii){
    this.liczbaOkręgów = 0;
    this.liczbaPartii = 0;
    okręgi = new Okręg[liczbaOkręgów];
    partie = new Partia[liczbaPartii];
  }

  void dodajOkręg(Okręg o){
    okręgi[liczbaOkręgów] = o;
    liczbaOkręgów++;
  }

  void dodajPartię(Partia p){
    partie[liczbaPartii] = p;
    liczbaPartii++;
  }

/* Łączy dwa okręi o podanych numerach. Dołącza okrąg o większym numerze
   do okręgu o mniejszym numerze. Ustawia na true połączonyZInnymOkręgiem
   okręgu dołączanego.
*/
  void połączOkręgi(int okręg1, int okręg2){
    if(okręg1 > okręg2){
      int okręgPom = okręg1;
      okręg1 = okręg2;
      okręg2 = okręgPom;
    }
    okręgi[okręg1 - 1].dołączOkręg(okręgi[okręg2 - 1]);
    okręgi[okręg2 - 1].połączonyZInnymOkręgiem = true;
  }

  void kampania(int[][] działania){
    for(Partia partia: partie){
      partia.wykonajDziałania(okręgi, działania);
    }
  }

/* Przeprowadza wybory w każdym okręgu wyborczym według podanej metody
   podziału mandatów między komitety wyborcze.
*/
  WynikWyborów przeprowadźWybory(Metoda m){
    WynikWyborów wynik = new WynikWyborów(liczbaOkręgów);
    for(int i = 0; i < liczbaOkręgów; i++){
      wynik.wyniki[i] = m.dajWynik(okręgi[i]);
    }
    return wynik;
  }

}
