class Field {
    var state: FieldState
    val fieldY: Int
    val fieldX: Int

    constructor(yPos: Int, xPos: Int) {
        this.fieldY = yPos
        this.fieldX = xPos
        this.state = FieldState.EMPTYFIELD
    }

    constructor(yPos: Int, xPos: Int, fieldState: FieldState) {
        this.fieldY = yPos
        this.fieldX = xPos
        this.state = fieldState
    }
}
