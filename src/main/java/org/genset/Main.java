package org.genset;

import org.genset.generator.GensetGenerator;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        GensetGenerator generator = new GensetGenerator(Path.of("D:\\Developments\\java\\PROJECT\\genset\\src\\main\\java\\org\\genset\\model"), "org.genset");
        generator.getParse();
    }
}
