package utility;

public class SpeedException extends RuntimeException{
    @Override
    public String toString(){
        return "Неизвестна скорость скольжения!";
    }
}
