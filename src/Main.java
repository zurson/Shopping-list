import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        String main_filename = "list.txt";
        String new_filename = "new_list.txt";

        Vector<String> list;

        list = get_list(new_filename);

        if(list == null)
        {
            list = get_list(main_filename);

            if(list == null)
            {
                System.out.println("File not found!");
                return;
            }
        }

        if (list.size() < 2)
        {
            System.out.println("File corrupted!");
            return;
        }


        while(true)
        {
            print_main_menu();

           int input = scan_number(0, 7);

            switch (input)
            {
                case 0:
                    System.out.println("\nEdytowanie listy zakonczone!");
                    return;

                case 1:
                    add_product(list);
                    break;

                case 2:
                    print_list(list, false);
                    break;

                case 3:
                    print_products_from_given_category(list);
                    break;

                case 4:
                    remove_all_products_from_list(list);
                    break;

                case 5:
                    remove_products_from_given_category(list);
                    break;

                case 6:
                    remove_one_product(list);
                    break;

                case 7:
                    save_list(list);
                    break;
            }
        }
    }

    public static Vector<String> get_list(String filename)
    {
        Vector<String> list = new Vector<>();

        try
        {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine())
            {
                String data = myReader.nextLine();

                if(data.isBlank())
                    continue;

                list.add(data);
            }

            myReader.close();
        }

        catch (FileNotFoundException e)
        {
            return null;
        }

        return list;
    }

    public static void print_list(Vector<String> list, boolean with_index)
    {
        System.out.println("\n\n--------------- LISTA ---------------");

        for(int i=0; i<list.size(); ++i)
        {
            String temp = list.get(i);

            if(temp.charAt(0) == '-' && i == list.size()-1)
                continue;

            if (i+1 < list.size())
            {
                String next = list.get(i+1);
                if ((temp.charAt(0) == '-' && next.charAt(0) == '-') || next.isBlank())
                    continue;
            }

            if (temp.charAt(0) == '-')
            {
                System.out.println(" ");
                System.out.println(list.get(i));
            }
            else
            {
                if(with_index)
                {
                    int index = i + 1;
                    System.out.println(index + " " + list.get(i));
                }
                else
                    System.out.println(list.get(i));
            }
        }
        System.out.println("\n--------------- LISTA ---------------");
    }

    public static Hashtable<Integer, Vector<String>> get_hash()
    {
        Hashtable<Integer, Vector<String>> table = new Hashtable<>();

        Vector<String> Spozywcze = new Vector<>();
        Spozywcze.add("Jajka");
        Spozywcze.add("Szynka");
        Spozywcze.add("Coca-Cola");

        Vector<String> Chemia = new Vector<>();
        Chemia.add("Płyn do szyb");
        Chemia.add("Kostka toaletowa");
        Chemia.add("Szampon");

        Vector<String> Motoryzacja = new Vector<>();
        Motoryzacja.add("Opony");
        Motoryzacja.add("Uszczelki");
        Motoryzacja.add("Żarówka");

        table.put(1, Spozywcze);
        table.put(2, Chemia);
        table.put(3, Motoryzacja);

        return table;
    }

    public static void print_main_menu()
    {
        System.out.println("\n\n--------------- MENU ---------------");
        System.out.println("0 - Przerwij program bez zapisywania");
        System.out.println("1 - Dodaj produkt do listy");
        System.out.println("2 - Wyswietl cala liste");
        System.out.println("3 - Wyswietl produkty z danej kategorii");
        System.out.println("4 - Usun liste");
        System.out.println("5 - Usun produkty z danej kategorii");
        System.out.println("6 - Usun produkt");
        System.out.println("7 - Zapisz liste");
        System.out.println("--------------- MENU ---------------");
    }

    public static int get_new_number(int min, int max)
    {
        Scanner sc = new Scanner(System.in);
        int input;

        if(sc.hasNextInt())
        {
            input = sc.nextInt();
        }
        else
        {
            System.out.print("Incorrect input! Try again: ");
            return -1;
        }

        if (input < min || input > max)
        {
            System.out.println("Incorrect input! Try again: ");
            return -1;
        }

        return input;
    }

    public static void add_product(Vector<String> list)
    {
        print_categories(list);

        int input = scan_number(1, 3);

        // Print products from category
        Hashtable<Integer, Vector<String>> table = get_hash();
        Vector<String> temp = table.get(input);
        print_list(temp, true);

        // Choose product
        int product_id = scan_number(1, temp.size());
        String choosen = temp.get(product_id - 1);

        insert_product(list, input, choosen);
        System.out.println("\nPomyslnie dodano produkt " + "'" + choosen + "'" + " do listy!" );
    }

    public static int scan_number(int min, int max)
    {
        boolean scan = true;
        int input;

        do
        {
            input = get_new_number(min, max);
            if (input != -1)
                scan = false;
        }
        while(scan);

        return input;
    }

    public static void print_categories(Vector<String> list)
    {
        int index = 1;

        System.out.println("\nDostepne kategorie:");

        for(int i=0; i<list.size(); ++i)
        {
            String temp = list.get(i);
            if (temp.charAt(0) == '-')
            {
                System.out.println(index + " " + list.get(i));
                index++;
            }
        }
    }

    public static void insert_product(Vector<String> list, int input, String choosen)
    {
        int index = 1;
        for(int i=0; i<list.size(); i++)
        {
            String s = list.get(i);
            if (s.charAt(0) == '-')
            {
                if (index == input)
                {
                    list.insertElementAt(choosen, i + 1);
                    break;
                }
                index++;
            }
        }
    }

    public static Vector<Integer> print_products_from_given_category(Vector<String> list)
    {
        Vector<Integer> vec = new Vector<>();
        vec.add(0);
        vec.add(0);

        print_categories(list);
        int input = scan_number(1, 3);

        System.out.println("\nProdukty z wybranej kategorii:");

        int index = 1;
        boolean flag = true;
        int counter = 0;

        for(int i=0; i<list.size() && flag; ++i)
        {
            String temp = list.get(i);

            if (temp.charAt(0) == '-')
            {
                if (index == input)
                {

                    if(temp.charAt(0) == '-' && i == list.size()-1)
                    {
                        System.out.println("brak");
                        vec.set(0, 0);
                        return vec;
                    }

                    if (i+1 < list.size())
                    {
                        String next = list.get(i+1);
                        if ((temp.charAt(0) == '-' && next.charAt(0) == '-') || next.isBlank())
                        {
                            System.out.println("brak");
                            vec.set(0, 0);
                            return vec;
                        }
                    }

                    i++;
                    for(int ile=1; i < list.size(); i++, ile++)
                    {
                        String product = list.get(i);
                        if (product.charAt(0) == '-')
                        {
                            flag = false;
                            break;
                        }
                        System.out.println(ile + " - " + product);
                        counter++;
                    }
                }

                if(flag)
                    index++;
            }
        }

        vec.set(0, counter);
        vec.set(1, input);
        return vec;
    }

    public static void remove_all_products_from_list(Vector<String> list)
    {
        for(int i=0; i<list.size(); ++i)
        {
            String product = list.get(i);
            if(product.charAt(0) != '-')
            {
                list.remove(i);
                --i;
            }
        }

        System.out.println("\nLista zostala wyczyszczona!");
    }

    public static void remove_products_from_given_category(Vector<String> list)
    {
        print_categories(list);
        int input = scan_number(1, 3);
        int index = 0;

        for(int i=0; i<list.size(); ++i)
        {
            String temp = list.get(i);
            if (temp.charAt(0) == '-')
            {
                index++;
                continue;
            }

            if (index == input)
            {
                String product = list.get(i);
                if(product.charAt(0) != '-')
                {
                    list.remove(i);
                    --i;
                }
                else
                    break;
            }
        }

        System.out.println("\nWybrana kategoria zostala wyczyszczona!");
    }

    public static void remove_one_product(Vector<String> list)
    {
        String product = "brak";
        Vector<Integer> vec = print_products_from_given_category(list);

        if(vec.get(0) == 0)
        {
            System.out.println("Podana kategoria jest pusta!");
            return;
        }

        int amount = vec.get(0);
        int input = vec.get(1);

        int number = scan_number(1, amount);

        boolean flag = true;
        int index = 1;

        for(int i=0; i<list.size() && flag; ++i)
        {
            String temp = list.get(i);

            if (temp.charAt(0) == '-')
            {
                if (index == input)
                {
                    i++;
                    int counter = 1;

                    while(i < list.size())
                    {
                        if(counter == number)
                        {
                            product = list.get(i);
                            list.remove(i);
                            flag = false;
                            break;
                        }

                        counter++;
                        i++;
                    }
                }
                index++;
            }
        }
        System.out.println("Pomyslnie usunieto produkt: " + product);
    }

    public static int create_file()
    {
        try
        {
            File myObj = new File("new_list.txt");
            if (myObj.createNewFile())
                System.out.println("File created: " + myObj.getName());
            else
                System.out.println("File already exists. Don't need to create new one!");
            return 1;
        }
        catch (IOException e)
        {
            System.out.println("Failed to create the file!");
            e.printStackTrace();
            return 0;
        }
    }
    public static void save_list(Vector<String> list)
    {
        if(create_file() == 0)
            return;

        try
        {
            FileWriter myWriter = new FileWriter("new_list.txt");

            for(int i=0; i<list.size(); ++i)
            {
                String element = list.get(i);
                myWriter.write(element + "\n");

                if(i+1 < list.size())
                {
                    String next = list.get(i+1);
                    if(next.charAt(0) == '-')
                        myWriter.write("\n");
                }
            }

            myWriter.close();
            System.out.println("List saved successfully!");
        }
        catch (IOException e)
        {
            System.out.println("Failed to write to the file!");
            e.printStackTrace();
        }
    }
}