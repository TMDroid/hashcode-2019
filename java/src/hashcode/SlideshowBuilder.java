package hashcode;

import com.adventofcode.Image;
import com.adventofcode.Slide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SlideshowBuilder {
    private File input;

    public SlideshowBuilder(File input) {
        this.input = input;
    }

    public List<Slide> work() {
        try {
            List<Image> images = readDataFromFile();
            return mapImagesToSlides(images);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    private List<Slide> mapImagesToSlides(List<Image> images) {
        List<Image> horizontalImages = images.stream().filter(Image::isHorizontal).collect(Collectors.toList());
        List<Image> verticalImages = new ArrayList<>(images);
        verticalImages.removeAll(horizontalImages);

        List<Slide> slides = new ArrayList<>();
        for(Image image: horizontalImages) {
            slides.add(new Slide(image));
        }

        if(verticalImages.size() % 2 != 0) {
            System.err.println("Whoooops");
            System.exit(2);
        }

        for(int i = 0; i < verticalImages.size(); i+=2) {
            Slide s = new Slide();
            s.addImage(verticalImages.get(i));
            s.addImage(verticalImages.get(i + 1));

            slides.add(s);
        }

        return slides;
    }

    private List<Image> readDataFromFile() throws FileNotFoundException {
        List<Image> images = new LinkedList<>();
        Scanner s = new Scanner(new FileInputStream(input));

        int numberOfImages = Integer.parseInt(s.next());

        for(int i = 0; i < numberOfImages; i++) {
            Integer imageId = i;
            Character hv = s.next().charAt(0);

            Image image = new Image(imageId, hv);
            images.add(image);
        }

        return images;
    }
}
