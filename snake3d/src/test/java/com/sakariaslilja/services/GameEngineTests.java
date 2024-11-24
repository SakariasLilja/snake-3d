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
    void incrementScore() {
        GameEngine engine = new GameEngine(new GameModel(), null);
        int initialScore = engine.getScore();
        engine.incrementScore();
        int newScore = initialScore + 1;
        assertEquals(newScore, engine.getScore(), "The incrementScore method should work as expected");
    }

    @Test
    @DisplayName("GameEngine toGameModel")
    void toGameModel() {
        GameModel model = new GameModel();
        GameEngine engine = new GameEngine(model, null);

        engine.incrementScore();
        GameModel createdModel = engine.toGameModel();

        assertEquals(createdModel.seed, model.seed, "The toGameModel should work as expected");
        assertNotEquals(model.score, createdModel.score, "The score should be different after incrementation");
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("GameEngine getAvailableGridPositions")
    void getAvailableGridPositions() {
        GameEngine engine = new GameEngine(new GameModel(), null);
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
    
}
