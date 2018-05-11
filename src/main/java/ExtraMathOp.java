import io.jenetics.prog.op.Op;

import java.util.function.Function;

// Class to add new operations that aren't defined in MathOp
public enum ExtraMathOp implements Op<Double> {
    // Here goes all the operation definitions (name, arity, function to evaluate)
    INTEGRAL("int", 1, (v) -> v[0]), // Dummy
    DERIVATIVE("deriv", 1, (v) -> v[0]); // Dummy

    // Copy pasted from MathOp (Can't be inherited)
    private final String _name;
    private final int _arity;
    private final Function<Double[], Double> _function;

    private ExtraMathOp(String name, int arity, Function<Double[], Double> function) {
        assert name != null;

        assert arity >= 0;

        assert function != null;

        this._name = name;
        this._function = function;
        this._arity = arity;
    }

    public int arity() {
        return this._arity;
    }

    public Double apply(Double[] doubles) {
        return (Double)this._function.apply(doubles);
    }

    public String toString() {
        return this._name;
    }
}
