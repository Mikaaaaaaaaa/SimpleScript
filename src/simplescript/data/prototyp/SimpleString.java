package simplescript.data.prototyp;

public class SimpleString extends Prototyp{


    @Override
    public Object getData(String input) {
        return String.valueOf(input);
    }

    @Override
    public Object getSaveData(String input) {
        return input;
    }

}
