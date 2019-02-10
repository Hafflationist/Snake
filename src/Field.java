public class Field {
    private FieldState fieldstate;
    private final int yPos;
    private final int xPos;

    public Field(int yPos, int xPos) {
        this.yPos = yPos;
        this.xPos = xPos;
    }

    public int getFieldY() {
        return yPos;
    }

    public int getFieldX() {
        return xPos;
    }

    public void setState(FieldState fieldState) {
        fieldstate = fieldState;
    }

    public FieldState getState() {
        return fieldstate;
    }
}
