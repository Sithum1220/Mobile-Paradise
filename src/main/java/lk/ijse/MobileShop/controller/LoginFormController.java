package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.MobileShop.utill.Navigation;

import java.io.IOException;

public class LoginFormController {
    public JFXButton btnLestGo;
    public Pane S1;
//    public Pane S2;
    public Label txtWelcome;

    public void btnOnActionLestGo(ActionEvent actionEvent) throws IOException {
        txtWelcome.setVisible(false);
        btnLestGo.setVisible(false);
        System.out.println("click");
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScene2.fxml"));
        Scene scene = btnLestGo.getScene();
        root.translateYProperty().set(scene.getHeight());

        S1.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            S1.getChildren().remove(S1);
        });
        timeline.play();
    }

    public void closeAction(MouseEvent mouseEvent) {
        Navigation.exit();
    }
}
