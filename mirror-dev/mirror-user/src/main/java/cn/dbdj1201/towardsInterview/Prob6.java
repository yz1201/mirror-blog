package cn.dbdj1201.towardsInterview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-07 19:15
 */
public class Prob6 {
    /* ： 从尾到头打印链表 ，逆向打印*/

    public static void main(String[] args) {
//        ILinkedList linkedList = test();

        reverseList(generateNodeList());
    }

    public static ILinkedList test() {
        Node n1 = generateNodeList();

        ILinkedList iLinkedList = new ILinkedList(n1);
        iLinkedList.show();
        return iLinkedList;
    }

    private static Node generateNodeList() {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        return n1;
    }

    public static void reverseList(Node head) {

        Stack<Node> stack = new Stack<>();

        Node temp = head;

        while (temp != null) {
            stack.push(temp);
            temp = temp.next;
        }

        while (!stack.empty()){
            System.out.println(stack.pop());
        }

    }


}

@Data
//@AllArgsConstructor
@NoArgsConstructor
class Node {
    int num;
    Node next;

    public Node(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Node{" +
                "num=" + num +
                '}';
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
class ILinkedList {
    //头节点
    Node head;

    public void addNode(Node node) {
        if (node == null) {
            return;
        }
        if (head == null) {
            head = node;
        }
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }

        temp.next = node;
    }

    //从头至尾打印链表
    public void show() {
        if (head == null) {
            log.info("fanbingle");
        }
        Node temp = this.head;
        StringBuilder sb = new StringBuilder();
        while (temp != null) {
//            System.out.print(temp + " -> ");
            sb.append(temp).append(" -> ");
            temp = temp.next;
        }
        sb.replace(sb.lastIndexOf(" -> "), sb.length(), "");
        System.out.println(sb);
    }

//    public void reverseShow() {
//
//    }


}
