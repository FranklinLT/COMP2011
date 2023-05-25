import java.util.Arrays;

/*
    "Q2_21101988D_LiTong" is the main class of Q2, it contains a "Matrix" class and a two-dimensional array
    which use to create a minimal heap by using the converted data (which is the hint I get from Q1).

    In this class, the establishment method of the minimum heap refers to the method of the maximum
    heap in Lecture 10.

    By establishing a minimum heap, this makes the same array as the root node only exist in the child
    nodes of the node itself. For example, if [0,1,0,1,0] is the root (heap_list[0]) node, and it has
    the same array in other nodes, then at least heap_list[1] or heap_list[2] has the same array, by
    the way, we only need to compare the root node to its left child and right child to detect werther
    the root is the answer or not.

    If the root node is the result we need to find, we return it, find the index of the result in the
    origin matrix. Otherwise, we drop the root node and find the new root and restart the process above,
    when the heap array only have two node, and they are different, we return (0,0).

    After we create the matrix class, it takes O( n * logn ) to create the minimum heap and takes O(n)
    to find the answer.

    Since the input array represents a symmetric binary matrix, we can take advantage of the
    characteristic of a symmetric binary matrix (always square), which makes it easy to construct two
    2D arrays in this class.
 */
public class Q2_21101988D_LiTong {
    private Matrix matrix;
    private int[][] heap_list;
    private int size;

    public Q2_21101988D_LiTong(int[][] a){
        matrix = new Matrix(a);
        size = 0;
        heap_list = new int[a.length][];
        for(int i = 0; i < a.length; i++){
            insert(matrix.matrix_array[i]);
        }
    }

    private void swap(int i, int j){
        int[] temp = heap_list[i];
        heap_list[i] = heap_list[j];
        heap_list[j] = temp;
    }

    public void up(int index){
        int parent = (index - 1) / 2;
        if(index == 0 || smaller_than(heap_list[parent], heap_list[index])) return;
        swap(index, parent);
        up(parent);
    }

    public void down(int index){
        if(index * 2 + 2 > size) return;
        int smaller = index * 2 + 1;
        if(smaller < size - 1 && smaller_than(heap_list[smaller + 1], heap_list[smaller])){
            smaller++;
        }
        if(smaller_than(heap_list[smaller], heap_list[index])){
            swap(smaller, index);
            down(smaller);
        }
    }

    public void insert(int[] a){
        heap_list[size] = a;
        up(size++);
    }

    public void deleteRoot(){
        if(size == 0) return;
        heap_list[0] = heap_list[--size];
        down(0);
    }

    /*
    This function is used to compare the size of two arrays when building a minimum heap.
    The comparison rules are as follows:
        (1) Compares the number of ones in two arrays, and the array with the smaller number is the smaller array.
            for example, [0,0,0,0,1] < [1,1,1,1,0]
        (2) When the number of numbers one is the same, traverse from the zeroth position of the array, and the
            array with number one appearing first is smaller.
            for example, [0,1,0,0,0] < [0,0,1,0,0]
     */

    public boolean smaller_than(int[] a1, int[]a2){
        if(Arrays.equals(a1, a2)) return false;
        int num1 = 0;
        int num2 = 0;
        for(int count = 0; count < a1.length; count++){
            if(a1[count] == 1) num1++;
            if(a2[count] == 1) num2++;
        }
        if(num2 > num1) return true;
        if(num2 < num1) return false;

        for(int i = 0; i < a1.length; i++){
            if(a1[i] < a2[i]) return false;
        }
        return true;
    }

    public int[] find_same() {
        if (size < 3) {
            if (size == 2 && Arrays.equals(heap_list[0], heap_list[1])){
                return ReFind(heap_list[0]);
            }
            else {
                return new int[]{0, 0};
            }
        }
        if(Arrays.equals(heap_list[0], heap_list[1]) || Arrays.equals(heap_list[0], heap_list[2])){
            return ReFind(heap_list[0]);
        }
        deleteRoot();
        return find_same();
    }

    /*
    This function is used to find the index about the same array.
     */
    private int[] ReFind(int[] a){
        int[] result = new int[2];
        for(int i = 0, n = 0; i < a.length && n < 2; i++){
            if(Arrays.equals(a, matrix.matrix_array[i])) result[n++] = i;
        }
        return result;
    }

    public static void main(String[] args){
        int[][] test_matrix1 = new int[][] {{3,1},{2,0},{1,3},{0,2},{}};
        int[][] test_matrix2 = new int[][] {};
        int[][] test_matrix3 = new int[][] {{1},{0,2},{3,1},{2}};
        Q2_21101988D_LiTong test = new Q2_21101988D_LiTong(test_matrix3);
        int[] answer = test.find_same();
        System.out.println("(" + answer[0] + "," + answer[1] + ")");
    }

}

/*
    This class is used to convert the input list into a matrix (represented by a two-dimensional list).
    it takes O( n ^ 2 ), l is the length of the two-dimensional list.

    After the two-dimensional list convert to a matrix, for example [3,1] to [0,1,0,1,0]. it can avoid
    using sorting algorithms to convert [3,1] into [1,3]. By this way, it is only necessary to design
    a function for comparing arrays containing only have 0 and 1 later.
 */
class Matrix {
    public int[][] matrix_array;

    public Matrix(int[][] a){
        matrix_array = new int[a.length][a.length];
        for(int i = 0; i < a.length; i++){
            translate(a[i], i);
        }
    }

    private void translate(int[] a, int index){
        for (int j : a) {
            matrix_array[index][j] = 1;
        }
    }
}