public class Row {
    Node head;

    public Row() {}

    public Row(Row row) {
        this.head = row.head;
    }

    static class Node {

        String[] data;
        Node next;

        Node(String[] d)
        {
            data = new String[d.length];

            System.arraycopy(d, 0, data, 0, d.length);

            next = null;
        }
    }

    public static void insert(Row list, String[] data)
    {
        Node new_node = new Node(data);
        new_node.next = null;
        
        if (list.head == null)
            list.head = new_node;

        else {
            Node last = list.head;
            while (last.next != null) {
                last = last.next;
            }
            
            last.next = new_node;
        }
    }

    @Override
    public String toString() {
        Node currNode = this.head;
        StringBuilder temp = new StringBuilder();
        //System.out.print("|--> Rows\n");

        while (currNode != null) {
            for (String s: currNode.data) {
                temp.append(s).append(" ");
            }
            temp.append("\n");
            currNode = currNode.next;
        }

        return temp.toString();
    }

    public static void printRow(Row list)
    {
        Node currNode = list.head;

        System.out.print("----( Rows )----\n");
        
        while (currNode != null) {
            for (String s: currNode.data) {
                System.out.print(s + " ");
            }
            System.out.println();

            currNode = currNode.next;
        }
    }

    public static Row deleteByKey(Row list, String key)
    {
        Node currNode = list.head, prev = null;

        if (currNode != null && currNode.data[0].equals(key)) {
            list.head = currNode.next; 

            System.out.println(key + " found and deleted");

            return list;
        }
        
        while (currNode != null && !currNode.data[0].equals(key)) {
            prev = currNode;
            currNode = currNode.next;
        }

        if (currNode != null) {
            prev.next = currNode.next;
            System.out.println(key + " found and deleted");
        }

        if (currNode == null)
            System.out.println(key + " not found");
        
        return list;
    }

    public static Row deleteAtPosition(Row list, int index)
    {
        Node currNode = list.head, prev = null;

        if (index == 0 && currNode != null) {
            list.head = currNode.next; 

            System.out.println(index + " position element deleted");
            
            return list;
        }

        int counter = 0;

        while (currNode != null) {
            if (counter == index) {
                prev.next = currNode.next;

                System.out.println(index + " position element deleted");
                break;
            }

            else {
                prev = currNode;
                currNode = currNode.next;
                counter++;
            }
        }
        
        if (currNode == null)
            System.out.println(index + " position element not found");

        return list;
    }
}
