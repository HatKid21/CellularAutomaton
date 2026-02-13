package org.example;

import com.raylib.java.Raylib;

public class App {

    private final Raylib raylib;
    private final InputHandler inputHandler;
    private final Cellular cellular;
    private final Renderer renderer;

    private boolean showGeneration;

    private double stepsPerSecond = 60;

    private final int FPS = 60;
    private boolean isPaused;
    private boolean isRunning;

    public App(){
        this.isPaused = true;
        this.isRunning = false;
        this.cellular = new Cellular(150,110);
        cellular.fillRandom();
        this.inputHandler = new InputHandler();
        this.raylib = new Raylib(800,600,"Cellular Automaton by HatKid");
        raylib.core.SetTargetFPS(FPS);
        this.renderer = new Renderer(raylib,5);
        showGeneration = true;
    }

    public void run(){
        isRunning = true;

        double acc = 0;

        while (!raylib.core.WindowShouldClose()){
            double dt = raylib.core.GetFrameTime();
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
                    break;
                }
                case QUIT: {
                    raylib.core.CloseWindow();
                    return;
                }
                case null, default :{
                    break;
                }
            }
            if (inputHandler.isSpeedDownHeld()){
                if (stepsPerSecond > 0) {
                    stepsPerSecond--;
                }
            }
            if (inputHandler.isSpeedUpHeld()){
                if (stepsPerSecond != 60) {
                    stepsPerSecond++;
                }
            }

            if (shouldStep && isPaused){
                cellular.step();
            }

            if (!isPaused){
                acc += dt;
                if (acc >= 1.0 / stepsPerSecond){
                    cellular.step();
                    acc -= 1.0 / stepsPerSecond;
               }
            } else{
                acc = 0;
            }

            if (showGeneration){
                renderer.drawGeneration(cellular);
            }

            if (isPaused){
                renderer.drawPaused(800);
            }

            renderer.drawSpeed(stepsPerSecond);
            drawNextStep();

        }
        raylib.core.CloseWindow();
    }

    private void drawNextStep(){
        renderer.beginFrame();
        renderer.drawGrid(cellular);
        renderer.endFrame();
    }

}
