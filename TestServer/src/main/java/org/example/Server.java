package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.util.Pair;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends UnicastRemoteObject implements InterfaceRMI {
    private final String header;
    private final Map<String, User> usersMap;
    private final Console serverConsole;
    private final int notFound = -1;
    private final String questionsMainFilePath;
    private static int testID;
    private final Map<Integer, Test> testsMap;

    // Added fields for refactoring
    private final PersistenceManager persistenceManager;
    private final List<Question> cachedQuestions;
    public static final int QUESTIONS_PER_TEST = 5;

    public Server() throws RemoteException {
        super();
        header = "[SERVER]";
        serverConsole = new Console();
        questionsMainFilePath = "data/pytania.txt";
        
        // Concurrent maps
        usersMap = new ConcurrentHashMap<>();
        testsMap = new ConcurrentHashMap<>();
        
        // Single Responsibility Managers
        persistenceManager = new PersistenceManager();
        
        // Load state
        persistenceManager.loadState();
        
        // Cache questions
        cachedQuestions = Question.loadQuestions(questionsMainFilePath, Integer.MAX_VALUE); // Load all
        serverConsole.printLog(header, "Loaded " + cachedQuestions.size() + " questions into memory.");
    }

    private class PersistenceManager {
        private final File usersFile = new File("data/users.json");
        private final File testsFile = new File("data/tests.json");
        private final ObjectMapper mapper;

        public PersistenceManager() {
            mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
        }

        public void loadState() {
            try {
                if (usersFile.exists()) {
                    Map<String, User> loadedUsers = mapper.readValue(usersFile, new TypeReference<Map<String, User>>() {});
                    usersMap.putAll(loadedUsers);
                    // Ensure all loaded users are marked as logged out initially
                    for (User u : usersMap.values()) {
                        u.setLoggedIn(false);
                    }
                    serverConsole.printLog(header, "Loaded " + usersMap.size() + " users from JSON.");
                }
                if (testsFile.exists()) {
                    Map<Integer, Test> loadedTests = mapper.readValue(testsFile, new TypeReference<Map<Integer, Test>>() {});
                    testsMap.putAll(loadedTests);
                    serverConsole.printLog(header, "Loaded " + testsMap.size() + " tests from JSON.");
                    // Update testID to max existing + 1
                    testID = testsMap.keySet().stream().max(Integer::compareTo).orElse(-1) + 1;
                } else {
                    testID = 0;
                }
            } catch (Exception e) {
                serverConsole.printLog(header, "Error loading state: " + e.getMessage());
                testID = 0;
            }
        }

        public void saveUsers() {
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(usersFile, usersMap);
            } catch (Exception e) {
                serverConsole.printLog(header, "Error saving users: " + e.getMessage());
            }
        }

        public void saveTests() {
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(testsFile, testsMap);
            } catch (Exception e) {
                serverConsole.printLog(header, "Error saving tests: " + e.getMessage());
            }
        }

        public void appendToTestyTxt(String content) {
            try (FileWriter writer = new FileWriter("data/testy.txt", true)) {
                writer.write(content + System.lineSeparator());
                serverConsole.printLog(header, "Zapisano do pliku: data/testy.txt");
            } catch (IOException e) {
                serverConsole.printLog(header, "Wystąpił błąd podczas zapisu do pliku: " + e.getMessage());
            }
        }
    }

    @Override
    public Pair<Boolean, String> register(String username, String password, String email, String firstName, String surname) throws RemoteException {
        if (usersMap.containsKey(username)) {
            return new Pair<>(Boolean.FALSE, "User: " + username + " already exists. Please choose another nickname!");
        }
        User newUser = new User(username, password, email, firstName, surname);
        usersMap.put(username, newUser);
        persistenceManager.saveUsers();
        
        String message = "User: " + username + " has been registered!";
        serverConsole.printLog(header, message);
        return new Pair<>(Boolean.TRUE, message);
    }

    @Override
    public User login(String username, String password) throws RemoteException {
        User foundUser = usersMap.get(username);
        if (foundUser != null && foundUser.getPassword().equals(password)) {
            synchronized (foundUser) {
                if (!foundUser.getIsLoggedIn()) {
                    foundUser.setLoggedIn(true);
                    serverConsole.printLog(header, "User: " + username + " has been logged in!");
                    return foundUser;
                }
            }
        }
        serverConsole.printLog(header, "User: " + username + " does not exist or is logged in or incorrect password!");
        return null;
    }

    @Override
    public boolean logout(String username) throws RemoteException {
        User foundUser = usersMap.get(username);
        if (foundUser != null) {
            synchronized (foundUser) {
                foundUser.setLoggedIn(false);
            }
            serverConsole.printLog(header, "User: " + foundUser.getName() + " has disconnected!");
            return true;
        }
        serverConsole.printLog(header, "User: " + username + " does not exist");
        return false;
    }

    @Override
    public AbstractMap.SimpleImmutableEntry<Integer, Integer> createTest(User clientUser) throws RemoteException {
        if (cachedQuestions.isEmpty() || QUESTIONS_PER_TEST > cachedQuestions.size()) {
            return new AbstractMap.SimpleImmutableEntry<>(notFound, notFound);
        }

        List<Question> testQuestions = new ArrayList<>(cachedQuestions);
        Collections.shuffle(testQuestions);
        List<Question> selectedQuestions = new ArrayList<>();
        
        for(int i = 0; i < QUESTIONS_PER_TEST; i++) {
            Question q = testQuestions.get(i);
            selectedQuestions.add(new Question(q.getQuestion(), q.getAnswerA(), q.getAnswerB(), q.getAnswerC(), q.getCorrectAnswer()));
        }

        int currentTestID;
        synchronized (Server.class) {
            currentTestID = testID++;
        }

        Test test = new Test(selectedQuestions, currentTestID);
        test.setUser(clientUser);
        testsMap.put(currentTestID, test);
        
        for (Question question : selectedQuestions) {
            serverConsole.printLog(header, question.toString());
        }

        User user = usersMap.get(clientUser.getName());
        if (user != null) {
            user.getTestsID().add(currentTestID);
            persistenceManager.saveUsers();
        }
        
        persistenceManager.saveTests();

        return new AbstractMap.SimpleImmutableEntry<>(currentTestID, QUESTIONS_PER_TEST);
    }

    @Override
    public Integer receiveTestScore(Integer testID) throws RemoteException {
        Test test = testsMap.get(testID);
        if (test != null) {
            int totalPoints = 0;
            for (Question question : test.getQuestions()) {
                question.checkAnswer();
                totalPoints += question.getPoint();
            }
            test.setTestScore(totalPoints);
            test.setDate(LocalDateTime.now());
            
            // Save to DB and append to logs
            persistenceManager.saveTests();
            persistenceManager.appendToTestyTxt(test.toString());
            
            return totalPoints;
        }
        return notFound;
    }

    @Override
    public Question getTestQuestion(Integer testID, Integer questionID) throws RemoteException {
        Test test = testsMap.get(testID);
        if (test != null) {
            serverConsole.printLog(header, "Test: " + testID + " exists! Sending question: " + questionID);
            return test.getQuestions().get(questionID);
        }
        return null;
    }

    @Override
    public boolean sendTestQuestion(Integer testID, Integer questionID, String answer) throws RemoteException {
        Test test = testsMap.get(testID);
        if (test != null) {
            test.getQuestions().get(questionID).setUserAnswer(answer);
            serverConsole.printLog(header, "Test: " + testID + " got answer: " + answer + " Sending question: " + questionID);
            return true;
        }
        return false;
    }

    @Override
    public List<Result> getUsersResults(String username) throws RemoteException {
        User foundUser = usersMap.get(username);
        if (foundUser != null) {
            List<Result> results = new ArrayList<>();
            for (Integer id : foundUser.getTestsID()) {
                Test test = testsMap.get(id);
                if(test != null && test.getDate() != null) { // only processed tests
                    int questionCount = test.getQuestions().size();
                    int correctAnswerCount = test.getTestScore();
                    double correctnessPercentage = ((double) correctAnswerCount / questionCount) * 100;
                    Result result = new Result(id, test.getDate(), questionCount, correctAnswerCount, correctnessPercentage);
                    serverConsole.printLog(header, result.toString());
                    results.add(result);
                }
            }
            return results;
        }
        return null;
    }
}