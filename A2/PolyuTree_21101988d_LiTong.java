package comp2011.a2;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.Math;

/**
 * 
 * @author Yixin Cao (November 1, 2022)
 *
 * A binary search tree for Polyu students.
 * 
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * Update on November 8: fix a bug in the main method. 
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * 
 * Since we only store students, the class doesn't use generics.
 * 
 */
public class PolyuTree_21101988d_LiTong { // Please change!
    /**
     * No modification to the class {@code Student} is allowed. 
     * If you change anything in this class, your work will not be graded.
     */
	static class Student {
		String id;
	    String name;
	    public Student(String id, String name) {
	        this.id = id;
	        this.name = name;
	    }
	    
	    public String toString() {return id + ", " + name;}
	}
	
    /**
     * No modification to the class {@code Node} is allowed. 
     * If you change anything in this class, your work will not be graded.
     */
    private class Node {
        Student e;
        public Node lc, rc; // left child; right child

        @SuppressWarnings("unused")
		public Node(Student data) {
            this.e = data;
        }

        public String toString() {
            return e.toString();
        }
    }

    Node root;
    
    
    //Those functions will help to build a AVL tree.
    private void left_turn(Node cur) {
    	Node tempNode = new Node(cur.e);
    	tempNode.lc = cur.lc;
    	tempNode.rc = cur.rc.lc;
    	cur.e = cur.rc.e;
    	cur.rc = cur.rc.rc;
    	cur.lc = tempNode;
    }
    
    private void right_turn(Node cur) {
    	Node tempNode = new Node(cur.e);
    	tempNode.rc = cur.rc;
    	tempNode.lc = cur.lc.rc;
    	cur.e = cur.lc.e;
    	cur.lc = cur.lc.lc;
    	cur.rc = tempNode;
    }
    
    private Node not_AVL(Node cur) {
    	if(cur == null) return cur;
    	boolean l_flag = isAVLtree(cur.lc);
    	boolean r_flag = isAVLtree(cur.rc);
    	
    	if(l_flag && r_flag && !isAVLtree(cur)) {
    		return cur;
    	}
    	else if(!l_flag) {
    		return not_AVL(cur.lc);
    	}
    	else {
    		return not_AVL(cur.rc);
    	}
    }
    
    private boolean isAVLtree(Node cur) {
    	if(cur == null) return true;
    	
    	int dif = Math.abs(get_high(cur.lc) - get_high(cur.rc));
    	if(isAVLtree(cur.lc) && isAVLtree(cur.rc) && dif <= 1) {
    		return true;
    	}
    	return false;
    }
    
    
    /**
     * Insert a new student into the tree.
     * 
     * VERY IMPORTANT.
     * 
     * I've discussed this question with the following students:
     *     1. 
     *     2. 
     *     3. 
     *     ... 
     * 
     * I've sought help from the following Internet resources and books:
     *     1. https://blog.csdn.net/qq_41665523/article/details/105025302
     *     2. https://blog.csdn.net/weixin_44775255/article/details/120732197
     *     3. 
     *     ... 
     * 
     * Running time: O(d * n). A function of d and n.
     */
    public void insert(Student s) {
    	Node tempNode = recinsert(root, s);
    	if(root == null) root = tempNode;
    		
    	tempNode = not_AVL(root); 
    	
    	if(tempNode == null) return;
    	
    	if(get_high(tempNode.lc) - get_high(tempNode.rc) > 1) {
    		if(tempNode.lc != null && get_high(tempNode.lc.lc) < get_high(tempNode.lc.rc)) {
    			left_turn(tempNode.lc);
    		}
    		right_turn(tempNode);
    		return;
    	}
    	
    	if(get_high(tempNode.rc) - get_high(tempNode.lc) > 1) {
    		if(tempNode.rc != null && get_high(tempNode.rc.lc) > get_high(tempNode.rc.rc)) {
    			right_turn(tempNode.rc);
    		}
    		left_turn(tempNode);
    		return;
    	}
    }
    
    private Node recinsert(Node cur, Student s) {
    	if(cur == null) {
    		Node tempNode = new Node(s);
    		cur = tempNode;
    		return cur;
    	}
    	  
    	if(s.name.compareTo(cur.e.name) <= 0) {
    		cur.lc = recinsert(cur.lc, s);
    	}
    	else {
    		cur.rc = recinsert(cur.rc, s);
    	}
    	return cur;
    }

    /**
     * Calculate the largest difference between the depths of the two subtrees of a node.
     * 
     * VERY IMPORTANT.
     * 
     * I've discussed this question with the following students:
     *     1. 
     *     2. 
     *     3. 
     *     ... 
     * 
     * I've sought help from the following Internet resources and books:
     *     1. https://blog.csdn.net/weixin_45408048/article/details/108511915     (related get_high function and isAVLtree function)
     *     2. 
     *     3. 
     *     ... 
     *  
     * Running time: O( d * n ). A function of d and n.
     */
    public int maxDepthDiff() {
    	//System.out.println(root.e.name);
    	return get_Max_depth(root);
    }
    
    private int get_Max_depth(Node cur) {
    	
    	if(cur == null) return 0;
    	
    	
    	int l_high = get_Max_depth(cur.lc);
    	int c_high = Math.abs(get_high(cur.lc) - get_high(cur.rc));
    	int r_high = get_Max_depth(cur.rc);
    	
    	if(l_high > r_high) r_high = l_high;
    	return (c_high > r_high) ? c_high : r_high;
    	
    }
    
    private int get_high(Node cur) {
    	if(cur == null) return 0;
    	return (Math.max(get_high(cur.lc), get_high(cur.rc)) + 1);
    }

    /**
     * Calculate the largest difference between the sizes of the two subtrees of a node.
     * 
     * VERY IMPORTANT.
     * 
     * I've discussed this question with the following students:
     *     1. 
     *     2. 
     *     3. 
     *     ... 
     * 
     * I've sought help from the following Internet resources and books:
     *     1. 
     *     2. 
     *     3. 
     *     ... 
     * 
     * Running time: O(n * n). A function of d and n.
     */
    public int maxSizeDiff() {
    	return get_Max_size(root);
    }
    
    private int get_Max_size(Node cur) {
    	if(cur == null) return 0;
    	
    	int l_size = get_Max_size(cur.lc);
    	int c_size = Math.abs(get_size(cur.lc) - get_size(cur.rc));
    	int r_size = get_Max_size(cur.rc);
    	
    	if(l_size > r_size) r_size = l_size;
    	return (c_size > r_size) ? c_size : r_size;
    }
    
    private int get_size(Node cur) {
    	if(cur == null) return 0;
    	return get_size(cur.lc) + get_size(cur.rc) + 1;
    }
    
    /**
     * Calculate the number of nodes that have only one child.
     * 
     * VERY IMPORTANT.
     * 
     * I've discussed this question with the following students:
     *     1. 
     *     2. 
     *     3. 
     *     ... 
     * 
     * I've sought help from the following Internet resources and books:
     *     1. 
     *     2. 
     *     3. 
     *     ... 
     * 
     * Running time: O(n). A function of d and n.
     */
    public int nodesWithOneChild() {	
    	return get_OneChild(root);
    }
    
    private int get_OneChild(Node cur) {
    	if(cur == null) return 0;
    	
    	int l_one = get_OneChild(cur.lc);
    	int c_one = have_OneChild(cur);
    	int r_one = get_OneChild(cur.rc);
    	
    	return l_one + c_one + r_one;
    }
    
    private int have_OneChild(Node cur) {
    	if(cur.lc != null && cur.rc != null) return 0;
    	if(cur.lc == null && cur.rc == null) return 0;
    	return 1;
    }
    
   
    /*
     * Find a student with the specified name.
     * You may return any of them if there are multiple students of this name.
     * 
     * VERY IMPORTANT.
     * 
     * I've discussed this question with the following students:
     *     1. 
     *     2. 
     *     3. 
     *     ... 
     * 
     * I've sought help from the following Internet resources and books:
     *     1. 
     *     2. 
     *     3. 
     *     ... 
     * 
     * Running time: O(d). A function of d and n.
     */
    public Student searchFullname(String name) {
    	return Fullname(name, root);
    }
    
    private Student Fullname(String name, Node cur) {
    	if(cur == null) return null;
    	
    	if(cur.e.name.equals(name)) return cur.e;
    	
    	if(name.compareTo(cur.e.name) <= 0) {
    		return Fullname(name, cur.lc);
    	}
    	return Fullname(name, cur.rc);
    }

    
    /*
     * Find all students with the specified surname.
     * Consider the first word as the surname. 
     * Warning: Make sure "Liu Dennis" does not show when you search "Li."
     * 
     * VERY IMPORTANT.
     * 
     * I've discussed this question with the following students:
     *     1. 
     *     2. 
     *     3. 
     *     ... 
     * 
     * I've sought help from the following Internet resources and books:
     *     1. 
     *     2. 
     *     3. 
     *     ... 
     * 
     * Running time: O(n). A function of d and n.
     */
    public Student[] searchSurname(String surname) { 
        return Surname(surname, root);
    }
    
    private Student[] Surname(String surname, Node rec){
    	if(rec == null) {
    		Student[] list = new Student[0];
    		return list;
    	}
    	
    	Student[] l_list = Surname(surname, rec.lc);
    	Student[] c_list = get_Surname(surname, rec);
    	Student[] r_list = Surname(surname, rec.rc);
    	
    	Student[] list = new Student[l_list.length + c_list.length + r_list.length];
    	int l1 = 0;
    	for(int i = 0; i < l_list.length ; i++) { list[l1++] = l_list[i]; }
    	for(int i = 0; i < c_list.length ; i++) { list[l1++] = c_list[i]; }
    	for(int i = 0; i < r_list.length ; i++) { list[l1++] = r_list[i]; }
    	
    	return list;
    }
    
    private Student[] get_Surname(String surname, Node cur) {
    	String[] temp = cur.e.name.split(" ");
    	if(surname.equals(temp[0])) {
    		Student[] list = new Student[1];
    		list[0] = cur.e;
    		return list;
    	}
    	Student[] list = new Student[0];
    	return list;
    }

    /*
     * Find all students who are taking a certain class.
     * The input is an array of student names.
     * 
     * VERY IMPORTANT.
     * 
     * I've discussed this question with the following students:
     *     1. 
     *     2. 
     *     3. 
     *     ... 
     * 
     * I've sought help from the following Internet resources and books:
     *     1. 
     *     2. 
     *     3. 
     *     ... 
     * 
     * Running time: O( n + clogc ). A function of d, n, and c.
     */
    public Student[] searchClass(String[] roster) {
    	Student[] ans = new Student[roster.length];
//    	for (int i = 0; i < roster.length; i++) {
//    		ans[i] = searchFullname(roster[i]); 
//    	}
    	heapsort(roster);
    	Student[] list_student = change_list(root);
    	
    	int ansl = 0, rosl = 0, list_sl = 0;
    	while(list_sl != list_student.length - 1 && rosl != roster.length - 1) {
    		if(list_student[list_sl].name.compareTo(roster[rosl]) < 0) {
    			list_sl++;
    		}
    		else {
    			ans[ansl++] = list_student[list_sl++];
    			rosl++;
    		}
    	}
    	return ans;
    }
    
    private Student[] change_list(Node cur) {
    	if(cur == null) {
    		Student[] list = new Student[0];
    		return list;
    	}
    	
    	Student[] l_list = change_list(cur.lc);
    	Student[] c_list = add_in(cur);
    	Student[] r_list = change_list(cur.rc);
    	
    	Student[] list = new Student[l_list.length + c_list.length + r_list.length];
    	
    	int l1 = 0;
    	for(int i = 0; i < l_list.length ; i++) { list[l1++] = l_list[i]; }
    	for(int i = 0; i < c_list.length ; i++) { list[l1++] = c_list[i]; }
    	for(int i = 0; i < r_list.length ; i++) { list[l1++] = r_list[i]; }
    	
    	return list;
    	
    }
    
    private Student[] add_in(Node cur) {
    	Student[] temp = new Student[1]; 
    	temp[0] = cur.e;
    	return temp;
    }
    
    
    //this is the heap sort in Lec10
    private static void swap(String [] a, int x, int y) {
    	String temp = a[x];
        a[x] = a[y];
        a[y] = temp;
    }
    
    
    private static void up(String[] a, int c) {
        if (c == 0) return;
        int p = (c - 1) / 2;
        if (a[c].compareTo(a[p]) <= 0 ) return;
        swap(a, c, p);
        up(a, p);
    }

    
    private static void down(String[] a, int p, int size) {
        if (p * 2 + 2 > size) return;
        int larger = p * 2 + 1;
        if (larger + 1 < size && a[larger].compareTo(a[larger+1]) < 0 ) 
            larger++;
        if (a[p].compareTo(a[larger]) >= 0 ) return;
        swap(a, p, larger);
        down(a, larger, size);
    }
    
      
    public static void heapsort(String[] a) {
        for (int i = 1; i < a.length; i++)
            up(a, i);
        for (int i = a.length - 1; i > 0; i--) {
            swap(a, 0, i);  
            down(a, 0, i);  
        } 
    }  

    /**
     * You are free to make any change to this method.
     * You can even remove it if you want to test your code with other means.  
     */
    public static void main(String[] args) {
        PolyuTree_21101988d_LiTong tree = new PolyuTree_21101988d_LiTong();
        /*
         * Here are 192 names you can use for testing.
         * 
         * You should test your code with more names (>= 1 million). One way is to generate random names.  
         * Tips: Given names can be random strings. You can assign a random surname from this list 
         * https://surnam.es/hong-kong.
         */
         String[] names = {"Chang Chi Fung", "Lee Cheuk Kwan", "Liu Tsz Ki", "Vallo David Jonathan Delos Reyes", "Jiang Han", "Park Taejoon", "Shin Hyuk", "Jung Junyoung", "Lam Wun Yiu", "Kwok Shan Shan", "Chui Cheuk Wai", "Lam Yik Chun", "Luo Yuehan", "Wang Hao", "Mansoor Haris", "Liang Wendi", "Meng Guanlin", "Wang Zhiyu", "Mak Ho Lun", "Liu Zixian", "Geng Qiyang", "Fong Chun Ming", "Cheung Chun Hei", "Lau Tsun Hang Ryan", "Cheung Cheuk Hang", "Liu Chi Hang", "Wong Yiu Nam", "Cheng Kok Yin", "Lam Wai Kit", "Liu Valerie", "Tam Chung Man", "Yan Tin Chun", "Lok Yin Chun", "Ng Ming Hei", "Lo Chun Hin", "Lam Pui Wa", "Lo Cho Hung", "Chu Tsz Fui", "Chow Ho Man", "Gao Ivan", "Ng Man Chau", "Iu Lam Ah", "Hung Wai Hin", "Tong Ka Yan", "Lo Ching Sau", "Lee Lee Ling", "Lam Ho Yin", "Sze Kin Ho", "Ng Siu Chi", "Wong Cheuk Laam", "Chan Yat Chun", "Lee Lap Shun", "Deng Chun Yung", "Fung Ki Ho", "Yeung Ting Kit", "Shiu Chi Yeung", "Kwan Yat Ming", "Chan Kin Kwan", "Leung Man Yi", "Yau Minki", "Hong Yuling", "Yung Wing Kiu", "Yuen Marco Siu Tung", "Lo Yung Sum", "Cheung Tsz Ho", "Chu Ka Hang", "Chan Man Yi", "Ng Yuet Kwan", "Lui Cheuk Lam Lily", "Tai Cheuk Hin", "Ong Chun Wa", "Yiu Pun Cham", "Cheng Ho Wing", "Wong Tsz Wai Desmond", "Lai Ho Sum", "Lee Siu Wai", "Lai Ming Hin", "Leung Hoi Ming", "To Ka Hei", "Tang Tsz Yeung", "Au Yeung Chun Yi", "Lau Ue Tin", "Yau Sin Yan", "Lam Ho Yan", "Tong Mei Chun", "Cheung Tsz Kwan", "Chiang Tin Hang", "Lai Kit Lun", "Cheung Sum Yin", "Wang Matthew Moyin", "Jiang Guanlin", "Edgar Christopher", "Liang Zhihong", "Bai Ruiyuan", "Chen Ru Bing", "Hu Wenqing", "Zhou Siyu", "Wang Yukai", "Lam Hei Yung", "Zhang Wanyu", "Wei Xianger", "Conte Chan Gabriel Alejandro", "Pratento Dylan Jefferson", "Lam Wan Yuet", "Chen Ziyang", "Jiang Zheng", "Xu Le", "He Boyan", "Liu Minghao", "Zhang Zhiyuan", "Chen Yuxuan", "Jin Cheng", "Liu Chenxi", "Qiu Siyi", "Han Wenyu", "Chan Cheuk Hei", "Ho Tsz Kan", "Du Haoxun", "Zheng Shouwen", "Ye Feng", "Yu Kaijing", "Lee Jer Tao", "Shen Ziqi", "Wang Yihe", "Liu Yanqi", "Zhang Wenxuan", "Huang Tianji", "Lu Zhoudao", "Zhang Tianyi", "Yuan Yunchen", "Liu Chengju", "Wei Siqi", "Liu Yuzhou", "Zhao Letao", "Huo Shuaining", "Li Kin Lung Anson", "Qin Qipeng", "Li Jiale", "He Rong", "Hiu Jason Kenneth", "Lam Ka Hang", "Li Tong", "Lau Choi Yu Elise", "Liu Dong", "Li Shuhang", "Zeng Yuejia", "Cai Zhenyu", "Lau Siu Hin", "Szeto Siu Chun", "Leung Cheuk Kit", "Cai Haoyu", "Ye Chenwei", "Huang Yidan", "Lee Kam Hung", "Wang Zhengyang", "Bao Yucheng", "Niyitegeka Berwa Aime Noel", "Lyateva Paulina Veselinova", "Zhang Boyu", "Chen Junru", "Fang Yuji", "Lin Qinfeng", "Tang Haichuan", "Hu Yuhang", "Zhou Taiqi", "Fang Anshu", "Wu Chao", "Zhang Haolin", "Ivanichev Mikhail", "Luo Yi", "Ieong Mei Leng", "Lee Wang Ho", "Jian Junxi", "Tam Tin", "Kjoelbro Niklas August", "Lee Hau Laam", "Pak Yi Ching", "Pang Kin Hei", "Xue Bingxin", "Lau Sin Yee", "Kwok Sze Ming", "Chan Lok Hin", "Chan Ho Yin Francis", "Chung Wai Ching", "Hu Hongjian", "Yiu Chi Chiu", "Tso Yuet Yan", "Chow Chun Wang", "Li Wun Wun", "Chen Junyu", "Kan Wai Yi", "Fong Chun Ho"};
        //String[] names = { "Tse Kay", "Ho Denise", "Yung Joey", "Tang Gloria", "Chan Eason", "Lau Andy", "Cheung Jacky" };

        SecureRandom random = new SecureRandom();
        int id = 22222222;
        for (String name : names) {
            id += random.nextInt(100);
            tree.insert(new Student(String.valueOf(id), name));
        }

        // Statistics
        System.out.println("The largest depth difference of a node is: " + tree.maxDepthDiff());
        System.out.println("The largest size difference of a node is: " + tree.maxSizeDiff());
        System.out.println(tree.nodesWithOneChild() + " nodes have a single child.");

        String[] comp2011 = {"Chang Chi Fung", "Lee Cheuk Kwan", "Liu Tsz Ki", "Vallo David Jonathan Delos Reyes", "Jiang Han", "Park Taejoon", "Shin Hyuk", "Jung Junyoung", "Lam Wun Yiu", "Kwok Shan Shan", "Chui Cheuk Wai", "Lam Yik Chun", "Luo Yuehan", "Wang Hao", "Mansoor Haris", "Liang Wendi", "Meng Guanlin", "Wang Zhiyu", "Mak Ho Lun", "Liu Zixian", "Geng Qiyang", "Fong Chun Ming", "Cheung Chun Hei", "Lau Tsun Hang Ryan", "Cheung Cheuk Hang", "Liu Chi Hang", "Wong Yiu Nam", "Cheng Kok Yin", "Lam Wai Kit", "Liu Valerie", "Tam Chung Man", "Yan Tin Chun", "Lok Yin Chun", "Ng Ming Hei", "Lo Chun Hin", "Lam Pui Wa", "Lo Cho Hung", "Chu Tsz Fui", "Chow Ho Man", "Gao Ivan", "Ng Man Chau", "Iu Lam Ah", "Hung Wai Hin", "Tong Ka Yan", "Lo Ching Sau", "Lee Lee Ling", "Lam Ho Yin", "Sze Kin Ho", "Ng Siu Chi", "Wong Cheuk Laam", "Chan Yat Chun", "Lee Lap Shun", "Deng Chun Yung", "Fung Ki Ho", "Yeung Ting Kit", "Shiu Chi Yeung", "Kwan Yat Ming", "Chan Kin Kwan", "Leung Man Yi", "Yau Minki", "Hong Yuling", "Yung Wing Kiu", "Yuen Marco Siu Tung", "Lo Yung Sum", "Cheung Tsz Ho", "Chu Ka Hang", "Chan Man Yi", "Ng Yuet Kwan", "Lui Cheuk Lam Lily", "Tai Cheuk Hin", "Ong Chun Wa", "Yiu Pun Cham", "Cheng Ho Wing", "Wong Tsz Wai Desmond", "Lai Ho Sum", "Lee Siu Wai", "Lai Ming Hin", "Leung Hoi Ming", "To Ka Hei", "Tang Tsz Yeung", "Au Yeung Chun Yi", "Lau Ue Tin", "Yau Sin Yan", "Lam Ho Yan", "Tong Mei Chun", "Cheung Tsz Kwan", "Chiang Tin Hang", "Lai Kit Lun", "Cheung Sum Yin", "Wang Matthew Moyin", "Jiang Guanlin", "Edgar Christopher", "Liang Zhihong", "Bai Ruiyuan", "Chen Ru Bing", "Hu Wenqing", "Zhou Siyu", "Wang Yukai", "Lam Hei Yung", "Zhang Wanyu", "Wei Xianger", "Conte Chan Gabriel Alejandro", "Pratento Dylan Jefferson", "Lam Wan Yuet", "Chen Ziyang", "Jiang Zheng", "Xu Le", "He Boyan", "Liu Minghao", "Zhang Zhiyuan", "Chen Yuxuan", "Jin Cheng", "Liu Chenxi", "Qiu Siyi", "Han Wenyu", "Chan Cheuk Hei", "Ho Tsz Kan", "Du Haoxun", "Zheng Shouwen", "Ye Feng", "Yu Kaijing", "Lee Jer Tao", "Shen Ziqi", "Wang Yihe", "Liu Yanqi", "Zhang Wenxuan", "Huang Tianji", "Lu Zhoudao", "Zhang Tianyi", "Yuan Yunchen", "Liu Chengju", "Wei Siqi", "Liu Yuzhou", "Zhao Letao", "Huo Shuaining", "Li Kin Lung Anson", "Qin Qipeng", "Li Jiale", "He Rong", "Hiu Jason Kenneth", "Lam Ka Hang", "Li Tong", "Lau Choi Yu Elise", "Liu Dong", "Li Shuhang", "Zeng Yuejia", "Cai Zhenyu", "Lau Siu Hin", "Szeto Siu Chun", "Leung Cheuk Kit", "Cai Haoyu", "Ye Chenwei", "Huang Yidan", "Lee Kam Hung", "Wang Zhengyang", "Bao Yucheng", "Niyitegeka Berwa Aime Noel", "Lyateva Paulina Veselinova", "Zhang Boyu", "Chen Junru", "Fang Yuji", "Lin Qinfeng", "Tang Haichuan", "Hu Yuhang", "Zhou Taiqi", "Fang Anshu", "Wu Chao", "Zhang Haolin", "Ivanichev Mikhail", "Luo Yi", "Ieong Mei Leng", "Lee Wang Ho", "Jian Junxi", "Tam Tin", "Kjoelbro Niklas August", "Lee Hau Laam", "Pak Yi Ching", "Pang Kin Hei", "Xue Bingxin", "Lau Sin Yee", "Kwok Sze Ming", "Chan Lok Hin", "Chan Ho Yin Francis", "Chung Wai Ching", "Hu Hongjian", "Yiu Chi Chiu", "Tso Yuet Yan", "Chow Chun Wang", "Li Wun Wun", "Chen Junyu", "Kan Wai Yi", "Fong Chun Ho"};
        String[] comp9999 = {"Tang Gloria", "Chan Eason"};
        Student[] ss = tree.searchClass(comp2011);
        System.out.println((comp2011.length - ss.length) + " Not Found\n" + ((ss != null)?Arrays.toString(ss):""));

        @SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
        System.out.println("Enter a name for search, end in a '*' for surname search." + " q to quit");
        while (input.hasNext()) {
            String search = input.nextLine().trim();
            if (search.equals("q"))
                break;
            if (search.indexOf('*') > 0) {
            	// call surname search
            	search = search.substring(0, search.length()-1);
            	Student[] list = tree.searchSurname(search);
                if (list == null)
                    System.out.println("Not Found");
                else
                    System.out.println(list.length + " students with surname \"" + search + "\" found:\n" + Arrays.toString(list));
            } else {
                // call full name search
                Student student = tree.searchFullname(search);
                if (student == null)
                    System.out.println("Not Found");
                else
                    System.out.println(student);
            }
        }
    }
}
