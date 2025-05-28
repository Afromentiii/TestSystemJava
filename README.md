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

# 📚 System Zdalnego Testowania – Dokumentacja

## 📘 Opis funkcjonalności RMI

### 🔐 Autoryzacja i Rejestracja

#### `User login(String username, String password) throws RemoteException`
- Loguje istniejącego użytkownika.
- Dane użytkowników przechowywane są w mapie `Map<String, User>`, gdzie kluczem jest `username`.
- Użytkownik może się zalogować tylko, jeśli:
  - Istnieje w systemie.
  - Nie jest już zalogowany.

#### `String register(String username, String password, String email, String firstName, String surname) throws RemoteException`
- Rejestruje nowego użytkownika, o ile dany `username` nie jest już zajęty.
- Tworzy obiekt `User` i dodaje go do mapy użytkowników.

#### `boolean logout(User loggedUser) throws RemoteException`
- Wylogowuje użytkownika.
- Ustawia pole `loggedIn` w obiekcie `User` na `false`.

---

### 📋 Obsługa Testów

#### `Test createTest(int howManyQuestions) throws RemoteException`
- Tworzy test złożony z określonej liczby pytań.
- Pytania są losowane z pliku `pytania.txt`, wykorzystując klasę `Question`.

#### `int receiveTestScore(Test test) throws RemoteException`
- Ocenia przesłany test.
- Zwraca liczbę zdobytych punktów.
- Wynik jest zapisywany do historii testów danego użytkownika.

---

### 🖥️ Funkcje dodatkowe

- **Konsola serwera (`Console`)**:
  - Wyświetla komunikaty o działaniach użytkowników i systemu.
- Możliwość dodania GUI po stronie serwera, np.:
  - Podgląd aktywności użytkowników.
  - Panel administracyjny.

---

### 🧪 Planowane rozszerzenia

- Statystyki użytkownika:
  - Liczba wykonanych testów.
  - Średni wynik.
  - Historia odpowiedzi.
- Szczegółowy podgląd wykonanych testów.
- Kategoryzacja testów i limity czasu.
- System powiadomień lub rankingów.

---

### ⚙️ Uwagi developerskie

- Istnieje tymczasowa metoda do przeprowadzania testu – wymaga rozbudowy.
- System powinien zostać rozszerzony o:
  - Obsługę wyjątków (np. błędne logowanie, rejestracja duplikatu).
  - Trwałe przechowywanie danych (np. z użyciem bazy danych).

