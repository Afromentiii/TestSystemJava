# W jaki sposób piszemy zmienne i funkcje.
Używamy CamelCase.

# Bibloteki.
JavaFX,
Tui.

# Must have.
Web Panel | Konsola, 
Baza danych, 
RMI.

# Server.
Baza danych,
Prosty webpanel.

# Notatki
Funkcje udostępniane przez RMI to:

User login(String username, String password) throws RemoteException;
String register(String username, String password, String email, String firstName, String surname) throws RemoteException;
boolean logout(User loggedUser) throws RemoteException;
Test createTest(int howManyQuestions) throws RemoteException;
int receiveTestScore(Test test) throws RemoteException;
login : bazuje na prywatnej Mapie gdzie kluczem jest pseudonim a wartoscia Obiekt User, pozwala zalogowac sie istniejacemu uzytkownikowi o ile nie jest zalogowany
register : tworzy uzytkownika i dodaje go do mapy o ile nie istnieje taki username

logout : przyjmuje od klienta Obiekt User i wylogowuje go ustawiajac zmienna setLoggedIn na false
createTest : tworzy i zwraca test na podstawie pytan utworzonych przez klase Question wczytanych z pliku pytania.txt
receiveTestScore : przyjmuje Test sprawdza go i zwraca wynik oraz zapisuje go do listy testow

Serwer posiada swoja konsole (Klasa:Console) ktora wyswietla komunikaty w konsoli, mozna dodac np frontend po stronie serwera zeby mial okienkowy podglad
Przydaloby sie zrobic np statystyki danego uzytkownika, podglad wykonaych testow itp
Na razie zostala utworzona tymczasowa funkcja do przeprowadzenia testu.
