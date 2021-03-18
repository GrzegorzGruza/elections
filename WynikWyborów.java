package wybory;

import java.lang.StringBuffer;

/* Klasa reprezentująca wynik wyborów w okręgu w trakcie wyborów w Bajtocji.
*/
class WynikWyborówOkręgu{
  int nrOkręgu;
  Wyborca[] wyborcy;
  Kandydat[] głosOddanyNa; //na kogo głosował wyborca[i]
  Lista[] listy;
  int[][] liczbaGłosów; //ile głosów oddano na kandydat z listy i na poz. j
  int[] liczbaWybranych; //ile wybranych z partii o danym ID

  WynikWyborówOkręgu(Okręg o){
    this.nrOkręgu = o.nrOkręgu;
    this.wyborcy = o.wyborcy;
    this.głosOddanyNa = new Kandydat[o.liczbaWyborców];
    this.listy = o.listy;
    this.liczbaGłosów = new int[o.listy.length][o.listy[0].kandydaci.length];
    this.liczbaWybranych = new int[o.listy.length];
  }

  int mandaty(int partia){
    return liczbaWybranych[partia];
  }

/* Zwraca zmienną typu String - tekstowy zapis wyników wyboru w okręgu.
*/
  public String toString(String[] nazwyPartii){
    StringBuffer s = new StringBuffer("");
    s.append("Okręg " + String.valueOf(nrOkręgu) + "\n");
    if(this instanceof PustyWynikWyborówOkręgu){
      s.append("--dołączono do innego okręgu--\n\n");
      return s.toString();
    }
    for(int i = 0; i < wyborcy.length; i++){
      s.append(wyborcy[i].imię + " " + wyborcy[i].nazwisko + " -> " +
               głosOddanyNa[i].imię + " " + głosOddanyNa[i].nazwisko + "\n");
    }
    for(int i = 0; i < listy.length; i++){
      for(int j = 0; j < listy[i].kandydaci.length; j++){
        s.append(listy[i].kandydaci[j].imię + " " +
                 listy[i].kandydaci[j].nazwisko + " " +
                 nazwyPartii[listy[i].kandydaci[j].partia - 1] + " " +
                 String.valueOf(listy[i].kandydaci[j].nr - 1) + ":  " +
                 String.valueOf(liczbaGłosów[i][j]) + "\n");
      }
    }
    for(int i = 0; i < nazwyPartii.length; i++){
      s.append(nazwyPartii[i] + ":  " +
               String.valueOf(liczbaWybranych[i]) + "\n");
    }
    s.append("\n");
    return s.toString();
  }
}

/* Reprezentuje wynik wyborów w oktęgu, który został przyłączony do innego
   okręgu.
*/
class PustyWynikWyborówOkręgu extends WynikWyborówOkręgu{
  PustyWynikWyborówOkręgu(Okręg o){
    super(o);
  }
}

/* Klasa symulująca wynik wyborów w całej Bajtocji.
*/
class WynikWyborów{
  WynikWyborówOkręgu[] wyniki;//wynik w poszczególnych okręgach

  WynikWyborów(int liczbaOkręgów){
    wyniki = new WynikWyborówOkręgu[liczbaOkręgów];
  }

/* Zwraca zmienną typu String - tekstowy zapis wyników wyborów.
*/
  public String toString(String[] nazwyPartii){
    StringBuffer s = new StringBuffer();
    for(WynikWyborówOkręgu w: wyniki) s.append(w.toString(nazwyPartii));

    int[] liczbaWybranych = new int[nazwyPartii.length];
    for(WynikWyborówOkręgu w: wyniki){
      for(int i = 0; i < nazwyPartii.length; i++){
        liczbaWybranych[i] += w.liczbaWybranych[i];
      }
    }
    s.append("Łącznie we wszystkich okręgach:\n");
    for(int i = 0; i < nazwyPartii.length; i++)
      s.append(nazwyPartii[i] + ":  " + String.valueOf(liczbaWybranych[i]) + "\n");
    return s.toString();
  }
}
