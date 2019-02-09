public class Field {
    private FieldState fieldstate;
    private final int yPos;
    private final int xPos;
    private static int instances = 0;

    public Field(int yPos, int xPos) {
        this.yPos = yPos;
        this.xPos = xPos;
        instances++;
        System.out.println("Field Class instances: " + instances);
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
