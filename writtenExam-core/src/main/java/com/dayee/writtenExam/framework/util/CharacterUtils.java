
package com.dayee.writtenExam.framework.util;

public class CharacterUtils {

    public static boolean isEnglishLetter(char input) {

        return (input >= 'a' && input <= 'z') || (input >= 'A' && input <= 'Z');
    }

    public static boolean isChineseLetter(char input) {

        return Character.UnicodeBlock.of(input) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS;
    }

    public static boolean isSpaceLetter(char input) {

        boolean returnboolean = input == 8 || input == 9
                                || input == 10
                                || input == 13
                                || input == 32
                                || input == 160;
        return returnboolean;
    }

    public static boolean isArabicNumber(char input) {

        boolean returnboolean = input >= '0' && input <= '9';
        return returnboolean;
    }

    public static boolean isCJKCharacter(char input) {

        boolean returnboolean = Character.UnicodeBlock.of(input) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS;
        return returnboolean;
    }

    /**
     * 进行字符规格化（全角转半角，大写转小写处理）
     * 
     * @param input
     * @return char
     */
    public static char regularize(char input) {

        if (input == 12288) {
            input = (char) 32;

        } else if (input > 65280 && input < 65375) {
            input = (char) (input - 65248);

        } else if (input >= 'A' && input <= 'Z') {
            input += 32;
        }
        return input;
    }

}
