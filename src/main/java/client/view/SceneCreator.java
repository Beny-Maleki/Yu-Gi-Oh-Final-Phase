package client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import client.model.enums.Menu;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class SceneCreator {
    public static Scene create(Menu menu) throws FileNotFoundException{
        try {
            Scene scene;
            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneCreator.class.getResource(menu.getAddress())));
            scene = new Scene(root);
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileNotFoundException();
        }

    }
}
