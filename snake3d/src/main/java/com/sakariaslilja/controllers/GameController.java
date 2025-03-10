package com.sakariaslilja.controllers;

import com.sakariaslilja.App;
import com.sakariaslilja.IConstants;
import com.sakariaslilja.models.GameModel;
import com.sakariaslilja.models.HighscoreModel;
import com.sakariaslilja.services.GameClock;
import com.sakariaslilja.services.GameEngine;
import com.sakariaslilja.services.GamesService;
import com.sakariaslilja.services.HighscoresService;
import com.sakariaslilja.services.Renderer;

import javafx.event.EventHandler;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * Controller for the game scene.
 */
public class GameController implements IConstants {

    @FXML
    Canvas gameCanvas;

    @FXML
    VBox bgoverlay;

    @FXML
    Button resumebtn, savebtn, savequitbtn;

    @FXML
    Label score;

    @FXML
    Label pauseLabel;

    GameEngine engine;
    Renderer renderer;

    long startTime = System.nanoTime();
    GameClock gameClock;
    
    /**
     * Method called when the scene is loaded.
     */
    public void initialize() {
        gameCanvas.setWidth(App.getWidth());
        gameCanvas.setHeight(App.getHeight());

        resumebtn.setPrefWidth(0.3 * App.getWidth());
        savebtn.setPrefWidth(0.3 * App.getWidth());
        savequitbtn.setPrefWidth(0.3 * App.getWidth());

        resumebtn.setPrefHeight(0.12 * App.getHeight());
        savebtn.setPrefHeight(0.12 * App.getHeight());
        savequitbtn.setPrefHeight(0.12 * App.getHeight());
        
        this.engine = App.getEngine();
        engine.setGameController(this);
        if (engine.isGameOver()) { pauseLabel.setText("Game Over!"); }
        this.renderer = new Renderer(gameCanvas.getGraphicsContext2D(), engine);

        this.gameClock = new GameClock(engine, renderer, score);
        gameClock.start();

        EventHandler<KeyEvent> buttonPressed = e -> {
            if (e.getCode().equals(KeyCode.ESCAPE) || e.getCode().equals(KeyCode.P)) { this.triggerPause(); }
            else { engine.doButtonAction(e.getCode()); }
        };

        bgoverlay.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (oldScene != null) {
                oldScene.removeEventHandler(KeyEvent.KEY_PRESSED, buttonPressed);
            }
            if (newScene != null) {
                newScene.addEventHandler(KeyEvent.KEY_PRESSED, buttonPressed);
            }
        });
    }

    /**
     * Navigates via [App] to mainpage.fxml.
     * @throws IOException The file mainpage.fxml wasn't found/couldn't be read.
     */
    @FXML
    private void gotoMainPage() throws IOException {
        App.setRoot("mainpage");
    }

    /**
     * Saves the game.
     * Displays icon to display if the game was successfully saved.
     */
    @FXML
    private void saveGame() {
        GameModel model = engine.toGameModel();
        boolean saveSuccess = GamesService.saveGame(model);
        if (saveSuccess) {
            // TODO: Add icons to display save status
            System.out.println("Game saved successfully!");
        }

        else {
            System.out.println("Error when saving game.");
        }
    }

    /**
     * Saves the game with {@code saveGame}.
     * Redirects the player to the main page.
     */
    @FXML
    private void saveAndQuit() throws IOException {
        saveGame();
        App.setRoot("mainpage");
    }

    /**
     * Triggers the visibility of the pause overlay and
     * pauses the game.
     */
    @FXML
    private void triggerPause() {
        bgoverlay.setVisible(!bgoverlay.isVisible());
        engine.togglePause();
    }

    /**
     * Saves the highscore of the world
     */
    private void saveHighscore() {
        HighscoreModel model = engine.gameHighscore();
        HighscoresService.addHighscore(model);
    }

    /**
     * Saves the highscore state of the world,
     * the world itself and pauses the game screen.
     */
    public void playerDied() {
        saveGame();
        saveHighscore();
        pauseLabel.setText("Game Over!");
        this.triggerPause();
    }
    
}
