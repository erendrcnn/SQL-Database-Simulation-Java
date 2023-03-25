public class Table {
    RowNode first;

    static class RowNode {
        
        Row dataR;
        RowNode next;

        RowNode(Row d)
        {
            dataR = d;
            next = null;
        }
    }

    public static void insert(Table list, Row dataR)
    {
        RowNode new_RowNode = new RowNode(dataR);
        new_RowNode.next = null;

        if (list.first == null)
            list.first = new_RowNode;

        else {
            RowNode last = list.first;
            while (last.next != null) {
                last = last.next;
            }

            last.next = new_RowNode;
        }
    }

    public static void printTable(Table list)
    {
        RowNode currRowNode = list.first;

        System.out.print("============[ TABLE ]============\n");

        while (currRowNode != null) {
            System.out.println(currRowNode.dataR.toString());
            currRowNode = currRowNode.next;
        }

        System.out.println("\n");
    }

    public int length() {
        Row.Node temp = this.first.dataR.head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    public static Table deleteByKey(Table list, Row key)
    {
        RowNode currRowNode = list.first, prev = null;

        if (currRowNode != null && currRowNode.dataR.equals(key)) {
            list.first = currRowNode.next;

            System.out.println(key + " found and deleted");

            return list;
        }

        while (currRowNode != null && !currRowNode.dataR.equals(key)) {
            prev = currRowNode;
            currRowNode = currRowNode.next;
        }

        if (currRowNode != null) {
            prev.next = currRowNode.next;
            System.out.println(key + " found and deleted");
        }

        if (currRowNode == null)
            System.out.println(key + " not found");

        return list;
    }

    public static Table deleteAtPosition(Table list, int index)
    {
        RowNode currRowNode = list.first, prev = null;

        if (index == 0 && currRowNode != null) {
            list.first = currRowNode.next;

            System.out.println(index + " position element deleted");

            return list;
        }

        int counter = 0;

        while (currRowNode != null) {
            if (counter == index) {
                prev.next = currRowNode.next;

                System.out.println(index + " position element deleted");
                break;
            }

            else {
                prev = currRowNode;
                currRowNode = currRowNode.next;
                counter++;
            }
        }

        if (currRowNode == null)
            System.out.println(index + " position element not found");

        return list;
    }

    /*
    public static void main(String[] args)
    {
        Table table1 = new Table();

        Row list1 = new Row();
        Row list0 = new Row();

        String[] header = {"id","first_name","last_name","email","ip_address"};
        String[] a1 = {"1","Koralle","Ganning","kganning0@soundcloud.com","201.54.159.99"};
        String[] a2 = {"2","Roberta","Thyer","rthyer1@economist.com","209.113.202.117"};
        String[] a3 = {"3","Aldon","Garken","agarken2@admin.ch","264.34.139.99"};
        String[] a4 = {"4","Rudolph","Points","rpoints3@dyndns.org","225.54.159.39"};
        String[] a5 = {"5","Aldric","Sopp","asopp4@tinyurl.com","634.54.159.9339"};
        String[] a6 = {"6","Renaldo","Nelane","rnelane5@google.com.br","561.54.159.969"};
        String[] a7 = {"7","Jannelle","Rosebotham","jrosebotham6@hatena.ne.jp","223.54.159.549"};
        String[] a8 = {"8","Esmeralda","Fawlkes","efawlkes7@merriam-webster.com","264.54.1439.96"};

        Row.insert(list1, header);
        Row.insert(list1, a1);
        Row.insert(list1, a2);
        Row.insert(list1, a3);
        Row.insert(list1, a4);
        Row.insert(list1, a5);
        Row.insert(list1, a6);
        Row.insert(list1, a7);
        Row.insert(list1, a8);

        Row.insert(list0, header);
        Row.insert(list0, a1);
        Row.insert(list0, a2);
        Row.insert(list0, a3);
        Row.insert(list0, a4);
        Row.insert(list0, a5);
        Row.insert(list0, a6);
        Row.insert(list0, a7);
        Row.insert(list0, a8);

        Row.printRow(list1);
        System.out.println();
        Row.deleteByKey(list1, "1");
        Row.printRow(list1);
        System.out.println();
        Row.deleteByKey(list1, "4");
        Row.printRow(list1);
        System.out.println();
        Row.deleteByKey(list1, "6");
        Row.printRow(list1);
        System.out.println();
        Row.deleteAtPosition(list1, 5);
        Row.printRow(list1);
        System.out.println();
        Row.deleteAtPosition(list1, 2);
        Row.printRow(list1);

        Table.insert(table1, list1);
        Table.insert(table1, list0);

        Table.printTable(table1);
    }
     */
}