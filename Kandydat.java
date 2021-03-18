package wybory;

class Kandydat{
  String imię;
  String nazwisko;
  int cechy[];
  int partia; //ID partii. 0 oznacza brak przynależności partyjnej
  int nr; //nr na liście wyborczej

  Kandydat(String imię, String nazwisko, int[] cechy){
    this.imię = imię;
    this.nazwisko = nazwisko;
    this.cechy = cechy;
    this.partia = 0;
    this.nr = 0;
  }
}
