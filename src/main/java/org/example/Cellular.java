package org.example;

import java.util.Random;

public class Cellular {

    private final int WIDTH;
    private final int HEIGHT;

    private int generation = 0;

    private static final Random random = new Random();

    private boolean[][] grid;

    public Cellular(int width, int height){
        this.HEIGHT = height;
        this.WIDTH = width;
        grid = new boolean[width][height];
    }

    public void fillRandom(){
        generation = 0;
        for (int x = 0; x < WIDTH;x++){
            for (int y = 0; y < HEIGHT;y++){
                grid[x][y] = random.nextBoolean();
            }
        }
    }

    public void step(){
        boolean[][] next = new boolean[WIDTH][HEIGHT];
        for (int w = 0; w < WIDTH; w++) {
            for (int h = 0; h < HEIGHT; h++) {
                int adj = getNeighbours(w,h);
                if (grid[w][h]){
                    if (adj < 2 || adj > 3){
                        next[w][h] = false;
                    } else {
                        next[w][h] = true;
                    }
                } else{
                    if (adj == 3){
                        next[w][h] = true;
                    }
                }
            }
        }
        grid = next;
        generation++;
    }

    private int getNeighbours(int w, int h){
        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0){
                    continue;
                }
                if (0 > w+i || w+i >= WIDTH){
                    continue;
                }
                if (0 > h+j || h+j >= HEIGHT){
                    continue;
                }
                if (grid[w+i][h+j]){
                    count++;
                }
            }
        }
        return count;
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public int getWidth(){
        return WIDTH;
    }

    public int getHeight(){
        return HEIGHT;
    }

    public int getGeneration() {
        return generation;
    }

    public void clear(){
        grid = new boolean[WIDTH][HEIGHT];
    }

}
