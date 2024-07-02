import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        int N;
        int counter;
        boolean adder;
        String info;
        String k_s, b_s, x1_s, y1_s;
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        Map <Integer, Point> plane = new HashMap<>();
        Map <String, Integer> toFile = new HashMap<>();
        ArrayList <ArrayList<Integer>> lines = new ArrayList<>();
        ArrayList <Boolean> exist = new ArrayList<>();
        File file = new File("lineInfo.txt");
        try {

            FileWriter writer = new FileWriter(file);
            writer.close();
        }
        catch (IOException e) {
            System.out.println("Произошла ошибка: " + e);
        }


        System.out.print("Введите количество точек: ");
        N = scan.nextInt();
        for(int i = 0; i <N; i++) {
            Point point = new Point(rand.nextInt(5), rand.nextInt(5));
            plane.put(i, point);
        }
        for (Map.Entry<Integer, Point> point: plane.entrySet()) {
            System.out.println("Ключ: " + point.getKey() + "; Координаты: x=" + point.getValue().getX() + ", y=" + point.getValue().getY());
        }
        System.out.println("\n");

        for (int n = 0; n < N; n++) {
            for (int i = n+1; i < N; i++) {
                counter = 2;
                adder = false;
                info = "Прямая проходит через точки: ";
                exist.clear();
                ArrayList<Integer> line = new ArrayList<>();
                line.add(n);
                info += n;
                double x1 = plane.get(n).getX(), y1 = plane.get(n).getY();
                double x2 = plane.get(i).getX(), y2 = plane.get(i).getY();
                x1_s = String.format("%.0f", x1);
                y1_s = String.format("%.0f", y1);
                double k = (y1 - y2) / (x1 - x2);
                double b = -x2 * k + y2;
                if (k == Math.round(k)) k_s = String.format("%.0f", k);
                else k_s = String.format("%.2f", k);
                if (b == Math.round(b)) b_s = String.format("%.0f", b);
                else b_s = String.format("%.2f", b);
                System.out.print("Прямая " + n + "-" + i);
                line.add(i);
                info += "-" + i;
                for (int j = 0; j < N; j++) {
                    if (j == i || j == n) continue;
                    if (x1 == x2 && x1 == plane.get(j).getX() && y1 != y2){
                        System.out.print("-" + j);
                        line.add(j);
                        counter++;
                        info += "-" + j;
                    }
                    else if (y1 == y2 && y1 == plane.get(j).getY() && x1 != x2){
                        System.out.print("-" + j);
                        line.add(j);
                        counter++;
                        info += "-" + j;
                    }
                    else if (k * plane.get(j).getX() + b == plane.get(j).getY()){
                        System.out.print("-" + j);
                        line.add(j);
                        counter++;
                        info += "-" + j;
                    }
                }
                if (x1 == x2) {
                    System.out.print(": x = " + x1_s);
                    info += "; Уравнение прямой: x = " + x1_s + ".";
                }
                else if (y1 == y2) {
                    System.out.print(": y = " + y1_s);
                    info += "; Уравнение прямой: y = " + y1_s + ".";
                }
                else {
                    System.out.print(": y = " + k_s + " * x + " + b_s);
                    info += "; Уравнение прямой: y = " + k_s + " * x + " + b_s + ".";
                }
                if(lines.isEmpty()) {
                    lines.add(line);
                    toFile.put(info, counter);
                }
                else {
                    for(ArrayList<Integer> a_line : lines){
                        exist.clear();
                        for (Integer digit : line){
                            exist.add(a_line.contains(digit));
                        }
                        if (!exist.contains(false) && counter == a_line.size()) {
                            adder = true;
                            break;
                        }
                    }
                    if (adder) System.out.print("\t(повторяется)");
                    else {
                        lines.add(line);
                        toFile.put(info, counter);
                    }
                }
                System.out.println();
            }
        }

        HashMap <String, Integer> orderedToFile = toFile.entrySet().stream().sorted(
                Map.Entry.comparingByValue(
                        Comparator.reverseOrder())
        ).collect(Collectors.toMap(
                Map.Entry::getKey, Map.Entry::getValue,
                (x, y) -> y, LinkedHashMap::new));

        for (Map.Entry<String, Integer> str: orderedToFile.entrySet()) {
            info = str.getKey();
            try {
                FileWriter writer = new FileWriter(file, true);
                writer.write(info + "\n");
                writer.close();
            }
            catch (IOException e) {
                System.out.println("Произошла ошибка: " + e);
            }
        }
    }
}

