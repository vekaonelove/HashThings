public class ExampleGeneric<T> {
    private T field1;
    private T field2;

    public ExampleGeneric(T field1, T field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    public T getField1() {
        return field1;
    }

    public T getField2() {
        return field2;
    }

    @Override
    public String toString() {
        return "ExampleGeneric{" +
                "field1=" + field1 +
                ", field2=" + field2 +
                '}';
    }
}