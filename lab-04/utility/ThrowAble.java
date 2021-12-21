package utility;

public interface ThrowAble {
    boolean getWinner();

    boolean getKnown();

    void waiting();

    void throwing(FloatAble coneFirst, FloatAble coneSecond) throws Exception;

    void knowing();

    void planning(FloatAble cone);

    void winning();

    void doingDefault();

    void taking(FloatAble cone);
}
