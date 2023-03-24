import java.util.*;

public class PerlinNoise {
    static int worldSize = (int)(Math.pow(2, 6) + 1);
    static final long seed = 123;
    static float octaveStrength = 1;
    
    public static void main(String[] args) {
        PerlinNoise noise = new PerlinNoise();
        System.out.println(Arrays.toString(seedGeneration(seed)));
        noise.findPoint(0.2f);
    }
    
    void findPoint(float step) {
        double[] pGen = perlinGeneration(4, seedGeneration(seed));
        double[] finalNoise = new double[worldSize];
        
        for (float x = 0; x < (worldSize - 1) * step; x += step) {
            int x1 = (int)Math.floor(x);
            int x2 = (int)Math.floor(x) + 1;
            double y1 = pGen[x1];
            double y2 = pGen[x2];
            
            int i = (int)(x / step);
            finalNoise[i] += (y1 + (x - x1) * ((y2 - y1) / (x2 - x1)));
        }
        
        finalNoise[worldSize - 1] = finalNoise(0);
        System.out.println(Arrays.toString(finalNoise));
    }
    
    static double[] perlinGeneration(int octaveCount, double[] seedMap) {
        double[] perlinMap = new double[worldSize];
        
        for (int x = 0; x < worldSize; x++) {
            perlinMap[x] = seedMap[0];
        }
        
        for (int o = 1; o <= octaveCount; o++) {
            int pitch = (int)Math.pow(2, o);
            float strength = 1 / (o * octaveStrength);
            
            for (int x = 0; x < worldSize - 1; x++) {
                if (x % (worldSize / pitch) == 0) {
                    perlinMap[x] += seedMap[x] * strength;
                }
                else {
                    int x1 = x - (x % pitch);
                    int x2 = x + (pitch - (x % pitch));
                    double y1 = seedMap[x1];
                    double y2 = seedMap[x2];
                    perlinMap[x] += (y1 + (x - x1) * ((y2 - y1) / (x2 - x1))) * strength;
                }
            }
            
            for (int x = 0; x < worldSize; x++) {
                perlinMap[x] /= octaveCount * strength;
            }
        }
        
        return perlinMap;
    }
    
    static double[] seedGeneration(long seed) {
        Random seedRandom = new Random();
        double[] height = new double[worldSize];
        
        seedRandom.setSeed(seed);
        for (int x = 0; x < height.length; x++) {
            height[x] = seedRandom.nextInt(1000);
            height[x] /= 1000;
        }
        
        return height;
    }
}
