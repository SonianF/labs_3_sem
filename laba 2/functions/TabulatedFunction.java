package functions;

public class TabulatedFunction {
    //Массив содержащий точки функции
    private FunctionPoint[] values;

    //Количество точек функции
    private int countOfPoint;

    /*создаёт объект табулированной функции по заданным левой и правой границе области
     определения, а также количеству точек для табулирования (значения функции в точках при этом
      следует считать равными 0);*/
    public TabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (pointsCount<2||Double.compare(leftX, rightX)==0||rightX<leftX)
            //Double.compare(double d1, double d2) - эта функция позволяет определить, какое из чисел больше, меньше ил числа равны
            throw new IllegalArgumentException();
        this.values=new FunctionPoint[pointsCount*2];
        this.countOfPoint=pointsCount;
        double step = (rightX-leftX)/(pointsCount-1.);
        for (int i=0; i<pointsCount; i++) {
            this.values[i]=new FunctionPoint(leftX+i*step, 0);
        }

    }

    /*аналогичен предыдущему конструктору, но вместо количества
    точек получает значения функции в виде массива.*/
    public TabulatedFunction(double leftX, double rightX, double[] array) {
        if (array.length==0||Double.compare(leftX, rightX)==0||rightX<leftX)
            //если ==0, то два числа равны
            //Double.compare(double d1, double d2) - эта функция позволяет определить, какое из чисел больше, меньше ил числа равны
            throw new IllegalArgumentException();
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
            throw new IllegalArgumentException();
        return new FunctionPoint(this.values[index]);
    }

    /* Заменяет указанную точку табулированной функции на заданную.
    В случае если координата x задаваемой точки лежит вне интервала, определяемого значениями соседних точек табулированной функции, то замена не проводится */
    public void setPoint(int index, FunctionPoint point) {
        isNotOutOfBounds(index);
        if (index == 0) {
            if (point.getX() < this.values[index + 1].getX()) {
                values[index] = new FunctionPoint(point); //добавляем эту точку, если значение следущей по оси х больше заданной
            } else {
                throw new IllegalArgumentException();
            }
        } else if (index == this.countOfPoint - 1) {
            if (point.getX() >= this.values[index - 1].getX()) {
                values[index] = new FunctionPoint(point);
            } else {
                throw new IllegalArgumentException();
            }
        } else { //если меняем не первый и не последний элемент
            if (point.getX() >= this.values[index - 1].getX() && point.getX() <= this.values[index + 1].getX()) {
                values[index] = new FunctionPoint(point); //если значение по х лежит между двумя соседними значения, то добавляем
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    /* возвращает значение абсциссы точки с указанным номером.*/
    public double getPointX(int index) {
        isNotOutOfBounds(index);
        return values[index].getX();
    }

    /* изменяет значение абсциссы точки с указанным номером. */
    public void setPointX(int index, double x) {
        isNotOutOfBounds(index);
        setPoint(index, new FunctionPoint(x, values[index].getY()));
    }

    /* возвращает значение ординаты точки с указанным номером.*/
    public double getPointY(int index) {
        isNotOutOfBounds(index);
        return values[index].getY();
    }

    /* изменяет значение ординаты точки с указанным номером.*/
    public void setPointY(int index, double y) {
        isNotOutOfBounds(index);
        setPoint(index, new FunctionPoint( values[index].getX(), y));
    }

    /* удаляет заданную точку табулированной функции.*/
    public void deletePoint(int index) {
            if (this.countOfPoint < 3) { //если количество элементов 2 и меньше, то удалять мы не можем по условию
                throw new IllegalStateException();
            }
            isNotOutOfBounds(index);
            this.values[index] = null; //обнуляем значения точки
            for (; index < this.countOfPoint-1; ++index) { //смещаем значения на -1 влево
            this.values[index] = this.values[index + 1];
            }
            --this.countOfPoint; // уменьшаем количество табулированных точек


    }

    /* добавляет новую точку табулированной функции. */
    public void addPoint(FunctionPoint point) {
        int index = 0;
        for (; index < countOfPoint && point.getX() > values[index].getX(); ++index) ;//нашли куда вставлять
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

    private void isNotOutOfBounds(int index) {
        if (index < 0 || index >= countOfPoint) {
            throw new IndexOutOfBoundsException();
        }
    }




}
