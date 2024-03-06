import java.io.*;

public class DatabaseSimulator {
    public static Database<Table> memoryData = new Database<>();
    public static Database<String> tableNameData = new Database<>();
    public static String path = "";
    public static String fileName = "";
    public static Table tables;
    public static Row rows;
    public static BinarySearchTree bst;

    // Create a table via the entered file path and save it in the database.
    public void createTable(String command) {
        String[] cmds = command.split(" ");
        if (cmds.length >= 4 && cmds[0].equals("CREATE") && cmds[1].equals("TABLE") && cmds[2].equals("FROM")) {
            path = cmds[3];
            if (path.contains("\\"))
                fileName = path.substring(path.lastIndexOf("\\") + 1, path.indexOf(".csv"));
            else
                fileName = path.substring(0, path.indexOf(".csv"));

            tables = new Table();
            rows = new Row();
            bst = new BinarySearchTree();

            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(path));
                String line = reader.readLine();
                String[] header = line.split(",");

                String[] tableName = new String[1];
                tableName[0] = fileName;
                tableNameData.add(fileName);
                // Add the name of the table to the first row.
                Row.insert(rows, tableName);
                // Add the header of the table to the second row.
                Row.insert(rows, header);
                // Read and save data line by line from csv file.
                while (line != null) {
                    line = reader.readLine();

                    if(line != null) {
                        String[] currentRow = line.split(",");
                        Row.insert(rows, currentRow);

                        if(bst.find(Integer.parseInt(currentRow[0])) == null) {
                            Row temp = new Row();
                            Row.insert(temp, currentRow);
                            bst.insert(Integer.parseInt(currentRow[0]), temp);
                        } else {
                            Row.insert(bst.find(Integer.parseInt(currentRow[0])), currentRow);
                        }
                    }
                }
                // Save all prepared rows in the table.
                Table.insert(tables, rows);

                // If there is a table with the same name, it deletes the old one and adds a new one.
                for (int i = 0; i < memoryData.size(); i++) {
                    if(memoryData.get(i).first.dataR.head.data[0].equals(fileName))
                        memoryData.remove(i);
                }

                // Add prepared table to database.
                memoryData.add(tables);
                // Terminate read operation.
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("HATA: Belirtilen dosya bulunamadi.\n" +
                                   "      Lutfen yeni bir dosya yolu deneyiniz.");
            } catch (IOException e) {
                System.out.println("HATA: Kurallara uymayan şekilde bir komut kullanildi.\n" +
                                   "      Lutfen tekrar deneyiniz.");
            }
        }
        else
            System.out.println("HATA: Girilen CREATE komutu gecersiz.");
    }

    // The method that filters the desired parameters for the SELECT command.
    public String[] query(String command) {
        if (memoryData.size() == 0) {
            System.out.println("HATA: Herhangi bir tablo olusturulmamis.\n" +
                               "      Oncelikle CREATE komutu ile tablo olusturun.");
            throw new NullPointerException();
        }

        String[] cmds = command.split(" ");

        if(cmds[3].contains(";"))
            cmds[3] = cmds[3].replaceAll(";","");

        if (!tableNameData.contains(cmds[3])) {
            System.out.println("HATA: Aranan tablo veritabaninda bulunamadi.\n" +
                               "      Farkli bir tablo ismi girerek tekrar deneyin.");
            throw new NullPointerException();
        }

        boolean isFound = false;

        String[] headerList;
        String[] searchs = cmds[1].split(",");
        String[] results = new String[1];
        int[] searchIndexs;
        int searchMOD = 0;

        if (cmds.length == 4){
            if (cmds[0].equals("SELECT") && cmds[2].equals("FROM")) {
                    for (int i = 0; i < memoryData.size(); i++) {   // Visit all databases.
                        Row.Node walk = memoryData.get(i).first.dataR.head;

                        if(walk.data[0].equals(cmds[3])) { // Does the table in the entered command exist in the database?
                            results = new String[memoryData.get(i).length() - 1];

                            // Delete the null values of the result array.
                            for (int m = 0; m < results.length; m++) {
                                if (null == results[m]) {
                                    results[m] = "";
                                }
                            }

                            if (cmds[3].equals(walk.data[0])) {   // Check the table header.
                                headerList = walk.next.data; // Get the header array of the table.
                                searchIndexs = new int[headerList.length]; // Which indexes of the header will be scanned?

                                // Specify the columns to be searched in the table.
                                // If "*" is entered, select all columns.
                                for (int j = 0; j < headerList.length; j++) {
                                    for (String search : searchs) {
                                        if (searchs[0].equals("*")) {
                                            searchIndexs[j] = 1;
                                            searchMOD++;
                                            isFound = true;
                                        } else if (headerList[j].equals(search)) {
                                            searchIndexs[j] = 1;
                                            searchMOD++;
                                            isFound = true;
                                        }
                                    }
                                }

                                if (isFound) {
                                    int count = 0;
                                    int nextLineControl = 0;
                                    walk = walk.next;

                                    while (walk != null) {
                                        if (searchs.length == 1 && !searchs[0].equals("*")) {
                                            for (int j = 0; j < searchIndexs.length; j++) {
                                                if (searchIndexs[j] == 1) {
                                                    results[count] += walk.data[j];
                                                    count++;
                                                }
                                            }
                                        } else {
                                            for (int j = 0; j < searchIndexs.length; j++) {
                                                if (searchIndexs[j] == 1) {
                                                    if (nextLineControl == searchMOD) {
                                                        nextLineControl = 0;
                                                        count++;
                                                    }

                                                    results[count] += walk.data[j] + "\t";
                                                    nextLineControl++;
                                                }
                                            }
                                        }
                                        walk = walk.next;
                                    }
                                }
                            }
                        }
                    }

                if (!isFound) {
                    if(cmds[1].equals("*"))
                        System.out.println("HATA: Veritabanindaki tabloda herhangi bir kolon bulunamadi.");
                    else
                        System.out.println("HATA: Veritabanindaki tabloda \"" + cmds[1] + "\" isminde bir kolon bulunamadi.");
                }
            }
        } else if (cmds.length == 6) {
            if (cmds[0].equals("SELECT") && cmds[2].equals("FROM") && cmds[4].equals("WHERE")){
                for (int i = 0; i < memoryData.size(); i++) {   // Visit all databases.
                    Row.Node walk = memoryData.get(i).first.dataR.head;

                    if (walk.data[0].equals(cmds[3])) {
                        // Does the table in the entered command exist in the database?
                        results = new String[memoryData.get(i).length() - 1];

                        // Separate the type and value to be filtered.
                        String[] whereEquality = cmds[5].split("=");

                        if (whereEquality[1].contains(";"))
                            whereEquality[1] = whereEquality[1].replaceAll(";","");

                        int whereIndex = -1;
                        
                        // Delete the null values of the result array.
                        for (int m = 0; m < results.length; m++) {
                            if (null == results[m]) {
                                results[m] = "";
                            }
                        }

                        if (cmds[3].equals(walk.data[0])) { // Check the table header.
                            headerList = walk.next.data; // Get the header array of the table.
                            searchIndexs = new int[headerList.length]; // Which indexes of the header will be scanned?

                            // Determine the index of the parameter to be scanned from the header. (WHERE TYPE)
                            for (int j = 0; j < headerList.length; j++) {
                                if (headerList[j].equals(whereEquality[0]))
                                    whereIndex = j;
                            }

                            if (whereIndex == -1) {
                                System.out.println("HATA: WHERE parametresi olarak verilen \"" + whereEquality[0] + "\" bulunamadi.");
                                throw new NullPointerException();
                            }

                            // Specify the columns to be searched in the table.
                            // If "*" is entered, select all columns.
                            for (int j = 0; j < headerList.length; j++) {
                                for (String search : searchs) {
                                    if (searchs[0].equals("*")) {
                                        searchIndexs[j] = 1;
                                        searchMOD++;
                                        isFound = true;
                                    } else if (headerList[j].equals(search)) {
                                        searchIndexs[j] = 1;
                                        searchMOD++;
                                        isFound = true;
                                    }
                                }
                            }

                            if (isFound) {
                                int count = 0;
                                int nextLineControl = 0;
                                String parameterValue = whereEquality[1];

                                // If searching by first column, rows with BST are found in O(logn).
                                if (whereIndex == 0){
                                    Row walkBST = new Row(bst.find(Integer.parseInt(parameterValue)));

                                    if(walkBST != null) {
                                        while (walkBST.head != null) {
                                            String[] bstRow = walkBST.head.data;
                                            for (int j = 0; j < searchIndexs.length; j++) {
                                                if (searchIndexs[j] == 1) {
                                                    results[count] += bstRow[j] + "\t";
                                                }
                                            }
                                            count++;
                                            walkBST.head = walkBST.head.next;
                                        }
                                    }
                                }

                                walk = walk.next;

                                while (walk != null) {
                                    // When the data is searched according to the first column, it was found via BST.
                                    // Exit directly without visiting all lines.
                                    if(whereIndex == 0)
                                        break;

                                    // All rows are searched except the first column.
                                    if (walk.data[whereIndex].equals(parameterValue)) {
                                        if (searchs.length == 1 && !searchs[0].equals("*")) {
                                            for (int j = 0; j < searchIndexs.length; j++) {
                                                if (searchIndexs[j] == 1) {
                                                    results[count] += walk.data[j];
                                                    count++;
                                                }
                                            }
                                        } else {
                                            for (int j = 0; j < searchIndexs.length; j++) {
                                                if (searchIndexs[j] == 1) {
                                                    if (nextLineControl == searchMOD) {
                                                        nextLineControl = 0;
                                                        count++;
                                                    }

                                                    results[count] += walk.data[j] + " ";
                                                    nextLineControl++;
                                                }
                                            }
                                        }
                                    }
                                    walk = walk.next;
                                }
                            }
                        }
                    }

                    if (!isFound) {
                        if (cmds[1].equals("*"))
                            System.out.println("HATA: Veritabanindaki tabloda herhangi bir kolon bulunamadi.");
                        else
                            System.out.println("HATA: Veritabanindaki tabloda \"" + cmds[1] + "\" isminde bir kolon bulunamadi.");
                    }
                }
            }
        } else
            System.out.println("HATA: Girilen query komutu yanlistir.\n" +
                               "      Komut: \"" + command + "\"");

        return results;
    }

    // Print all the data in the entered table to the screen.
    public void printSchema(String tableName) {
        if(memoryData == null)
            System.out.println("HATA: Herhangi bir tablo olusturulmamis.\n" +
                               "      Oncelikle CREATE komutu ile tablo olusturun.");

        boolean isFound = false;

        for (int i = 0; i < memoryData.size(); i++) {
            if ( tableName.equals(memoryData.get(i).first.dataR.head.data[0]) ) {
                System.out.println(memoryData.get(i).first.dataR);
                isFound = true;
                break;
            }
        }

        if (!isFound)
            System.out.println("HATA: Veritabaninda \"" + tableName + "\" isminde bir tablo bulunamadi.");
    }
}
