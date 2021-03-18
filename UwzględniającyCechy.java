package wybory;

import java.util.Random;

/* Wybiera tego spośród kandydatów, który ma największą wartość, czyli tego,
   dla którego metoda wartośćKandydata przyjmuje największą wartość.
   Jeśli zmienna 'partia' jest niezerowa, to wyborca wybiera kandydata tylko
   spośród listy partii, która posiada ID równe zmiennej 'partia',
   a w przeciwnym razie wybiera kandydata ze wszystkich list.
*/
abstract class UwzględniającyCechy extends Wyborca{
  int partia;

  UwzględniającyCechy(String imię, String nazwisko, int partia){
    super(imię, nazwisko);
    this.partia = partia;
  }

  abstract int wartośćKandydata(Kandydat k);

  Kandydat zagłosuj(Lista[] listy){
    int maksymalnaWartość = Integer.MIN_VALUE;
    int liczbaMaksymalnych = 0;
    int pierwszaPartia = 1;
    int ostatniaPartia = listy.length;
    if(partia != 0){
      pierwszaPartia = partia;
      ostatniaPartia = partia;
    }
    for(int p = pierwszaPartia - 1; p <= ostatniaPartia - 1; p++){
      for(Kandydat k: listy[p].kandydaci){
        int wartość = wartośćKandydata(k);
        if(wartość > maksymalnaWartość){
          maksymalnaWartość = wartość;
          liczbaMaksymalnych = 1;
        }else if(wartość == maksymalnaWartość){
          liczbaMaksymalnych++;
        }
      }
    }
    int wylosowanyKandydat = (new Random()).nextInt(liczbaMaksymalnych) + 1;
    int obecnyKandydat = 0;
    for(int p = pierwszaPartia - 1; p <= ostatniaPartia - 1; p++){
      for(Kandydat k: listy[p].kandydaci){
        int wartość = wartośćKandydata(k);
        if(wartość == maksymalnaWartość){
          obecnyKandydat++;
          if(obecnyKandydat == wylosowanyKandydat) return k;
        }
      }
    }
    return null;
  }
}
