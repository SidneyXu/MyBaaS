package com.maxleap.pandora.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author sneaky
 * @since 1.0.0
 */
@Deprecated
public class Utils {
  private static Logger logger = LoggerFactory.getLogger(Utils.class);

  static Function<Method, String> keyFunction = v -> Character.toLowerCase(v.getName().charAt(3)) + v.getName().substring(4);

  static BinaryOperator<Method> mergeFunction = (existVal, newValue) -> {
    throw new IllegalStateException("ambiguous!! can't be bind value, method: " + newValue.getName());
  };

  public static Map<String, Method> getSetterMethods(Class clazz) {
    return Arrays.stream(clazz.getMethods())
        .filter(Utils::isSetter)
        .collect(Collectors.toMap(keyFunction, Function.identity(), mergeFunction));
  }

  public static boolean isSetter(Method method) {
    return method.getName().startsWith("set") && method.getParameterTypes().length == 1;
  }

  public static List<HostAndPort> listUrl(String urls, int defaultPort) {
    List<HostAndPort> hostAndPorts = new ArrayList<>();
    String[] arrayUrl = urls.split(",");

    for (String url : arrayUrl) {
      url = url.trim();
      try {
        int i = url.indexOf(":");
        int port = -1;
        if (i > 0) {
          port = Integer.parseInt(url.substring(i + 1));
          url = url.substring(0, i);
        } else {
          port = defaultPort;
        }
        hostAndPorts.add(new HostAndPort(url, port));
      } catch (Exception e) {
        throw new IllegalArgumentException(e.getMessage() + ", urls: " + urls, e);
      }
    }

    return hostAndPorts;
  }
}
