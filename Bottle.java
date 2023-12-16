public class Bottle {
  private int size;
  private int top;
  private ColorBlock[] array;

  public Bottle(int size) {
    top = -1;
    this.size = size;
    this.array = new ColorBlock[size];
  }

  public int getSize() {
    return size;
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

  public ColorBlock pop() {
    if (this.isEmpty()) {
      return null;
    }
    top--;
    return array[top + 1];
  }

  public Boolean push(ColorBlock CB) {
    if (this.isFull()) {
      return false;
    }
    top++;
    array[top] = CB;
    return true;
  }

  public ColorBlock getTop() {
    if (this.isEmpty()) {
      return null;
    }
    return array[top];
  }
}
