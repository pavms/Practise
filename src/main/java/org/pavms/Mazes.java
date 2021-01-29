package org.pavms;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Mazes {

    public static void main(String[] args) throws URISyntaxException, IOException {

        Loader loader = new Loader();
        Solver solver = new Solver();

        URL url = Mazes.class.getClassLoader().getResource("maze");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(url.toURI()))) {

            for (Path path : stream) {
                if (!Files.isDirectory(path)) {

                    System.out.println("Solution for " + path);

                    Maze maze = loader.load(path);
                    Solution solution = solver.solve(maze);
                    System.out.println(solution);
                }
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
