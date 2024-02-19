package org.genset.generator;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.logging.Logger;

public class GensetGenerator {

    private final Path modelPath;
    private final String packageName;

    public GensetGenerator(Path modelPath, String packageName) {
        this.modelPath = modelPath;
        this.packageName = packageName;
    }

    public void getParse() {
        try(InputStream inputStream = new FileInputStream(this.getClass().getClassLoader().getResource("test.yaml").getFile())) {
            Yaml yaml = new Yaml();
            Map<String, Map<String, Object>> data = yaml.load(inputStream);

            Map<String, Object> modelData = data.get("model");

            modelData.forEach((s, o) -> {
                Map<String, Object> userData = (Map<String, Object>) modelData.get(s);

                try{
                    Path filePath = Path.of(modelPath + "/" + s + ".java");
                    Files.createFile(filePath);
                    System.out.println("##");
                    System.out.println(s + " is created");

                    String content = generateFileContent(s, userData);
                    Files.write(filePath, content.getBytes());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String generateFileContent(String className, Map<String, Object> yamlData) {
        StringBuilder contentBuilder = new StringBuilder();

        contentBuilder.append("package " + packageName + ".model" + ";\n\n");
        contentBuilder.append("public class ").append(className).append(" {\n\n");
        yamlData.forEach((fieldName, fieldType) -> {
            System.out.println(fieldName + " with type " + fieldType + " is created");
            contentBuilder.append("\tprivate ").append(fieldType).append(" ").append(fieldName).append(";\n");
        });
        contentBuilder.append("\n}");

        return contentBuilder.toString();
    }

}
