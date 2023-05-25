import java.util.ArrayList;

/*
    This is the main class of Q1, this class has an "index_structure" named MinTree and a two-dimensional array
    which is the input one.
 */

public class Q1_21101988D_LiTong {
    private index_structure MinTree;
    private int[][] lists;

    public Q1_21101988D_LiTong(int[][] lists){
        this.lists = lists;
        this.MinTree = new index_structure(lists);
    }


    /*
    This is the merge method from Lab 10, I use the same way to merge two array.
     */

    private static int[] twoWayMerge(int[] a1, int[] a2) {
        int i = 0, j = 0, k = 0;
        int n1 = a1.length, n2 = a2.length;
        int[] res = new int[n1 + n2];
        while (i < n1 && j < n2)
            res[k++] = (a1[i] <= a2[j])?a1[i++]:a2[j++];
        for (; i < n1; ) res[k++] = a1[i++];
        for (; j < n2; ) res[k++] = a2[j++];
        return res;
    }


    /*
    This function ask for a int array which contain the index of the two smallest array and then follow the index
    to find the array store in the "lists", then merge them and create a new "information" class and insert it to
    the minimum heap.
     */
    public void merge_two_min(int[] indices){
        int index1 = indices[0], index2 = indices[1];
        lists[index2] = twoWayMerge(lists[index1], lists[index2]);
        MinTree.insert(new index_structure.information(lists[index2], index2));
    }

    /*
    This function use a loop to call "merge_two_min" function to merge all the array step by step, and it will
    return the result array (after merge all the arrays).
     */

    public int[] merge_All(){
        if(lists.length == 1) return lists[0];
        while (MinTree.data.size() >= 2){
            merge_two_min(MinTree.return_two_min());
            //System.out.println(MinTree.data.size());
        }
        return lists[MinTree.data.get(0).index];
    }

    public static void main(String[] args){
        int[][] L = new int[][] {{1,2,3},{4},{5},{6}};
        Q1_21101988D_LiTong test = new Q1_21101988D_LiTong(L);

        int[] output = test.merge_All();
        for(int element : output){
            System.out.print(element + " ");
        }
        System.out.println();
    }
}

/*
    Consider that we need to find two smallest array in a constant time, we need to create a minimum heap to store
    the information about the input two-dimensional array, the index_structure class also has a inside class called
    "information", it will use to store the length and index of the array in the input two-dimensional array. By
    the way, there is no need to store the elements store in the array which makes it more efficient.

    In this class, the establishment method of the minimum heap refers to the method of the maximum
    heap in Lecture 10. The arraylist "data" is the minimum heap list.

    It takes O( n ) to build the heap and take O( 1 ) to find the two smallest array
 */
class index_structure {
    public ArrayList<information> data;

    static class information{
        int length;
        int index;

        public information(int[] a, int index){
            this.index = index;
            this.length = a.length;
        }
    }

    public index_structure(int[][] data){
        this.data = new ArrayList<>();
        for(int i = 0; i < data.length; i++){
            information inf = new information(data[i], i);
            insert(inf);
        }
    }

    private void swap(int i, int j) {
        information temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }

    public void up(int index){
        int parent = (index - 1) / 2;
        if (index == 0 || data.get(parent).length < data.get(index).length) return;
        swap(index, parent);
        up(parent);
    }

    public void down(int index){
        if(2 * index + 2 > data.size()) return;
        int smaller = 2 * index + 1;
        if(smaller < data.size() - 1 && data.get(smaller).length > data.get(smaller + 1).length){
            smaller++;
        }
        if(data.get(index).length <= data.get(smaller).length) return;
        swap(index, smaller);
        down(smaller);
    }

    public void insert(information cur){
        data.add(cur);
        up(data.indexOf(cur));
    }

    public void removeMin(){
        if(data.size() == 0) return;
        data.set(0, data.get(data.size() - 1));
        data.remove(data.size() - 1);
        down(0);
    }

    /*
    This function will return two smallest array and remove them from the heap. After calling the function
    multiple time, all the small array will be merged into a big one.

    it is O( 1 )
     */
    public int[] return_two_min(){
        if(data.size() == 2) {
            int[] answer = new int[] {data.get(0).index, data.get(1).index};
            removeMin();
            removeMin();
            return answer;
        }
        int index2 = (data.get(1).length <= data.get(2).length) ? data.get(1).index : data.get(2).index;

        int[] answer = new int[] {data.get(0).index, index2};
        removeMin();
        removeMin();
        return answer;
    }
}