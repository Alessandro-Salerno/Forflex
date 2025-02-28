package alessandrosalerno.forflex;

import alessandrosalerno.forflex.errors.runtime.ForflexParameterCountError;
import alessandrosalerno.forflex.errors.runtime.ForflexParameterTypeError;
import alessandrosalerno.forflex.algebra.ForflexAlgebra;
import alessandrosalerno.forflex.algebra.ForflexRealNumber;

import java.util.HashMap;
import java.util.Map;

public class ForflexUtils {
    private static class DefaultFunctions extends HashMap<String, ForflexFunction<?>> {
        private DefaultFunctions() {
            this.put("pow", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] params) {
                    ForflexRealNumber a = ForflexUtils.requireParameterType(params, 0, ForflexRealNumber.class);
                    ForflexRealNumber b = ForflexUtils.requireParameterType(params, 1, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.pow(a.getPrimitive(), b.getPrimitive()));
                }
            });

            this.put("sqrt", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] params) {
                    ForflexRealNumber x = ForflexUtils.requireParameterType(params, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.sqrt(x.getPrimitive()));
                }
            });

            this.put("cbrt", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] params) {
                    ForflexRealNumber x = ForflexUtils.requireParameterType(params, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.cbrt(x.getPrimitive()));
                }
            });

            this.put("sin", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] params) {
                    ForflexRealNumber x = ForflexUtils.requireParameterType(params, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.sin(x.getPrimitive()));
                }
            });

            this.put("cos", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] params) {
                    ForflexRealNumber x = ForflexUtils.requireParameterType(params, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.cos(x.getPrimitive()));
                }
            });

            this.put("round", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] params) {
                    ForflexRealNumber x = ForflexUtils.requireParameterType(params, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.round(x.getPrimitive()));
                }
            });

            this.put("floor", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] params) {
                    ForflexRealNumber x = ForflexUtils.requireParameterType(params, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.floor(x.getPrimitive()));
                }
            });

            this.put("ceil", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] params) {
                    ForflexRealNumber x = ForflexUtils.requireParameterType(params, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.ceil(x.getPrimitive()));
                }
            });

            this.put("min", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] params) {
                    ForflexRealNumber ret = null;

                    for (int i = 0; i < params.length; i++) {
                        ForflexRealNumber x = ForflexUtils.requireParameterType(params, i, ForflexRealNumber.class);
                        if (i == 0 || x.getPrimitive() < ret.getPrimitive()) {
                            ret = x;
                        }
                    }

                    return ret;
                }
            });

            this.put("max", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] params) {
                    ForflexRealNumber ret = null;

                    for (int i = 0; i < params.length; i++) {
                        ForflexRealNumber x = ForflexUtils.requireParameterType(params, i, ForflexRealNumber.class);
                        if (i == 0 || x.getPrimitive() > ret.getPrimitive()) {
                            ret = x;
                        }
                    }

                    return ret;
                }
            });
        }
    }

    public static Map<String, ForflexFunction<?>> DEFAULT_FUNCTIONS = new DefaultFunctions();

    public static <T extends ForflexAlgebra<?>> T requireParameterType(ForflexAlgebra<?>[] params, int index, Class<T> clazz) {
        if (index >= params.length) {
            throw new ForflexParameterCountError(index, params.length);
        }

        if (clazz.isInstance(params[index])) {
            return clazz.cast(params[index]);
        }

        throw new ForflexParameterTypeError(clazz, params[index].getClass(), index);
    }

    public static <T> T requireParameterPrimitiveType(ForflexAlgebra<?>[] params, int index, Class<T> clazz) {
        if (index >= params.length) {
            throw new ForflexParameterCountError(index, params.length);
        }

        if (clazz.isInstance(params[index].getPrimitive())) {
            return clazz.cast(params[index].getPrimitive());
        }

        throw new ForflexParameterTypeError(clazz, params[index].getClass(), index);
    }
}
