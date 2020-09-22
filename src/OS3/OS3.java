package OS3;
import java.util.Scanner;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.*;

public class OS3 {
    // Стек номеров потоков
    static Stack<Integer> stack = new Stack<>();
    // Пул потоков
    static ExecutorService service = Executors.newFixedThreadPool(5);
    // Главный метод
    public static void main(String args[]) {
        int command;
        Scanner input = new Scanner(System.in);
        do {
            menu_main();
            // Проверка ввода переменной выбора
            while (!input.hasNextInt()) {
                System.out.println("Ошибка ввода.");
                System.out.println();
                System.out.print("Введите команду: ");
                input.next();
            }
            command = input.nextInt();
            // Обработка выбора
            switch (command) {
                case 1:
                    System.out.println();
                    stack_filling();
                    main_thread_function();
                    break;
                case 2:
                    System.out.println("Завершение работы.");
                    break;
                default:
                    System.out.println("Ошибка ввода.");
                    System.out.println();
                    break;
            }
        } while (command != 2);
        service.shutdown();
    }

    // Метод заполнения стека номеров
    public static void stack_filling() {
        for (int index = 0; index < 5; index++) {stack.push(index);}
    }

    // Метод отображения меню
    public static void menu_main() {
        System.out.println();
        System.out.println("Меню программы:");
        System.out.println("1. Начать работу потоков.");
        System.out.println("2. Выйти из программы.");
        System.out.println();
        System.out.print("Введите команду: ");
    }

    // Метод отображения обновленного меню
    public static void menu_update() {
        System.out.println();
        System.out.println("Меню программы:");
        System.out.println("1. Повторить работу потоков.");
        System.out.println("2. Повторить и вернуться назад.");
        System.out.println();
        System.out.print("Введите команду: ");
    }

    // Метод запуска потоков
    public static void main_thread_function() {
        // Запуск потоков
        thread_function(false);
        int command;
        Scanner input = new Scanner(System.in);
        do {
            menu_update();
            // Проверка ввода переменной выбора
            while (!input.hasNextInt()) {
                System.out.println("Ошибка ввода.");
                System.out.println();
                System.out.print("Введите команду: ");
                input.next();
            }
            command = input.nextInt();
            // Обработка выбора
            switch (command) {
                case 1:
                    System.out.println();
                    stack_filling();
                    thread_function(false);
                    break;
                case 2:
                    stack_filling();
                    thread_function(true);
                    System.out.println();
                    System.out.println("Возвращение в главное меню.");
                    break;
                default:
                    System.out.println("Ошибка ввода.");
                    System.out.println();
                    break;
            }
        } while (command != 2);
    }

    // Метод выдачи потокам заданий
    public static void thread_function(boolean flag) {
        // Метод задания каждому потоку
        Runnable task = () -> {
            Random random = new Random();
            int thread_number;
            thread_number = stack.pop();
            System.out.println("Поток №" + thread_number + " запущен.");
            int time = random.nextInt((3500 - 1000) + 1) + 1000;
            // Усыпление потока
            try { Thread.sleep(time); } catch (InterruptedException ie) { }
            float sleep_time = (float)time/1000;
            System.out.println("Поток №" + thread_number + " провел в состоянии сна " + sleep_time + " секунд." );
            if (flag) { System.out.println("Поток №" + thread_number + " завершил работу. "); } };
        for (int i = 0; i < 5; i++) { service.submit(task); }
        try { Thread.currentThread().join(3535); } catch (InterruptedException ie) { }
    }
}