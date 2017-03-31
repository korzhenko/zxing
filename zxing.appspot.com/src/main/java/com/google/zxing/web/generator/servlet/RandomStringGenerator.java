package com.google.zxing.web.generator.servlet;

import java.util.Random;

/**
 */
final class RandomStringGenerator {
    private static final  String sLowerCharString = "abcdefghijklmnopqrstuvwxyz";
    private static final String sDigitalString = "0123456789";
    private static final String sUpperCharString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String sSymbolString = "~`!@$%^&*()-_+= []|\\:;\"'.<>?/#,";

    private static final  Random mRandom = new Random();

    private RandomStringGenerator() {
    }

    public static char generateChar(char pCharPattern) {
        String sourceString;
        switch (pCharPattern) {
            case 'n':
                sourceString = sDigitalString;
                break;
            case 'c':
                sourceString = sLowerCharString;
                break;
            case 'C':
                sourceString = sUpperCharString;
                break;
            case '!':
                sourceString = sSymbolString;
                break;
            case '.':
                sourceString = sUpperCharString + sDigitalString + sLowerCharString + sSymbolString;
                break;
            case 's':
                sourceString = sUpperCharString + sDigitalString + sLowerCharString;
                break;
            case 'b':
                return (char) mRandom.nextInt(255);
            default:
                return pCharPattern;
        }
        return sourceString.charAt(mRandom.nextInt(sourceString.length() - 1));
    }

    /**
     * Generate random string from pattern.
     *
     * <p>
     * You can use following characters as pattern.
     * <ul>
     * <li>{@code c} : Any Latin lower-case character</li>
     * <li>{@code C} : Any Latin upper-case character</li>
     * <li>{@code n} : Any digit {@code [0-9]}</li>
     * <li>{@code !} : A symbol character {@code [~`!@$%^&*()-_+= []|\:;"'.<>?/#,]}</li>
     * <li>{@code .} : Any of the above</li>
     * <li>{@code s} : A "salt" character {@code [A-Za-z0-9./]}</li>
     * <li>{@code b} : An ASCIII character which has code from 0 to 255</li>
     * </ul>
     *
     * <p>
     * e.g.
     *
     * <pre>
     * <code>
     * RandomStringGenerator generator = new RandomStringGenerator();
     *
     * // generates random string (e.g. "aB4@X.Ç")
     * String randomString = generator.generateFromPattern(&quot;cCn!.sb&quot;);
     * </code>
     * </pre>
     *
     * @param pPattern Pattern string
     * @return Random string which is generated according to pattern
     */

    public static String generate(String pPattern) {
        String returnValue = "";
        for (int i = 0; i < pPattern.length(); i++) {
            returnValue += generateChar(pPattern.charAt(i));
        }
        return returnValue;
    }

    /**
     * Generate random string from pattern.
     *
     * <p>
     * You can use following characters as pattern.
     * <ul>
     * <li>{@code c} : Any Latin lower-case character</li>
     * <li>{@code C} : Any Latin upper-case character</li>
     * <li>{@code n} : Any digit {@code [0-9]}</li>
     * <li>{@code !} : A symbol character {@code [~`!@$%^&*()-_+= []|\:;"'.<>?/#,]}</li>
     * <li>{@code .} : Any of the above</li>
     * <li>{@code s} : A "salt" character {@code [A-Za-z0-9./]}</li>
     * <li>{@code b} : An ASCIII character which has code from 0 to 255</li>
     * </ul>
     *
     * <p>
     * e.g.
     *
     * <pre>
     * <code>
     * RandomStringGenerator generator = new RandomStringGenerator();
     *
     * // generates random string (e.g. "aB4@X.Ç")
     * String randomString = generator.generateFromPattern(&quot;cCn!.sb&quot;);
     * </code>
     * </pre>
     *
     * @param pPattern Pattern string
     * @param count quantity of the generated  string
     * @param separator separator between strings
     * @return Random string which is generated according to pattern
     */

    public static String generate(String pPattern, int count, String separator) {
        String returnValue = RandomStringGenerator.generate(pPattern);
        for (int i = 1; i < count; i++) {
            returnValue += separator + RandomStringGenerator.generate(pPattern);
        }
        return returnValue;
    }


}
