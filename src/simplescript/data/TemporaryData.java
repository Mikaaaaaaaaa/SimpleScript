package simplescript.data;

import simplescript.data.prototyp.Prototyp;
import simplescript.data.prototyp.SimpleString;
import simplescript.file.DefaultFile;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;

public class TemporaryData extends SimpleExecute {

    private final HashMap<String, String> map;
    private final HashMap<String, Prototyp> configurations;

    public TemporaryData() {
        this.map = new HashMap<>();
        this.configurations = new HashMap<>();

        addConfiguration("string", new SimpleString());
        //System.out.println(executeVariable("\"Hello World\""));
    }

    public Object executeVariable(String s) {
        if(s.contains("=")) {
            return writeVariable(s);
        } else {
            return readVariable(s);
        }
    }

    private void saveData(String key, String value) {
        map.put(key, value);
    }

    public void addConfiguration(String key, Prototyp prototyp) {
        this.configurations.put(key, prototyp);
    }

    private Object getData(String key) {
        String[] values = map.get(key.replaceAll("\\s", "")).split("REDFLAG_HAMBURG");
        return configurations.get(values[0].replaceAll("\\s", "")).getData(values[1]);
    }

    public Object readVariable(String input) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] value = input.split("\\+");
        for (String s : value) {
            if(s.contains("$")) {
                stringBuilder.append(getData(s.replace("$", "")));
                continue;
            }
            stringBuilder.append(getStringsBetweenChar(s));
        }
        return stringBuilder.toString();
    }

    public Object writeVariable(String input) {
        StringBuilder finalLine = new StringBuilder();
        if(input.contains("=")) {
            String[] parts = input.split("=");
            String name = parts[0].split("_")[0],
                    type = parts[0].split("_")[1];

            String[] value = parts[1].split("\\+");
            for (String s : value) {
                if(s.contains("\"")) {
                    finalLine.append(getStringsBetweenChar(s));
                } else if(s.contains("$")) {
                    finalLine.append(readVariable(s));
                } else {
                    System.out.println("ERROR: " + s);
                }
            }
            saveData(name.replace("$", ""), type + "REDFLAG_HAMBURG" + configurations.get(type.replaceAll("\\s", "")).getSaveData(finalLine.toString()));
        }
        return finalLine.toString();
    }

    private String getStringsBetweenChar(String input) {
        return input.substring(input.indexOf("\"") + 1, input.lastIndexOf("\""));
    }

    @Override
    String getName() {
        return "Data";
    }

    @Override
    String getAuthor() {
        return "Mika";
    }

    @Override
    String getVersion() {
        return "DEVELOPMENT-0.0.1";
    }

    public static void main(String[] args) {
        new DefaultFile().executeFile("/Users/mika/IdeaProjects/demo/demo/SimpleScript/src/simplescript/data/test.smplscrpt");
    }

}
