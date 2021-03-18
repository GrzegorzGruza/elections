package wybory;

import java.util.Random;
import java.util.Arrays;


/* Metoda Hare’a-Niemeyera
   Liczbę uzyskanych manatów oblicza się według wzoru:
                Q = (P*M)/G, gdzie:
   Q – liczba uzyskanych przez partię mandatów,
   P – liczba głosów oddanych na partię,
   M – liczba mandatów do obsadzenia,
   G – liczba wszystkich oddanych głosów.
   Jeśli nie zostaną rozdzielone wszystkie mandaty,
   to pozostałe mandaty przydziela się tym listom, dla których wyliczone ilorazy
   wykazują kolejno najwyższe wartości po przecinku, np. 0,39; 0,27; 0,05.
*/
class MetodaHN extends Metoda{
  String nazwaMetody(){
    return "Metoda Hare’a-Niemeyera";
  }

  int[] rozdzielMandaty(int[] głosyNaPartie, int liczbaMandatów){
    int głosy = 0;
    for(int g: głosyNaPartie) głosy += g;
    int[] mandaty = new int[głosyNaPartie.length];
    double[] części = new double[głosyNaPartie.length];
    int rozdzieloneMandaty = 0;
    for(int i = 0; i < części.length; i++){
      części[i] = ((double) głosyNaPartie[i]) / głosy *liczbaMandatów;
      mandaty[i] = (int) części[i];
      rozdzieloneMandaty += mandaty[i];
    }
    for(int j = 0; j < liczbaMandatów - rozdzieloneMandaty; j++){
      double maksymalnyUłamek = 0;
      int liczbaMaksymalnych = 0;
      for(int i = 0; i < części.length; i++){
        if(części[i] - (int) części[i] > maksymalnyUłamek){
          maksymalnyUłamek = części[i] - (int) części[i];
          liczbaMaksymalnych = 1;
        }else if(części[i] - (int) części[i] == maksymalnyUłamek){
          liczbaMaksymalnych++;
        }
      }
      int wylosowany = (new Random()).nextInt(liczbaMaksymalnych) + 1;
      int obecny = 0;
      for(int i = 0; i < części.length; i++){
        if(części[i] - (int) części[i] == maksymalnyUłamek){
          obecny++;
          if(obecny == wylosowany){
            mandaty[i]++;
            części[i] = 0;
            break;
          }
        }
      }
    }
    return mandaty;
  }
}
