package wybory;

/* Symuluje działanie listy wyborczej partii w danym okręgu wyborczym w Bajtocji.
*/
class Lista{
  int partia;//ID partii
  int liczbaKandydatów; //liczba kandydatów dodanych do tablicy 'kandydaci'
  Kandydat[] kandydaci;

  Lista(int liczbaKandydatów, int partia){
    this.liczbaKandydatów = 0;
    kandydaci = new Kandydat[liczbaKandydatów];
    this.partia = partia;
  }

  void dodajKandydata(Kandydat k){
    k.partia = partia;
    k.nr = liczbaKandydatów + 1; //Kandydat otrzymuje kolejny numer
    kandydaci[liczbaKandydatów] = k;
    liczbaKandydatów++;
  }

  void dołączListę(Lista l){
    int nowaLiczbaKandydatów = this.liczbaKandydatów + l.liczbaKandydatów;
    Kandydat[] nowiKandydaci = new Kandydat[nowaLiczbaKandydatów];
    for(int i = 0; i < this.liczbaKandydatów; i++){
      nowiKandydaci[i] = this.kandydaci[i];
    }
    for(int i = 0; i < l.liczbaKandydatów; i++){
      nowiKandydaci[i + this.liczbaKandydatów] = l.kandydaci[i];
      nowiKandydaci[i].nr += this.liczbaKandydatów;
    }
    this.kandydaci = nowiKandydaci;
    this.liczbaKandydatów = nowaLiczbaKandydatów;
  }
}
