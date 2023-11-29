package functions;

public class FunctionPoint {
    private double x;
    private double y;
    public FunctionPoint(double x, double y) {
        this.x=x;
        this.y=y;
    }
    public FunctionPoint(FunctionPoint point) {
        this.x= point.getX();
        this.y=point.getY();
    }

    public FunctionPoint() {
        this.x=0.0;
        this.y=0.0;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double x) {
        this.x=x;
    }

    public void setY(double y) {
        this.y=y;
    }

    /*Метод возвращает текстовое описание точки. */
    public String toString(){
       return "(" + x + ";" + y +")";
    }

    /*Метод возвращает истинное значение тогда и только тогда, когда переданный в метод объект
    также является точкой и его координаты в точности совпадают с координатами объекта, у которого вызывается метод.*/
    public boolean equals(Object o) {
        //instanceof - ключевое слово, используемое для проверки, содержит ли ссылочная переменная ссылку на объект заданного типа или нет
        return o instanceof FunctionPoint && this.getX()==((FunctionPoint) o).getX() && this.getY()==((FunctionPoint) o).getY();
    }

    /*Метод должен возвращать значение хэш-кода для объекта точки. */
    public int hashCode() {
        long longX= Double.doubleToLongBits(x);
        long longY = Double.doubleToLongBits(y);
        int x1= (int)(longX)&(0x0000ffff); //получаем младшие биты
        int x2=(int) (longX>>32); //получаем старшие биты
        int y1= (int)(longY)&(0x0000ffff);
        int y2=(int) (longY>>32);
        return x1^x2^y1^y2;
    }

    public Object clone() {
        Object result = null;
        try {
            result = super.clone();
        }
        catch (CloneNotSupportedException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }



}
