
class MySecondClass {
private int x;
private int y;
public MySecondClass () { 
this.x=0; 
this.y=0;
}
private MySecondClass (int x, int y) { 
this.x=x; 
this.y=y;
}
public void setX (int value)  {this.x = value;}
public void setY (int value)  {this.y = value;}
public int getX () {return x;} 
public int getY () {return y;}
public int summ () {return this.x+this.y;}
}
