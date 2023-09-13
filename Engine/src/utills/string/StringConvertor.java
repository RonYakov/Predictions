package utills.string;

public abstract class StringConvertor {
    public synchronized static int convertStringToInt(String valueToConvert)
    {
        try {
            return Integer.parseInt(valueToConvert);
        }
        catch (NumberFormatException e) {
            throw new NumberFormatException( "Method ConvertStringToInt failed! Problem " + e.getMessage() + ". Please enter decimal number. ");
        }
    }

    public synchronized static boolean isStringInt(String valueToCheck) {
        try {
            int ingore = Integer.parseInt(valueToCheck);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public synchronized static float convertStringToFloat(String valueToConvert) {
        try {
            return Float.parseFloat(valueToConvert);
        }
        catch (NumberFormatException e) {
            throw new NumberFormatException( "Method ConvertStringToFloat failed! Problem " + e.getMessage() + ". Please enter float number. ");
        }
    }

    public synchronized static boolean isStringFloat(String valueToCheck) {
        try {
            float ingore = Float.parseFloat(valueToCheck);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public synchronized static boolean convertStringToBool(String valueToConvert)
    {
        boolean res;

        res = (valueToConvert.equalsIgnoreCase("true") || valueToConvert.equalsIgnoreCase("false"));

        if(!res) {
            throw new IllegalArgumentException("Method ConvertStringToBool failed! Problem from input: " + valueToConvert + ". Please enter True or False. ");
        }
        else{
            return Boolean.parseBoolean(valueToConvert); //will not throw any ex because checked above completely
        }
    }

    public synchronized static boolean isStringBoolean(String valueToCheck) {
        if(valueToCheck.equals("true") || valueToCheck.equals("false")) {
            return true;
        }
        else {
            return false;
        }
    }
}
