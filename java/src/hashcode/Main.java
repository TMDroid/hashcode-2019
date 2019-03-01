package hashcode;

import com.adventofcode.Slide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

public class Main {

    public static void main(String argv[]) {
        if(argv.length < 1) {
            System.out.println("Input file must be given as a parameter");
            System.exit(0);
        }

        File input = new File(argv[0]);
        SlideshowBuilder builder = new SlideshowBuilder(input);
        List<Slide> slides = builder.work();

        writeFile(slides);
    }

    private static void writeFile(List<Slide> slides) {
        PrintStream ps = null;
        try {
            ps = new PrintStream(new FileOutputStream(new File("../output.txt")));
            ps.println(slides.size());

            for (Slide s : slides) {
                ps.println(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}
