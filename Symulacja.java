package wybory;

/* Klasa przeprowadająca symulację wyborów.
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;

class Symulacja{

  static void niePodanoPlikuZDanymi(){
    System.out.println("Preferowany sposób wywołania programu to:");
    System.out.println("java Symulacja ścieżka_do_pliku_z_danymi\n");
    System.out.println("Nie podano ścieżki do pliku z danymi.");
    System.out.println("Proszę wpisać dane ręcznie.");
  }

  static void zaDużoArgumentówProgramu(){
    System.out.println("Preferowany sposób wywołania programu to:");
    System.out.println("java Symulacja ścieżka_do_pliku_z_danymi\n");
    System.out.println("Podano za dużą liczbę argumentów programu.");
    System.out.println("Proszę wpisać dane ręcznie.");
  }

/* Spradza czy podano odpowiedznią liczbę argumentów programu, aby móc
   wczytać dane z pliku. W wypadku nieprawidłowości wypisuje
   niePodanoPlikuZDanymi lub zaDużoArgumentówProgramu i oczekuje wpisania
   danych ręcznie.
*/
  static Scanner dajWejście(String[] args) throws FileNotFoundException{
    Scanner wejście;
    if(args.length == 0){
      niePodanoPlikuZDanymi();
      wejście = new Scanner(System.in);
    }else if(args.length > 1){
      zaDużoArgumentówProgramu();
      wejście = new Scanner(System.in);
    }else{
      wejście = new Scanner(new File(args[0]));
    }
    return wejście;
  }

/* Wczytuje pary okręgów do scalenia i zwraca tablicę int[licz. scalanych][2],
   czyli pary okręgów do scalenia.
*/
  static int[][] pobierzScalane(Scanner wejście){
    int liczbaScalanych = wejście.nextInt();
    StringBuffer paraStrBuff;
    String[] para = new String[2];
    int[][] doPołączenia = new int[liczbaScalanych][2];
    for(int i = 0; i < liczbaScalanych; i++){
      paraStrBuff = new StringBuffer(wejście.next());
      paraStrBuff.delete(0, 1);
      paraStrBuff.delete(paraStrBuff.length() - 1, paraStrBuff.length());
      para = (paraStrBuff.toString()).split(",");
      doPołączenia[i][0] = Integer.parseInt(para[0]);
      doPołączenia[i][1] = Integer.parseInt(para[1]);
    }
    return doPołączenia;
  }

/* Wczytuje z wejścia i zwraca tablicę z nazwami partii.
*/
  static String[] pobierzNazwyPartii(Scanner wejście){
    wejście.nextLine(); //ignorujemy enter po wczytywaniu intów z pobierzScalane
    String[] nazwyPartii = wejście.nextLine().split(" ");
    return nazwyPartii;
  }

/* Wczytuje z wejścia i zwraca w postaci tablicy budżety poszczególnych partii.
*/
  static int[] pobierzBudżety(Scanner wejście, int p){
    int budżety[] = new int[p];
    for(int i = 0; i < p; i++)
      budżety[i] = wejście.nextInt();
    return budżety;
  }

/* Wczytuje z wejścia typy partii. Dodaje partie do wyborów.
*/
  static void pobierzPartie(Scanner wejście, Wybory wybory, int[] budżety, int p){
    String typ;
    for(int i = 0; i < p; i++){
      typ = wejście.next();
      if(typ.equals("R"))
        wybory.dodajPartię(new PartiaRozmach(budżety[i], i + 1));
      else if(typ.equals("S"))
        wybory.dodajPartię(new PartiaSkromna(budżety[i], i + 1));
      else if(typ.equals("W"))
        wybory.dodajPartię(new PartiaWłasna(budżety[i], i + 1));
      else if(typ.equals("Z"))
        wybory.dodajPartię(new PartiaZachłannie(budżety[i], i + 1));
    }
  }

/* Wczytuje z wejścia i zwraca w postaci tablicy liczbę wyborców w okręgach.
*/
  static int[] pobierzLiczbyWyborców(Scanner wejście, int n){
    int[] liczbaWyborców = new int[n];
    for(int i = 0; i < n; i++)
      liczbaWyborców[i] = wejście.nextInt();
    return liczbaWyborców;
  }

/* Wczytuje z wejścia i dodaje do wyborów (do odpowiednich list partyjnych
  w odpowiednich okręgach) kandydatów.
*/
  static void pobierzKandydatów(Scanner wejście, Wybory wybory,
                                int n, int p, int c, int[] liczbaWyborców){
    String imię, nazwisko, nazwaPartii;
    int numerOkręgu, pozycjaNaLiście;
    int[] cechy;
    for(int o = 1; o <= n; o++){
      Okręg okręg = new Okręg(o, liczbaWyborców[o - 1], p);
      for(int l = 1; l <= p; l++){
        Lista lista = new Lista(liczbaWyborców[o - 1]/10, l);
        for(int k = 1; k <= liczbaWyborców[o - 1]/10; k++){
          imię = wejście.next();
          nazwisko = wejście.next();
          numerOkręgu = wejście.nextInt();
          nazwaPartii = wejście.next();
          pozycjaNaLiście = wejście.nextInt();
          cechy = new int[c];
          for(int cecha = 0; cecha < c; cecha++){
            cechy[cecha] = wejście.nextInt();
          }
          Kandydat kandydat = new Kandydat(imię, nazwisko, cechy);
          lista.dodajKandydata(kandydat);
        }
        okręg.dodajListę(lista);
      }
      wybory.dodajOkręg(okręg);
    }
  }

/* Wczytuje z wejścia i zwraca jako wynik wyborce typu ŻelaznyElektoratPartyjny.
*/
  static Wyborca WyborcaŻEP(String imię, String nazwisko,
                            Scanner wejście, Map<String, Integer> partiaID){
    int partia = partiaID.get(wejście.next());
    return new ŻelaznyElektoratPartyjny(imię, nazwisko, partia);
  }

/* Wczytuje z wejścia i zwraca jako wynik wyborce typu ŻelaznyElektoratKandydata.
*/
  static Wyborca WyborcaŻEK(String imię, String nazwisko,
                            Scanner wejście, Map<String, Integer> partiaID){
    int partia = partiaID.get(wejście.next());
    int nr = wejście.nextInt();
    return new ŻelaznyElektoratKandydata(imię, nazwisko, partia, nr);
  }

/* Wczytuje z wejścia i zwraca jako wynik wyborce typu MinimalizującyJednocechowy.
*/
  static Wyborca WyborcaMiJ(String imię, String nazwisko, Scanner wejście,
                            Map<String, Integer> partiaID, boolean partyjny){
    int cecha = wejście.nextInt();
    int partia = 0;
    if(partyjny){
      partia = partiaID.get(wejście.next());
    }
    return new MinimalizującyJednocechowy(imię, nazwisko, partia, cecha);
  }
/* Wczytuje z wejścia i zwraca jako wynik wyborce typu MaksymalizującyJednocechowy.
*/
  static Wyborca WyborcaMaJ(String imię, String nazwisko, Scanner wejście,
                            Map<String, Integer> partiaID, boolean partyjny){
    int cecha = wejście.nextInt();
    int partia = 0;
    if(partyjny){
      partia = partiaID.get(wejście.next());
    }
    return new MaksymalizującyJednocechowy(imię, nazwisko, partia, cecha);
  }

/* Wczytuje z wejścia i zwraca jako wynik wyborce typu Wszechstronny.
*/
  static Wyborca WyborcaW(String imię, String nazwisko, int c, Scanner wejście,
                          Map<String, Integer> partiaID, boolean partyjny){
    int wagi[] = new int[c];
    for(int i = 0; i < c; i++) wagi[i] = wejście.nextInt();
    int partia = 0;
    if(partyjny){
      partia = partiaID.get(wejście.next());
    }
    return new Wszechstronny(imię, nazwisko, partia, wagi);
  }

/* Wczytuje wyborów z wejścia i dodaje do odpowiednich okręgów.
*/
  static void pobierzWyborców(Scanner wejście, Wybory wybory, int n, int c,
                              Map<String, Integer> partiaID, int[] liczbaWyborców)
                              throws Exception{
    String imię, nazwisko;
    int nrOkręgu, typWyborcy;
    for(int o = 1; o <= n; o++){
      for(int w = 1; w <= liczbaWyborców[o - 1]; w++){
        imię = wejście.next();
        nazwisko = wejście.next();
        nrOkręgu = wejście.nextInt();
        typWyborcy = wejście.nextInt();
        if(typWyborcy == 1)
          wybory.okręgi[o - 1].dodajWyborcę(
            WyborcaŻEP(imię, nazwisko, wejście, partiaID));
        else if(typWyborcy == 2)
          wybory.okręgi[o - 1].dodajWyborcę(
            WyborcaŻEK(imię, nazwisko, wejście, partiaID));
        else if(typWyborcy == 3)
          wybory.okręgi[o - 1].dodajWyborcę(
            WyborcaMiJ(imię, nazwisko, wejście, partiaID, false));
        else if(typWyborcy == 4)
          wybory.okręgi[o - 1].dodajWyborcę(
            WyborcaMaJ(imię, nazwisko, wejście, partiaID, false));
        else if(typWyborcy == 5)
          wybory.okręgi[o - 1].dodajWyborcę(
            WyborcaW(imię, nazwisko, c, wejście, partiaID, false));
        else if(typWyborcy == 6)
          wybory.okręgi[o - 1].dodajWyborcę(
            WyborcaMiJ(imię, nazwisko, wejście, partiaID, true));
        else if(typWyborcy == 7)
          wybory.okręgi[o - 1].dodajWyborcę(
            WyborcaMaJ(imię, nazwisko, wejście, partiaID, true));
        else if(typWyborcy == 8)
          wybory.okręgi[o - 1].dodajWyborcę(
            WyborcaW(imię, nazwisko, c, wejście, partiaID, true));
        else
          throw new Exception();
      }
    }
  }

/* Na podstawie tablicy z nazwami partii tworzy mapę, której kluczami są
   nazwy partii, a wartościami ID nadane partiom.
*/
  static Map<String, Integer> mapaNazw(int p, String[] nazwyPartii){
    Map<String, Integer> partiaID = new HashMap<>();
    for(int i = 1; i <= p; i++) partiaID.put(nazwyPartii[i - 1], i);
    return partiaID;
  }

/* Pobiera z wejścia i zwraca jako wynik tablicę działań,
   które mogą być wykonane przez partie.
*/
  static int[][] pobierzDziałania(Scanner wejście, int d, int c){
    int[][] działania = new int[d][c];
    for(int i = 0; i < d; i++){
      for(int j = 0; j < c; j++){
        działania[i][j] = wejście.nextInt();
      }
    }
    return działania;
  }

/* Wykonuje połączenia okręgów zadane tablicą int[licz. połączń][2] doPołączenia.
*/
  static void wykonajPołączeniaOkręgów(Wybory wybory,int[][] doPołączenia){
    for(int i = 0; i < doPołączenia.length; i++)
      wybory.połączOkręgi(doPołączenia[i][0], doPołączenia[i][1]);
  }

  public static void main(String[] args){
    try{
      Scanner wejście = dajWejście(args);
      int n = wejście.nextInt(); //liczba podstawowych okręgów
      int p = wejście.nextInt(); //liczba partii
      int d = wejście.nextInt(); //liczba działań
      int c = wejście.nextInt(); //liczba cech kandydatów
      Wybory wybory = new Wybory(n, p);

      int[][] doPołączenia = pobierzScalane(wejście); //2 linia wejścia
      String[] nazwyPartii = pobierzNazwyPartii(wejście);//3 linia wejścia
      wybory.nazwyPartii = nazwyPartii;
      Map<String, Integer> partiaID = mapaNazw(p, nazwyPartii);
      int budżety[] = pobierzBudżety(wejście, p);//4 linia wejścia
      pobierzPartie(wejście, wybory, budżety, p);//5 linia wejścia
      int liczbaWyborców[] = pobierzLiczbyWyborców(wejście, n);//6 linia wejścia
      pobierzKandydatów(wejście, wybory, n, p, c, liczbaWyborców);//kolejne linie 1
      pobierzWyborców(wejście, wybory, n, c, partiaID, liczbaWyborców);//kolejne linie 2
      int[][] działania = pobierzDziałania(wejście, d, c);//ostatnie d wierszy
      wykonajPołączeniaOkręgów(wybory, doPołączenia);
      wybory.kampania(działania);

      MetodaHN hn = new MetodaHN();
      MetodaDH dh = new MetodaDH();
      MetodaSL sl = new MetodaSL();
      System.out.println("Metoda Hare’a-Niemeyera:\n" +
                         wybory.przeprowadźWybory(hn).toString(nazwyPartii));
      System.out.println("Metoda D'Hondta:\n" +
                         wybory.przeprowadźWybory(dh).toString(nazwyPartii));
      System.out.print("Metoda Sainte-Laguë:\n" +
                         wybory.przeprowadźWybory(sl).toString(nazwyPartii));
     }
     catch(FileNotFoundException e){
       System.out.println("Podono złą ścieżkę do pliku z danymi");
     }
     catch(Exception e){
       System.out.println("Dane niemożliwe do zinterpretowania.");
     }

  }
}
