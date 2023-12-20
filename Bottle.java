public class Bottle {
  private int size;
  private int top;
  private boolean isSelected;
  private ColorBlock[] array;

  public Bottle(int size) {
    top = -1;
    this.size = size;
    this.array = new ColorBlock[size];
    this.isSelected = false;
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

  public Boolean isComplete() {
    if (this.isFull() && this.getTop().getHeight() == this.getSize()) {
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

  public int getColorBlocksCount() {
    return top + 1;
  }

  public boolean getSelect() {
    return isSelected;
  }

  public static Bottle getSelectedBottle(Bottle... bottles) {
    for (int i = 0; i < bottles.length; i++) {
      if (bottles[i].getSelect()) {
        return bottles[i];
      }
    }
    return null;
  }

  public void select() {
    this.isSelected = true;
  }

  public void deSelect() {
    if (this.isSelected == true) {
      this.isSelected = false;
    }
  }
}
