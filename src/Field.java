public class Field {
    private FieldState _fieldstate;
    private final int _yPos;
    private final int _xPos;
    private static int instances = 0;

    public Field(int yPos, int xPos) {
        _yPos = yPos;
        _xPos = xPos;
        instances++;
        System.out.println("Field Class instances: " + instances);
    }

    public int getFieldY() {
        return _yPos;
    }

    public int getFieldX() {
        return _xPos;
    }

    public void setState(FieldState fieldState) {
        _fieldstate = fieldState;
    }

    public FieldState getState() {
        return _fieldstate;
    }
}
