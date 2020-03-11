package sample;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.*;
import java.net.URI;
import java.nio.CharBuffer;
import java.nio.file.*;
import java.util.*;



public class Controller {
    @FXML
    private Tab tabCreateNewUser;
    @FXML
    private Tab tabDataBase;
    @FXML
    private TextField textFieldUserName;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldBlackMatter;
    @FXML
    private TextField textFieldUserLevel;
    @FXML
    private CheckBox checkBoxVIP;
    @FXML
    private TextField textFieldDelete;

    @FXML
    private TextField textFieldUserNameInput;
    @FXML
    private TextField passwordFieldInput;
    @FXML
    private TextField textFieldEmailInput;
    @FXML
    private TextField textFieldBlackMatterInput;
    @FXML
    private TextField textFieldUserLevelInput;
    @FXML
    private CheckBox checkBoxVIPInput;

    // buttons
    @FXML
    private Button btnClear;
    @FXML
    private Button btnDelete;

    // labels
    @FXML
    private Label labelLog;

    // TableView
    @FXML
    private TableView tableViewUsers;
    @FXML
    private TableColumn<UserAccount, String> columnUserName;
    @FXML
    private TableColumn<UserAccount, String> columnPassword;
    @FXML
    private TableColumn<UserAccount, String> columnEmail;
    @FXML
    private TableColumn<UserAccount, Integer> columnBlackMatter;
    @FXML
    private TableColumn<UserAccount, Integer> columnUserLevel;
    @FXML
    private TableColumn<UserAccount, Boolean> columnVIP;

    // fields
    private ObservableList<UserAccount> usersData = FXCollections.observableArrayList();

    // methods
    public String getTextFieldUserName() {
        return textFieldUserName.getText();
    }

    public String getPasswordField() {
        return passwordField.getText();
    }

    public String getTextFieldEmail() {
        //TODO: try/catch on '@' and others (regular expression needed)
        return textFieldEmail.getText();
    }

    public int getTextFieldBlackMatter() {
        int i = Integer.parseInt(textFieldBlackMatter.getText());
        //TODO: try/catch NumberFormatException
        return i;
    }

    public boolean getCheckBoxVIP() {
        return checkBoxVIP.isSelected();
    }

    public int getTextFieldUserLevel() {
        int i = Integer.parseInt(textFieldUserLevel.getText());
        //TODO: try/catch NumberFormatException
        return i;
    }

    public void actionBtnClear() {
        labelLog.setText("All fields cleared");

        textFieldUserName.setText("");
        passwordField.setText("");
        textFieldEmail.setText("");
        checkBoxVIP.setSelected(false);
        textFieldBlackMatter.setText("");
        textFieldUserLevel.setText("");


        FadeTransition labelLogFadeInTransition = new FadeTransition(Duration.millis(500), labelLog);
        labelLogFadeInTransition.setFromValue(0.0);
        labelLogFadeInTransition.setToValue(1.0);

        FadeTransition labelLogFadeOutTransition = new FadeTransition(Duration.millis(500), labelLog);
        labelLogFadeOutTransition.setFromValue(1.0);
        labelLogFadeOutTransition.setToValue(0.0);

        PauseTransition pauseTransition = new PauseTransition(Duration.millis(2000));

        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(
                labelLogFadeInTransition,
                pauseTransition,
                labelLogFadeOutTransition
        );

        sequentialTransition.play();
    }

    public void actionBtnSubmit() {
        labelLog.setText("Submit successful");

        FadeTransition labelLogFadeInTransition = new FadeTransition(Duration.millis(500), labelLog);
        labelLogFadeInTransition.setFromValue(0.0);
        labelLogFadeInTransition.setToValue(1.0);

        FadeTransition labelLogFadeOutTransition = new FadeTransition(Duration.millis(500), labelLog);
        labelLogFadeOutTransition.setFromValue(1.0);
        labelLogFadeOutTransition.setToValue(0.0);

        PauseTransition pauseTransition = new PauseTransition(Duration.millis(2000));

        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(
                labelLogFadeInTransition,
                pauseTransition,
                labelLogFadeOutTransition
        );

        sequentialTransition.play();

        if (Main.accountList.getDataBaseHashMap().containsKey(getTextFieldUserName()) == false) {

            String path = "src\\sample\\database.txt";
            File dataBase = new File(path);
            try {
                FileWriter fileWriter = new FileWriter(dataBase, true);
                fileWriter.append(getTextFieldUserName() + " " + getPasswordField() + " " + getTextFieldEmail() + " " + getTextFieldBlackMatter() + " " + getTextFieldUserLevel() + " " + getCheckBoxVIP());
                fileWriter.append("\n");
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Main.accountList.getDataBaseHashMap().put(getTextFieldUserName(), new UserAccount(
                    getTextFieldUserName(),
                    getPasswordField(),
                    getTextFieldEmail(),
                    getTextFieldBlackMatter(),
                    getTextFieldUserLevel(),
                    getCheckBoxVIP()));

            //TODO: separate function of listing all objects
            for (Object o : Main.accountList.getDataBaseHashMap().keySet()) {
                System.out.println(Main.accountList.getDataBaseHashMap().get(o));
            }
            System.out.println("-----------------------");

        } else {
            //TODO: labelLog transition
            System.out.println("Account already exists");
        }

        //Path file = Paths.get("D:/Programmes files 7/GitHub/Repositories/JavaFX_test/src/sample/database.txt");
        //File dataBase = new File();

        //FileOutputStream outputStream = new FileOutputStream(dataBase, true);
//        try {
//            FileWriter fileWriter = new FileWriter("src/database.txt", true);
//            fileWriter.write(getTextFieldUserName() + " " + getPasswordField() + " " + getTextFieldEmail() + " " + getTextFieldBlackMatter() + " " + getTextFieldUserLevel() + " " + getCheckBoxVIP());
//            fileWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void refreshTableView() {
        int i = 0;
        usersData.removeAll(usersData);
        tableViewUsers.setItems(usersData);
        System.out.println();
        System.out.println();
        for (Object o : Main.accountList.getDataBaseHashMap().values()) {
            usersData.add((UserAccount) o);
            System.out.println(++i);
        }

        columnUserName.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("userName"));
        columnPassword.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("password"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("email"));
        columnBlackMatter.setCellValueFactory(new PropertyValueFactory<UserAccount, Integer>("blackMatter"));
        columnUserLevel.setCellValueFactory(new PropertyValueFactory<UserAccount, Integer>("userLevel"));
        columnVIP.setCellValueFactory(new PropertyValueFactory<UserAccount, Boolean>("userVIP"));

        System.out.println("loaded");
        tableViewUsers.setItems(usersData);

        System.out.println("set");
    }

    public void initFromFile() {
        String path = "src\\sample\\database.txt";
        File dataBase = new File(path);

        try {
            try {
                FileReader fileReader = new FileReader(dataBase);
            } catch (FileNotFoundException e) {
                FileWriter fileWriter = new FileWriter(dataBase);
            }
            FileReader fileReader = new FileReader(dataBase);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            StringBuffer stringBuffer = new StringBuffer();
//            int ch;
//            while (((ch=fileReader.read()) != -1) ) {
//                stringBuffer.append((char)ch);
//                fileReader
//            }

            s = bufferedReader.readLine();
            while (s != null) {
//                stringBuffer.append(s);
//                System.out.println(stringBuffer);
                System.out.println(s);

                ArrayList<String> arrayList = new ArrayList<String>();
                for (String retval : s.split(" ")) {
                    arrayList.add(retval);
                }
                if (Main.accountList.getDataBaseHashMap().containsKey(getTextFieldUserName()) == false) {
                    Main.accountList.getDataBaseHashMap().put(arrayList.get(0), new UserAccount(arrayList.get(0), arrayList.get(1), arrayList.get(2), Integer.parseInt(arrayList.get(3)), Integer.parseInt(arrayList.get(4)), Boolean.parseBoolean(arrayList.get(5))));
                }

//                    stringBuffer.delete(0, stringBuffer.length());
//                System.out.println(stringBuffer);
                s = bufferedReader.readLine();
            }

            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDataBase() {
        if (textFieldDelete.getText().equals("delete")) {
            String path = "src\\sample\\database.txt";
            File dataBase = new File(path);

            if (dataBase.delete()) {
                System.out.println("DataBase deleted");
            } else {
                System.out.println("File does not exist");
            }
            textFieldDelete.setText("");
        } else {
            //TODO: labelLog transition
            System.out.println("Check failed");
            textFieldDelete.setText("");
        }
    }
}
