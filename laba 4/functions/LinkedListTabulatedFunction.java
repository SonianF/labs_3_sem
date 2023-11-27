package functions;

public class LinkedListTabulatedFunction {
    private class FunctionNode {
        public FunctionPoint item; //значение узла
        public FunctionNode prev; //ссылка на предыдущий узел
        public FunctionNode next; //ссылка на следующий узел

        public FunctionNode() { //конструктор по умолчанию
            this.item = null;
            this.prev = null;
            this.next = null;
        }

        public FunctionNode(FunctionNode node) { //конструктор копирования узла
            this.item = node.item;
            this.prev = node.prev;
            this.next = node.next;
        }
    }

    private FunctionNode head; //ссылка на голову
    private int currentIndex; // номер текущего элемента
    private FunctionNode currentNode; //ссылка на текущий элемент
    private int countOfPoint; //количество элементов

    /*Метод возвращающий ссылку на объект элемента списка по его номеру. Нумерация значащих элементов (голова списка в данном случае к ним не относится) должна начинаться с 0. */
    FunctionNode getNodeByIndex(int index) {
        index = index % countOfPoint;
        if (Math.abs(index - currentIndex) > index) { //то мы идем вперед/вправо от головы
            currentNode = head.next;
            currentIndex = index;
            int steps = index;
            while (steps-- > 0) {
                currentNode = currentNode.next;
            }
        } else if (Math.abs(index - currentIndex) > countOfPoint - index) {//то мы идем назад/влево от головы
            currentNode = head.next;
            currentIndex = index;
            int steps = countOfPoint - index;
            while (steps-- > 0) {
                currentNode = currentNode.prev;
            }
        } else if (index > currentIndex) { //отсчет от текущего элемента вперед
            int steps = index - currentIndex;
            currentIndex = index;
            while (steps-- > 0) {
                currentNode = currentNode.next;
            }
        } else { //отсчет от текущего элемента назад
            int steps = currentIndex - index;
            currentIndex = index;
            while (steps-- > 0) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;

    }

    private FunctionNode addNodeToTail() { //Метод, добавляющий новый элемент в конец списка и возвращающий ссылку на объект этого элемента.
        FunctionNode newNode = new FunctionNode();
        if (head == head.next) { //если список был пустым
            head.next = newNode;
            newNode.prev = newNode.next = newNode;
        } else {
            newNode.prev = head.next.prev;
            newNode.next = head.next;
            head.next.prev.next = newNode;
            head.next.prev = newNode;
        }
        countOfPoint++;
        return newNode;
    }

    private FunctionNode addNodeByIndex(int index) { //Метод, добавляющий новый элемент в указанную позицию списка и возвращающий ссылку на объект этого элемента.
        index = index % countOfPoint; //Т.к. у нас циклический список
        FunctionNode placeOfInsert = getNodeByIndex(index); //находим узел списка, перед которым будем вставлять
        FunctionNode newNode = new FunctionNode();
        newNode.prev = placeOfInsert.prev;
        newNode.next = placeOfInsert.next;
        placeOfInsert.prev.next = newNode;
        placeOfInsert.next.prev = newNode;
        countOfPoint++;
        currentNode = newNode;
        currentIndex = index;
        return newNode;
    }

    private FunctionNode deleteNodeByIndex(int index) { //Метод, удаляющий элемент списка по номеру и возвращающий ссылку на объект удаленного элемента.
        index = index % countOfPoint; //Т.к. у нас циклический список
        FunctionNode nodeToDelete = getNodeByIndex(index); //находим узел списка, перед которым будем удалять
        nodeToDelete.prev.next = nodeToDelete.next;
        nodeToDelete.next.prev = nodeToDelete.prev;
        countOfPoint--;
        return nodeToDelete;

    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (pointsCount<2||Double.compare(leftX, rightX)==0||rightX<leftX)
            //Double.compare(double d1, double d2) - эта функция позволяет определить, какое из чисел больше, меньше ил числа равны
            throw new IllegalArgumentException();
        //coutOfPoint=pointsCount;
        double step = (rightX-leftX)/(pointsCount-1.); //вычисляем интервал между точками
        head = new FunctionNode();
        head.next=head.prev=head; //создали пустой список
        this.countOfPoint=pointsCount;
        for (int i=0; i<pointsCount; i++) {
            FunctionNode newNode = addNodeToTail();
            newNode.item=new FunctionPoint(leftX+i*step, 0);
        }
        currentNode=head.next;
    }

    /*аналогичен предыдущему конструктору, но вместо количества
    точек получает значения функции в виде массива.*/
    public LinkedListTabulatedFunction(double leftX, double rightX, double[] array) {
        if (array.length<2||Double.compare(leftX, rightX)==0||rightX<leftX)
            //если ==0, то два числа равны
            //Double.compare(double d1, double d2) - эта функция позволяет определить, какое из чисел больше, меньше ил числа равны
            throw new IllegalArgumentException();
        double step = (rightX-leftX)/(array.length-1.); //вычисляем интервал между точками
        head = new FunctionNode();
        head.next=head.prev=head; //создали пустой список
        this.countOfPoint= array.length;
        for (int i=0; i<array.length; i++) {
            FunctionNode newNode = addNodeToTail();
            newNode.item=new FunctionPoint(leftX+i*step, array[i]);
        }
        currentNode=head.next;
    }

    /*конструктор, получающий сразу все точки функции в виде массива объектов типа FunctionPoint.*/
    public LinkedListTabulatedFunction(FunctionPoint[] points) throws IllegalArgumentException{
        if (points.length<2)
            throw new IllegalArgumentException("The number of points must be more than two");
        countOfPoint=points.length;
        head=new FunctionNode();
        head.next=head.prev =head;
        for (int i=0; i<countOfPoint; i++) { //в цикле добавляем элементы
            if (i!=0 && points[i-1].getX()>=points[i].getX()){ //проверка на упорядоченность элементов по значениям х
                throw new IllegalArgumentException("The points must be ordered by abscissa");
            }
            FunctionNode newNode = addNodeToTail();
            newNode.item=points[i];
        }
        currentNode=head.next;
    }

    /*возвращает значение левой границы области определения табулированной функции.*/
    public double getLeftDomainBorder() {
        return head.next.item.getX();
    }

    /*возвращает значение правой границы области определения табулированной функции.*/
    public double getRightDomainBorder() {return head.next.prev.item.getX();}

    /* Возвращает значение функции в точке x, если эта точка лежит в области определения функции.
     В противном случае метод должен возвращать значение неопределённости (оно хранится, например, в поле NaN класса Double). */
    public double getFunctionValue(double x) {
        if (getLeftDomainBorder()<=x && x<=getRightDomainBorder()) {
            if (x < currentNode.item.getX()) { //если значение лежит до currentNode, считаем от head
                currentNode = head.next;
                currentIndex = 0;
            }
            do {
                if (currentNode.item.getX()>=x) {
                    return ((currentNode.item.getY()-currentNode.prev.item.getY())*(x-currentNode.prev.item.getX()))/ (currentNode.item.getX()-currentNode.prev.item.getX())+currentNode.prev.item.getY();
                }
                currentNode=currentNode.next;
                currentIndex=(currentIndex+1)%countOfPoint;
            }
            while(currentNode!=head.next);
        }
        return Double.NaN;
    }

    /*возвращает количество точек*/
    public int getPointsCount() {
        return this.countOfPoint;
    }

    /* возвращает ссылку на объект, описывающий точку, имеющую указанный номер */
    public FunctionPoint getPoint(int index) {
        if (index>this.countOfPoint||index<0)
            throw new FunctionPointIndexOutOfBoundsException();
        return getNodeByIndex(index).item;
    }

    /* Заменяет указанную точку табулированной функции на заданную.
    В случае если координата x задаваемой точки лежит вне интервала, определяемого значениями соседних точек табулированной функции, то замена не проводится */
    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException
    {
        if (index < 0 || index >= countOfPoint) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if ((index!=0 && getNodeByIndex(index-1).item.getX()>=point.getX()) || (index!=countOfPoint-1 && point.getX() >= getNodeByIndex(index+1).item.getX())) {
            throw new InappropriateFunctionPointException();
        }
        getNodeByIndex(index).item=point;
    }

    /* возвращает значение абсциссы точки с указанным номером.*/
    public double getPointX(int index) {
        if (index < 0 || index >= countOfPoint) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return getNodeByIndex(index).item.getX();
    }

    /* изменяет значение абсциссы точки с указанным номером. */
    public void setPointX(int index, double x) throws InappropriateFunctionPointException{
        if (index < 0 || index >= countOfPoint) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if ((index!=0 && getNodeByIndex(index-1).item.getX()>=x || (index!=countOfPoint-1 && x >= getNodeByIndex(index+1).item.getX()))) {
            throw new InappropriateFunctionPointException();
        }
        getNodeByIndex(index).item.setX(x);
    }

    /* возвращает значение ординаты точки с указанным номером.*/
    public double getPointY(int index) {
        if (index < 0 || index >= countOfPoint) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return getNodeByIndex(index).item.getY();
    }

    /* изменяет значение ординаты точки с указанным номером.*/
    public void setPointY(int index, double y) {
        if (index < 0 || index >= countOfPoint) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        getNodeByIndex(index).item.setY(y);
    }

    /* удаляет заданную точку табулированной функции.*/
    public void deletePoint(int index) {
        if (this.countOfPoint < 3) { //если количество элементов 2 и меньше, то удалять мы не можем по условию
            throw new IllegalStateException();
        }
        if (index < 0 || index >= countOfPoint) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        deleteNodeByIndex(index);
    }

    /* добавляет новую точку табулированной функции. */
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (point.getX()<currentNode.item.getX()){ //если абсцисса новой точки лежит до currentNode, считаем от head, иначе от current
            currentNode=head.next;
            currentIndex=0;
        }
        do {
            if (currentNode.item.getX()>=point.getX()) { //если нашли точку, которая по х больше нашего икса, вставляем на место перед ней
                //вставляем новую точку до current
                if (currentNode.item.getX()==point.getX()) {
                    throw new InappropriateFunctionPointException();
                }
                FunctionNode newNode = addNodeByIndex(currentIndex);
                newNode.item=point;
                return;
            }
            currentNode=currentNode.next;
            currentIndex = (currentIndex+1)% countOfPoint;
        } while (currentNode!=head.next);
        //если мы вышли из цикла,значит новая точка больше всех остальных -> Вставляем в хвост
        FunctionNode newNode = addNodeToTail();
        newNode.item=point;
    }
}