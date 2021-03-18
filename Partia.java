package wybory;

import java.util.Random;

abstract class Partia{
  int budżet;
  int partia; /*indywidualny, niepowtarzalny numer nadawany partii (ID).
                ID jest dodatnią liczbą całkowitą.*/
  Partia(int budżet, int partia){
    this.budżet = budżet;
    this.partia = partia;
  }
/* Zwraca libczę całkowitą - koszt, jaki poniesie partia wykonując w okręgu 'o'
   działanie zadane tablicą 'działanie'.
*/
  int kosztDziałania(Okręg o, int[] działanie){
    int koszt = 0;
    for(int d: działanie) koszt += Math.abs(d);
    koszt *= o.liczbaWyborców;
    return koszt;
  }

/* Zwraca liczbę całkowitą - wartość, jaką dla partii ma działanie
   przeprowadzone w okręgu 'o' i zadane tablicą 'działanie'.
*/
  abstract int wartośćDziałania(Okręg o, int[] działanie);

/* W okręgu 'o' wykonuje działanie partii zadane przez talicę 'działanie'.
   Modyfikuje wagi każdego wyborcy Wszechstronnego.
*/
  void wykonajDziałanie(Okręg o, int[] działanie){
    Wszechstronny w;
    for(Wyborca wyborca: o.wyborcy){
      if(!(wyborca instanceof Wszechstronny)) continue;
      w = (Wszechstronny) wyborca;
      for(int i = 0; i < w.wagi.length; i++){
        w.wagi[i] += działanie[i];
        if(w.wagi[i] > 100) w.wagi[i] = 100;
        if(w.wagi[i] < -100) w.wagi[i] = -100;
      }
    }
  }

/* Wykonuje dostępne działania, dopóki budżet na to pozwala.
   Za każdym razem wybiera to działanie, które ma największą wartość,
   czyli to, dla którego funkcja wartośćDziałania zwróciła największy wynik.
   Ignoruje działania o zerowym koszcie.
*/
  void wykonajDziałania(Okręg[] okręgi, int[][] działania){
    int maksWartość, liczbaMaksymalnych = 0, wartość = 0, koszt = 0;
    while(budżet > 0){
      maksWartość = Integer.MIN_VALUE;
      for(int[] działanie: działania){
        for(Okręg o: okręgi){
          koszt = kosztDziałania(o, działanie);
          if(koszt > budżet || koszt == 0) continue;//koszt za duży lub zerowy
          wartość = wartośćDziałania(o, działanie);
          if(wartość > maksWartość){
            maksWartość = wartość;
            liczbaMaksymalnych = 1;
          }else if(wartość == maksWartość){
            liczbaMaksymalnych++;
          }
        }
      }
      if(maksWartość == Integer.MIN_VALUE) return;//szystkie działania za drogie
      int wylosowane = (new Random()).nextInt(liczbaMaksymalnych) + 1;
      int obecne = 0;
      for(int[] działanie: działania){
        for(Okręg o: okręgi){
          koszt = kosztDziałania(o, działanie);
          if(koszt > budżet || koszt == 0) continue;//koszt za duży lub zerowy
          wartość = wartośćDziałania(o, działanie);
          if(wartość == maksWartość){
            obecne++;
            if(obecne == wylosowane){
              budżet -= koszt;
              wykonajDziałanie(o, działanie);
              break;
            }
          }
        }
      }
    }
  }
}

/* Z dostępnych działań zawsze wybiera najdroższe, na które ją stać.
*/
class PartiaRozmach extends Partia{
  PartiaRozmach(int budżet, int partia){
    super(budżet, partia);
  }

  int wartośćDziałania(Okręg o, int[] działanie){
    return kosztDziałania(o, działanie);
  }
}

/* Z dostępnych działań zawsze wybiera najtańsze.
*/
class PartiaSkromna extends Partia{
  PartiaSkromna(int budżet, int partia){
    super(budżet, partia);
  }

  int wartośćDziałania(Okręg o, int[] działanie){
    return -kosztDziałania(o, działanie);
  }
}

/* Z dostępnych działań zawsze wybiera to, które zwiększy sumę sum ważonych
   kandydatów swoich kandydatów.
*/
class PartiaZachłannie extends Partia{
  PartiaZachłannie(int budżet, int partia){
    super(budżet, partia);
  }

  int wartośćDziałania(Okręg o, int[] działanie){
    int wartość = 0, nowaWartośćWagi = 0;
    Wszechstronny wszechstronny;
    for(Kandydat kandydat: o.listy[partia - 1].kandydaci){
      for(Wyborca wyborca: o.wyborcy){
        if(!(wyborca instanceof Wszechstronny)) continue;
        wszechstronny = (Wszechstronny) wyborca;
        for(int i = 0; i < kandydat.cechy.length; i++){
          nowaWartośćWagi = wszechstronny.wagi[i] + działanie[i];
          if(nowaWartośćWagi > 100) nowaWartośćWagi = 100;
          if(nowaWartośćWagi < -100) nowaWartośćWagi = -100;
          wartość += kandydat.cechy[i] * nowaWartośćWagi;
        }
      }
    }
    return wartość;
  }
}

/* Posiada strategię podobną do strategii zachłannej, z tą różnicą, że pod uwagę
   brani są wyłącznie kandydaci na pierwszej połowie listy.
*/
class PartiaWłasna extends Partia{
  PartiaWłasna(int budżet, int partia){
    super(budżet, partia);
  }

  int wartośćDziałania(Okręg o, int[] działanie){
    int wartość = 0, nowaWartośćWagi = 0;
    Wszechstronny wszechstronny;
    for(Kandydat kandydat: o.listy[partia - 1].kandydaci){
      if(kandydat.nr > o.listy[partia - 1].liczbaKandydatów / 2) continue;
      for(Wyborca wyborca: o.wyborcy){
        if(!(wyborca instanceof Wszechstronny)) continue;
        wszechstronny = (Wszechstronny) wyborca;
        for(int i = 0; i < kandydat.cechy.length; i++){
          nowaWartośćWagi = wszechstronny.wagi[i] + działanie[i];
          if(nowaWartośćWagi > 100) nowaWartośćWagi = 100;
          if(nowaWartośćWagi < -100) nowaWartośćWagi = -100;
          wartość += kandydat.cechy[i] * nowaWartośćWagi;
        }
      }
    }
    return wartość;
  }
}
