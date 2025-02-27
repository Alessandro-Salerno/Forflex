package alessandrosalerno.forflex;

record Token(TokenType type,
                    String value,
                    int index,
                    int line,
                    int col) {
    @Override
    public String toString() {
        return "Token[Type=" + this.type() + ", Value=" + this.value() + "]";
    }
}
