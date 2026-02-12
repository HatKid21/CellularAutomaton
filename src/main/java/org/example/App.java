package org.example;

import com.raylib.java.Raylib;

public class App {

    private final Raylib raylib;
    private final InputHandler inputHandler;
    private final Cellular cellular;
    private final Renderer renderer;

    private boolean isPaused = false;
    private boolean isRunning = false;

    public App(){
        this.cellular = new Cellular(50,50);
        cellular.fillRandom();
        this.inputHandler = new InputHandler();
        this.raylib = new Raylib(800,600,"Cellular Automata by HatKid");
        raylib.core.SetTargetFPS(60);
        this.renderer = new Renderer(raylib,5);
        renderer.showGeneration();
    }

    public void run(){
        isRunning = true;
        while (!raylib.core.WindowShouldClose()){
            InputState state = inputHandler.update(raylib);
            boolean shouldStep = false;
            switch (state){
                case CLEAR : {
                    cellular.clear();
                    break;
                }
                case RANDOMIZE: {
                    cellular.fillRandom();
                    break;
                }
                case TOGGLE_PAUSE: {
                    isPaused = !isPaused;
                    break;
                }
                case ONE_STEP: {
                    if (isPaused){
                        shouldStep = true;
                    }
                }
                case null, default :{
                    break;
                }
            }

            if (shouldStep || !isPaused){
                cellular.step();
            }

            drawNextStep();

        }
    }

    private void drawNextStep(){
        renderer.beginFrame();
        renderer.drawGrid(cellular);
        renderer.endFrame();
    }

}
