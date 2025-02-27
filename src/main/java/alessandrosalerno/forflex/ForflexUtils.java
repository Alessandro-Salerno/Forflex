package alessandrosalerno.forflex;

import alessandrosalerno.forflex.errors.runtime.ForflexParameterCountError;
import alessandrosalerno.forflex.errors.runtime.ForflexParameterTypeError;
import alessandrosalerno.forflex.algebra.ForflexAlgebra;
import alessandrosalerno.forflex.algebra.ForflexRealNumber;

import java.util.HashMap;
import java.util.Map;

public class ForflexUtils {
    private static class DefaultFunctions extends HashMap<String, ForflexFunction> {
        private DefaultFunctions() {
            this.put("pow", new ForflexFunction() {
                @Override
                public ForflexAlgebra run(Object[] params) {
                    ForflexRealNumber a = ForflexUtils.requireParameterType(params, 0, ForflexRealNumber.class);
                    ForflexRealNumber b = ForflexUtils.requireParameterType(params, 1, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.pow(a.getDouble(), b.getDouble()));
                }
            });

            this.put("sqrt", new ForflexFunction() {
                @Override
                public ForflexAlgebra run(Object[] params) {
                    ForflexRealNumber x = ForflexUtils.requireParameterType(params, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.sqrt(x.getDouble()));
                }
            });

            this.put("cbrt", new ForflexFunction() {
                @Override
                public ForflexAlgebra run(Object[] params) {
                    ForflexRealNumber x = ForflexUtils.requireParameterType(params, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.cbrt(x.getDouble()));
                }
            });

            this.put("sin", new ForflexFunction() {
                @Override
                public ForflexAlgebra run(Object[] params) {
                    ForflexRealNumber x = ForflexUtils.requireParameterType(params, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.sin(x.getDouble()));
                }
            });

            this.put("cos", new ForflexFunction() {
                @Override
                public ForflexAlgebra run(Object[] params) {
                    ForflexRealNumber x = ForflexUtils.requireParameterType(params, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.cos(x.getDouble()));
                }
            });

            this.put("round", new ForflexFunction() {
                @Override
                public ForflexAlgebra run(Object[] params) {
                    ForflexRealNumber x = ForflexUtils.requireParameterType(params, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.round(x.getDouble()));
                }
            });

            this.put("floor", new ForflexFunction() {
                @Override
                public ForflexAlgebra run(Object[] params) {
                    ForflexRealNumber x = ForflexUtils.requireParameterType(params, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.floor(x.getDouble()));
                }
            });

            this.put("ceil", new ForflexFunction() {
                @Override
                public ForflexAlgebra run(Object[] params) {
                    ForflexRealNumber x = ForflexUtils.requireParameterType(params, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.ceil(x.getDouble()));
                }
            });

            this.put("min", new ForflexFunction() {
                @Override
                public ForflexAlgebra run(Object[] params) {
                    ForflexRealNumber ret = null;

                    for (int i = 0; i < params.length; i++) {
                        ForflexRealNumber x = ForflexUtils.requireParameterType(params, i, ForflexRealNumber.class);
                        if (i == 0 || x.getDouble() < ret.getDouble()) {
                            ret = x;
                        }
                    }

                    return ret;
                }
            });

            this.put("max", new ForflexFunction() {
                @Override
                public ForflexAlgebra run(Object[] params) {
                    ForflexRealNumber ret = null;

                    for (int i = 0; i < params.length; i++) {
                        ForflexRealNumber x = ForflexUtils.requireParameterType(params, i, ForflexRealNumber.class);
                        if (i == 0 || x.getDouble() > ret.getDouble()) {
                            ret = x;
                        }
                    }

                    return ret;
                }
            });
        }
    }

    public static Map<String, ForflexFunction> DEFAULT_FUNCTIONS = new DefaultFunctions();

    public static <T> T requireParameterType(Object[] params, int index, Class<T> clazz) {
        if (index >= params.length) {
            throw new ForflexParameterCountError(index, params.length);
        }

        if (clazz.isInstance(params[index])) {
            return clazz.cast(params[index]);
        }

        throw new ForflexParameterTypeError(clazz, params[index].getClass(), index);
    }
}
