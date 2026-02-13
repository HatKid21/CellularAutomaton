package org.example;

import com.raylib.java.Raylib;

public class App {

    private final Raylib raylib;
    private final InputHandler inputHandler;
    private final Cellular cellular;
    private final Renderer renderer;

    private boolean showGeneration;

    private double stepsPerSecond = 60;

    private boolean isPaused;
    private boolean isRunning;

    public App(){
        this.isPaused = true;
        this.isRunning = false;
        this.cellular = new Cellular(50,50);
        cellular.fillRandom();
        this.inputHandler = new InputHandler();
        this.raylib = new Raylib(800,600,"Cellular Automaton by HatKid");
        raylib.core.SetTargetFPS(60);
        this.renderer = new Renderer(raylib,5);
        showGeneration = true;
    }

    public void run(){
        isRunning = true;

        double acc = 0;

        while (!raylib.core.WindowShouldClose()){
            double dt = raylib.core.GetFrameTime();
            System.out.println(stepsPerSecond);
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
                case SPEED_UP: {
                    stepsPerSecond++;
                    break;
                }
                case SPEED_DOWN:{
                    if (stepsPerSecond != 0) {
                        stepsPerSecond--;
                    }
                    break;
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
                stepsPerSecond++;
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
