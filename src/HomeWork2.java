import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Tests for validateLogin
//        AntiCheatCheck: 8666A3CBCF0CF8D73BBB0723361EDD16
//        TEST CASE 0   Expected: true                 Actual: true                 +
//        TEST CASE 1   Expected: true                 Actual: true                 +
//        TEST CASE 2   Expected: true                 Actual: true                 +
//        TEST CASE 3   Expected: false                Actual: true                 -
//        TEST CASE 4   Expected: false                Actual: true                 -
//        TEST CASE 5   Expected: false                Actual: true                 -
//        TEST CASE 6   Expected: true                 Actual: true                 +
//        TEST CASE 7   Expected: false                Actual: true                 -
//        TEST CASE 8   Expected: false                Actual: true                 -
//        TEST CASE 9   Expected: false                Actual: false                +
//        TEST CASE 10  Expected: false                Actual: false                +
//        TEST CASE 11  Expected: true                 Actual: true                 +
//
//        Tests for isLoginValid
//        AntiCheatCheck: 8666A3CBCF0CF8D73BBB0723361EDD16
//        TEST CASE 12  Expected: true                 Actual: false                -
//        TEST CASE 13  Expected: true                 Actual: false                -
//        TEST CASE 14  Expected: true                 Actual: false                -
//        TEST CASE 15  Expected: false                Actual: false                +
//        TEST CASE 16  Expected: false                Actual: false                +
//        TEST CASE 17  Expected: false                Actual: false                +
//        TEST CASE 18  Expected: true                 Actual: false                -
//        TEST CASE 19  Expected: false                Actual: false                +
//        TEST CASE 20  Expected: false                Actual: false                +
//        TEST CASE 21  Expected: false                Actual: false                +
//        TEST CASE 22  Expected: false                Actual: false                +
//        TEST CASE 23  Expected: true                 Actual: false                -
//


/**
 * Задание:
 * 1. Создайте вложенный класс LoginValidationException, унаследуйте его от Exception
 * 2. Реализуйте проверку "Логина" в методе validateLogin по следуюзим правилам:
 * - должен содержать только латинские буквы, цифры и знак подчеркивания
 * - должен содержать как минимум одну маленькую, одну большую букву, цифру и нижнее подчеркивание
 * - максимальная длинна логина- 20 символов
 * - если логин не соответствует требованиям - выбросить LoginValidationException
 * - можно использовать регулярные выражения
 * 3. Реализуйте проверку логина в методе isLoginValid по следующим правилам
 * - метод должен вызывать метод validateLogin
 * - если метод validateLogin не выбросил ошибку - вернуть true
 * - если метод validateLogin выбросил ошибку - вернуть false
 */


/**
 * Правила выполнения домашнего задания:
 * <p>
 * 1. Вся работа выполняется в файле доамашнего задания
 * Если вас просят создать класс - его надо создать вложенным, т.е. внутри класса HomeWorkN
 * вы создаете класс MyClass {} (все в одном файле)
 * 2. Задание считается выполненным если все тесты пройдены.
 * Тест выполнен если напротив него находится знак ✅
 * 3. Что бы запустить тесты нажмите значек запуска кода напротив мтода main.
 * ‼️ВАЖНО‼️ Вся работа в модкле ведется на JDK-17, перед запуском скачайте его и установите
 * правильный JDK и уровень языка в Settings -> Project Structure
 * Если у тебя не выбран JDK17 toList() бдует выдавать ошибку
 */
public class HomeWork2 {
    public static List<Boolean> checkLoginResults = Arrays.asList(
            true, true, true, false, false, false, true, false, false, false, false, true
    );
    public static List<String> loginList = Arrays.asList(
            "Minecraft_12",                                     // true
            "Player_3433",                                      // true
            "Dok_a111",                                         // true
            "Java",                                             // false
            "1122233",                                          // false
            "Play__",                                           // false
            "_Sun2_",                                           // true
            "____",                                             // false
            "Winx!",                                            // false
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa12_",            // false
            "WOWOWOOWOWOWOOWOWOWOWOWOW",                        // false
            "Correct_22"                                        // true
    );

    public static void validateLogin(String login) {
        String regExp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*_)[a-zA-Z\\d_]{1,20}$";
        Pattern validatePattern = Pattern.compile(regExp);
        Matcher matcher = validatePattern.matcher(login);
        if (!matcher.matches()) {
            throw new LoginValidationException("Invalid login");
        }
    }

    /*
    Это метод main - нажми play что бы запустить тесты
    Ничего не меняй в тестах, они уже написаны так что бы проверить твое решение
    */

    public static Boolean isLoginValid(String login) {
        try {
            validateLogin(login);
        } catch (LoginValidationException e) {
            return false;
        }
        return true;
    }

    /* Техническая секция - сюда писать ничего не надо */

    public static void main(String[] args) {
        System.out.println("\nTests for validateLogin");
        AntiCheat.run();
        for (int i = 0; i < loginList.size(); i++) {
            try {
                validateLogin(loginList.get(i));
                printTestCase(i, checkLoginResults.get(i), true, 20);
            } catch (Exception e) {
                printTestCase(i, checkLoginResults.get(i), false, 20);
            }
        }

        System.out.println("\nTests for isLoginValid");
        AntiCheat.run();
        for (int i = 0; i < loginList.size(); i++)
            printTestCase(i + loginList.size(),
                    checkLoginResults.get(i),
                    isLoginValid(loginList.get(i)),
                    20);
    }

    public static String constLen(String str, int len) {
        StringBuilder sb = new StringBuilder(str);
        while (len-- - str.length() > 0) sb.append(" ");
        return sb.toString();
    }

    public static void printTestCase(int n, Boolean exp, Boolean act, int minLen) {
        Function<String, String> green = str -> "\u001B[34m" + str + "\u001B[0m";
        Function<String, String> yellow = str -> "\u001B[33m" + str + "\u001B[0m";
        System.out.print("TEST CASE " + constLen(String.valueOf(n), 4));
        System.out.print("Expected: " + yellow.apply(constLen(exp.toString(), minLen)) + " Actual: " + green.apply(constLen(act.toString(), minLen) + " "));
        if (Objects.equals(exp, act)) System.out.print("+");
        else System.out.print("-");
        System.out.println();
    }

    static class LoginValidationException extends RuntimeException {
        LoginValidationException() {
            super();
        }

        LoginValidationException(String message) {
            super(message);
        }
    }

    public static class AntiCheat {
        public static void run() {
            StringBuilder sb = new StringBuilder("");
            List<String> antiCheatList = new ArrayList<>();
            antiCheatList.addAll(loginList);
            antiCheatList.addAll(checkLoginResults.stream().map(Object::toString).toList());
            antiCheatList.add(sb.toString());
            calcHash(antiCheatList);
        }

        ;

        public static String bytesToHex(byte[] bytes) {
            char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
            char[] hexChars = new char[bytes.length * 2];
            for (int j = 0; j < bytes.length; j++) {
                int v = bytes[j] & 0xFF;
                hexChars[j * 2] = HEX_ARRAY[v >>> 4];
                hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
            }
            return new String(hexChars);
        }

        public static void calcHash(List<String> list) {
            String total = String.join("", list);
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(total.getBytes());
                byte[] digest = md.digest();
                System.out.println("AntiCheatCheck: " + bytesToHex(digest));
            } catch (NoSuchAlgorithmException ignored) {
            }
        }
    }

}