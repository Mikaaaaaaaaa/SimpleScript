package simplescript.data.prototyp;

public class IntegerPrototyp extends Prototyp{




    @Override
    public Object getData(String input) {
        //$d_integer = calculate(3+3+3/3*5)
        if(input.split("\\(")[0].equals("calculate")) {

        }
        return null;
    }

    @Override
    public Object getSaveData(String input) {
        return null;
    }
}
