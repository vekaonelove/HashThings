//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import linkedList.LinkedList;
import linkedList.impl.LinkedListImpl;

public class Main {
    public static void main(String[] args) {
        LinkedList<Integer> list1 = new LinkedListImpl<>();
        for (int i=0; i<10; i++){
            list1.addToEnd(i);
            System.out.println("added element:" + list1.getElementByIndex(i));
        }
        System.out.println("list = " + list1);
    }
}