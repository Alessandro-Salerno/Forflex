package alessandrosalerno.forflex;

import alessandrosalerno.forflex.errors.runtime.ForflexArgumentCountError;
import alessandrosalerno.forflex.errors.runtime.ForflexArgumentTypeError;
import alessandrosalerno.forflex.algebra.ForflexAlgebra;
import alessandrosalerno.forflex.algebra.ForflexRealNumber;

import java.util.HashMap;
import java.util.Map;

public class ForflexUtils {
    private static class DefaultFunctions extends HashMap<String, ForflexFunction<?>> {
        private DefaultFunctions() {
            this.put("pow", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] args) {
                    ForflexRealNumber a = ForflexUtils.requireArgumentType(args, 0, ForflexRealNumber.class);
                    ForflexRealNumber b = ForflexUtils.requireArgumentType(args, 1, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.pow(a.getPrimitive(), b.getPrimitive()));
                }
            });

            this.put("sqrt", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] args) {
                    ForflexRealNumber x = ForflexUtils.requireArgumentType(args, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.sqrt(x.getPrimitive()));
                }
            });

            this.put("cbrt", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] args) {
                    ForflexRealNumber x = ForflexUtils.requireArgumentType(args, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.cbrt(x.getPrimitive()));
                }
            });

            this.put("sin", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] args) {
                    ForflexRealNumber x = ForflexUtils.requireArgumentType(args, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.sin(x.getPrimitive()));
                }
            });

            this.put("cos", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] args) {
                    ForflexRealNumber x = ForflexUtils.requireArgumentType(args, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.cos(x.getPrimitive()));
                }
            });

            this.put("round", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] args) {
                    ForflexRealNumber x = ForflexUtils.requireArgumentType(args, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.round(x.getPrimitive()));
                }
            });

            this.put("floor", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] args) {
                    ForflexRealNumber x = ForflexUtils.requireArgumentType(args, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.floor(x.getPrimitive()));
                }
            });

            this.put("ceil", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] args) {
                    ForflexRealNumber x = ForflexUtils.requireArgumentType(args, 0, ForflexRealNumber.class);
                    return new ForflexRealNumber(Math.ceil(x.getPrimitive()));
                }
            });

            this.put("min", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] args) {
                    ForflexRealNumber ret = null;

                    for (int i = 0; i < args.length; i++) {
                        ForflexRealNumber x = ForflexUtils.requireArgumentType(args, i, ForflexRealNumber.class);
                        if (i == 0 || x.getPrimitive() < ret.getPrimitive()) {
                            ret = x;
                        }
                    }

                    return ret;
                }
            });

            this.put("max", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] args) {
                    ForflexRealNumber ret = null;

                    for (int i = 0; i < args.length; i++) {
                        ForflexRealNumber x = ForflexUtils.requireArgumentType(args, i, ForflexRealNumber.class);
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

    public static <T extends ForflexAlgebra<?>> T requireArgumentType(ForflexAlgebra<?>[] args, int index, Class<T> clazz) {
        if (index >= args.length) {
            throw new ForflexArgumentCountError(index, args.length);
        }

        if (clazz.isInstance(args[index])) {
            return clazz.cast(args[index]);
        }

        throw new ForflexArgumentTypeError(clazz, args[index].getClass(), index);
    }

    public static <T> T requireArgumentPrimitiveType(ForflexAlgebra<?>[] args, int index, Class<T> clazz) {
        if (index >= args.length) {
            throw new ForflexArgumentCountError(index, args.length);
        }

        if (clazz.isInstance(args[index].getPrimitive())) {
            return clazz.cast(args[index].getPrimitive());
        }

        throw new ForflexArgumentTypeError(clazz, args[index].getClass(), index);
    }
}
