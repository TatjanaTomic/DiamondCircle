package model.game;

public class GameSimulation {

    private static GameSimulation instance = null;

    private GameSimulation() {

    }

    public static GameSimulation getInstance() {
        if(instance == null)
            instance = new GameSimulation();

        return instance;
    }


}
