import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

public class SeamCarving {
    private int[] pixels;
    private int type, height, width;

    // Field getters

    int[] getPixels() {
        return pixels;
    }

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    // Read and write images

    void readImage(String filename) throws IOException {
        BufferedImage image = ImageIO.read(new File(filename));
        type = image.getType();
        height = image.getHeight();
        width = image.getWidth();
        pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
    }

    void writeImage(String filename) throws IOException {
        BufferedImage image = new BufferedImage(width, height, type);
        image.setRGB(0, 0, width, height, pixels, 0, width);
        ImageIO.write(image, "jpg", new File(filename));
    }

    // Accessing pixels and their neighbors

    Color getColor(int h, int w) {
        int pixel = pixels[w + h * width];
        return new Color(pixel, true);
    }

    // for a general position, returns the neighbors above,
    // right, below, and left

    ArrayList<Position> getHVneighbors(int h, int w) {
        ArrayList<Position> neighbors = new ArrayList<>();

        // Checking if the pixel is on the left edge of the picture, only add right neighbor
        if (w == 0) neighbors.add(new Position(h, w + 1));
        // Checking if the pixel is on the right edge of the picture, only add left neighbor
        else if (w + 1 == width) neighbors.add(new Position(h, w - 1));
        // Since it isn't on the left or right edge, add both left and right neighbors
        else {
            neighbors.add(new Position(h, w - 1));
            neighbors.add(new Position(h, w + 1));
        }

        // Checking if the pixel is on the top edge of the picture, only add bottom neighbor
        if (h == 0) neighbors.add(new Position(h + 1, w));
        // Checking if the pixel is on the bottom edge of the picture, only add top neighbor
        else if (h + 1 == height) neighbors.add(new Position(h - 1, w));
        // Since it isn't on the top or bottom edge, add both top and bottom neighbors
        else {
            neighbors.add(new Position(h + 1, w));
            neighbors.add(new Position(h - 1, w));
        }
        
        return neighbors;
    }

    // For a general position, returns the three neighbors
    // under it: below left, below, and below right

    ArrayList<Position> getBelowNeighbors(int h, int w) {
        ArrayList<Position> neighbors = new ArrayList<>();
        if (h + 1 == height) return neighbors;
        neighbors.add(new Position(h + 1, w));
        if (w == 0) {
            neighbors.add(new Position(h + 1, w + 1));
            return neighbors;
        } else if (w + 1 == width) {
            neighbors.add(new Position(h + 1, w - 1));
            return neighbors;
        } else {
            neighbors.add(new Position(h + 1, w + 1));
            neighbors.add(new Position(h + 1, w - 1));
            return neighbors;
        }
    }

    // Computing energy at given pixel
    // Get the 4 surrounding neighbors and sum
    // the squares of the differences of RGB values

    int computeEnergy(int h, int w) {
        Color c = getColor(h, w);
        Function<Integer, Integer> sq = n -> n * n;
        int energy = 0;
        for (Position p : getHVneighbors(h, w)) {
            Color nc = getColor(p.getFirst(), p.getSecond());
            energy += sq.apply(nc.getRed() - c.getRed());
            energy += sq.apply(nc.getGreen() - c.getGreen());
            energy += sq.apply(nc.getBlue() - c.getBlue());
        }
        return energy;
    }

    // Find seam of minimum total energy starting from (h,w) going down
    // returns the list of positions in the seam and its cost
    //
    // use a hashtable to memoize the work
    //
    // The steps to follow are:
    // 1. Compute the energy at the current position
    // 2. Find the neighbors below the current position
    // 3. If there are no neighbors (we are at the bottom row), return the
    //    appropriate result
    // 4. Otherwise, recursively findSeam starting from each below neighbor's
    //    position
    // 5. Return the minimum answer after adding the current node and current
    //    energy

    final Map<Position, Pair<List<Position>, Integer>> hash = new HashMap<>();

    Pair<List<Position>, Integer> findSeam(int h, int w) {

        // See if the subproblem has already been computed
        Position probKey = new Position(h, w);
        if (hash.containsKey(probKey)) return hash.get(probKey);

        // Compute the energy at the current position
        int curPixelEnergy = computeEnergy(h, w);

        // Find the neighbors below the current position
        ArrayList<Position> belowNeighbors = getBelowNeighbors(h, w);

        // If the current pixel is at the bottom, return the energy of the current pixel
        if (belowNeighbors.size() == 0) return new Pair<>(new Node<>(probKey, new Empty<>()), curPixelEnergy);
        // Since the current pixel is not at the bottom, find the pixel below with the smallest energy
        else {

            // Set the seam with the minimum amount of energy as the left-most neighbor
            Pair<List<Position>, Integer> minEnergyBelow =
                    findSeam(belowNeighbors.get(0).getFirst(), belowNeighbors.get(0).getSecond());

            // For every other neighbor, compute their energy and determine if they are less than
            // the current minimum amount of energy
            for (int i = 1; i < belowNeighbors.size(); i++) {

                Pair<List<Position>, Integer> possibleMinEnergyBelow =
                        findSeam(belowNeighbors.get(i).getFirst(),
                                belowNeighbors.get(i).getSecond());

                if (possibleMinEnergyBelow.getSecond() < minEnergyBelow.getSecond())
                minEnergyBelow = possibleMinEnergyBelow;
            }

            // Put the answer to the subproblem in the hash table and return the answer
            Pair<List<Position>, Integer> returnSeam = new Pair<>(new Node<>(probKey, minEnergyBelow.getFirst()),
                    curPixelEnergy+ minEnergyBelow.getSecond());
            hash.put(probKey, returnSeam);
            return returnSeam;
        }
    }

    // Call findSeam for all position in the first row (h=0)
    // and returns the best (the one with the lowest
    // total energy)
    //
    // CLEAR the hashtable before starting; each calculation
    // of bestSeam needs to start with a fresh hashtable
    // but all the calls the findSeam will share the same
    // hashtable

    Pair<List<Position>, Integer> bestSeam() {

        hash.clear();
        Pair<List<Position>, Integer> minSeam = findSeam(0, 0);
        for (int i = 1; i < width; i++) {
            Pair<List<Position>, Integer> curSeam = findSeam(0, i);
            if (curSeam.getSecond() < minSeam.getSecond()) minSeam = curSeam;
        }

        return minSeam;
    }

    // Putting it all together; find best seam and copy pixels
    // without that seam
    //
    // the logic is to create a small array of pixels, copy all
    // the pixels from the old array to the new array except
    // the ones in the seam

    void cutSeam() {

        // Find the pixels to cut
        Pair<List<Position>, Integer> bestSeam = bestSeam();
        List<Position> pixelsToCut = bestSeam.getFirst();

        // Set up a new array to store the cut image
        int[] newPixels = new int[(width-1)*height];

        boolean hasCutInLine = false;

        // Keep a separate counter for the new image since it's length will be different than the
        // cut image
        int j = 0;
        for (int i = 0; i < pixels.length; i++) {

            if (hasCutInLine == true && i%width == 0) hasCutInLine = false;

            // If there are no more pixels to be cut, finish copying the pixels
            if (pixelsToCut instanceof Empty) {
                newPixels[j] = pixels[i];
                j += 1;
                continue;
            }

            try {
                // If the current pixel being copied is the first node in bestSeam, don't copy it and
                // remove the node from bestSeam
                if (pixelsToCut.getFirst().getSecond() == i%width && !hasCutInLine) {
                    pixelsToCut = pixelsToCut.getRest();
                    hasCutInLine = true;
                }
                // Since the current pixel shouldn't be deleted, add it to the new image and
                // increment the new image counter (j)
                else {
                    newPixels[j] = pixels[i];
                    j += 1;
                }
            } catch (EmptyListE e) {}
        }

        // Set the image as the cut image
        pixels = newPixels;
        width = width - 1;
    }
}


