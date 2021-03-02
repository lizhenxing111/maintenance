import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.regex.Pattern;

public class MyQueue<T> {
    private Stack<T> stack1;
    private Stack<T> stack2;

    public MyQueue() {
        this.stack1 = new Stack<T>();
        this.stack2 = new Stack<T>();
    }

    public void push(T t) {
        stack1.push(t);
    }

    public T peek() {
        if (!stack1.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.peek();
    }

    public T pop() {
        if (!stack1.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }
    public boolean isEmpty(){
        return stack2.isEmpty()&&stack1.isEmpty();
    }
    public static void main(String[] args) {
        MyQueue<Integer> integerMyQueue = new MyQueue<>();
        integerMyQueue.push(1);
        integerMyQueue.push(2);
        integerMyQueue.push(3);

    }
}
