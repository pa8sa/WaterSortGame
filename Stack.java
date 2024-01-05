public class Stack {
  private int size;
  private int top;
  private String[] array;

  public Stack(int size) {
    this.size = size;
    this.array = new String[size];
    this.top = -1;
  }

  public Boolean isEmpty() {
    if (this.top == -1) {
      return true;
    }
    return false;
  }

  public Boolean isFull() {
    if (this.top == size - 1) {
      return true;
    }
    return false;
  }

  public String pop() {
    if (!this.isEmpty()) {
      top--;
      return array[top + 1];
    }
    return null;
  }

  public void push(String command) {
    if (!this.isFull()) {
      top++;
      array[top] = command;
    }
  }

  public String getTop() {
    if (!this.isEmpty()) {
      return array[top];
    }
    return null;
  }

  public void display() {
    for (int i = 0; i < top + 1; i++) {
      System.out.print(array[i] + " => ");
    }
    System.out.println();
  }
}
