package wybory;

import java.util.Random;
import java.util.Arrays;

/* W metodzie tej dla każdego komitetu wyborczego obliczane są kolejne ilorazy
   całkowitej liczby głosów uzyskanych przez dany komitet
   i kolejnych wyrazów ciągu arytmetycznego, którego pierwszym elementem jest 1,
   a dwa sąsiednie elementy różnią się o 'różnicaMiędzyDzielnikami'.
   O podziale miejsc pomiędzy komitetami decyduje wielkość obliczonych
   w ten sposób ilorazów
*/
abstract class MetodaDzielników extends Metoda{

  abstract int różnicaMiędzyDzielnikami();

  int przydzielMandat(int[] głosyNaPartie, int[] dzielniki){
    double maksWynik = 0;
    int liczbaMaksWyników = 0;
    double wynik;
    for(int i = 0; i < głosyNaPartie.length; i++){
      wynik = Double.valueOf(głosyNaPartie[i]) / dzielniki[i];
      if(wynik > maksWynik){
        maksWynik = wynik;
        liczbaMaksWyników = 1;
      }else if(wynik == maksWynik){
        liczbaMaksWyników++;
      }
    }
    Random generator = new Random();
    int wylosowany = generator.nextInt(liczbaMaksWyników) + 1;
    int obecny = 0;
    for(int i = 0; i < głosyNaPartie.length; i++){
      wynik = Double.valueOf(głosyNaPartie[i]) / dzielniki[i];
      if(wynik == maksWynik){
        obecny++;
        if(obecny == wylosowany){
          return i;
        }
      }
    }
    return generator.nextInt(głosyNaPartie.length);
  }

  int[] rozdzielMandaty(int[] głosyNaPartie, int liczbaMandatów){
    int[] mandaty = new int[głosyNaPartie.length];

    int[] dzielniki = new int[głosyNaPartie.length];
    Arrays.fill(dzielniki, 1);
    int dlaKogoMandat;
    for(int i = 0; i < liczbaMandatów; i++){
      dlaKogoMandat = przydzielMandat(głosyNaPartie, dzielniki);
      mandaty[dlaKogoMandat]++;
      dzielniki[dlaKogoMandat] += różnicaMiędzyDzielnikami();
    }
    return mandaty;
  }
}

/* Metoda D’Hondta
   różnicaMiędzyDzielnikami wynosi 1
*/
class MetodaDH extends MetodaDzielników{
  String nazwaMetody(){
    return "Metoda D’Hondta";
  }
  int różnicaMiędzyDzielnikami(){
    return 1;
  }
}

/* Metoda Sainte-Laguë
   różnicaMiędzyDzielnikami wynosi 2
*/
class MetodaSL extends MetodaDzielników{
  String nazwaMetody(){
    return "Metoda Sainte-Laguë";
  }
  int różnicaMiędzyDzielnikami(){
    return 2;
  }
}
