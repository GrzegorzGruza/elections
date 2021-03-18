package wybory;

/* Symuluje metodę przeliczania głosów na liczbę mandatów otrzymanych
   przez partie biorące udział w wyborach.
*/
abstract class Metoda{

    abstract String nazwaMetody();

/* Zwraca tablicę, w której na i-tej pozycji znajduje się liczba mandatów
   otrzymnych przez partię o ID równym 'i'.
*/
    abstract int[] rozdzielMandaty(int[] głosyNaPartie, int liczbaMandatów);

/* Przeprowadza wybory w okręgu 'o'. Rozdziela mandtaty według metody
   'rozdzielMandaty'. Zwraca WynikWyborów.
*/
    WynikWyborówOkręgu dajWynik(Okręg o){
      if(o.połączonyZInnymOkręgiem == true)
        return (new PustyWynikWyborówOkręgu(o));
      WynikWyborówOkręgu wynik = new WynikWyborówOkręgu(o);
      int[] głosyNaPartie = new int[o.liczbaList];
      Kandydat głosNa;
      for(int i = 0; i < o.wyborcy.length; i++){
        głosNa = o.wyborcy[i].zagłosuj(o.listy);
        wynik.głosOddanyNa[i] = głosNa;
        wynik.liczbaGłosów[głosNa.partia - 1][głosNa.nr - 1]++;
        głosyNaPartie[głosNa.partia - 1]++;
      }
      wynik.liczbaWybranych = rozdzielMandaty(głosyNaPartie, o.liczbaWyborców / 10);
      return wynik;
    }
}
