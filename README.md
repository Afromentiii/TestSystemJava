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

# 📚 Funkcje udostępniane przez RMI

## 🔐 Autoryzacja

### `User login(String username, String password) throws RemoteException`
- Bazuje na prywatnej mapie, gdzie kluczem jest pseudonim, a wartością obiekt `User`.
- Pozwala zalogować się istniejącemu użytkownikowi, o ile **nie jest zalogowany**.

### `String register(String username, String password, String email, String firstName, String surname) throws RemoteException`
- Tworzy użytkownika i dodaje go do mapy, o ile **nie istnieje taki `username`**.

### `boolean logout(User loggedUser) throws RemoteException`
- Przyjmuje od klienta obiekt `User` i wylogowuje go, ustawiając zmienną `setLoggedIn` na `false`.

---

## 🧪 Obsługa testów

### `Test createTest(int howManyQuestions) throws RemoteException`
- Tworzy i zwraca test na podstawie pytań utworzonych przez klasę `Question`.
- Pytania są wczytywane z pliku `pytania.txt`.

### `int receiveTestScore(Test test) throws RemoteException`
- Przyjmuje test, sprawdza go i zwraca wynik.
- Zapisuje wynik do listy testów.

---

## 🖥️ Informacje dodatkowe

- Serwer posiada swoją konsolę (klasa: `Console`), która wyświetla komunikaty w konsoli.
- Można dodać np. frontend po stronie serwera, żeby miał okienkowy podgląd.
- Przydałoby się zrobić np. **statystyki danego użytkownika**, **podgląd wykonanych testów** itp.
- Na razie została utworzona **tymczasowa funkcja do przeprowadzenia testu**.

---
