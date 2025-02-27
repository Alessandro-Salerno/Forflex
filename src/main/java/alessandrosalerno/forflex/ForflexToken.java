package alessandrosalerno.forflex;

public record ForflexToken(ForflexTokenType type, String value,
                           int index,
                           int line,
                           int col) {
    @Override
    public String toString() {
        return "Token[Type=" + this.type() + ", Value=" + this.value() + "]";
    }
}
