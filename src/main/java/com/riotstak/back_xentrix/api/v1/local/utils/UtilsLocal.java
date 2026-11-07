package com.riotstak.back_xentrix.api.v1.local.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import javax.validation.*;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.*;
import org.springframework.validation.ObjectError;

import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Base64;

import org.apache.commons.codec.digest.DigestUtils;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.AplicationLogThread;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.adapters.bd1.AplicationLog;

public class UtilsLocal {

    private static String regxOnlyLettersAndNumbers() {
        return "[A-Za-z0-9\\s]+";
    }

    private static String regxOnlyLetters() {
        return "^[A-Za-z\\s]+$";
    }

    public static Validator getValidatorDto() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator;
    }

    public static String getErrorMessagesList(List<String> errors) {
        StringBuilder messages = new StringBuilder();
        messages.append("Lista_errores:");
        errors.forEach(err -> messages.append("- " + err));
        return messages.toString();
    }

    public static ArrayList<ObjectError> emptyErrorList() {
        return new ArrayList<ObjectError>();
    }

    public static String arrayStringToString(String[] array) {
        String str = String.join(", ", array);
        return str;
    }

    public static String numbersOrNull() {
        return "^[0-9]+$|^(null)$";
    }

    public static Boolean onlyNumbers(String numberStr) {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(numberStr);
        Boolean result = false;
        if (matcher.matches()) {
            result = true;
        }
        return result;
    }

    public static Boolean onlyAlphanumeric(String numberStr) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher matcher = pattern.matcher(numberStr);
        Boolean result = false;
        if (matcher.matches()) {
            result = true;
        }
        return result;
    }

    private static String getRealPath(HttpServletRequest req) {
        return req.getSession().getServletContext().getRealPath("/");
    }

    public static String getDateTimeNow() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeNow = DateTime.now(DateTimeZone.forID("America/Bogota")).toString(formatter);
        return dateTimeNow;
    }

    public static String getDateTimeNowValid() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        String dateTimeNow = DateTime.now(DateTimeZone.forID("America/Bogota")).toString(formatter);
        return dateTimeNow;
    }

    public static LocalDateTime getCurrentDateTimeLocal() {
        return LocalDateTime.now(ZoneId.of("America/Bogota"));
    }

    public static String getDateTimeNowFormat(String format) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        String dateTimeNow = DateTime.now(DateTimeZone.forID("America/Bogota")).toString(formatter);
        return dateTimeNow;
    }

    /**
     * Convertir una fecha de String a Timestamp
     * Ejemplos formatos [ format ]
     * "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
     * "yyyy-MM-dd hh:mm:ss.SSS"
     * "yyyy-MM-dd hh:mm:ss"
     * "yyyy-MM-dd"
     */
    public static Timestamp strDateToTimestamp(String strDate, String format) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        Timestamp timestamp = null;
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(strDate);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            // UtilsLocal.log("Error convirtiendo String a Timestamp: ");
            // UtilsLocal.log(e.getMessage());
        }
        // UtilsLocal.log("Fecha convertida: ");
        // UtilsLocal.log(timestamp.toString());
        return timestamp;
    }

    public static String getIpAddr(HttpServletRequest req) {
        String ip = req.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        return ip;
    }

    public static void log(AplicationLog info) {
        System.out.println(info);
        AplicationLogThread aplicationLogThread = new AplicationLogThread(null, null);
        aplicationLogThread.start();
    }

    public static String encryptingTransactionToB64(String txt) {
        String encrypted = Base64.getEncoder().encodeToString(txt.getBytes());
        int size = encrypted.length();
        int i = 0, end = 4, step = 4;
        boolean ok = true;
        String strOk = "";
        while (i < size && ok) {
            if (end >= size) {
                end = size;
                ok = false;
            }
            String textX = encrypted.substring(i, (end));
            if (i == 0)
                strOk = textX;
            else
                strOk += "-" + textX;

            i = (end);
            end = i + (step);
        }
        return strOk;
    }

    /**
     * Descifrar las Llaves para su posterior uso
     */
    public static String decryptingData(String encrytpt) {
        byte[] bytesDecode = Base64.getDecoder().decode(encrytpt);
        String decrypted = new String(bytesDecode);
        // System.out.println("--- DesCifrado = " + decrypted );
        String txt1 = decrypted.substring(8, decrypted.length());
        String txt2 = txt1.substring(0, (txt1.length() - 8));
        return txt2;
    }

    public static String decryptingTransactionId(String encrytptTransaction) {
        encrytptTransaction = encrytptTransaction.replaceAll("-", "");
        byte[] bytesDecode = Base64.getDecoder().decode(encrytptTransaction);
        String decrypted = new String(bytesDecode);
        // System.out.println("--- DesCifrado = " + decrypted );
        String txt2 = decrypted.substring(0, (decrypted.length() - 5));
        return txt2;
    }

    /**
     * Obtener el Sistema Operativo
     */
    public static String getOs() {
        String mySO = System.getProperty("os.name");
        mySO = mySO.toLowerCase();
        // log("... Sistema Operativo: " + mySO);

        String os = "linux";
        InputStream inputStream = null;
        if (mySO.contains("windows") || mySO.contains("window") || mySO.contains("win"))
            os = "windows";
        return os;
    }

    /**
     * Establecer la Llave de integridad
     */
    public static String setIntegrityHash(String integrityKey, String reference, BigInteger amount, String currency) {
        String stringDecode = reference + amount.toString() + currency + integrityKey;
        // System.out.println( " __stringDecode:" + stringDecode );
        String integrity = DigestUtils.sha256Hex(stringDecode);
        return integrity;
    }

    /**
     * Método que entrega el token del día
     * 
     * @param secret, clave secreta
     *                @return, Cadena de caracteres cifrada en sha512
     */
    public static String generateToken(String secret) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        String dateTimeNow = DateTime.now(DateTimeZone.forID("America/Bogota")).toString(formatter);

        String simpleToken = DigestUtils.sha512Hex(secret + dateTimeNow);
        return simpleToken;
    }

    /**
     * Método que genera una cadena aleatoria con los datos enviados
     * 
     * @param chars,  Cadena de caracteres con los cuales se generara el resultado
     *                random
     * @param length, Longitud de la cadena random que se desea
     *                @return, Cadena de caracteres random obtenidos de chars con la
     *                longitud solicitada en length
     */
    public static String generateRandomChain(String chars, Integer length) {
        SecureRandom random = new SecureRandom();
        return IntStream.range(0, length)
                .map(i -> random.nextInt(chars.length()))
                .mapToObj(randomIndex -> String.valueOf(chars.charAt(randomIndex)))
                .collect(Collectors.joining());
    }

    /**
     * Método que cifra en Base64 una cadena dada
     * 
     * @param text, Cadena que se desea cifrar
     *              @return, Cadena cifrada
     */
    public static String generateBase64(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    public static String generateBase64Byte(byte[] text) {
        // return Base64.getEncoder().encodeToString( text.getBytes() );
        return Base64.getEncoder().encodeToString(text);
    }

    /**
     * Método que cifra en SHA256 una cadena dada
     * 
     * @param text, Cadena que se desea cifrar
     *              @return, Cadena cifrada
     */
    public static String generateSHA256(String text) {
        return DigestUtils.sha256Hex(text);
    }

    /**
     * Método que valida una cadena de texto segun la expresion regular necesitada
     * 
     * @param value Cadena que se desea validar
     * @param regex Expresion regular personalizada
     *              @return, True si cumple
     */
    public static boolean validateFormat(String value, String regex, Integer length) {

        boolean matchesRegex = value.matches(regex);

        // Si length es null o 0, solo validamos el regex. De lo contrario, verificamos
        // longitud
        if (length == null || length <= 0) {
            return matchesRegex; // Solo regresamos si coincide con el regex
        } else {
            return matchesRegex && value.length() <= length; // Verificamos regex y longitud
        }
    }

}
