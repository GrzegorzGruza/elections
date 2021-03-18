package wybory;

/* Oddaje głos na tego spośród kandydatów, który posiada największą wartość
   wybranej przez wyborcę cechy.
*/
class MaksymalizującyJednocechowy extends UwzględniającyCechy{
  int cecha;

  MaksymalizującyJednocechowy(String imię, String nazwisko,
                             int partia, int cecha){
    super(imię, nazwisko, partia);
    this.cecha = cecha;
  }

  int wartośćKandydata(Kandydat k){
    return k.cechy[cecha - 1];
  }
}

/* Oddaje głos na tego spośród kandydatów, który posiada najmniejszą wartość
   wybranej przez wyborcę cechy.
*/
class MinimalizującyJednocechowy extends UwzględniającyCechy{
  int cecha;

  MinimalizującyJednocechowy(String imię, String nazwisko,
                             int partia, int cecha){
    super(imię, nazwisko, partia);
    this.cecha = cecha;
  }

  int wartośćKandydata(Kandydat k){
    return k.cechy[cecha - 1] * (-1);
  }
}

/* Każdej cesze kandydata przypisuje pewną wagę.
   Oddaje głos na tego spośród kandydatów, który posiada największą
   sumę po wszystkich cechach waga_cechy*cecha.
*/
class Wszechstronny extends UwzględniającyCechy{
  int[] wagi;

  Wszechstronny(String imię, String nazwisko,
                int partia, int[] wagi){
    super(imię, nazwisko, partia);
    this.wagi = wagi;
  }

  int wartośćKandydata(Kandydat k){
    int suma = 0;
    for(int i = 0; i < wagi.length && i < k.cechy.length; i++){
      suma += wagi[i] * k.cechy[i];
    }
    return suma;
  }
}
