package wybory;

/* Symuluje wyborcę w Bajtocji.
*/
abstract class Wyborca{
  String imię;
  String nazwisko;
  Wyborca(String imię, String nazwisko){
    this.imię = imię;
    this.nazwisko = nazwisko;
  }
/* Wybiera kandydata z tablicy list wyborczych 'listy', na którego oddał głos.
*/
  abstract Kandydat zagłosuj(Lista[] listy);
}
