package com.UserAsClient.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import com.UserAsClient.Main;
import com.helper.Crypter;
import com.helper.Parser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginSceneController {
    @FXML
    public TextField userName;

    @FXML
    public TextField password;

    private Stage stage;

    @FXML
    private void login(ActionEvent event) {
        event.consume();

        Boolean found = false;

        String user = userName.getText();
        String pass = password.getText();

        File file = new File("data/userAccount.txt");
        Scanner sc = null;

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            sc = new Scanner(file);

            // read each line
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                Map<String, String> data = Parser.parseRecord(line);

                if (Crypter.hashSHA256(user + pass + data.get("salt")).equals(data.get("hash"))) {
                    found = true;
                    break;
                }
            }

            // TODO: Replace this with referal to the main content once the FXML is done
            if (found) {
                System.out.println("User found");
            } else {
                System.out.println("User not found");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }

    @FXML
    private void moveToRegister(ActionEvent event) {
        event.consume();

        System.out.println(getClass());

        stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("registerScene.fxml"));
        try {
            Parent root = loader.load();
            RegisterSceneController controller = loader.getController();
            controller.setWrapText();
            Scene scene = new Scene(root, 600, 400);

            stage.setTitle("Register");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
