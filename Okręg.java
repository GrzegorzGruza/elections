package wybory;

/* Symuluje działanie okręgu wyborczego w Bajtocji.
*/
class Okręg{
  int nrOkręgu;
  int liczbaWyborców;//liczba wyborców dodanych już do tablicy 'wyborcy'
  int liczbaList;//liczba list dodanych już do tablicy 'listy'
  Wyborca[] wyborcy;
  Lista[] listy;//listy[i] przetrzymuje listę wyborczą partii o ID równym 'i'
  boolean połączonyZInnymOkręgiem;

  Okręg(int nrOkręgu, int liczbaWyborców, int liczbaList){
    this.nrOkręgu = nrOkręgu;
    this.liczbaWyborców = 0;
    this.liczbaList = 0;
    wyborcy = new Wyborca[liczbaWyborców];
    listy = new Lista[liczbaList];
    połączonyZInnymOkręgiem = false;
  }

  void dodajWyborcę(Wyborca w){
    wyborcy[liczbaWyborców] = w;
    liczbaWyborców++;
  }

  void dodajListę(Lista l){
    listy[liczbaList] = l;
    liczbaList++;
  }

  void dołączOkręg(Okręg o){
    int nowaLiczbaWyborców = this.liczbaWyborców + o.liczbaWyborców;
    Wyborca[] nowiWyborcy = new Wyborca[nowaLiczbaWyborców];
    for(int i = 0; i < this.liczbaWyborców; i++){
      nowiWyborcy[i] = this.wyborcy[i];
    }
    for(int i = 0; i < o.liczbaWyborców; i++){
      nowiWyborcy[i + this.liczbaWyborców] = o.wyborcy[i];
    }
    this.wyborcy = nowiWyborcy;
    this.liczbaWyborców = nowaLiczbaWyborców;

    for(int i = 0; i < liczbaList; i++){
      (this.listy[i]).dołączListę(o.listy[i]);
    }
  }
}
