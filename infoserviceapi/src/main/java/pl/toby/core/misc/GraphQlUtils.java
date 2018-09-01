package pl.toby.core.misc;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class GraphQlUtils {

    private static String operate(Field f, Object o) throws IllegalAccessException {
        String result = "";
        f.setAccessible(true);
        if(f.get(o) != null && !Modifier.isStatic(f.getModifiers())) {
            if(!(f.get(o) instanceof Collection<?>)) {
                if(f.get(o) instanceof String || f.get(o) instanceof UUID) {
                    result += f.getName() + ":\"" + f.get(o) + "\",";
                }
                else if(f.get(o) instanceof Object[]) {
                    result += f.getName() + ":" + Arrays.toString((Object[]) f.get(o)) + ",";
                }
                else if(f.get(o) instanceof Integer || f.get(o) instanceof Long) {
                    result += f.getName() + ":" + f.get(o) + ",";
                }
                else {
                    result += f.getName() + ":" + render(f.get(o)) + ",";
                }
            }
            else {
                if(((Collection) f.get(o)).size() > 0) {
                    String col = f.getName() +  ":[";
                    for(Object c: (Collection) f.get(o)) {
                        col += render(c) + ",";
                    }
                    col+= "]";
                    result += col.replace(",]", "]") + ",";
                }
            }
        }

        return result;
    }




    public static String render(Object o) throws IllegalAccessException {
        String result = "{";

        if(o.getClass().getSuperclass().getDeclaredFields().length > 0) {
            for(Field f : o.getClass().getSuperclass().getDeclaredFields()) {
                result += operate(f, o);
            }
        }

        for(Field f : o.getClass().getDeclaredFields()) {
            result += operate(f, o);
        }

        result += "}";

        return result.replace(",}", "}");
    }

}
