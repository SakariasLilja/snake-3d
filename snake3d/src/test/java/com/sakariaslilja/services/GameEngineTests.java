package com.sakariaslilja.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sakariaslilja.entities.Apple;
import com.sakariaslilja.models.GameModel;
import com.sakariaslilja.models.Vector3D;

public class GameEngineTests {

    @Test
    @DisplayName("GameEngine incrementScore")
    public void incrementScore() {
        GameEngine engine = new GameEngine(new GameModel());
        int initialScore = engine.getScore();
        engine.incrementScore();
        int newScore = initialScore + 1;
        assertEquals(newScore, engine.getScore(), "The incrementScore method should work as expected");
    }

    @Test
    @DisplayName("GameEngine toGameModel")
    public void toGameModel() {
        GameModel model = new GameModel();
        GameEngine engine = new GameEngine(model);

        engine.incrementScore();
        GameModel createdModel = engine.toGameModel();

        assertEquals(createdModel.seed, model.seed, "The toGameModel should work as expected");
        assertNotEquals(model.score, createdModel.score, "The score should be different after incrementation");
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("GameEngine getAvailableGridPositions")
    public void getAvailableGridPositions() {
        GameEngine engine = new GameEngine(new GameModel());
        Vector3D position = new Vector3D(0, 0, 0);

        ArrayList<Apple> apples = new ArrayList<>();
        Apple apple = new Apple(position);
        apples.add(apple);

        ArrayList<Vector3D> allPositions = engine.getAvailableGridPositions();
        ArrayList<Vector3D> appleOccupied = engine.getAvailableGridPositions(apples);

        assertNotEquals(allPositions.size(), appleOccupied.size(), "Passing elements to remove should remove said elements");
        int index = allPositions.indexOf(position);
        assertNotEquals(-1, index, "The position should exist in the original array");
        assertNotEquals(allPositions.get(index), appleOccupied.get(index), "The position at the deleted index should not be the same");
    }

    @Test
    @DisplayName("GameEngine apple count")
    public void appleCount() {
        GameEngine engine = new GameEngine(new GameModel());

        assertEquals(0, engine.countApples(), "The number of apples should be 0 if not set to anything");

        ArrayList<Apple> apples = new ArrayList<>();
        int expectedAppleCount = 3;
        for (int i = 0; i < expectedAppleCount; i ++) {
            Apple apple = new Apple(new Vector3D(i, i, i));
            apples.add(apple);
        }
        engine.setApples(apples);

        assertEquals(expectedAppleCount, engine.countApples(), "The number of apples should be correct");
    }

    @Test
    @DisplayName("GameEngine increaseScore")
    public void increaseScore() {
        GameEngine engine = new GameEngine(new GameModel());
        int oldScore = engine.getScore();
        int increaseAmount = 5;
        engine.increaseScore(increaseAmount);

        assertEquals(oldScore + increaseAmount, engine.getScore(), "The score should be increased by the correct amount");
    }

    @Test
    @DisplayName("GameEngine gridPositions")
    public void gridPositions() {
        int width = 3;
        int height = 4;
        int depth = 7;

        GameModel model = new GameModel();
        model.worldWidth = width;
        model.worldHeight = height;
        model.worldDepth = depth;
        GameEngine engine = new GameEngine(model);

        int expectedSize = width * height * depth;

        assertEquals(expectedSize, engine.gridPositionCount(), "The number of grid positions should be correct");
    }

    @Test
    @DisplayName("GameEngine spawnApple")
    public void spawnApple() {
        GameModel model = new GameModel();
        GameEngine engine = new GameEngine(model);
        int oldAppleCount = engine.countApples();

        Apple apple = new Apple(new Vector3D(0,0,0));
        ArrayList<Apple> apples = new ArrayList<>();
        apples.add(apple);
        engine.setApples(apples);

        int appleLimit = 3;
        engine.spawnApple(appleLimit);

        assertEquals(oldAppleCount + 2, engine.countApples(), "The number of apples should be correct");

        for (int i = 0; i < 10; i++) {
            engine.spawnApple(appleLimit);
        }

        assertEquals(appleLimit, engine.countApples(), "The number of apples should not exceed the limit");

        int worldSize = model.worldDepth * model.worldHeight * model.worldWidth;
        int largeNumber = 2 * worldSize;

        for (int i = 0; i < largeNumber; i++) {
            engine.spawnApple(largeNumber);
        }

        assertEquals(worldSize, engine.countApples(), "The number of apples should not exceed world size");
    }

    @Test
    @DisplayName("GameEngine pause")
    public void pauseGame() {
        GameEngine engine = new GameEngine(new GameModel());

        int appleCount = engine.countApples();

        engine.togglePause();
        engine.update();

        assertEquals(appleCount, engine.countApples(), "Updating the game shouldn't spawn new apples if the game is paused");
    }
    
}
