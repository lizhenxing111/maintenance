import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyStack<T> {
    private Queue<T> queue1;
    private Queue<T> queue2;

    public MyStack() {
        this.queue1 = new LinkedBlockingQueue<T>();
        this.queue2 = new LinkedBlockingQueue<T>();
    }
    public void push(T t){
        queue1.add(t);
        if (queue2.isEmpty()){
            queue2.add(queue1.poll());
        }else{
            while (!queue2.isEmpty()){
                queue1.add(queue2.poll());
            }
            while (!queue1.isEmpty()){
                queue2.add(queue1.poll());
            }
        }
    }
    public T pop(){
        return queue2.poll();
    }
    public T peek(){
        return queue2.peek();
    }
    public boolean isEmpty(){
        return queue1.isEmpty()&&queue2.isEmpty();
    }
    public static void main(String[] args) {
        MyStack<Integer> integerMyQueue = new MyStack<>();
        integerMyQueue.push(1);
        integerMyQueue.push(2);
        integerMyQueue.push(3);
        while (!integerMyQueue.isEmpty()){
            System.out.println(integerMyQueue.pop());
        }
    }
}
