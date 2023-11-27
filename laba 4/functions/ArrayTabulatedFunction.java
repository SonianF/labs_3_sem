package functions;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ArrayTabulatedFunction implements TabulatedFunction, java.io.Externalizable {
    //Массив содержащий точки функции
    private FunctionPoint[] values;

    //Количество точек функции
    private int countOfPoint;

    public ArrayTabulatedFunction() {};

    /*создаёт объект табулированной функции по заданным левой и правой границе области
     определения, а также количеству точек для табулирования (значения функции в точках при этом
      следует считать равными 0);*/
    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException{
        if (leftX>=rightX) {
            throw new IllegalArgumentException("The left border of the area must be lower that the right");
        }
        if (pointsCount<2)
            throw new IllegalArgumentException("The number of points must be more than two");
        this.values=new FunctionPoint[pointsCount*2];
        this.countOfPoint=pointsCount;
        double step = (rightX-leftX)/(pointsCount-1.);
        for (int i=0; i<pointsCount; i++) {
            this.values[i]=new FunctionPoint(leftX+i*step, 0);
        }

    }

    /*конструктор, получающий сразу все точки функции в виде массива объектов типа FunctionPoint.*/
    public ArrayTabulatedFunction(FunctionPoint[] points) {
        if (points.length<2)
            throw new IllegalArgumentException("The number of points must be more than two");
        countOfPoint=points.length;
        values=new FunctionPoint[countOfPoint];
        for (int i=0; i<countOfPoint; i++) { //в цикле добавляем элементы
            if (i!=0 && points[i-1].getX()>=points[i].getX()){ //проверка на упорядоченность элементов по значениям х
                throw new IllegalArgumentException("The points must be ordered by abscissa");
            }
            values[i]=points[i];
        }
    }

    /*аналогичен предыдущему конструктору, но вместо количества
    точек получает значения функции в виде массива.*/
    public ArrayTabulatedFunction(double leftX, double rightX, double[] array) throws IllegalArgumentException{
        if (leftX>=rightX) {
            throw new IllegalArgumentException("The left border of the area must be lower that the right");
        }
        if (array.length<2)
            throw new IllegalArgumentException("The number of points must be more than two");
        this.values=new FunctionPoint[array.length];
        this.countOfPoint= array.length;
        double step = (rightX-leftX)/(array.length-1.);
        for (int i = 0; i< array.length; i++) {
            this.values[i]=new FunctionPoint(leftX+i*step, array[i]);
        }

    }



    /*возвращает значение левой границы области определения табулированной функции.*/
    public double getLeftDomainBorder() {
        return this.values[0].getX();
    }

    /*возвращает значение правой границы области определения табулированной функции.*/
    public double getRightDomainBorder() {
        return this.values[values.length-1].getX();
    }

    /* Возвращает значение функции в точке x, если эта точка лежит в области определения функции.
     В противном случае метод должен возвращать значение неопределённости (оно хранится, например, в поле NaN класса Double). */
    public double getFunctionValue(double x) {

        if (x>getRightDomainBorder()||x<getLeftDomainBorder()) return Double.NaN; //если не лежит в области
        int i;
        for (i=1; i<this.countOfPoint&& this.values[i].getX()<x;++i);
        double leftX = this.values[i-1].getX();
        double leftY = this.values[i-1].getY();
        double rightX = this.values[i].getX();
        double rightY = this.values[i].getY();
        return ((rightX-leftX)*(x-leftX)/(rightY-leftY)+leftY);
    }

    /*возвращает количество точек*/
    public int getPointsCount() {
        return this.countOfPoint;
    }

    /* возвращает ссылку на объект, описывающий точку, имеющую указанный номер */
    public FunctionPoint getPoint(int index) {
        if (index>this.countOfPoint||index<0)
            throw new FunctionPointIndexOutOfBoundsException();
        return new FunctionPoint(this.values[index]);
    }

    /* Заменяет указанную точку табулированной функции на заданную.
    В случае если координата x задаваемой точки лежит вне интервала, определяемого значениями соседних точек табулированной функции, то замена не проводится */
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index < 0 || index >= countOfPoint) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if ((index!=0 && values[index-1].getX()>=point.getX()) || (index!=countOfPoint-1 && point.getX() >= this.values[index + 1].getX())) {
            throw new InappropriateFunctionPointException();
        }
        values[index]=point;
    }

    /* возвращает значение абсциссы точки с указанным номером.*/
    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
        if (index < 0 || index >= countOfPoint) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return values[index].getX();
    }

    /* изменяет значение абсциссы точки с указанным номером. */
    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException{
        if (index < 0 || index >= countOfPoint) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if ((index!=0 && values[index-1].getX()>=x || (index!=countOfPoint-1 && x >= this.values[index + 1].getX()))) {
            throw new InappropriateFunctionPointException();
        }
        values[index].setX(x);
    }

    /* возвращает значение ординаты точки с указанным номером.*/
    public double getPointY(int index) {
        if (index < 0 || index >= countOfPoint) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return values[index].getY();
    }

    /* изменяет значение ординаты точки с указанным номером.*/
    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException {
        if (index < 0 || index >= countOfPoint) {
            throw new FunctionPointIndexOutOfBoundsException("Index is not natural number.");
        }
        values[index].setY(y);
    }

    /* удаляет заданную точку табулированной функции.*/
    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException {
            if (this.countOfPoint < 3) { //если количество элементов 2 и меньше, то удалять мы не можем по условию
                throw new IllegalStateException();
            }
            if (index < 0 || index >= countOfPoint) {
                throw new FunctionPointIndexOutOfBoundsException("Error! The index of the deleted element is less than 0.");
            }
            this.values[index] = null; //обнуляем значения точки
            for (; index < this.countOfPoint-1; ++index) { //смещаем значения на -1 влево
            this.values[index] = this.values[index + 1];
            }
            --this.countOfPoint; // уменьшаем количество табулированных точек


    }

    /* добавляет новую точку табулированной функции. */
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        int index = 0;
        for (; index < countOfPoint && point.getX() > values[index].getX(); ++index) ;//нашли куда вставлять
        if (point.getX()==getPointX(index)) throw new InappropriateFunctionPointException();
        if (values.length>=this.countOfPoint+1){
            System.arraycopy(values, 0, values, 0, index);
            System.arraycopy(values, index, values, index+1, this.countOfPoint-index);
            values[index]=point;
            countOfPoint++;
        }
        else {
            FunctionPoint[] array = new FunctionPoint[countOfPoint+1];
            System.arraycopy(values, 0, array, 0, index);
            array[index]=point;
            System.arraycopy(values, index,array,index+1, countOfPoint-index);
            values=array;
            countOfPoint++;
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(values);
        out.writeObject(countOfPoint);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        values = (FunctionPoint[]) in.readObject();
        countOfPoint= (int) in.readObject();
    }
}
