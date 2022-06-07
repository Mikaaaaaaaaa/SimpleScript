package simplescript.file;

import simplescript.data.TemporaryData;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultFile {

    private final Map<String, List<String>> lines, linesToExecute;

    public DefaultFile() {
        this.lines = new HashMap<>();
        this.linesToExecute = new HashMap<>();
    }

    private void readFile(String path) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<String> liness = new ArrayList<>();
        assert inputStream != null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                liness.add(line);
            }
            lines.put(path, liness);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void executeFile(String path) {
        this.readFile(path);
        List<String> liness = lines.get(path);
        List<String> executeLiness = new ArrayList<>();
        boolean setup = true;
        String classType = null;
        List<String> loadings = new ArrayList<>();
        List<String> resource = new ArrayList<>();
        for (String line : liness) {
            if(setup) {
                if(line.contains("load")) {
                    String[] resources = line.split(" ");
                    loadings.add(resources[1]);
                } else if((line.contains("class") || line.contains("sampleClass") && classType == null)) {
                    classType = line.contains("sample") ? "sampleClass" : "class";
                    if(line.contains("include")) {
                        String include = line.split("include")[1];
                        String[] includes = include.split(",");
                        for (String s : includes) {
                            resource.add(s.replace(":", ""));
                        }
                    }
                }
                continue;
            }
            executeLiness.add(line);
        }
        linesToExecute.put(path, executeLiness);
        executeLines(path);
    }

    public void executeLines(String path) {
        System.out.println("successfully loaded");
        List<String> liness = linesToExecute.get(path);
        TemporaryData temporaryData = new TemporaryData();
        for (String s : liness) {
            temporaryData.executeVariable(s);
        }
    }
}
